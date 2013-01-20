package com.developerb.healthcontrol

import org.springframework.beans.factory.DisposableBean

import java.util.concurrent.Callable
import java.util.concurrent.Executors

import static java.lang.System.currentTimeMillis
import static java.util.concurrent.TimeUnit.MILLISECONDS

class HealthControlService implements DisposableBean {

    static transactional = false

    def grailsApplication
    def executor = Executors.newSingleThreadExecutor()

    def reportAll() {
        return healthControls.collect { HealthControl hc ->
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
        catch (Throwable trouble) {
            def state = new StateOfHealth(trouble)
            return new HealthReport(control, state)
        }
    }


    static class Task implements Callable<StateOfHealth> {
        private final HealthControl control;

        Task(HealthControl control) {
            this.control = control
        }

        @Override
        StateOfHealth call() throws Exception {
            def health = control.execute()

            if (health) {
                return health;
            }
            else {
                def ex = new IllegalStateException("${control.name} returned null!")
                return new StateOfHealth(ex)
            }
        }
    }


    HealthControl[] getHealthControls() {
        grailsApplication.mainContext.getBeansOfType(HealthControl).values()
    }

    @Override
    void destroy() throws Exception {
        log.info "Destroying health control executor"
        executor.shutdownNow()
    }

}
