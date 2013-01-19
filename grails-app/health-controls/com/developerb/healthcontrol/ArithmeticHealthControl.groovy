package com.developerb.healthcontrol

import static com.developerb.healthcontrol.StateOfHealth.dead
import static com.developerb.healthcontrol.StateOfHealth.healthy

class ArithmeticHealthControl implements HealthControl {

    String name = "Arithmetic"
    String description = "Basic arithmetic health controls"
    Long timeoutMillis = 50

    @Override
    StateOfHealth execute() {
        if (2 + 2 == 4) {
            return healthy("Everything is alright with the universe")
        }
        else {
            return dead("Oh my, arithmetic is broken!")
        }
    }

}