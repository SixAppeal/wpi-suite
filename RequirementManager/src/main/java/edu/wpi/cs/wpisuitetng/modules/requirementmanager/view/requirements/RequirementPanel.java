/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.tabs.RequirementTabsPanel;

/**
 * @version $Revision: 1.0 $
 * @author Rolling Thunder
 */
public class RequirementPanel extends JPanel implements RequirementButtonListener {
    protected static final long serialVersionUID = -4952819151288519999L;

    private ViewEventController viewEventController = ViewEventController.getInstance();
    private UpdateRequirementController updateRequirementController = UpdateRequirementController.getInstance();

    private List<RequirementPanelListener> listeners = new LinkedList<RequirementPanelListener>();
    private Requirement displayRequirement;
    private ViewMode viewMode;

    private RequirementInformationPanel infoPanel;
    private RequirementTabsPanel tabsPanel;
    private RequirementButtonPanel buttonPanel;

    private RequirementConfirmationDialog confirmDialog;

    private boolean readyToClose = false;

    /**
     * Constructor for editing a requirement
     * 
     * @param editingRequirement requirement to edit
     */
    public RequirementPanel(Requirement editingRequirement) {
        viewMode = (ViewMode.EDITING);

        displayRequirement = editingRequirement;
        this.buildLayout();

        confirmDialog = new RequirementConfirmationDialog();
    }

    /**
     * Constructor for creating a requirement
     * 
     * @param parentID the parent id, or -1 if no parent.
     */
    public RequirementPanel(int parentID) {
        viewMode = (ViewMode.CREATING);

        displayRequirement = new Requirement();
        displayRequirement.setId(-2);

        try {
            displayRequirement.setParentID(parentID);
        } catch (Exception e) {
            //TODO: Stop swallowing exceptions
            e.printStackTrace();
        }
        this.buildLayout();
    }

    /**
     * Builds the layout of the panel.
     */
    private void buildLayout() {
        buttonPanel = new RequirementButtonPanel(this, viewMode, displayRequirement);
        listeners.add(buttonPanel);
        tabsPanel = new RequirementTabsPanel(this, viewMode, displayRequirement);
        listeners.add(tabsPanel);
        infoPanel = new RequirementInformationPanel(this, viewMode, displayRequirement);
        listeners.add(infoPanel);

        JSplitPane contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, infoPanel, tabsPanel);

        this.setLayout(new BorderLayout());
        this.add(contentPanel, BorderLayout.CENTER); // Add scroll pane to panel
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Method OKPressed.
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementButtonListener#OKPressed()
     */
    @Override
    public void OKPressed() {
        if (infoPanel.validateFields(true)) {
            infoPanel.update();
            readyToClose = true;
            viewEventController.removeTab(this);
        }
    }

    /**
     * Method clearPressed.
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementButtonListener#clearPressed()
     */
    @Override
    public void clearPressed() {
        infoPanel.clearInfo();
    }

    /**
     * Method cancelPressed.
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementButtonListener#cancelPressed()
     */
    @Override
    public void cancelPressed() {
        viewEventController.refreshTable();
        viewEventController.refreshTree();
        viewEventController.removeTab(this);
    }

    /**
     * Deletes the requirement. Sets all fields uneditable, sets status to
     * deleted and closes the tab.
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementButtonListener#deletePressed()
     */
    public void deletePressed() {
        if (this.displayRequirement.getStatus() == RequirementStatus.INPROGRESS)
            return;
        readyToClose = true;
        displayRequirement.setStatus(RequirementStatus.DELETED);

        updateRequirementController.updateRequirement(displayRequirement);

        viewEventController.refreshTable();
        viewEventController.refreshTree();
        viewEventController.removeTab(this);
    }

    /**
     * Fires to all listeners whether the requirement has been deleted or not
     * 
     * @param b whether the requirement has been deleted or not.
     */
    public void fireDeleted(boolean b) {
        for (RequirementPanelListener listener : listeners) {
            listener.fireDeleted(b);
        }
    }

    /**
     * Fires to all listeners whether the requirement is valid or not
     * 
     * @param b whether the requirement is valid or not.
     */
    public void fireValid(boolean b) {
        for (RequirementPanelListener listener : listeners) {
            listener.fireValid(b);
        }
    }

