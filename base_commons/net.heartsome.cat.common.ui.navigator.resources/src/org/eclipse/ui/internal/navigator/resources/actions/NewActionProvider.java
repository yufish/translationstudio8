/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.navigator.resources.resource.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.navigator.WizardActionGroup;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardRegistry;

/**
 * Provides the new (artifact creation) menu options for a context menu.
 * 
 * <p>
 * The added submenu has the following structure
 * </p>
 * 
 * <ul>
 * <li>a new generic project wizard shortcut action, </li>
 * <li>a separator, </li>
 * <li>a set of context senstive wizard shortcuts (as defined by
 * <b>org.eclipse.ui.navigator.commonWizard</b>), </li>
 * <li>another separator, </li>
 * <li>a generic examples wizard shortcut action, and finally </li>
 * <li>a generic "Other" new wizard shortcut action</li>
 * </ul>
 * 
 * @since 3.2
 * 
 */
public class NewActionProvider extends CommonActionProvider {

	private static final String FULL_EXAMPLES_WIZARD_CATEGORY = "org.eclipse.ui.Examples"; //$NON-NLS-1$

	private static final String NEW_MENU_NAME = "common.new.menu";//$NON-NLS-1$

	private ActionFactory.IWorkbenchAction showDlgAction;

	private IAction newProjectAction;

	private IAction newExampleAction;

	private WizardActionGroup newWizardActionGroup;

	private boolean contribute = false;

	public void init(ICommonActionExtensionSite anExtensionSite) {

		if (anExtensionSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			IWorkbenchWindow window = ((ICommonViewerWorkbenchSite) anExtensionSite.getViewSite()).getWorkbenchWindow();
			showDlgAction = ActionFactory.NEW_WIZARD_DROP_DOWN.create(window);
			showDlgAction.setText(WorkbenchNavigatorMessages.actions_NewActionProvider_NewMenu_label);
			contribute = true;
		}
	}

	/**
	 * Adds a submenu to the given menu with the name "group.new" see
	 * {@link ICommonMenuConstants#GROUP_NEW}). The submenu contains the following structure:
	 * 
	 * <ul>
	 * <li>a new generic project wizard shortcut action, </li>
	 * <li>a separator, </li>
	 * <li>a set of context senstive wizard shortcuts (as defined by
	 * <b>org.eclipse.ui.navigator.commonWizard</b>), </li>
	 * <li>another separator, </li>
	 * <li>a generic examples wizard shortcut action, and finally </li>
	 * <li>a generic "Other" new wizard shortcut action</li>
	 * </ul>
	 */
	public void fillContextMenu(IMenuManager menu) {
		// append the submenu after the GROUP_NEW group.
		menu.insertAfter(ICommonMenuConstants.GROUP_NEW, showDlgAction);
	}

	/**
	 * Return whether or not any examples are in the current install.
	 * 
	 * @return True if there exists a full examples wizard category.
	 */
	private boolean hasExamples() {
		IWizardRegistry newRegistry = PlatformUI.getWorkbench().getNewWizardRegistry();
		IWizardCategory category = newRegistry.findCategory(FULL_EXAMPLES_WIZARD_CATEGORY);
		return category != null;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#dispose()
	 */
	public void dispose() {
		if (showDlgAction!=null) {
			showDlgAction.dispose();
			showDlgAction = null;
		}
		super.dispose();
	}
}
