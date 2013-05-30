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
package com.developerb.healthcontrol.adapter

import com.developerb.healthcontrol.HealthControl
import com.developerb.healthcontrol.StateOfHealth

/**
 * Provides support for health controls not implementing the HealthControl interface.
 * Should make it possible to plugins to provide health controls without declaring
 * a compile time dependency to this plugin.
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class RawHealthControlAdapter implements HealthControl {

    private final underlyingHealthControl

    RawHealthControlAdapter(Object underlyingHealthControl) {
        if (!underlyingHealthControl.hasProperty("name")) {
            notCompatibleBecauseOf(underlyingHealthControl, "Missing 'name' property")
        }
        if (!underlyingHealthControl.hasProperty("description")) {
            notCompatibleBecauseOf(underlyingHealthControl, "Missing 'description' property")
        }
        if (!underlyingHealthControl.hasProperty("timeoutMillis")) {
            notCompatibleBecauseOf(underlyingHealthControl, "Missing 'timeoutMillis' property")
        }
        if (!underlyingHealthControl.respondsTo("execute")) {
            notCompatibleBecauseOf(underlyingHealthControl, "Missing 'execute' method")
        }

        this.underlyingHealthControl = underlyingHealthControl
    }

    void notCompatibleBecauseOf(Object implementation, String reason) throws IllegalStateException {
        throw new IllegalStateException("${implementation.class.name} is not compatible with the HealthControl interface: $reason")
    }

    @Override
    String getName() {
        underlyingHealthControl.name
    }

    @Override
    String getDescription() {
        underlyingHealthControl.description
    }

    @Override
    Long getTimeoutMillis() {
        underlyingHealthControl.timeoutMillis
    }

    @Override
    StateOfHealth execute() {
        underlyingHealthControl.execute()
    }

}
