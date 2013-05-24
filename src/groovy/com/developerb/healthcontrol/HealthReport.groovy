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
