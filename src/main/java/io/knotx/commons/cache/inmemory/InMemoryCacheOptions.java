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

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import java.util.Objects;

@DataObject(generateConverter = true)
public class InMemoryCacheOptions {

  private long ttl = 5000L;
  private long maximumSize = 1000L;

  public InMemoryCacheOptions(JsonObject json) {
    InMemoryCacheOptionsConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    InMemoryCacheOptionsConverter.toJson(this, output);
    return output;
  }

  public long getTtl() {
    return ttl;
  }

  /**
   * Sets TTL after write in ms. Defaults to 5000ms.
   *
   * @param ttl TTL after write in millis
   * @return a reference to this so that API can be used fluently
   */
  public InMemoryCacheOptions setTtl(long ttl) {
    this.ttl = ttl;
    return this;
  }

  public long getMaximumSize() {
    return maximumSize;
  }

  /**
   * Sets maximum cache size (number of entries). Defaults to 1000.
   *
   * @param maximumSize maximum size in entries
   * @return a reference to this so that API can be used fluently
   */
  public InMemoryCacheOptions setMaximumSize(long maximumSize) {
    this.maximumSize = maximumSize;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InMemoryCacheOptions that = (InMemoryCacheOptions) o;
    return ttl == that.ttl &&
        maximumSize == that.maximumSize;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ttl, maximumSize);
  }

  @Override
  public String toString() {
    return "InMemoryCacheOptions{" +
        "ttlMs=" + ttl +
        ", maximumSize=" + maximumSize +
        '}';
  }
}
