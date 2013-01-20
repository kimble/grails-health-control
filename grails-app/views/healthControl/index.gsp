<%@ page import="com.developerb.healthcontrol.HealthLevel" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="refresh" content="60">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Health Control - ${applicationName}</title>
    <r:require modules="jquery,bootstrap,fontAwesome"/>

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

    h1 {
        border-bottom: 5px solid #474242
    }

    .footer {
        margin-top: 5em;
    }

    .footer a {
        color: #aaa;
    }

    .control-row {
        margin-bottom: 0.5em;
    }

    .control-row h4 {
        border-bottom: 1px solid #ddd;
        padding-bottom: 6px;
    }

    .control-row h4:hover {
        cursor: pointer;
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
        <g:set var="reportDataId" value="report-${report.type}" />

        <div class="control-row level-${stateOfHealth.level.toString().toLowerCase()}">
            <div class="row-fluid">
                <div class="span12">
                    <h4 data-toggle="collapse" data-target="#${reportDataId}">
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

            <div id="${reportDataId}" class="collapse">
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
        </div>
    </g:each>




    <div class="footer" style="text-align: right">
        <p><a href="http://github.com/kimble/grails-health-control">&copy; Grails Health Control - Developer-B 2012</a></p>
    </div>
</div>

<r:layoutResources/>

</body>
</html>