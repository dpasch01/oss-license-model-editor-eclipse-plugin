package cs.ucy.ac.cy.osslicense.model.editor.wizard;

import org.eclipse.jface.wizard.Wizard;

public class LicenseModelWizard extends Wizard {

	LicenseModelWizardPage wizardPage;

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
		System.out.println(wizardPage.getText1());
		return true;
	}

}
