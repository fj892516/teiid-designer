/* Generated By:JJTree: Do not edit this line. LoopStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;

public class LoopStatement extends AssignmentStatement implements Labeled {

    private String cursorName;

    private Block loopBlock;

    private Command query;

    private String label;

    public LoopStatement(int id) {
        super(id);
    }

    public LoopStatement(TeiidParser p, int id) {
        super(p, id);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return
     */
    public String getCursorName() {
        return cursorName;
    }

    /**
     * @param string
     */
    public void setCursorName(String cursorName) {
        this.cursorName = cursorName;
    }

    /**
     * @return
     */
    public Block getBlock() {
        return loopBlock;
    }

    /**
     * @param block
     */
    public void setBlock(Block block) {
        loopBlock = block;
    }

    /**
     * @return
     */
    public Command getCommand() {
        return query;
    }

    /**
     * Sets the command. 
     */
    public void setCommand(Command command){
        this.query = command;
    }

    /**
     * @param query
     */
    public void setCommand(Query query) {
        this.query = query;
    }

    /** Accept the visitor. **/
    public void jjtAccept(Teiid8ParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=fe4519dc09735b802146c0bcd4db1308 (do not edit this line) */