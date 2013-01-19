package com.developerb.healtcontrol.artefact;

import org.codehaus.groovy.grails.commons.AbstractInjectableGrailsClass;

/**
 * @author Kim A. Betti <kim@developer-b.com>
 */
public final class DefaultHealthControlGrailsClass extends AbstractInjectableGrailsClass implements HealthControlGrailsClass {

    public DefaultHealthControlGrailsClass(Class<?> wrappedClass) {
        super(wrappedClass, HealthControlArtefactHandler.TYPE);
    }



}
