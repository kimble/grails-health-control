package com.developerb.healthcontrol

import static com.developerb.healthcontrol.StateOfHealth.frail

/**
 * Note that this isn't explicitly implementing the HealthControl interface
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class ExperimentalHealthControl {

    String name = "Experimental!"
    String description = "An experimental health control not implementing the HealthControl interface"
    Long timeoutMillis = 50

    StateOfHealth execute() {
        frail("I'm a bit frail...")
    }

}
