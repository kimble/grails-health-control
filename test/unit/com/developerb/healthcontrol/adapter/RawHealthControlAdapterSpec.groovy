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
package com.developerb.healthcontrol.adapter

import com.developerb.healthcontrol.StateOfHealth
import grails.plugin.spock.UnitSpec

import static com.developerb.healthcontrol.StateOfHealth.dead
import static com.developerb.healthcontrol.StateOfHealth.healthy

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class RawHealthControlAdapterSpec extends UnitSpec {

    def "health control adapter applied to a compliant class"() {
        given:
        def healthControl = new DangerousHealthControl()
        def adapted = new RawHealthControlAdapter(healthControl)

        when:
        adapted.execute()

        then:
        healthControl.executionCounter == 1

        and:
        adapted.name == "Dangerous"
        adapted.description == "Health control not implementing the interface"
        adapted.timeoutMillis == 100
    }

    def "Health control adapter applied to non-compatible class"() {
        when:
        def healthControl = new NonCompatibleHealthControl()
        def adapted = new RawHealthControlAdapter(healthControl)

        then:
        def ex = thrown(IllegalStateException)
        ex.message == "com.developerb.healthcontrol.adapter.RawHealthControlAdapterSpec\$NonCompatibleHealthControl " +
                      "is not compatible with the HealthControl interface: Missing 'timeoutMillis' property"
    }


    private class DangerousHealthControl {

        String name = "Dangerous"
        String description = "Health control not implementing the interface"

        Long timeoutMillis = 100
        Long executionCounter = 0

        StateOfHealth execute() {
            executionCounter++

            healthy("As a horse!")
        }

    }

    private class NonCompatibleHealthControl {

        String name = "Dangerous"
        String description = "Health control not properly implementing the interface"

        Long timeoutMiiiiillis = 100 // Typo... would be detected early had we implemented the interface

        StateOfHealth execute() {
            dead("As a fish!")
        }

    }

}
