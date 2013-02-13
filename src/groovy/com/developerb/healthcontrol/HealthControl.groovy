package com.developerb.healthcontrol

/**
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
     * @return how many millis it'll execute before being killed
     */
    Long getTimeoutMillis()

    /**
     * @return health result
     */
    StateOfHealth execute()

}