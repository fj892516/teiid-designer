/* Generated By:JJTree: Do not edit this line. CompareCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.lang.parser.AbstractTeiidParserVisitor;

@SuppressWarnings( "unused" )
public class ExpressionSymbol extends Symbol implements SingleElementSymbol, Expression {

    private Expression expression;

    public ExpressionSymbol(int id) {
        super(id);
    }

    public ExpressionSymbol(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Get the type of the symbol
     * @return Type of the symbol, may be null before resolution
     */
    public Class getType() {
        return this.expression.getType();
    }

    /**
     * Get the expression for this symbol
     * @return Expression for this symbol
     */
    public Expression getExpression() {
        return this.expression;
    }
    
    /**
     * Set the expression represented by this symbol.
     * @param expression Expression for this expression symbol
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.expression == null) ? 0 : this.expression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ExpressionSymbol other = (ExpressionSymbol)obj;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        return true;
    }

    /** Accept the visitor. **/
    public void accept(AbstractTeiidParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=b3e2979ada751b1aae0903db53c85d1c (do not edit this line) */
