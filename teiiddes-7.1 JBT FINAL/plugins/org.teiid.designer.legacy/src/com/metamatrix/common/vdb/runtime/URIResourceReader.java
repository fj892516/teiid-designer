/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */

package com.metamatrix.common.vdb.runtime;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import com.metamatrix.common.xml.XMLReaderWriter;
import com.metamatrix.common.xml.XMLReaderWriterImpl;
import org.teiid.core.util.ArgCheck;

/**
 */
public class URIResourceReader {
    private Map resources = new HashMap();

    // private static final String TRUE = Boolean.TRUE.toString();
    private static final String FALSE = Boolean.FALSE.toString();

    public static final String RESOURCE_NAME = "uriprimarymodeltypes.xml"; //$NON-NLS-1$

    public URIResourceReader() {

    }

    public Map getURIResources() throws Exception {
        InputStream in = loadAsResource(RESOURCE_NAME);

        ArgCheck.isNotNull(in);
        Document doc = null;

        try {
            doc = getXMLReaderWriter().readDocument(in);
        } catch (JDOMException e) {
            try {
                in.close();
            } catch (Exception ce) {

            }
            throw new Exception(e.getMessage());
        }

        Element root = doc.getRootElement();

        Collection uriElements = root.getChildren(Constants.URI.ELEMENT);

        Iterator iterator = uriElements.iterator();
        while (iterator.hasNext()) {
            Element uriElement = (Element)iterator.next();
            String name = null;
            String authlevel = URIModelResource.AUTH_LEVEL.ALL;
            String isxmldoctype = FALSE;
            String isphysicalbinding = FALSE;
            List attrs = uriElement.getAttributes();
            for (Iterator ait = attrs.iterator(); ait.hasNext();) {
                Attribute a = (Attribute)ait.next();
                String n = a.getName();
                if (n.equalsIgnoreCase(Constants.URI.Attribute.NAME)) {
                    name = a.getValue();
                } else if (n.equalsIgnoreCase(Constants.URI.Attribute.AUTHLEVEL)) {
                    authlevel = a.getValue();
                } else if (n.equalsIgnoreCase(Constants.URI.Attribute.ISXMLDOCTYPE)) {
                    isxmldoctype = a.getValue();
                } else if (n.equalsIgnoreCase(Constants.URI.Attribute.PHYSICAL_BINDING)) {
                    isphysicalbinding = a.getValue();
                }

                // System.out.println("Name: " + n + " value: " + a.getValue());

            }
            ArgCheck.isNotNull(name, "The attribute " + Constants.URI.Attribute.NAME + " was not defined"); //$NON-NLS-1$ //$NON-NLS-2$
            BasicURIModelResource resource = new BasicURIModelResource(name);
            resource.setAuthLevel(authlevel);

            // System.out.println("Name: " + name + " auth: " + authlevel + " doc: " + isxmldoctype);
            resource.setIsXMLDocType(new Boolean(isxmldoctype).booleanValue());
            resource.setIsPhysicalBindingAllowed(new Boolean(isphysicalbinding).booleanValue());

            resources.put(name, resource);

        }

        if (in != null) {
            try {
                in.close();
            } catch (Exception ce) {

            }
        }

        return resources;

    }

    private InputStream loadAsResource( String resourceName ) throws Exception {
        InputStream is = null;
        try {
            is = URIResourceReader.class.getResourceAsStream(resourceName);
            // .getClassLoader().getResourceAsStream(resourceName);
            ArgCheck.isNotNull(is);
        } catch (Exception e) {
            throw e;

        }
        return is;
    }

    private XMLReaderWriter getXMLReaderWriter() {
        return new XMLReaderWriterImpl();
    }

    public static class Constants {
        public static final String URI_RESOURCES = "urimodeltypes";//$NON-NLS-1$

        public static class URI {
            public static final String ELEMENT = "uri"; //$NON-NLS-1$

            public static class Attribute {
                public static final String NAME = "name"; //$NON-NLS-1$
                // indicates to what level the authorization will be enabled
                public static final String AUTHLEVEL = "authlevel"; //$NON-NLS-1$
                // indicates if this is uri represents an xml document type
                public static final String ISXMLDOCTYPE = "xmldoctype"; //$NON-NLS-1$
                // indicates if this uri is associated with a physical model type, does it allow a binding
                public static final String PHYSICAL_BINDING = "physicalbindingallowed"; //$NON-NLS-1$

            }
        }

    }

}