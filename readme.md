Grails Health Control
=====================

Health control artefacts for Grails. Implement simple checks to verify the sanity of databases, remote services and other fragile things. The results will show up on a simple dashboard.



Access control 
--------------

The plugin supports simple access restriction out of the box. This can be configured by adding the following snippet to your configuration. The health control can then be accessed by adding `?secret=xxx` to the url. Anything more fancy / secure then this is way outside the scope of this plugin. Have a look at Spring Security or Shiro if you feel that you need more security. 

    healthControl {
        secret = "my-secret"
    }


Status codes
------------

The endpoint will return with a http `200 Ok` response if all checks are okay or fragile, `501 Not Implemented` if no health controls are implemented and finally `500 Internal Service Error` if one or more health controls fails or returns the `dead` code.


Content negotiation - Alternative data formats
----------------------------------------------

Provided that you've configured Grails with mime types for content type negotiation you can request json and xml in addition to html by passing the appropriate `Accept` header with your request. Although the content negotiation support in Grails seems to be fairly fragile / broken when it comes to dealing with the accept header so you might have to add .json to the request path like `../your/path/healthControl.json?secret=...`.

See: http://grails.org/doc/latest/guide/single.html#contentNegotiation


Things to do..
--------------

* "Timeout bug"

