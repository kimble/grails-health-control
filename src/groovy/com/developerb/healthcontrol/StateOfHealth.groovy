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

import static com.developerb.healthcontrol.HealthLevel.*

/**
 * Carries information about the outcome of a 'health control'.
 * It can be health, frail or dead.
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
@EqualsAndHashCode
class StateOfHealth implements Comparable<StateOfHealth> {

    final HealthLevel level
    final String message
    final Throwable trouble
    final Map properties

    /**
     * Will create a 'dead' state of health with the message from the passed exception.
     *
     * @param trouble exception that has occurred during health control
     */
    StateOfHealth(Throwable trouble) {
        this(DEAD, "Exception: ${trouble?.message}", trouble)
    }

    StateOfHealth(HealthLevel level, String message, Throwable trouble) {
        this(level, message, [:])

        this.trouble = trouble
    }

    StateOfHealth(HealthLevel level, String message, Map properties = [:]) {
        assert level, "Can't construct a state of health without a level"

        this.message = message ?: 'No message'
        this.properties = properties
        this.level = level
        this.trouble = null
    }

    static healthy(String message, Map properties = [:]) {
        return new StateOfHealth(HEALTHY, message, properties)
    }

    static frail(String message, Map properties = [:]) {
        return new StateOfHealth(FRAIL, message, properties)
    }

    static dead(String message, Map properties = [:]) {
        return new StateOfHealth(DEAD, message, properties)
    }

    void addProperty(String name, Object value) {
        properties[name] = value
    }

    int compareTo(StateOfHealth other) {
        return other.level.ordinal() - level.ordinal()
    }

    String toString() {
        return "${level}: ${message}, ${properties}"
    }

}
