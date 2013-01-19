package com.developerb.healthcontrol

import org.codehaus.groovy.grails.commons.GrailsApplication

class HealthControlController {

    GrailsApplication grailsApplication
    HealthControlService healthControlService


    def index() {
        [
            healthReports: healthControlService.reportAll(),
            applicationName : grailsApplication.metadata.get("app.name")
        ]
    }

}
