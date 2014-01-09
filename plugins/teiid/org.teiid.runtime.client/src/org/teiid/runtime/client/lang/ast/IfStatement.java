/* Generated By:JJTree: Do not edit this line. IfStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;

public class IfStatement extends Statement {

    // the IF block
    private Block ifBlock;
    
    // the ELSE block
    private Block elseBlock;
    
    // criteria on the if block
    private Criteria condition;
    
    public IfStatement(int id) {
        super(id);
    }

    public IfStatement(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Get the statement's IF block.
     * @return The IF <code>Block</code> object.
     */
    public Block getIfBlock() {
        return ifBlock;
    }
    
    /**
     * Set the statement's IF block.
     * @param block The IF <code>Block</code> object.
     */
    public void setIfBlock(Block block) {
        this.ifBlock = block;
    }

    /**
     * Get the statement's ELSE block.
     * @return The ELSE <code>Block</code> object.
     */
    public Block getElseBlock() {
        return elseBlock;
    }
    
    /**
     * Set the statement's ELSE block.
     * @param block The ELSE <code>Block</code> object.
     */
    public void setElseBlock(Block block) {
        elseBlock = block;
    }
    
    /**
     * Return a boolean indicating if the statement has an else block.
     * @return A boolean indicating if the statement has an else block
     */
    public boolean hasElseBlock() {
        return (elseBlock != null);
    }

    /**
     * Get the condition that determines which block needs to be executed.
     * @return The <code>Criteria</code> to determine block execution
     */
    public Criteria getCondition() {
        return condition;
    }
    
    /**
     * Set the condition that determines which block needs to be executed.
     * @param criteria The <code>Criteria</code> to determine block execution
     */
    public void setCondition(Criteria criteria) {
        this.condition = criteria;
    }

    /** Accept the visitor. **/
    public void jjtAccept(Teiid8ParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=bb19833978a016bb6733f82348868799 (do not edit this line) */