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

import static com.developerb.healthcontrol.StateOfHealth.frail

/**
 * Note that this isn't explicitly implementing the HealthControl interface
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class ExperimentalHealthControl {

    String name = "Experimental!"
    String description = "An experimental health control not implementing the HealthControl interface"
    Long timeoutMillis = 50

    StateOfHealth execute() {
        frail("I'm a bit frail...")
    }

}
