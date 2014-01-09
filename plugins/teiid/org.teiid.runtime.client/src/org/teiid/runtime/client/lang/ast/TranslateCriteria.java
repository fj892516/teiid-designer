/* Generated By:JJTree: Do not edit this line. TranslateCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import java.util.ArrayList;
import java.util.List;
import org.teiid.designer.annotation.Removed;
import org.teiid.runtime.client.lang.parser.TeiidParser;

@Removed("8.0.0")
public class TranslateCriteria extends Criteria {

    // the selector object used to determine if a type of criteria is specified 
    // on the user's query  
    private CriteriaSelector criteriaSelector;
    
    // List of comparecriteria(element-value pairs) used to translate the user's criteria
    private List<CompareCriteria> translations;

    public TranslateCriteria(int id) {
        super(id);
    }

    public TranslateCriteria(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Get the <code>CriteriaSelector</code>
     * @return <code>CriteriaSelector</code> of this obj
     */
    public CriteriaSelector getSelector() {
        return criteriaSelector;
    }

    /**
     * Set the <code>CriteriaSelector</code>
     * @param selector The <code>CriteriaSelector</code> of this obj
     */
    public void setSelector(CriteriaSelector selector) {
        this.criteriaSelector = selector;
    }

    /**
     * Return a boolean indicating if the object has any translations.
     * @return A boolean indicating if the object has any translations
     */
    public boolean hasTranslations() {
        if(this.translations != null) {
            return (this.translations.size() > 0);          
        }
        return false;
    }

    /**
     * Set a list of comparecriteria(element-value pairs) used to translate the user's criteria.
     *
     * @param translations A list of criteria used to translate user's criteria
     */
    public void setTranslations(List<CompareCriteria> translations) {
        this.translations = translations;
    }
    
    /**
     * Add a comparecriteria(element-value pair) to the list used to translate the user's criteria.
     * @param criteria A <code>ComapareCriteria</code> object to be added to a collection
     */
    public void addTranslation(CompareCriteria criteria) {
        if(this.translations == null) {
            this.translations = new ArrayList();    
        }

        this.translations.add(criteria);
    }
    
    /**
     * Get a list of comparecriteria(element-value pairs) used to translate the user's criteria.
     * @return A list of criteria used to translate user's criteria
     */
    public List<CompareCriteria> getTranslations() {
        return this.translations;
    }

    /** Accept the visitor. **/
    public void jjtAccept(Teiid7ParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=f23323b45743f602279ed13dcd37babe (do not edit this line) */