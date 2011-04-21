/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package com.metamatrix.modeler.internal.vdb.ui.wizards;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.ide.IDE;

import com.metamatrix.core.util.CoreStringUtil;
import com.metamatrix.core.util.I18nUtil;
import com.metamatrix.modeler.core.ModelerCore;
import com.metamatrix.modeler.core.validation.rules.StringNameValidator;
import com.metamatrix.modeler.internal.core.workspace.ModelUtil;
import com.metamatrix.modeler.internal.ui.PluginConstants;
import com.metamatrix.modeler.internal.ui.explorer.ModelExplorerLabelProvider;
import com.metamatrix.modeler.internal.ui.viewsupport.ModelProjectSelectionStatusValidator;
import com.metamatrix.modeler.internal.ui.viewsupport.ModelUtilities;
import com.metamatrix.modeler.internal.vdb.ui.editor.VdbEditor;
import com.metamatrix.modeler.ui.UiConstants;
import com.metamatrix.modeler.ui.UiPlugin;
import com.metamatrix.modeler.ui.viewsupport.ModelingResourceFilter;
import com.metamatrix.modeler.vdb.ui.VdbUiConstants;
import com.metamatrix.ui.internal.InternalUiConstants;
import com.metamatrix.ui.internal.eventsupport.SelectionUtilities;
import com.metamatrix.ui.internal.util.WidgetFactory;
import com.metamatrix.ui.internal.util.WidgetUtil;
import com.metamatrix.ui.internal.util.WizardUtil;
import com.metamatrix.ui.internal.viewsupport.ListContentProvider;
import com.metamatrix.ui.internal.wizard.AbstractWizard;

/**
 * @since 4.0
 */
