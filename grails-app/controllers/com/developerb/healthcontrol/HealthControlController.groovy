package com.developerb.healthcontrol

import org.codehaus.groovy.grails.commons.GrailsApplication

import static java.util.UUID.randomUUID

/**
 *
 */
class HealthControlController {

    static scope = "singleton"

    GrailsApplication grailsApplication
    HealthControlService healthControlService


    def beforeInterceptor = {
        def providedSecret = request.getParameter("secret")
        if (providedSecret != grailsApplication.config.healthControl.secret) {
            response.status = 401
            return false
        }
    }


    def index() {
        [
            healthReports: healthControlService.reportAll(),
            applicationName : grailsApplication.metadata.get("app.name")
        ]
    }

}
