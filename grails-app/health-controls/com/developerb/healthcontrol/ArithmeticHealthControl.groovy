package com.developerb.healthcontrol

import static com.developerb.healthcontrol.StateOfHealth.dead
import static com.developerb.healthcontrol.StateOfHealth.healthy

class ArithmeticHealthControl implements HealthControl {

    String name = "Arithmetic"
    String description = "Basic arithmetic health controls"
    Long timeoutMillis = 50

    StateOfHealth execute() {
        if (2 + 2 == 4) {
            healthy("Everything is alright with the universe")
        }
        else {
            dead("Oh my, arithmetic is broken!")
        }
    }

}