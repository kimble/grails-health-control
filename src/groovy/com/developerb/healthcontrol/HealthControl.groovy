package com.developerb.healthcontrol

/**
 * Health control contract. Since v0.4 it's no longer required to implement this
 * interface, but implementations must non the less conform to this contract.
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
interface HealthControl {

    /**
     * @return short and concise name
     */
    String getName()

    /**
     * @return one line description
     */
    String getDescription()

    /**
     * @return how many millis it'll execute before being interrupted
     */
    Long getTimeoutMillis()

    /**
     * It's your responsibility to make sure that implementations of
     * this methods reacts to interrupts!
     *
     * @return health result
     */
    StateOfHealth execute()

}