/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.transformation.ui.editors.sqleditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;
import org.teiid.designer.core.ModelerCore;
import org.teiid.designer.core.query.QueryValidationResult;
import org.teiid.designer.core.query.QueryValidator;
import org.teiid.designer.query.IQueryParser;
import org.teiid.designer.query.metadata.IQueryMetadataInterface;
import org.teiid.designer.query.sql.lang.ICommand;
import org.teiid.designer.transformation.ui.UiConstants;
import org.teiid.designer.ui.common.graphics.ColorManager;
import org.teiid.query.ui.sqleditor.component.QueryDisplayComponent;


/**
 * SqlEditorPanel
 *
 * @since 8.0
 */
public class SqlDisplayPanel extends Composite implements UiConstants {

    private ColorManager colorManager;
    private IVerticalRuler verticalRuler;
    private SqlTextViewer sqlTextViewer;
    private Document sqlDocument;
    private QueryDisplayComponent queryDisplayComponent;

    /** The width of the vertical ruler. */
    protected final static int VERTICAL_RULER_WIDTH = 0;

    /**
     * Constructor.
     * 
     * @param parent Parent of this control
     */
    public SqlDisplayPanel( Composite parent ) {
        super(parent, SWT.READ_ONLY);
        init();
    }

    /**
     * Initialize the panel.
     */
    private void init() {
        QueryValidator validator = new QueryValidatorImpl();
        queryDisplayComponent = new QueryDisplayComponent(validator, QueryValidator.UNKNOWN_TRNS);

        colorManager = new ColorManager();
        verticalRuler = new VerticalRuler(VERTICAL_RULER_WIDTH);

        int styles = SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.FULL_SELECTION;

        sqlTextViewer = new SqlTextViewer(this, verticalRuler, styles, colorManager);

        sqlDocument = new Document();
        sqlTextViewer.setDocument(sqlDocument);
        sqlTextViewer.setEditable(false);

        sqlTextViewer.setRangeIndicator(new DefaultRangeIndicator());

        // Set overall grid layout
        Control control = sqlTextViewer.getControl();
        GridLayout gridLayout = new GridLayout();
        this.setLayout(gridLayout);
        gridLayout.numColumns = 1;
        GridData gridData = new GridData(GridData.FILL_BOTH);
        this.setLayoutData(gridData);
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;

        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        control.setLayoutData(gridData);
    }

    /**
     * Sets the SQL Statement on the Panel
     * 
     * @param SQLString the SQL String to set on the panel
     */
    public void setText( final String sql ) {
        if (sql != null) {
            queryDisplayComponent.setText(sql, true, null);

            // Refresh the EditorPanel, using the queryDisplayComponent
            refreshWithDisplayComponent();
        }
    }

    public void setQueryValidator( QueryValidator validator ) {
        this.queryDisplayComponent.setQueryValidator(validator);
    }

    public String getText() {
        return sqlDocument.get();
    }

    /**
     * Refreshes the Query JTextPane with the contents of the queryDisplayComponent
     */
    private void refreshWithDisplayComponent() {
        String sql = queryDisplayComponent.toString();
        sqlDocument.set(sql);
    }

    /**
     * Get the Command for the currently displayed SQL
     * 
     * @return the command, null if the query is not both parseable and resolvable
     */
    public ICommand getCommand() {
        return queryDisplayComponent.getCommand();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public TextViewer getTextViewer() {

        return sqlTextViewer;
    }

    /**
     * This class is an implementation of the QueryValidator which only parses the supplied sql String.
     */
    class QueryValidatorImpl implements QueryValidator {

        /**
         * Validate the sqlString of the given type, valid types are {@link QueryValidator#SELECT_TRNS}, {@link QueryValidator#INSERT_TRNS}, {@link QueryValidator#UPDATE_TRNS}
         * , {@link QueryValidator#DELETE_TRNS} {@link QueryValidator#UNKNOWN_TRNS}, if this is a unknown transform, the query will only be parsed.
         * 
         * @param sqlString The sqlString that needs to be validate
         * @param type The type of sql being passed in
         * @param isUUIDSql Boolean to determine if this is a UUID sql string
         * @param cacheResult Boolean to determine if the result needs to be cached.
         * @return The ValidationResult.
         */
        @Override
		public QueryValidationResult validateSql( String sqlString,
                                                  int type,
                                                  boolean cacheResult ) {
            ICommand command = null;
            IStatus status = null;
            try {
                // QueryParser is not thread-safe, get new parser each time
                IQueryParser parser = ModelerCore.getTeiidQueryService().getQueryParser();
                command = parser.parseDesignerCommand(sqlString);
            } catch (Exception e) {
                status = new Status(IStatus.ERROR, org.teiid.query.ui.UiConstants.PLUGIN_ID, 0, e.getMessage(), e);
            }

            return new QueryValidationResultImpl(command, status);
        }

        /**
         * The QueryMetadataInterface used to validate the sql.
         * 
         * @return QueryMetadataInterface
         */
        @Override
		public IQueryMetadataInterface getQueryMetadata() {
            return null;
        }

        @Override
		public boolean isValidRoot() {
            return true;
        }

		@Override
		public EObject getTransformationRoot() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setElementSymbolOptimization( ElementSymbolOptimization status ) {
		}

    }

    /**
     * QueryValidationResultImpl
     */
    public class QueryValidationResultImpl implements QueryValidationResult {

        private boolean isParsable = false;
        private boolean isResolvable = false;
        private boolean isValidatable = false;
        private ICommand command = null;
        private Collection<IStatus> statuses = null;

        /**
         * Construct an instance of SqlTransformationResult.
         */
        public QueryValidationResultImpl( final ICommand command,
                                          IStatus status ) {
            if (status != null) {
                this.statuses = new ArrayList<IStatus>(1);
                this.statuses.add(status);
            } else {
                this.statuses = Collections.emptyList();
            }
            this.command = command;
            isParsable = command != null ? true : false;
        }

        /**
         * get the Parsable status
         * 
         * @return 'true' if parsable, 'false' if not
         */
        @Override
		public boolean isParsable() {
            return this.isParsable;
        }

        /**
         * get the Resolvable status
         * 
         * @return 'true' if resolvable, 'false' if not
         */
        @Override
		public boolean isResolvable() {
            return this.isResolvable;
        }

        /**
         * get the Validatable status
         * 
         * @return 'true' if validatable, 'false' if not
         */
        @Override
		public boolean isValidatable() {
            return this.isValidatable;
        }

        /**
         * Get the Command language object. This will be null if the SQL String was not parsable.
         * 
         * @return the SQL command
         */
        @Override
		public ICommand getCommand() {
            return this.command;
        }

        /**
         * @see org.teiid.designer.core.query.QueryValidationResult#getStatusList()
         * @since 4.2
         */
        @Override
		public Collection<IStatus> getStatusList() {
            return this.statuses;
        }

		@Override
		public Collection<IStatus> getUpdateStatusList() {
			// TODO Auto-generated method stub
			return Collections.EMPTY_LIST;
		}

		@Override
		public Collection<IStatus> getUpdateStatusList(int cmdType) {
			// TODO Auto-generated method stub
			return Collections.EMPTY_LIST;
		}

    }
}
