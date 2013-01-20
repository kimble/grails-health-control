Grails Health Control
=====================

Health control artefacts for Grails. Implement simple checks to verify the sanity of databases, remote services and other fragile things. The results will show up on a simple dashboard.



Access control 
--------------

The plugin supports simple access restriction out of the box. This can be configured by adding the following snippet to your configuration. The health control can then be accessed by adding `?secret=xxx` to the url. Anything more fancy / secure then this is way outside the scope of this plugin. Have a look at Spring Security or Shiro if you feel that you need more security. 

    healthControl {
        secret = "my-secret"
    }

Things to do..
--------------

* Appropriate http response codes
* Json / content negotiation
