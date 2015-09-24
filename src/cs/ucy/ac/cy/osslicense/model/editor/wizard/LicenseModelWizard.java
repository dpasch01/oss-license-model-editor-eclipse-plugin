package cs.ucy.ac.cy.osslicense.model.editor.wizard;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class LicenseModelWizard extends Wizard implements INewWizard {

	LicenseModelWizardPage wizardPage;
	ISelection selection;
	
	public LicenseModelWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "Export My Data";
	}

	@Override
	public void addPages() {
		wizardPage = new LicenseModelWizardPage("Haloumi");
		addPage(wizardPage);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
