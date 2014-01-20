/* Generated By:JJTree: Do not edit this line. DerivedColumn.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.lang.parser.AbstractTeiidParserVisitor;

public class DerivedColumn extends SimpleNode {

    private String alias;

    private Expression expression;

    private boolean propagateName = true;

    public DerivedColumn(int id) {
        super(id);
    }

    public DerivedColumn(TeiidParser p, int id) {
        super(p, id);
    }

    public boolean isPropagateName() {
        return propagateName;
    }
    
    public void setPropagateName(boolean propagateName) {
        this.propagateName = propagateName;
    }
    
    public String getAlias() {
        return alias;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setAlias(String name) {
        this.alias = name;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.alias == null) ? 0 : this.alias.hashCode());
        result = prime * result + ((this.expression == null) ? 0 : this.expression.hashCode());
        result = prime * result + (this.propagateName ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        DerivedColumn other = (DerivedColumn)obj;
        if (this.alias == null) {
            if (other.alias != null) return false;
        } else if (!this.alias.equals(other.alias)) return false;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        if (this.propagateName != other.propagateName) return false;
        return true;
    }

    /** Accept the visitor. **/
    public void accept(AbstractTeiidParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=b8c0cc1023777a56f0a24e4cb9eb6b4a (do not edit this line) */
