package com.developerb.healthcontrol

import spock.lang.Specification

import java.util.concurrent.TimeoutException

import static com.developerb.healthcontrol.HealthLevel.DEAD
import static com.developerb.healthcontrol.HealthLevel.FRAIL
import static com.developerb.healthcontrol.HealthLevel.HEALTHY
import static com.developerb.healthcontrol.StateOfHealth.frail
import static com.developerb.healthcontrol.StateOfHealth.healthy

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class HealthControlServiceSpec extends Specification {

    def service = new HealthControlService()

    def "Health control executing longer then configured timeout"() {
        when:
        def report = service.reportHealth(new SleepyHealthControl())

        then:
        report.stateOfHealth.message == "Timeout: Maximum execution time of 50ms exceeded"
        report.stateOfHealth.trouble instanceof TimeoutException
    }

    def "Health control throwing exception"() {
        when:
        def report = service.reportHealth(new ExceptionThrowingHealthControl())

        then:
        report.stateOfHealth.trouble instanceof IllegalStateException
        report.stateOfHealth.message == "Exception: Uuups.."
    }

    def "Three different outcomes"() {
        given:
        service.healthControlRepository = [
                healthControls : [
                        new SleepyHealthControl(),
                        new ExceptionThrowingHealthControl(),
                        new AlrightHealthControl(),
                        new FrailHealthControl()
                ]
        ]

        when:
        def reports = service.reportAll()

        then:
        reports[0].stateOfHealth.level == DEAD
        reports[0].stateOfHealth.message == "Timeout: Maximum execution time of 50ms exceeded"

        and:
        reports[1].stateOfHealth.level == DEAD
        reports[1].stateOfHealth.message == "Exception: Uuups.."

        and:
        reports[2].stateOfHealth.level == HEALTHY
        reports[2].stateOfHealth.message == "I'm always healthy!"

        and:
        reports[3].stateOfHealth.level == FRAIL
        reports[3].stateOfHealth.message == "I'm always frail :-/"
    }


    class SleepyHealthControl implements HealthControl {

        String name = "Sleepy health control"
        String description = "Sleeps for 100ms"
        Long timeoutMillis = 50

        @Override
        StateOfHealth execute() {
            Thread.sleep(100)
            throw new IllegalStateException("Should have timed out before this exception was thrown")
        }

    }

    class ExceptionThrowingHealthControl implements HealthControl {

        String name = "Exception throwing health control"
        String description = "Throws an unchecked exception"
        Long timeoutMillis = 50

        @Override
        StateOfHealth execute() {
            throw new IllegalStateException("Uuups..")
        }

    }

    class AlrightHealthControl implements HealthControl {

        String name = "Everything alright control"
        String description = "Always healthy"
        Long timeoutMillis = 50

        @Override
        StateOfHealth execute() {
            healthy("I'm always healthy!")
        }

    }

    class FrailHealthControl implements HealthControl {

        String name = "Frail health control"
        String description = "Always frail"
        Long timeoutMillis = 50

        @Override
        StateOfHealth execute() {
            frail("I'm always frail :-/")
        }

    }

}
