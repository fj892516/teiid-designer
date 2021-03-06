/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.ui.common.viewsupport;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;

/**
 * A settable IStatus.
 * Can be an error, warning, info or ok. For error, info and warning states,
 * a message describes the problem.
 *
 * @since 8.0
 */
public class StatusInfo implements IStatus {

    private String fStatusMessage;
    private int fSeverity;
    private String pluginID;

    /**
     * Creates a status set to OK (no message)
     */
    public StatusInfo(String pluginID) {
        this(pluginID, OK, null);
    }

    /**
     * Creates a status .
     * @param severity The status severity: ERROR, WARNING, INFO and OK.
     * @param message The message of the status. Applies only for ERROR,
     * WARNING and INFO.
     */
    public StatusInfo(String pluginID, int severity, String message) {
        fStatusMessage= message;
        fSeverity= severity;
    }

    /**
     *  Returns if the status' severity is OK.
     */
    @Override
	public boolean isOK() {
        return fSeverity == IStatus.OK;
    }

    /**
     *  Returns if the status' severity is WARNING.
     */
    public boolean isWarning() {
        return fSeverity == IStatus.WARNING;
    }

    /**
     *  Returns if the status' severity is INFO.
     */
    public boolean isInfo() {
        return fSeverity == IStatus.INFO;
    }

    /**
     *  Returns if the status' severity is ERROR.
     */
    public boolean isError() {
        return fSeverity == IStatus.ERROR;
    }

    /**
     * @see IStatus#getMessage
     */
    @Override
	public String getMessage() {
        return fStatusMessage;
    }

    /**
     * Sets the status to ERROR.
     * @param The error message (can be empty, but not null)
     */
    public void setError(String errorMessage) {
        Assert.isNotNull(errorMessage);
        fStatusMessage= errorMessage;
        fSeverity= IStatus.ERROR;
    }

    /**
     * Sets the status to WARNING.
     * @param The warning message (can be empty, but not null)
     */
    public void setWarning(String warningMessage) {
        Assert.isNotNull(warningMessage);
        fStatusMessage= warningMessage;
        fSeverity= IStatus.WARNING;
    }

    /**
     * Sets the status to INFO.
     * @param The info message (can be empty, but not null)
     */
    public void setInfo(String infoMessage) {
        Assert.isNotNull(infoMessage);
        fStatusMessage= infoMessage;
        fSeverity= IStatus.INFO;
    }

    /*
     * @see IStatus#matches(int)
     */
    @Override
	public boolean matches(int severityMask) {
        return (fSeverity & severityMask) != 0;
    }

    /**
     * Returns always <code>false</code>.
     * @see IStatus#isMultiStatus()
     */
    @Override
	public boolean isMultiStatus() {
        return false;
    }

    /*
     * @see IStatus#getSeverity()
     */
    @Override
	public int getSeverity() {
        return fSeverity;
    }

    /*
     * @see IStatus#getPlugin()
     */
    @Override
	public String getPlugin() {
        return this.pluginID;
    }

    /**
     * Returns always <code>null</code>.
     * @see IStatus#getException()
     */
    @Override
	public Throwable getException() {
        return null;
    }

    /**
     * Returns always the error severity.
     * @see IStatus#getCode()
     */
    @Override
	public int getCode() {
        return fSeverity;
    }

    /**
     * Returns always <code>null</code>.
     * @see IStatus#getChildren()
     */
    @Override
	public IStatus[] getChildren() {
        return new IStatus[0];
    }

}
