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
import com.developerb.healtcontrol.artefact.HealthControlArtefactHandler
import com.developerb.healthcontrol.HealthControl
import com.developerb.healthcontrol.HealthControlRepository
import com.developerb.healthcontrol.adapter.RawHealthControlAdapterFactory

class HealthControlGrailsPlugin {

    def version = "0.4"
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
        healthControlAdapterFactory(RawHealthControlAdapterFactory)
        healthControlRepository(HealthControlRepository) { it.autowire = true }

        Closure configureBean = (Closure) configureHealthControlBeans.clone()
        configureBean.delegate = delegate

        application.healthControlClasses.each { healthControlClass ->
            configureBean.call(healthControlClass)
        }
    }

    def onChange = { event ->
        if (application.isHealthControlClass(event.source)) {
            def healthControlClass = application.addArtefact(HealthControlArtefactHandler.TYPE, event.source)
            def configureBean = configureHealthControlBeans.clone()

            def beanDefinitions = beans {
                configureBean.delegate = delegate
                configureBean.call(healthControlClass)
            }

            // now that we have a BeanBuilder calling registerBeans and passing the app ctx will
            // register the necessary beans with the given app ctx
            beanDefinitions.registerBeans(event.ctx)
        }
    }

    def configureHealthControlBeans = { grailsClass ->
        log.info "Registering health control: ${grailsClass.fullName}"

        def healthControlBeanName = "${grailsClass.shortName}_HealthControl"
        def unadaptedBeanName = "${grailsClass.shortName}_UnAdapted"

        if (!HealthControl.isAssignableFrom(grailsClass.clazz)) {
            "$unadaptedBeanName"(grailsClass.clazz) { bean ->
                bean.autowire = true
            }

            "$healthControlBeanName"(healthControlAdapterFactory: "adapt", ref(unadaptedBeanName))
        }
        else {
            "$healthControlBeanName"(grailsClass.clazz) { bean ->
                bean.autowire = true
            }
        }
    }

}
