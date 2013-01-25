/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.repository;

import org.overlord.sramp.repository.jcr.JCRRepositoryCleaner;

/**
 * A repository manager that uses the S-RAMP atom interface that can be used for testing.
 */
public class CleanableRepositoryManager extends AtomRepositoryManager implements Cleanable {

    /**
     * The default port for the server running S-RAMP. Value is ({@value}.
     */
    public static final int DEFAULT_SERVER_PORT = 8081;

    /**
     * The name of the system property whose value is the JSON ModeShape repository configuration file.
     */
    public static final String MODESHAPE_CONFIG_URL_SYS_PROP = "sramp.modeshape.config.url"; //$NON-NLS-1$

    /**
     * The name of the system property whose value is the configured port of the server running S-RAMP.
     */
    public static final String SERVER_PORT_SYS_PROP = "org.jboss.resteasy.port"; //$NON-NLS-1$

    private final JCRRepositoryCleaner cleaner = new JCRRepositoryCleaner();

    /**
     * Constructs a default s-ramp jetty/atom repository manager using localhost and default port.
     * @throws Exception if there is a problem constructing repository manager
     */
    public CleanableRepositoryManager() throws Exception {
        super(String.format("%s:%s", "http://localhost", System.getProperty(SERVER_PORT_SYS_PROP))); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.repository.Cleanable#clean()
     */
    @Override
    public void clean() throws Exception {
        this.cleaner.clean();
    }

}