package com.developerb.healthcontrol

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
enum HealthLevel {

    /**
     * Everything is a-okay!
     */
    HEALTHY,

    /**
     * It's sorta working, but not as well as it's supposed to.
     * Broken SLA?
     */
    FRAIL,

    /**
     * Not even a little bit..
     * Completely useless
     */
    DEAD

}