public final class NewVdbWizard extends AbstractWizard
    implements INewWizard, InternalUiConstants.Widgets, CoreStringUtil.Constants, UiConstants {

    private static final String I18N_PREFIX = I18nUtil.getPropertyPrefix(NewVdbWizard.class);

    private static final String TITLE = getString("title"); //$NON-NLS-1$
    private static final String PAGE_TITLE = getString("pageTitle"); //$NON-NLS-1$
    private static final String VDB_NAME_ERROR = getString("vdbNameError"); //$NON-NLS-1$

    private static final int COLUMN_COUNT = 3;

    private static final String NAME_LABEL = getString("nameLabel"); //$NON-NLS-1$
    private static final String FOLDER_LABEL = getString("folderLabel"); //$NON-NLS-1$

    private static final String INITIAL_MESSAGE = getString("initialMessage"); //$NON-NLS-1$
    private static final String CREATE_FILE_ERROR_MESSAGE = getString("createFileErrorMessage"); //$NON-NLS-1$

    private static final String NOT_MODEL_PROJECT_MSG = getString("notModelProjectMessage"); //$NON-NLS-1$
    private static final String SELECT_FOLDER_MESSAGE = getString("selectFolderMessage"); //$NON-NLS-1$
    private static final StringNameValidator nameValidator = new StringNameValidator(StringNameValidator.DEFAULT_MINIMUM_LENGTH,
                                                                                     StringNameValidator.DEFAULT_MAXIMUM_LENGTH,
                                                                                     new char[] {'\''});

    private static String getString( final String id ) {
        return VdbUiConstants.Util.getString(I18N_PREFIX + id);
    }

    String name;
    IContainer folder;

    private WizardPage pg;
    private Text nameText, folderText;
    private Button btnBrowse;
    private ISelectionStatusValidator projectValidator = new ModelProjectSelectionStatusValidator();

    IStructuredSelection initialSelection;

    /**
     * @since 4.0
     */
    public NewVdbWizard() {
        super(UiPlugin.getDefault(), TITLE, null);
    }

    /**
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     * @since 4.0
     */
    @Override
    public boolean finish() {
        // append VDB file extension if needed
        if (!name.endsWith(ModelerCore.VDB_FILE_EXTENSION)) {
            name += ModelerCore.VDB_FILE_EXTENSION;
        }

        // create VDB resource
        final IRunnableWithProgress op = new IRunnableWithProgress() {
            @SuppressWarnings("unchecked")
			public void run( final IProgressMonitor monitor ) throws InvocationTargetException {
                try {
                    final IFile vdbFile = NewVdbWizard.this.folder.getFile(new Path(NewVdbWizard.this.name));
                    vdbFile.create(new ByteArrayInputStream(new byte[0]), false, monitor);
                    NewVdbWizard.this.folder.refreshLocal(IResource.DEPTH_INFINITE, monitor);


                    // open editor
                    IWorkbenchPage page = UiPlugin.getDefault().getCurrentWorkbenchWindow().getActivePage();
                    IDE.openEditor(page, vdbFile);
                    
                    //Thread.sleep(200);
                    
                    
                    
                    if (initialSelection != null && !initialSelection.isEmpty()) {
                    	if( isAllModelsSelected(initialSelection) ) {
                    		VdbEditor editor = getVdbEditor(vdbFile);
                    		
                    		if( editor != null ) {
	                    		List selectedModels = SelectionUtilities.getSelectedIResourceObjects(initialSelection);
	                    		List<IFile> models = new ArrayList<IFile>();
	                    		
	                    		for (Iterator iter = selectedModels.iterator(); iter.hasNext();) {
	                    			models.add((IFile)iter.next());
	                    		}
	                    		
	                    		editor.addModels(models);
	                    		editor.doSave(new NullProgressMonitor());
                    		}
                    	}
                    }
                    
                } catch (final Exception err) {
                    throw new InvocationTargetException(err);
                } finally {
                    monitor.done();
                }
            }
        };
        try {
            new ProgressMonitorDialog(getShell()).run(false, true, op);
            return true;
        } catch (Throwable err) {
            if (err instanceof InvocationTargetException) {
                err = ((InvocationTargetException)err).getTargetException();
            }
            VdbUiConstants.Util.log(err);
            WidgetUtil.showError(CREATE_FILE_ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
     * @since 4.0
     */
    public void init( final IWorkbench workbench,
                      final IStructuredSelection selection ) {

        if (isAllModelsSelected(selection)) {
            initialSelection = new StructuredSelection(selection.toArray());
        }
        if (selection != null && !selection.isEmpty()) {
            this.folder = ModelUtil.getContainer(selection.getFirstElement());
        }

        if (folder != null && !folderInModelProject()) {
            // Create empty page
            this.pg = new WizardPage(NewVdbWizard.class.getSimpleName(), PAGE_TITLE, null) {
                public void createControl( final Composite parent ) {
                    setControl(createEmptyPageControl(parent));
                }
            };
            this.pg.setMessage(NOT_MODEL_PROJECT_MSG, IMessageProvider.ERROR);
        } else {

            // Create and add page
            this.pg = new WizardPage(NewVdbWizard.class.getSimpleName(), PAGE_TITLE, null) {
                public void createControl( final Composite parent ) {
                    setControl(createPageControl(parent));
                }
            };
            this.pg.setMessage(INITIAL_MESSAGE);

            // If current selection not null, set folder to selection if a folder, or to containing folder if not
            if (this.folder != null) {
                if (!projectValidator.validate(new Object[] {this.folder}).isOK()) {
                    this.folder = null;
                }
            } else { // folder == null
                this.pg.setMessage(SELECT_FOLDER_MESSAGE, IMessageProvider.ERROR);
            }
        }

        this.pg.setPageComplete(false);
        addPage(pg);
    }

    private boolean folderInModelProject() {
        boolean result = false;

        if (this.folder != null) {
            IProject project = this.folder.getProject();
            try {
                if (project != null && project.getNature(PluginConstants.MODEL_PROJECT_NATURE_ID) != null) {
                    result = true;
                }
            } catch (CoreException ex) {
                VdbUiConstants.Util.log(ex);
            }
        }

        return result;
    }

    /**
     * Indicates if all selected objects are {@link IResource}s.
     * 
     * @param theSelection the selection being checked
     * @return <code>true</code> if all selected objects are <code>EObject</code>; <code>false</code> otherwise.
     */
    @SuppressWarnings( "unchecked" ) boolean isAllModelsSelected( ISelection theSelection ) {
        boolean result = ((theSelection != null) && !theSelection.isEmpty() && (theSelection instanceof IStructuredSelection) && SelectionUtilities.isAllIResourceObjects(theSelection));

        if (result) {
            List selectedObjects = SelectionUtilities.getSelectedObjects(theSelection);
            for (Iterator iter = selectedObjects.iterator(); iter.hasNext();) {
                IResource res = (IResource)iter.next();
                if (!ModelUtilities.isModelFile(res)) {
                    result = false;
                    break;
                }
            }

        }

        return result;
    }
    
    private boolean isAllModelsSelected() {
    	return isAllModelsSelected(this.initialSelection);
    }

    /**
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     * @since 4.0
     */
    @Override
    public boolean canFinish() {
        // defect 16154 -- Finish can be enabled even if errors on page.
        // check the page's isComplete status (in super) -- just follow its advice.
        return super.canFinish();
    }

    Composite createEmptyPageControl( final Composite parent ) {
        return new Composite(parent, SWT.NONE);
    }

    /**
     * @param parent 
     * @return composite the page
     * @since 4.0
     */
    @SuppressWarnings("unchecked")
	Composite createPageControl( final Composite parent ) {
        // Create page
        final Composite pg = new Composite(parent, SWT.NONE);
        pg.setLayout(new GridLayout(COLUMN_COUNT, false));
        // Add widgets to page
        WidgetFactory.createLabel(pg, FOLDER_LABEL);
        final String name = (this.folder == null ? null : this.folder.getFullPath().makeRelative().toString());
        this.folderText = WidgetFactory.createTextField(pg, GridData.FILL_HORIZONTAL, 1, name, SWT.READ_ONLY);
        this.folderText.addModifyListener(new ModifyListener() {
            public void modifyText( final ModifyEvent event ) {
                folderModified();
            }
        });
        btnBrowse = WidgetFactory.createButton(pg, BROWSE_BUTTON);
        btnBrowse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( final SelectionEvent event ) {
                browseButtonSelected();
            }
        });
        WidgetFactory.createLabel(pg, NAME_LABEL);
        this.nameText = WidgetFactory.createTextField(pg, GridData.HORIZONTAL_ALIGN_FILL, COLUMN_COUNT - 1);
        this.nameText.addModifyListener(new ModifyListener() {
            public void modifyText( final ModifyEvent event ) {
                nameModified();
            }
        });

        // set focus to browse button if no folder selected. otherwise set focus to text field
        if (folder == null) {
            btnBrowse.setFocus();
        } else {
            nameText.setFocus();
        }
        
        if( isAllModelsSelected() ) {
	        Group group = WidgetFactory.createGroup(pg, getString("selectedModelsGroupTitle"), GridData.FILL_BOTH, COLUMN_COUNT, COLUMN_COUNT); //$NON-NLS-1$
	        TableViewer viewer = new TableViewer(group);
	        GridData gdv = new GridData(GridData.FILL_BOTH);
	        //gdv.horizontalSpan = COLUMN_COUNT;
	        viewer.getControl().setLayoutData(gdv);
	        viewer.setContentProvider(new ListContentProvider());
	        viewer.setLabelProvider(new ModelExplorerLabelProvider());
	        List selectedModels = SelectionUtilities.getSelectedIResourceObjects(initialSelection);
	        viewer.setInput(selectedModels);
        }
        return pg;
    }

    /**
     * @since 4.0
     */
    void browseButtonSelected() {
        this.folder = WidgetUtil.showFolderSelectionDialog(this.folder, new ModelingResourceFilter(), projectValidator);

        if (folder != null) {
            this.folderText.setText(folder.getFullPath().makeRelative().toString());

            if (CoreStringUtil.isEmpty(nameText.getText())) {
                nameText.setFocus();
            }
        }

        validatePage();
    }

    /**
     * @since 4.0
     */
    void folderModified() {
        validatePage();
    }

    /**
     * @since 4.0
     */
    void nameModified() {
        validatePage();
    }

    /**
     * @since 4.0
     */
    private void validatePage() {
        final IContainer folder;
        try {
            folder = WizardUtil.validateFileAndFolder(this.nameText,
                                                      this.folderText,
                                                      this.pg,
                                                      ModelerCore.VDB_FILE_EXTENSION,
                                                      false);
            if (this.pg.getMessageType() == IMessageProvider.ERROR) {
                // WizardUtil.validateFileAndFolder can set error message and message type so no need to do further
                // validation if an error was already found (JBEDSP-588)
                return;
            }

            IStatus status = projectValidator.validate(new Object[] {folder});
            String proposedName = this.nameText.getText();

            if (!status.isOK()) {
                // only update the message if the vFolder is non-null;
                // if WizardUtil returned null, it already set the status
                // this corrects the case where the wrong message shows for
                // a bad filename.
                if (folder != null) {
                    this.pg.setErrorMessage(status.getMessage());
                    this.pg.setPageComplete(false);
                } // endif
            } else if (!nameValidator.isValidName(proposedName)) {
                this.pg.setErrorMessage(VDB_NAME_ERROR);
                this.pg.setPageComplete(false);
            } else if (ModelUtilities.vdbNameReservedValidation(proposedName) != null) {
                this.pg.setErrorMessage(ModelUtilities.vdbNameReservedValidation(proposedName));
                this.pg.setPageComplete(false);
            } else {
                this.pg.setErrorMessage(null);
                this.pg.setPageComplete(true);
            }

            if (this.pg.isPageComplete()) {
                this.name = proposedName;
                this.folder = folder;
            }
        } catch (final CoreException err) {
            VdbUiConstants.Util.log(err);
            WizardUtil.setPageComplete(this.pg, err.getLocalizedMessage(), IMessageProvider.ERROR);
        }
    }
    
	/**
	 * Finds the visible VDB Editor for the supplied VDB
	 * 
	 * If an editor is NOT open for this vdb, then null is returned.
	 * 
	 * @param vdb
	 * @return the VdbEditor
	 */
	public VdbEditor getVdbEditor(final IFile vdb) {
		final IWorkbenchWindow window = UiPlugin.getDefault()
				.getCurrentWorkbenchWindow();

		if (window != null) {
			final IWorkbenchPage page = window.getActivePage();

			if (page != null) {
				VdbEditor editor = findEditorPart(page, vdb);
				if( editor != null ) {
					return editor;
				}
			}
		}
		return null;
	}

	private VdbEditor findEditorPart(final IWorkbenchPage page, IFile vdbFile) {
		// look through the open editors and see if there is one available for
		// this model file.
		final IEditorReference[] editors = page.getEditorReferences();
		for (int i = 0; i < editors.length; ++i) {

			final IEditorPart editor = editors[i].getEditor(false);
			if (editor instanceof VdbEditor) {
				final VdbEditor vdbEditor = (VdbEditor) editor;
				final IPath editorVdbPath = vdbEditor.getVdb().getName();
				if (vdbFile.getFullPath().equals(editorVdbPath))
					return vdbEditor;

			}
		}

		return null;
	}
}