package com.developerb.healthcontrol

import static com.developerb.healthcontrol.HealthLevel.DEAD
import static java.lang.System.currentTimeMillis
import static java.util.concurrent.TimeUnit.MILLISECONDS

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeoutException

import org.springframework.beans.factory.DisposableBean

class HealthControlService implements DisposableBean {

    static transactional = false

    def healthControlRepository
    def executor = Executors.newSingleThreadExecutor()

    def reportAll() {
        return healthControlRepository.healthControls.collect { HealthControl hc ->
            reportHealthWithTiming(hc)
        }
    }

    HealthReport reportHealthWithTiming(HealthControl control) {
        def start = currentTimeMillis()
        def health = reportHealth(control)

        health.addProperty("Verification time", (currentTimeMillis() - start) + " ms.")
        return health
    }

    HealthReport reportHealth(HealthControl control) {
        def task = new Task(control)
        def pending = executor.submit(task)

        try {
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

    void destroy() throws Exception {
        log.info "Destroying health control executor"
        executor.shutdownNow()
    }

}
