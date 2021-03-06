/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.designer.runtime;

import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.IServerLifecycleListener;
import org.eclipse.wst.server.core.IServerListener;
import org.eclipse.wst.server.core.ServerEvent;
import org.teiid.designer.runtime.TeiidServerFactory.ServerOptions;
import org.teiid.designer.runtime.adapter.TeiidServerAdapterFactory;
import org.teiid.designer.runtime.spi.ITeiidServer;
import org.teiid.designer.runtime.spi.ITeiidServerManager;

/**
 * Singleton listener for monitoring both the {@link IServer}s'
 * life-cycle and their state.
 */
public class TeiidParentServerListener implements IServerLifecycleListener, IServerListener {
    
    private static TeiidParentServerListener instance;
    
    /**
     * Get the singleton instance of of this class
     * 
     * @return instance
     */
    public static TeiidParentServerListener getInstance() {
        if (instance == null)
            instance = new TeiidParentServerListener();
        
        return instance;
    }
    
    private TeiidServerAdapterFactory factory = new TeiidServerAdapterFactory();
    
    private boolean sleep;
    
    private TeiidParentServerListener() {}

    @Override
    public void serverAdded(IServer server) {
        if (sleep) return;
        
        // Initialise the Teiid Instance manager is not already initialised
        DqpPlugin.getInstance().getServerManager();

        server.addServerListener(this);

        // New server added so add a teiid instance, even though it is not currently connected
        try {
            factory.adaptServer(server, ServerOptions.NO_CHECK_CONNECTION, ServerOptions.ADD_TO_REGISTRY);
        } catch (final Exception ex) {
            DqpPlugin.handleException(ex);
        }
    }

    @Override
    public void serverChanged(IServer server) {
        if (sleep) return;
        
        ITeiidServerManager serverManager = DqpPlugin.getInstance().getServerManager();

        try {
            for (ITeiidServer teiidServer : serverManager.getServers()) {
                if (! server.equals(teiidServer.getParent()))
                    continue;

                ITeiidServer newTeiidServer = factory.adaptServer(server, ServerOptions.NO_CHECK_SERVER_REGISTRY, ServerOptions.NO_CHECK_CONNECTION);
            
                /*
                 * Cannot use updateServer as it replaces rather than modifies the existing server
                 * and references in editor will thus hang on to the old defunct version.
                 *
                 * Only update the settings which may have been queried from the server.
                 */
                teiidServer.getTeiidAdminInfo().setAll(newTeiidServer.getTeiidAdminInfo());
                teiidServer.getTeiidJdbcInfo().setPort(newTeiidServer.getTeiidJdbcInfo().getPort());

                teiidServer.notifyRefresh();

                return;
            }
        
            /*
             * We have a parent server with no Teiid Instance attached
             * This may be intentional if the parent server is not teiid
             * enabled but should check just in case.
             */
            factory.adaptServer(server, ServerOptions.ADD_TO_REGISTRY);
        } catch (Exception ex) {
            DqpPlugin.handleException(ex);
        }
    }
   
    @Override
    public void serverRemoved(IServer server) {
        if (sleep) return;
        
        server.removeServerListener(this);
        
        ITeiidServerManager serverManager = DqpPlugin.getInstance().getServerManager();
        
        // Tidy up the server manager by removing the related Teiid Instance
        for (ITeiidServer teiidServer : serverManager.getServers()) {
            if (server.equals(teiidServer.getParent())) {
                serverManager.removeServer(teiidServer);
                break;
            }
        }
    }
    
    @Override
    public void serverChanged(ServerEvent event) {
        if (sleep) return;
        
        if (event == null) return;

        int eventKind = event.getKind();
        if ((eventKind & ServerEvent.SERVER_CHANGE) == 0) return;

        // server change event
        if ((eventKind & ServerEvent.STATE_CHANGE) == 0) return;

        int state = event.getState();
        IServer parentServer = event.getServer();

        try {
            if (state == IServer.STATE_STOPPING || state == IServer.STATE_STOPPED) {
                ITeiidServer teiidServer = factory.adaptServer(parentServer);
                if (teiidServer != null)
                    teiidServer.disconnect();
            
            } else if (state == IServer.STATE_STARTED) {

                ITeiidServer teiidServer = factory.adaptServer(parentServer, ServerOptions.ADD_TO_REGISTRY);
                if (teiidServer != null && teiidServer.isParentConnected()) {
                    /*
                     * Update all the settings since the server has been started and a
                     * proper set of queries can take place.
                     */
                    ITeiidServer queryServer = factory.adaptServer(parentServer,
                                                               ServerOptions.NO_CHECK_SERVER_REGISTRY);

                    if (queryServer != null) {
                        /*
                         * Updates those settings that may have been successfully queried from the
                         * contacted server.
                         */
                        teiidServer.getTeiidAdminInfo().setAll(queryServer.getTeiidAdminInfo());
                        teiidServer.getTeiidJdbcInfo().setPort(queryServer.getTeiidJdbcInfo().getPort());
                    }
                    else {
                        // If the query server is null then this is not a Teiid-enabled JBoss Server but
                        // a TeiidServer was cached in the registry, presumably due to an adaption
                        // being made while the server was not started. Since we now know better, we
                        // can correct the registry.
                        DqpPlugin.getInstance().getServerManager().removeServer(teiidServer);
                    }

                    teiidServer.reconnect();
                
                }
            }
        } catch (Exception ex) {
            DqpPlugin.handleException(ex);
        }
    }

    /**
     * Deafen this listener
     */
    public void sleep() {
        sleep = true;
    }

    /**
     * Awaken this listener
     */
    public void wake() {
        sleep = false;
    }
}