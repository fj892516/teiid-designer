/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.runtime.client.lang.ast;

import org.teiid.designer.annotation.Removed;

/**
 *
 */
@SuppressWarnings( "unused" )
public interface WindowFunction extends Node, SingleElementSymbol, Expression {

    /**
     * @return the function
     */
    AggregateSymbol getFunction();

    /**
     * @param function the function to set
     */
    void setFunction(AggregateSymbol function);

    /**
     * @return the windowSpecification
     */
    WindowSpecification getWindowSpecification();

    /**
     * @param windowSpecification the windowSpecification to set
     */
    void setWindowSpecification(WindowSpecification windowSpecification);

    /**
     * @return name
     */
    @Removed("8.0.0")
    String getName();

    /**
     * @param name
     */
    @Removed("8.0.0")
    void setName(String name);

    Class<?> getType();

    /** Accept the visitor. **/
    void jjtAccept(Teiid8ParserVisitor visitor, Object data);

}