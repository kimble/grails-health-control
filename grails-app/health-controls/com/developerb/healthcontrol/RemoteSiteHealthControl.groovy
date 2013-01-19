package com.developerb.healthcontrol

import static com.developerb.healthcontrol.StateOfHealth.frail
import static com.developerb.healthcontrol.StateOfHealth.healthy
import static java.lang.System.currentTimeMillis

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class RemoteSiteHealthControl implements HealthControl {

    static final int sla = 500

    def siteUrls = [
            "Google" : "http://www.google.com/",
            "Bing!!" : "http://www.bing.com/"
    ]

    String name = "Remote site"
    String description = "Read remote site content"
    Long timeoutMillis = siteUrls.size() * sla * 2


    @Override
    StateOfHealth execute() {
        Map props = [:]
        int maxLatency = siteUrls.collect { entry ->
            long start = currentTimeMillis()
            new URL(entry.value).text
            long latency = currentTimeMillis() - start
            props[entry.key] = latency + "ms"

            return latency
        }
        .max { millis ->
            return millis
        }

        if (maxLatency > sla) {
            return frail("At least one site breached sla", props)
        }
        else {
            return healthy("All sites fetch within sla", props)
        }
    }

}
