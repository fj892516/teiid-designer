/* Generated By:JJTree: Do not edit this line. Criteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.teiid.designer.query.sql.lang.ICompoundCriteria;
import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.types.DataTypeManagerService;

public class Criteria extends SimpleNode implements Expression {

    public Criteria(int id) {
        super(id);
    }

    public Criteria(TeiidParser p, int id) {
        super(p, id);
    }

    @Override
    public Class<?> getType() {
        return DataTypeManagerService.DefaultDataTypes.BOOLEAN.getTypeClass();
    }

    /**
     * Helper method for {@link #separateCriteriaByAnd(Criteria)} that 
     * can be called recursively to collect parts.
     * @param crit Crit to break apart
     * @param parts Collection to add parts to
     */
    private static void separateCriteria(Criteria crit, Collection<Criteria> parts) {
        if(crit instanceof CompoundCriteria) {
            CompoundCriteria compCrit = (CompoundCriteria) crit;
            if(compCrit.getOperator() == ICompoundCriteria.AND) {
                for (Criteria conjunct : compCrit.getCriteria()) {
                    separateCriteria(conjunct, parts);
                }
            } else {
                parts.add(crit);    
            }
        } else {
            parts.add(crit);        
        }   
    }

    /**
     * This utility method will pull apart a tree of criteria by breaking all
     * compound AND criteria apart.  For instance, ((A=1 AND B=2) AND C=3) 
     * will be broken into A=1, B=2, C=3.  
     * @param crit Criteria to break apart
     * @return List of Criteria, empty list if crit is null
     */     
    public static List<Criteria> separateCriteriaByAnd(Criteria crit) {
        if(crit == null) { 
            return Collections.emptyList();
        }
        
        List<Criteria> parts = new ArrayList<Criteria>();
        separateCriteria(crit, parts);
        return parts;           
    }

    /** Accept the visitor. **/
    public void jjtAccept(Teiid8ParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=d76fcc28dd6818ea751c44825b2d1060 (do not edit this line) */