/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.runtime.ui.wizards.webservices.util;

/**
 * This class is able to create an instance of a WebArchiveBuilder class.
 *
 * @since 8.0
 */
public class WebArchiveBuilderFactory {

    /**
     * Creates an instance of a WebArchiveBuilder.
     */
    public static WebArchiveBuilder create() {

        return new DefaultWebArchiveBuilderImpl();
    }

    /**
     * Creates an instance of a RestWebArchiveBuilder.
     */
    public static WebArchiveBuilder createRestWebArchiveBuilder() {

        return new RestWebArchiveBuilderImpl();
    }
}
