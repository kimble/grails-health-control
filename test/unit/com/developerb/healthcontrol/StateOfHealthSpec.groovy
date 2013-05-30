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
import static com.developerb.healthcontrol.StateOfHealth.healthy
import spock.lang.Specification

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class StateOfHealthSpec extends Specification {

    def "ordering"() {
        given:
        def states = [
            healthy("I'm healthy"),
            frail("I'm frail")
        ]

        when:
        def ordered = states.sort()

        then:
        ordered[0].message == "I'm frail"
        ordered[1].message == "I'm healthy"
    }

}
