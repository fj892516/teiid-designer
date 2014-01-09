/* Generated By:JJTree: Do not edit this line. XMLForest.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import java.util.List;
import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.types.DataTypeManagerService;

public class XMLForest extends SimpleNode implements Expression {

    private List<DerivedColumn> args;

    private XMLNamespaces namespaces;

    public XMLForest(int id) {
        super(id);
    }

    public XMLForest(TeiidParser p, int id) {
        super(p, id);
    }

    @Override
    public Class<?> getType() {
        return DataTypeManagerService.DefaultDataTypes.XML.getTypeClass();
    }

    public XMLNamespaces getNamespaces() {
        return namespaces;
    }
    
    public void setNamespaces(XMLNamespaces namespaces) {
        this.namespaces = namespaces;
    }
    
    public List<DerivedColumn> getArgs() {
        return args;
    }

    public void setArguments(List<DerivedColumn> args) {
        this.args = args;
    }

    /** Accept the visitor. **/
    public void jjtAccept(Teiid8ParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=f7a58421cd938bda6fd8786ca42a7b99 (do not edit this line) */