package com.developerb.healthcontrol

import static com.developerb.healthcontrol.StateOfHealth.frail

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
        frail("I'm a bit frail...")
    }

}
