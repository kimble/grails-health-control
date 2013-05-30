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

import groovy.transform.EqualsAndHashCode

/**
 * Wraps the outcome from a health control and adds meta data about
 * the check that resulted in the wrapped StateOfHealth.
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
@EqualsAndHashCode
class HealthReport implements Comparable<HealthReport> {

    final String type, name, description
    final StateOfHealth stateOfHealth

    protected HealthReport(HealthControl control, StateOfHealth stateOfHealth) {
        assert control, "Health control parameter is mandatory"
        assert stateOfHealth, "State of health parameter is mandatory"

        this.name = control.name
        this.type = control.class.simpleName
        this.description = control.description
        this.stateOfHealth = stateOfHealth
    }

    void addProperty(String key, Object value) {
        stateOfHealth.addProperty(key, value)
    }

    HealthLevel getLevel() {
        stateOfHealth.level
    }

    int compareTo(HealthReport other) {
        def diff = stateOfHealth.compareTo(other.stateOfHealth)

        if (diff == 0) {
            diff = name.compareToIgnoreCase(other.name)
        }

        return diff
    }

}
