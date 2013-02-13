package com.developerb.healtcontrol.artefact;

import org.codehaus.groovy.grails.commons.AbstractInjectableGrailsClass;
import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;
import org.codehaus.groovy.grails.commons.InjectableGrailsClass;

/**
 * @author Kim A. Betti <kim@developer-b.com>
 */
public final class HealthControlArtefactHandler extends ArtefactHandlerAdapter {

    public static final String TYPE = "HealthControl";

    public HealthControlArtefactHandler() {
        super(TYPE, HealthControlArtefactHandler.class, DefaultHealthControlGrailsClass.class, null);
    }

    public boolean isArtefactClass(@SuppressWarnings("rawtypes") Class clazz) {
        return clazz != null && clazz.getName().endsWith(TYPE);
    }

    public static interface HealthControlGrailsClass extends InjectableGrailsClass {
    }

    public static final class DefaultHealthControlGrailsClass extends AbstractInjectableGrailsClass implements HealthControlGrailsClass {
        public DefaultHealthControlGrailsClass(Class<?> wrappedClass) {
            super(wrappedClass, HealthControlArtefactHandler.TYPE);
        }
    }
}
