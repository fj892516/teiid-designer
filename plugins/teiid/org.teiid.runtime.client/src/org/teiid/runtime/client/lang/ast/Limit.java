/* Generated By:JJTree: Do not edit this line. Limit.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;

public class Limit extends SimpleNode {

    public static String NON_STRICT = "NON_STRICT"; //$NON-NLS-1$

    private Expression offset;

    private Expression rowLimit;

    private boolean implicit;

    private boolean strict = true;

    public Limit(int id) {
        super(id);
    }

    public Limit(TeiidParser p, int id) {
        super(p, id);
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }
    
    public boolean isStrict() {
        return strict;
    }
    
    public boolean isImplicit() {
        return implicit;
    }
    
    public void setImplicit(boolean implicit) {
        this.implicit = implicit;
    }
    
    public Expression getOffset() {
        return offset;
    }
    
    public void setOffset(Expression offset) {
        this.offset = offset;
    }
    
    public Expression getRowLimit() {
        return rowLimit;
    }
    
    public void setRowLimit(Expression rowLimit ) {
        this.rowLimit = rowLimit;
    }

    /** Accept the visitor. **/
    public void jjtAccept(Teiid8ParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=ac356bcb126e51b23a771bf5e2b89dfc (do not edit this line) */