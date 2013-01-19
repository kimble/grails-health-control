package com.developerb.healthcontrol

import spock.lang.Specification

import static com.developerb.healthcontrol.StateOfHealth.frail
import static com.developerb.healthcontrol.StateOfHealth.healthy

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
