package com.developerb.healthcontrol

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class ExperimentalHealthControl implements HealthControl {

    String name = "Experimental"
    String description = "Just experimenting"
    Long timeoutMillis = 50

    @Override
    StateOfHealth execute() {
        return StateOfHealth.frail("I'm a bit frail...")
    }

}
