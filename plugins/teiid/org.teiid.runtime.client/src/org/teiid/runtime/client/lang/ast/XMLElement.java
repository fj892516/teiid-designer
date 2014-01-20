/* Generated By:JJTree: Do not edit this line. XMLElement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import java.util.List;
import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.lang.parser.AbstractTeiidParserVisitor;
import org.teiid.runtime.client.types.DataTypeManagerService;

public class XMLElement extends SimpleNode implements Expression {

    private String name;

    private XMLNamespaces namespaces;

    private XMLAttributes attributes;

    private List<Expression> content;

    public XMLElement(int id) {
        super(id);
    }

    public XMLElement(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the content
     */
    public List<Expression> getContent() {
        return this.content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(List<Expression> content) {
        this.content = content;
    }

    /**
     * @return the namespaces
     */
    public XMLNamespaces getNamespaces() {
        return this.namespaces;
    }

    /**
     * @param namespaces the namespaces to set
     */
    public void setNamespaces(XMLNamespaces namespaces) {
        this.namespaces = namespaces;
    }

    /**
     * @return the attributes
     */
    public XMLAttributes getAttributes() {
        return this.attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(XMLAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public Class<?> getType() {
        return DataTypeManagerService.DefaultDataTypes.XML.getTypeClass();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.attributes == null) ? 0 : this.attributes.hashCode());
        result = prime * result + ((this.content == null) ? 0 : this.content.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.namespaces == null) ? 0 : this.namespaces.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        XMLElement other = (XMLElement)obj;
        if (this.attributes == null) {
            if (other.attributes != null) return false;
        } else if (!this.attributes.equals(other.attributes)) return false;
        if (this.content == null) {
            if (other.content != null) return false;
        } else if (!this.content.equals(other.content)) return false;
        if (this.name == null) {
            if (other.name != null) return false;
        } else if (!this.name.equals(other.name)) return false;
        if (this.namespaces == null) {
            if (other.namespaces != null) return false;
        } else if (!this.namespaces.equals(other.namespaces)) return false;
        return true;
    }

    /** Accept the visitor. **/
    public void accept(AbstractTeiidParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=eb3a7f3ac8fdfad86e1083695fcbb970 (do not edit this line) */
