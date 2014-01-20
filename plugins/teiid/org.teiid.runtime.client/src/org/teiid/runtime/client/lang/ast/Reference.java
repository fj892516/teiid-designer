/* Generated By:JJTree: Do not edit this line. Reference.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.lang.parser.AbstractTeiidParserVisitor;

public class Reference extends SimpleNode implements Expression {

    private int refIndex;

    private Class<?> type;

    private ElementSymbol expression;

    private boolean positional;

    public Reference(int id) {
        super(id);
    }

    public Reference(TeiidParser p, int id) {
        super(p, id);
    }

    public Class<?> getType() {
        if (this.isPositional() && this.expression == null) {
            return type;
        }
        return expression.getType();
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public boolean isPositional() {
        return this.positional;
    }

    /**
     * @param positional the positional to set
     */
    public void setPositional(boolean positional) {
        this.positional = positional;
    }

    public ElementSymbol getExpression() {
        return this.expression;    
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(ElementSymbol expression) {
        this.expression = expression;
    }

    public int getIndex() {
        return this.refIndex;
    }

    public void setIndex(int refIndex) {
        this.refIndex = refIndex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.expression == null) ? 0 : this.expression.hashCode());
        result = prime * result + (this.positional ? 1231 : 1237);
        result = prime * result + this.refIndex;
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Reference other = (Reference)obj;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        if (this.positional != other.positional) return false;
        if (this.refIndex != other.refIndex) return false;
        if (this.type == null) {
            if (other.type != null) return false;
        } else if (!this.type.equals(other.type)) return false;
        return true;
    }

    /** Accept the visitor. **/
    public void accept(AbstractTeiidParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=929474660e4f338ac928ae34c54e9019 (do not edit this line) */
