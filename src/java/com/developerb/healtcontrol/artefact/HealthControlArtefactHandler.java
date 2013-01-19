package com.developerb.healtcontrol.artefact;

import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;

/**
 * @author Kim A. Betti <kim@developer-b.com>
 */
public final class HealthControlArtefactHandler extends ArtefactHandlerAdapter {

    public static final String TYPE = "HealthControl";

    public HealthControlArtefactHandler() {
        super(TYPE, HealthControlArtefactHandler.class, DefaultHealthControlGrailsClass.class, null);
    }

    public boolean isArtefactClass(Class clazz) {
        return clazz != null && clazz.getName().endsWith(TYPE);
    }

}
