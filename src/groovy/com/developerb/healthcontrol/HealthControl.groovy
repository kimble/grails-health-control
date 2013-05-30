/*
 * Copyright 2013 Grails Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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