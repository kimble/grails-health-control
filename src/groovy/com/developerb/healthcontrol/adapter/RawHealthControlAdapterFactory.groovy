package com.developerb.healthcontrol.adapter

/**
 * Used by the Spring configuration (is there a better option..?)
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class RawHealthControlAdapterFactory {

    RawHealthControlAdapter adapt(Object unadapted) {
        new RawHealthControlAdapter(unadapted)
    }

}
