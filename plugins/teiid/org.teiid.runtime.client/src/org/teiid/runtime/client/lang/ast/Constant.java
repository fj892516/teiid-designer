/* Generated By:JJTree: Do not edit this line. Constant.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import java.math.BigDecimal;
import java.util.List;
import org.teiid.runtime.client.lang.parser.AbstractTeiidParserVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;
import org.teiid.runtime.client.types.DataTypeManagerService;
import org.teiid.runtime.client.types.DataTypeManagerService.DefaultDataTypes;

public class Constant extends SimpleNode implements Expression {

    private Class<?> type = DataTypeManagerService.DefaultDataTypes.NULL.getTypeClass();

    private boolean multiValued;

    public Constant(int id) {
        super(id);
    }

    public Constant(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Get type of constant, if known
     * @return Java class name of type
     */
    @Override
    public Class<?> getType() {
        return this.type;
    }
    
    /**
     * TODO: remove me when a null type is supported
     * @param type
     */
    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * Get value of constant
     * @return Constant value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param value
     */
    public void setValue(Object value) {
        jjtSetValue(value);
        DataTypeManagerService service = DataTypeManagerService.getInstance();
        if (this.value == null) {
            this.type = DataTypeManagerService.DefaultDataTypes.NULL.getClass();
        } else { 
            this.type = this.value.getClass();

            Class<?> originalType = type;
            while (type.isArray() && !type.getComponentType().isPrimitive()) {
                type = type.getComponentType();
            }

            DefaultDataTypes dataType = service.getDataType(type);
            if (dataType != null) {
                //array of a runtime-type
                this.type = originalType;
            } else if (originalType.isArray() && !originalType.getComponentType().isPrimitive()) {
                this.type = DataTypeManagerService.DefaultDataTypes.OBJECT.getTypeArrayClass();
            } else {
                this.type = DataTypeManagerService.DefaultDataTypes.OBJECT.getClass();
            }
        }
    }

    public boolean isMultiValued() {
        return multiValued;
    }

    public void setMultiValued(List<?> value) {
        this.multiValued = true;
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Constant other = (Constant)obj;

        if (this.value == null && other.value == null) {
            // Only consider type information if values are not null
            return true;
        }

        if (this.value instanceof BigDecimal) {
            if (this.value == other.value) {
                return true;
            }
            if (!(other.value instanceof BigDecimal)) {
                return false;
            }
            return ((BigDecimal)this.value).compareTo((BigDecimal)other.value) == 0;
        }

        if (this.type == null) {
            if (other.type != null) return false;
        } else if (!this.type.equals(other.type)) return false;

        return multiValued == other.multiValued && other.getValue().equals(this.getValue());
    }

    /** Accept the visitor. **/
    @Override
    public void accept(AbstractTeiidParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=6271d54b51de261eb4775571a208cc1b (do not edit this line) */
