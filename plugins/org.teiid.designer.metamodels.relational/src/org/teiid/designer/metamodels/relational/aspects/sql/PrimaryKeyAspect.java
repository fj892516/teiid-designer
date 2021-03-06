/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.relational.aspects.sql;

import org.teiid.designer.core.index.IndexConstants;
import org.teiid.designer.core.metamodel.aspect.MetamodelEntity;


/**
 * PrimaryKeyAspect
 *
 * @since 8.0
 */
public class PrimaryKeyAspect extends UniqueKeyAspect {
    
    public PrimaryKeyAspect(MetamodelEntity entity) {
        super(entity);   
    }    

    /* (non-Javadoc)
     * @See org.teiid.designer.core.metamodel.aspect.sql.SqlAspect#isRecordType(char)
     */
    @Override
    public boolean isRecordType(char recordType) {
        return (recordType == IndexConstants.RECORD_TYPE.PRIMARY_KEY);
    }

}
