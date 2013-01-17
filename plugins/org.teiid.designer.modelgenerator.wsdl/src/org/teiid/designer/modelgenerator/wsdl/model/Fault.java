/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.modelgenerator.wsdl.model;

/**
 * @since 8.0
 */
public interface Fault extends WSDLElement {
	
	public void setMessage(Message message);
	public Message getMessage();
	public Operation getOperation();

}