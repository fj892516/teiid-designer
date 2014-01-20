/* Generated By:JJTree: Do not edit this line. Command.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.SourceHint;
import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.lang.parser.AbstractTeiidParserVisitor;

public class Command extends SimpleNode {

    /** The option clause */
    private Option option;

    private SourceHint sourceHint;

    public Command(int id) {
        super(id);
    }

    public Command(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Get the option clause for the query.
     * @return option clause
     */
    public Option getOption() {
        return option;
    }
    
    /**
     * Set the option clause for the query.
     * @param option New option clause
     */
    public void setOption(Option option) {
        this.option = option;
    }

    /**
     * @return the sourceHint
     */
    public SourceHint getSourceHint() {
        return sourceHint;
    }

    /**
     * @param sourceHint the sourceHint to set
     */
    public void setSourceHint(SourceHint sourceHint) {
        this.sourceHint = sourceHint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.option == null) ? 0 : this.option.hashCode());
        result = prime * result + ((this.sourceHint == null) ? 0 : this.sourceHint.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Command other = (Command)obj;
        if (this.option == null) {
            if (other.option != null) return false;
        } else if (!this.option.equals(other.option)) return false;
        if (this.sourceHint == null) {
            if (other.sourceHint != null) return false;
        } else if (!this.sourceHint.equals(other.sourceHint)) return false;
        return true;
    }

    /** Accept the visitor. **/
    public void accept(AbstractTeiidParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=328e6e6dec01c1dc65d33fce3077b4f3 (do not edit this line) */
