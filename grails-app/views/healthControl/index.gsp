<%@ page import="com.developerb.healthcontrol.HealthLevel" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Health Control - ${applicationName}</title>
    <r:require modules="bootstrap"/>
    <r:require modules="fontAwesome"/>

    <r:layoutResources/>

    <style type="text/css">
    body {
        padding-top: 20px;
        padding-bottom: 40px;
    }


    /* Custom container */
    .container-narrow {
        margin: 0 auto;
        max-width: 800px;
    }

    .container-narrow > hr {
        margin: 30px 0;
    }


    .footer {
        color: #aaa;
        margin-top: 3em;
    }

    .control-row {
        margin-bottom: 0.5em;
    }

    .control-row h4 {
        border-bottom: 1px solid #ddd;
        padding-bottom: 6px;
    }

    .control-row h4 span {
        margin-top: 5px;
    }

    .control-row.level-healthy {

    }

    .control-row.level-frail h4 {
        color: #ED840E;
    }
    .control-row.level-frail small {
        color: #BA9F6B;
    }

    .control-row.level-dead h4 {
        color: #DD4B27;
    }
    .control-row.level-dead h4 {
        color: #C13030;
    }

    .health-properties {
        color: #444;
    }

    pre {
        font-size: 0.7em;
    }
    </style>
</head>
<body>

<div class="container-narrow">
    <h1 style="margin-bottom: 1em"><span class="icon-user-md"></span> Health Control <small> - ${applicationName}</small></h1>



    <g:each in="${healthReports.sort()}" var="report">
        <g:set var="stateOfHealth" value="${report.stateOfHealth}" />

        <div class="control-row level-${stateOfHealth.level.toString().toLowerCase()}">
            <div class="row-fluid">
                <div class="span12">
                    <h4>
                        <g:if test="${stateOfHealth.level == HealthLevel.HEALTHY}">
                            <span class="icon-ok-circle"></span>
                        </g:if>
                        <g:elseif test="${stateOfHealth.level == HealthLevel.FRAIL}">
                            <span class="icon-warning-sign"></span>
                        </g:elseif>
                        <g:elseif test="${stateOfHealth.level == HealthLevel.DEAD}">
                            <span class="icon-ban-circle"></span>
                        </g:elseif>

                        ${report.name}
                        <small> - ${report.description}</small>
                    </h4>
                </div>
            </div>


            <div class="row-fluid health-properties">
                <div class="span6">
                    <em>${stateOfHealth.message}</em>
                </div>
                <div class="span6">
                    <g:if test="${stateOfHealth.properties}">
                        <ul class="inline">
                            <g:each in="${stateOfHealth.properties}" var="property">
                                <li><strong><span class="icon-angle-right"> </span>${property.key}: </strong> ${property.value}</li>
                            </g:each>
                        </ul>
                    </g:if>
                </div>
            </div>


            <g:if test="${stateOfHealth.trouble}">
                <div class="row-fluid">
                    <div class="span12">
                        <pre>${org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(stateOfHealth.trouble)}</pre>
                    </div>
                </div>
            </g:if>
        </div>
    </g:each>




    <div class="footer">
        <hr>
        <p class="pull-right">&copy; Grails Health Control - Developer-B 2012</p>
    </div>
</div>

<r:layoutResources/>

</body>
</html>