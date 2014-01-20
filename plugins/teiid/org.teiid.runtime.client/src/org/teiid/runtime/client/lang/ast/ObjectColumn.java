/* Generated By:JJTree: Do not edit this line. ObjectColumn.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.lang.parser.AbstractTeiidParserVisitor;

public class ObjectColumn extends ProjectedColumn {

    private String path;

    private Expression defaultExpression;

    public ObjectColumn(int id) {
        super(id);
    }

    public ObjectColumn(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the defaultExpression
     */
    public Expression getDefaultExpression() {
        return this.defaultExpression;
    }

    /**
     * @param defaultExpression the defaultExpression to set
     */
    public void setDefaultExpression(Expression defaultExpression) {
        this.defaultExpression = defaultExpression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.defaultExpression == null) ? 0 : this.defaultExpression.hashCode());
        result = prime * result + ((this.path == null) ? 0 : this.path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ObjectColumn other = (ObjectColumn)obj;
        if (this.defaultExpression == null) {
            if (other.defaultExpression != null) return false;
        } else if (!this.defaultExpression.equals(other.defaultExpression)) return false;
        if (this.path == null) {
            if (other.path != null) return false;
        } else if (!this.path.equals(other.path)) return false;
        return true;
    }

    /** Accept the visitor. **/
    public void accept(AbstractTeiidParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=93bed62047b8d070d6aa7cdb43356933 (do not edit this line) */
