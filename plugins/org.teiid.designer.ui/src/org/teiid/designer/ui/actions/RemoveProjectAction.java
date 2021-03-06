/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.teiid.designer.ui.UiConstants;
import org.teiid.designer.ui.UiPlugin;
import org.teiid.designer.ui.common.actions.AbstractAction;
import org.teiid.designer.ui.common.eventsupport.SelectionUtilities;
import org.teiid.designer.ui.views.DatatypeHierarchyView;


/**
 * @since 8.0
 */
public class RemoveProjectAction extends AbstractAction {
	private static final String TITLE = UiConstants.Util.getString("RemoveProjectAction.title"); //$NON-NLS-1$
    /**
     * Construct an instance of ImportMetadata.
     */
    public RemoveProjectAction() {
    	super(UiPlugin.getDefault());
    	this.setText(TITLE);
    	this.setToolTipText(TITLE);
    	setEnabled(true);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     */
    @Override
    public void selectionChanged(IWorkbenchPart thePart,
                                 ISelection theSelection) {
        super.selectionChanged(thePart, theSelection);
        
        boolean enable = false;
        // Added the instanceof check for Defect 11152. This was enabling for a non-editable view
        // This was easiest way to fix.  Could check for model type, but.....
        if ( !(thePart instanceof DatatypeHierarchyView) && SelectionUtilities.isSingleSelection(theSelection) ) {
             Object obj = SelectionUtilities.getSelectedObject(theSelection);
             if ( obj != null && obj instanceof IProject && !((IProject)obj).isOpen() ) {
                 enable = true;
             }
        }
        setEnabled(enable);
    }

	@Override
    protected void doRun() {
        Object obj = SelectionUtilities.getSelectedObject(getSelection());
        if ( obj != null && obj instanceof IProject && !((IProject)obj).isOpen() ) {
        	// Need to close the project
        	try {
				((IProject)obj).delete(false, false, null);
			} catch (CoreException e) {
				UiConstants.Util.log(e);
			}
        }
        setEnabled(true);
	}
}
