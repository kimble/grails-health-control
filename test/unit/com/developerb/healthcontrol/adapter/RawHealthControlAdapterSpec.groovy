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
