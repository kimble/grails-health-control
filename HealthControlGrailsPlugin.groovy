import com.developerb.healtcontrol.artefact.HealthControlArtefactHandler
import com.developerb.healthcontrol.HealthControl

class HealthControlGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "2.0 > *"

    def pluginExcludes = [
        "grails-app/health-controls/**",
    ]

    def title = "Health Control"
    def author = "Kim A. Betti"
    def authorEmail = "kim@developer-b.com"
    def description = 'Automated control of services, resources and other things that might fail from time to time.'

    def watchedResources = "file:./grails-app/health-controls/**/*HealthControl.groovy"
    def artefacts = [ HealthControlArtefactHandler ]

    def documentation = "http://grails.org/plugin/health-control"
    def license = "APACHE"

    def organization = [ name: "Developer-B", url: "http://www.developer-b.com/" ]
    def issueManagement = [ system: "GitHub", url: "http://github.com/kimble/grails-health-control/issues" ]
    def scm = [ url: "http://github.com/kimble/grails-health-control/" ]

    def doWithSpring = {
        // Configure realms defined in the project.
        def healthControlBeans = []
        application.healthControlClasses.each { healthControlClass ->
            log.info "Registering health control: ${healthControlClass.fullName}"

            configureHealthControl.delegate = delegate
            healthControlBeans << configureHealthControl(healthControlClass)
        }
    }

    def configureHealthControl = { grailsClass ->
        def beanName = grailsClass.shortName + "Instance"

        // Should implement interface
        if (!HealthControl.isAssignableFrom(grailsClass.clazz)) {
            throw new IllegalStateException("${grailsClass} should implement the HealthControl interface")
        }

        // Create the health control bean.
        "${beanName}"(grailsClass.clazz) { bean ->
            bean.autowire = true
        }

        return beanName
    }

    def onChange = { event ->
        if (application.isHealthControlClass(event.source)) {
            def healthControlClass = application.addArtefact(HealthControlArtefactHandler.TYPE, event.source)
            def beanDefinitions = beans {
                "${healthControlClass.shortName}Instance"(healthControlClass.clazz) { bean ->
                    bean.autowire = true
                }
            }
            // now that we have a BeanBuilder calling registerBeans and passing the app ctx will
            // register the necessary beans with the given app ctx
            beanDefinitions.registerBeans(event.ctx)
        }
    }

    def onConfigChange = { event ->
        // ...
    }

}
