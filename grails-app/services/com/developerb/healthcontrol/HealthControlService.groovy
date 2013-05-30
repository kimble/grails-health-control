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

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

import static com.developerb.healthcontrol.HealthLevel.DEAD
import static java.lang.System.currentTimeMillis
import static java.util.concurrent.Executors.newSingleThreadExecutor
import static java.util.concurrent.TimeUnit.MILLISECONDS

class HealthControlService {

    static transactional = false

    def healthControlRepository


    def reportAll() {
        return healthControlRepository.healthControls.collect { HealthControl hc ->
            reportHealthWithTiming(hc)
        }
    }

    boolean hasAtLeastOneHealthControl() {
        return healthControlRepository.healthControls.length > 0
    }

    HealthReport reportHealthWithTiming(HealthControl control) {
        def start = currentTimeMillis()
        def health = reportHealth(control)

        health.addProperty("Verification time", (currentTimeMillis() - start) + " ms.")
        return health
    }

    /**
     * Todo: Is there a better / more efficient way of ensuring that task isn't outliving its timeout?
     */
    HealthReport reportHealth(HealthControl control) {
        def task = new Task(control)
        def executor = newSingleThreadExecutor()

        try {
            def pending = executor.submit(task)
            def state = pending.get(control.timeoutMillis, MILLISECONDS)
            return new HealthReport(control, state)
        }
        catch (TimeoutException ex) {
            def state = new StateOfHealth(DEAD, "Timeout: Maximum execution time of ${control.timeoutMillis}ms exceeded", ex)
            return new HealthReport(control, state)
        }
        catch (ExecutionException ex) {
            def cause = ex.cause
            def state = new StateOfHealth(DEAD, "Exception: ${cause.message}", cause)
            return new HealthReport(control, state)
        }
        catch (Throwable trouble) {
            def state = new StateOfHealth(DEAD, "Exception: ${trouble.message}", trouble)
            return new HealthReport(control, state)
        }
        finally {
            executor.shutdownNow()
            if (!executor.isTerminated()) {
                log.warn("Executor not terminated after health control! Make sure that your health controls respond to interrupts!")
            }
        }
    }


    static class Task implements Callable<StateOfHealth> {
        private final HealthControl control

        Task(HealthControl control) {
            this.control = control
        }

        StateOfHealth call() throws Exception {
            def health = control.execute()

            if (health) {
                return health
            }
            else {
                def ex = new IllegalStateException("${control.name} returned null!")
                return new StateOfHealth(ex)
            }
        }
    }

}
