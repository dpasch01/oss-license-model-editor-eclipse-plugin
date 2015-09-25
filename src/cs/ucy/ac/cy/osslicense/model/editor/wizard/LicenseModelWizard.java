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
		System.out.println("License: "+wizardPage.getLicenseTitle());
		System.out.println("Rights:");
		for(String right:wizardPage.getLicenseRights()){
			System.out.println(right);
		}
		
		System.out.println("Obligations:");
		for(String obligation:wizardPage.getLicenseObligations()){
			System.out.println(obligation);
		}
		
		System.out.println("LimitedLiability: "+wizardPage.hasLimitedLiability());
		System.out.println("ProvidedWithoutWarranty: "+wizardPage.hasProvidedWithoutWarranty());
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
