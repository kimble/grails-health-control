/*
 * Copyright 2013 Grails Community
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
package com.developerb.healthcontrol

import static com.developerb.healthcontrol.StateOfHealth.dead
import static com.developerb.healthcontrol.StateOfHealth.healthy

class ArithmeticHealthControl implements HealthControl {

    String name = "Arithmetic"
    String description = "Basic arithmetic health controls"
    Long timeoutMillis = 50

    StateOfHealth execute() {
        if (2 + 2 == 4) {
            healthy("Everything is alright with the universe")
        }
        else {
            dead("Oh my, arithmetic is broken!")
        }
    }

}