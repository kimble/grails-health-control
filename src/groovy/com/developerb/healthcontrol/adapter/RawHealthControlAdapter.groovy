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
