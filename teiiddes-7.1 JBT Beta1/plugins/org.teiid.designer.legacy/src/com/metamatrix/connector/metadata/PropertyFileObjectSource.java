/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */

package com.metamatrix.connector.metadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.metamatrix.connector.metadata.internal.IObjectSource;
import org.teiid.core.TeiidRuntimeException;

/**
 * Read a property file and deliver the results as an object source. 
 * This plugs into the object connector to make a property file visible as a relational table.
 */
public class PropertyFileObjectSource implements IObjectSource {
    private String propertyFilePath;
    
    public PropertyFileObjectSource() {
    	this("com/metamatrix/connector/metadata/enum/"); //$NON-NLS-1$
    }
    
    public PropertyFileObjectSource(String propertyFilePath) {
        this.propertyFilePath = propertyFilePath;
    }
    
    /* 
     * @see com.metamatrix.connector.metadata.internal.IObjectSource#getObjects(java.lang.String, java.util.Map)
     */
    public Collection getObjects(String propertyFileName, Map criteria) {
        if (criteria != null && criteria.size() >0) {
            throw new UnsupportedOperationException("Criteria is not supported"); //$NON-NLS-1$
        }
        InputStream input = null;
        try {
        	propertyFileName = expandPropertyFileName(propertyFileName);
        	
            input = this.getClass().getClassLoader().getResourceAsStream(propertyFileName);
            if (input == null) {
            	throw new TeiidRuntimeException(propertyFileName+" file not found");
            }
            //input = new BufferedInputStream(new FileInputStream(propertyFileName));
            Properties properties = new Properties();
            properties.load(input);
            List results = new ArrayList();
            for (Iterator iterator=properties.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                PropertyHolder holder = new PropertyHolder(new Integer((String) entry.getKey()));
                holder.setValue(entry.getValue());
                results.add(holder);
            }
            return results;
        } catch (FileNotFoundException e) {
            throw new TeiidRuntimeException(e);
        } catch (IOException e) {
            throw new TeiidRuntimeException(e);
        } finally {
            if (input != null) {
                try {
					input.close();
				} catch (IOException e) {
				}
            }
        }
    }

    private String expandPropertyFileName(String propertyFileName) {
        if (propertyFilePath == null) {
            return propertyFileName;    
        }
        return propertyFilePath + propertyFileName;
    }

}