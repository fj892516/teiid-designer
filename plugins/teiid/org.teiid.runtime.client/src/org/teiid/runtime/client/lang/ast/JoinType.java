/* Generated By:JJTree: Do not edit this line. JoinType.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.runtime.client.lang.parser.TeiidParser;

public class JoinType extends SimpleNode {

    /**
     * Delineation of the category of join type
     */
    public enum Kind {
        /** Represents an inner join:  a INNER JOIN b */
        JOIN_INNER(false),

        /** Represents a right outer join:  a RIGHT OUTER JOIN b */
        JOIN_RIGHT_OUTER(true),

        /** Represents a left outer join:  a LEFT OUTER JOIN b */
        JOIN_LEFT_OUTER(true),

        /** Represents a full outer join:  a FULL OUTER JOIN b */
        JOIN_FULL_OUTER(true),

        /** Represents a cross join:  a CROSS JOIN b */
        JOIN_CROSS(false),

        /** Represents a union join:  a UNION JOIN b - not used after rewrite */
        JOIN_UNION(true),

        /** internal SEMI Join type */
        JOIN_SEMI(false),

        /** internal ANTI SEMI Join type */
        JOIN_ANTI_SEMI(true);

        private final boolean outer;

        private Kind(boolean outer) {
            this.outer = outer;
        }

        public int getTypeCode() {
            return this.ordinal();
        }
        
        public boolean isOuter() {
            return this.outer;
        }
    }

    private Kind kind = Kind.JOIN_CROSS;

    public JoinType(int id) {
        super(id);
    }

    public JoinType(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * @return the kind
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * @param kind the kind to set
     */
    public void setKind(Kind kind) {
        this.kind = kind;
    }

    /** Accept the visitor. **/
    public void jjtAccept(Teiid8ParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=682a038ace51527d7a11065b4087303a (do not edit this line) */