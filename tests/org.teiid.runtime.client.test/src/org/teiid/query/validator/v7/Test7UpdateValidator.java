/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.query.validator.v7;

import org.teiid.designer.runtime.version.spi.TeiidServerVersion;
import org.teiid.query.sql.AbstractTestFactory;
import org.teiid.query.sql.v7.Test7Factory;
import org.teiid.query.validator.AbstractTestUpdateValidator;

/**
 *
 */
public class Test7UpdateValidator extends AbstractTestUpdateValidator {

    private Test7Factory factory;

    /**
     *
     */
    public Test7UpdateValidator() {
        super(new TeiidServerVersion("7.7.0")); //$NON-NLS-1$
    }

    @Override
    protected AbstractTestFactory getFactory() {
        if (factory == null)
            factory = new Test7Factory(getQueryParser());

        return factory;
    }
}