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

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public final class JsonParser {

  private JsonParser() {
    // Utility class
  }

  public static Object parseIfJson(String value) {
    value = value.trim();
    if (value.startsWith("{")) {
      return new JsonObject(value);
    } else if (value.startsWith("[")) {
      return new JsonArray(value);
    } else {
      return value;
    }
  }

}
