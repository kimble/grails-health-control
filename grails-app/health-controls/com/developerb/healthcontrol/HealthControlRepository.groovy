package com.developerb.healthcontrol

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class HealthControlRepository {

    def grailsApplication

    HealthControl[] getHealthControls() {
        return grailsApplication.mainContext.getBeansOfType(HealthControl).values()
    }

}
