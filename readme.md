Grails Health Control
=====================

Health control artefacts for Grails. Implement simple checks to verify the sanity of databases, remote services and other fragile things. It's all about monitoring and verifying as many assumptions as possible before they bite you in the ass.. The results will show up on a simple dashboard.

The whole thing is heavily inspired by the awesome Metrics library by Yammer: http://metrics.codahale.com/

![Screenshot](https://github.com/kimble/grails-health-control/raw/master/docs/screenshots/dashboard.png)


Access control 
--------------

The plugin supports simple access restriction out of the box. This can be configured by adding the following snippet to your configuration. The health control can then be accessed by adding `?secret=xxx` to the url. Anything more fancy / secure then this is way outside the scope of this plugin. Have a look at Spring Security or Shiro if you feel that you need more security. 

    healthControl {
        secret = "my-secret"
    }


The main (only) concept
-----------------------

Automated "health control" is the only concept in the plugin. Each implementation has to implement the `HealthControl` interface, the name has to end with _HealthControl_ and be placed in a `health-controls` folder under `grails-app`.

Each implementation must be given a short and concise, single line name, description + a configured timeout in milliseconds. The `execute` method have to return a `StateOfHealth` instance.

The health of whatever is being monitored can be "healthy", "frail" or "dead".


Http status codes
-----------------

The endpoint will return with a http `200 Ok` response if all checks are okay or fragile, `501 Not Implemented` if no health controls are implemented and finally `500 Internal Service Error` if one or more health controls fails or returns the `dead` code.


Content negotiation - Alternative data formats
----------------------------------------------

Provided that you've configured Grails with mime types for content type negotiation you can (in theory) request json and xml in addition to html by passing the appropriate `Accept` header with your request. Although, in practice the content negotiation support in Grails seems to be fairly fragile / broken when it comes to dealing with the accept header so you might have to add .json to the request path like `../your/path/healthControl.json?secret=...`.

See: http://grails.org/doc/latest/guide/single.html#contentNegotiation


Health Control ideas
--------------------

Feel free to add your idea to the list or send a pull request with an implementation!

* Write / read to database, no-sql or old fashioned
* Ping external soap / rest services (SLAs..)
* Check external service usage quotas
* Available disk space
* Verify that storage folders can be written to
* Jvm thread deadlocks


Pro tip: Free monitoring with Google App Script
------------------------------------------------

It's pretty straight forward to write a Google App Script using Google App Script that periodically polls the dashboard and notifies you via email, or even phone using a service like Twilio when the Health Control Dashboard page returns a non 200 status code.

https://developers.google.com/apps-script/
http://www.twilio.com/


Example
-------

All your health control implementations goes into `grails-app/health-controls/com/company/.../xxxHealthControl.groovy`.

    import com.developerb.healthcontrol.HealthControl
    import com.developerb.healthcontrol.StateOfHealth

    import static com.developerb.healthcontrol.StateOfHealth.*

    class ArithmeticHealthControl implements HealthControl {

        String name = "Arithmetic"
        String description = "Basic arithmetic health controls"
        Long timeoutMillis = 50

        StateOfHealth execute() {
            if (2 + 2 == 4) {
                healthy("Everything is alright with the universe")
            }
            else if (2 + 2 > 3 && 2 + 2 < 5) {
                frail("Our integers are confused...")
            }
            else {
                dead("Oh my, arithmetic is broken!")
            }
        }

    }


Roadmap, bugs and things to do..
--------------------------------

* "Timeout bug" - I've seen a strange bug related to timeouts that I've not been able to reproduce
* Add some common / sample health control implementations either via abstract classes or as templates installable with a script.
