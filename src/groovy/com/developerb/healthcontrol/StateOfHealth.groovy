package com.developerb.healthcontrol

import static com.developerb.healthcontrol.HealthLevel.*

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class StateOfHealth implements Comparable<StateOfHealth> {

    final HealthLevel level;
    final String message;
    final Throwable trouble;
    final Map properties;


    StateOfHealth(HealthLevel level, String message, Throwable trouble) {
        this(level, message, [:]);

        this.trouble = trouble
    }

    StateOfHealth(HealthLevel level, String message, Map properties = [:]) {
        this.message = message ?: 'No message'
        this.properties = properties
        this.level = level
        this.trouble = null
    }

    static healthy(String message, Map properties = [:]) {
        return new StateOfHealth(HEALTHY, message, properties);
    }

    static frail(String message, Map properties = [:]) {
        return new StateOfHealth(FRAIL, message, properties);
    }

    static dead(String message, Map properties = [:]) {
        return new StateOfHealth(DEAD, message, properties);
    }

    void addProperty(String name, Object value) {
        properties[name] = value
    }

    @Override
    int compareTo(StateOfHealth other) {
        return other.level.ordinal() - level.ordinal()
    }

    String toString() {
        return "${level}: ${message}, ${properties}"
    }

}
