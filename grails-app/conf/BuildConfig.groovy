grails.project.work.dir = "target"

grails.project.dependency.resolution = {

    inherits("global") {
        excludes 'ehcache'
    }
    log "warn"

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0", {
            export = false
        }
    }

    plugins {
        build (":release:2.2.0", ":rest-client-builder:1.0.3", ":tomcat:$grailsVersion") {
            export = false
        }

        compile ":twitter-bootstrap:2.2.2"
        runtime ":resources:1.2.RC2", ":jquery:1.8.3"

        test(":spock:0.7") {
            exclude "spock-grails-support"
            export = false
        }
    }
}
