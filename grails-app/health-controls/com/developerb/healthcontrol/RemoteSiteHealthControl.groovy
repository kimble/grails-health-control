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
            "Bing" : "http://www.bing.com/"
    ]

    String name = "Remote site"
    String description = "Read remote site content"
    Long timeoutMillis = siteUrls.size() * sla * 2

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
