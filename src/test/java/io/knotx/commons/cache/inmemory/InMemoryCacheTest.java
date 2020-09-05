/*
 * Copyright (C) 2019 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.commons.cache.inmemory;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.knotx.junit5.KnotxExtension;
import io.reactivex.Observable;
import io.vertx.junit5.VertxTestContext;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(KnotxExtension.class)
class InMemoryCacheTest {

  private static final String KEY = "key";
  private static final String VALUE = "value";

  @Test
  @DisplayName("Expect eviction after write when ttl set")
  void expectEvictionAfterTtl(VertxTestContext testContext) throws InterruptedException {
    InMemoryCache tested = new InMemoryCache(1000, 150);
    tested.put(KEY, VALUE);
    TimeUnit.MILLISECONDS.sleep(150);
    tested.get(KEY)
        .doOnComplete(testContext::completeNow)
        .subscribe(value -> testContext
                .failNow(new RuntimeException("Expected completion but got: " + value)),
            testContext::failNow);
  }

  @Test
  @DisplayName("Expect eviction when more elements put than max size")
  void expectEvictionAfterMaximumSizeReached(VertxTestContext testContext) {
    final int MAX_SIZE = 30;
    InMemoryCache tested = new InMemoryCache(MAX_SIZE, 60000);

    List<Integer> elementsPut = IntStream.rangeClosed(1, 100).boxed().collect(toList());
    elementsPut.forEach(i -> tested.put(KEY + i, VALUE));

    AtomicInteger stillPresent = new AtomicInteger();
    Observable.fromIterable(elementsPut)
        .flatMap(i -> tested.get(KEY + i).toObservable())
        .doOnNext(value -> stillPresent.getAndIncrement())
        .doOnComplete(() -> testContext.verify(() -> {
          int presentAtCompletion = stillPresent.get();
          assertTrue(presentAtCompletion <= MAX_SIZE, String
              .format("Expected still present [%s] to be less than or equal to maximumSize [%s]",
                  presentAtCompletion, MAX_SIZE));
          testContext.completeNow();
        }))
        .subscribe();
  }

  @Test
  @DisplayName("Expect null value not accepted by cache")
  void expectNullValueNotAcceptedByCache() {
    InMemoryCache tested = new InMemoryCache(1000, 5000);

    assertThrows(NullPointerException.class, () -> tested.put(KEY, null));
  }

}