    /**
     * Fires to all listeners whether changes have occured
     * 
     * @param b whether changes have occured.
     */
    public void fireChanges(boolean b) {
        for (RequirementPanelListener listener : listeners) {
            listener.fireChanges(b);
        }
    }

    /**
     * Fires to all listeners to refresh.
     */
    public void fireRefresh() {
        for (RequirementPanelListener listener : listeners) {
            listener.fireRefresh();
        }
    }

    /**
     * displays the given error message
     * 
     * @param msg the message to display.
     */
    public void displayError(String msg) {
        buttonPanel.getErrorPanel().displayError(msg);
    }

    /**
     * Removes the given error message
     * 
     * @param msg the message to display.
     */
    public void removeError(String msg) {
        buttonPanel.getErrorPanel().removeError(msg);
    }

    /**
     * @return whether the requirement panel as a whole is ready to be removed.
     */
    public boolean readyToRemove() {
        if (readyToClose) {
            return true;
        }

        //If there is anything with unsaved changes, confirm
        for (RequirementPanelListener listener : listeners) {
            if (!listener.readyToRemove()) {

                return confirmDialog.confirmExit(this);
            }
        }

        return true;
    }

    //Getters and Setters

    /**
     * @return the requirement information panel.
     */
    public RequirementInformationPanel getInfoPanel() {
        return this.infoPanel;
    }

    /**
     * @return the button panel
     */
    public RequirementButtonPanel getButtonPanel() {
        return this.buttonPanel;
    }

    /**
     * @return the display requirement.
     */
    public Requirement getDisplayRequirement() {
        return displayRequirement;
    }

    /**
     * @return the tabs panel
     */
    public RequirementTabsPanel getTabsPanel() {
        return tabsPanel;
    }

    /**
     * @return the listeners
     */
    protected List<RequirementPanelListener> getListeners() {
        return this.listeners;
    }

    /**
     * @param listeners the listeners to set
     */
    protected void setListeners(List<RequirementPanelListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * @return the viewMode
     */
    protected ViewMode getViewMode() {
        return this.viewMode;
    }

    /**
     * @param viewMode the viewMode to set
     */
    protected void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    /**
     * @return the readyToClose
     */
    protected boolean isReadyToClose() {
        return this.readyToClose;
    }

    /**
     * @param readyToClose the readyToClose to set
     */
    protected void setReadyToClose(boolean readyToClose) {
        this.readyToClose = readyToClose;
    }

    /**
     * @param displayRequirement the displayRequirement to set
     */
    protected void setDisplayRequirement(Requirement displayRequirement) {
        this.displayRequirement = displayRequirement;
    }

    /**
     * @param infoPanel the infoPanel to set
     */
    protected void setInfoPanel(RequirementInformationPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    /**
     * @param tabsPanel the tabsPanel to set
     */
    protected void setTabsPanel(RequirementTabsPanel tabsPanel) {
        this.tabsPanel = tabsPanel;
    }

    /**
     * @param buttonPanel the buttonPanel to set
     */
    protected void setButtonPanel(RequirementButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    /**
     * @return the viewEventController
     */
    protected ViewEventController getViewEventController() {
        return this.viewEventController;
    }

    /**
     * @param viewEventController the viewEventController to set
     */
    protected void setViewEventController(ViewEventController viewEventController) {
        this.viewEventController = viewEventController;
    }

    /**
     * @return the updateRequirementController
     */
    protected UpdateRequirementController getUpdateRequirementController() {
        return this.updateRequirementController;
    }

    /**
     * @param updateRequirementController the updateRequirementController to set
     */
    protected void setUpdateRequirementController(UpdateRequirementController updateRequirementController) {
        this.updateRequirementController = updateRequirementController;
    }

    /**
     * @return the confirmDialog
     */
    public RequirementConfirmationDialog getConfirmDialog() {
        return this.confirmDialog;
    }

    /**
     * @param confirmDialog the confirmDialog to set
     */
    public void setConfirmDialog(RequirementConfirmationDialog confirmDialog) {
        this.confirmDialog = confirmDialog;
    }

}
