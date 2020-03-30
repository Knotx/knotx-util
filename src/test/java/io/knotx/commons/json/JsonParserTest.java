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
package io.knotx.commons.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonParserTest {

  private static final String SAMPLE_STRING = "Some-text with, letters and signs!";
  private static final JsonObject SAMPLE_JSON_OBJECT = new JsonObject()
      .put("user", "abner24")
      .put("data", new JsonObject()
          .put("login", "22-21")
          .put("actions", new JsonArray()
              .add("search")
              .add("visit")));
  private static final JsonArray SAMPLE_JSON_ARRAY = new JsonArray()
      .add(23)
      .add("string")
      .add(new JsonObject()
          .put("nested", "value"));

  @Test
  @DisplayName("Expect empty string to be left as-is")
  void emptyString() {
    Object response = JsonParser.parseIfJson("");

    assertTrue(response instanceof String);
    assertEquals("", response);
  }

  @Test
  @DisplayName("Expect sample string to be left as-is")
  void sampleString() {
    Object response = JsonParser.parseIfJson(SAMPLE_STRING);

    assertTrue(response instanceof String);
    assertEquals(SAMPLE_STRING, response);
  }

  @Test
  @DisplayName("Expect JsonObject to be reconstructed")
  void sampleJsonObject() {
    Object response = JsonParser.parseIfJson(SAMPLE_JSON_OBJECT.toString());

    assertTrue(response instanceof JsonObject);
    assertEquals(SAMPLE_JSON_OBJECT, response);
  }

  @Test
  @DisplayName("Expect JsonArray to be reconstructed")
  void sampleJsonArray() {
    Object response = JsonParser.parseIfJson(SAMPLE_JSON_ARRAY.toString());

    assertTrue(response instanceof JsonArray);
    assertEquals(SAMPLE_JSON_ARRAY, response);
  }

  @Test
  @DisplayName("Expect invalid JsonObject to yield an exception")
  void invalidJsonObject() {
    assertThrows(DecodeException.class, () -> JsonParser.parseIfJson("{this is an invalid JsonObject}"));
  }

  @Test
  @DisplayName("Expect invalid JsonObject to yield an exception")
  void invalidJsonArray() {
    assertThrows(DecodeException.class, () -> JsonParser.parseIfJson("{this is an invalid JsonArray}"));
  }

}
