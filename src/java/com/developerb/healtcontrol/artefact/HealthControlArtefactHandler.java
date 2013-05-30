/*
 * Copyright 2013 Grails Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
