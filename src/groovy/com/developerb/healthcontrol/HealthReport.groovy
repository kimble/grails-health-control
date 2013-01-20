package com.developerb.healthcontrol

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class HealthReport implements Comparable<HealthReport> {

    final String type, name, description;
    final StateOfHealth stateOfHealth;

    protected HealthReport(HealthControl control, StateOfHealth stateOfHealth) {
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

    @Override
    int compareTo(HealthReport other) {
        def diff = stateOfHealth.compareTo(other.stateOfHealth)

        if (diff == 0) {
            diff = name.compareToIgnoreCase(other.name)
        }

        return diff
    }

}
