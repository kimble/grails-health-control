class HealthControlGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "2.2 > *"

    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Health Control"
    def author = "Kim A. Betti"
    def authorEmail = "kim@developer-b.com"
    def description = '''\
Automated control of services, resources and other things that might fail from time to time.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/health-control"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "Developer-B", url: "http://www.developer-b.com/" ]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "http://github.com/kimble/grails-health-control/" ]

    def doWithWebDescriptor = { xml -> }
    def doWithSpring = { }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event -> }

}
