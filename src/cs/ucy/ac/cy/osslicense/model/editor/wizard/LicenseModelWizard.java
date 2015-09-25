package cs.ucy.ac.cy.osslicense.model.editor.wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import cs.ucy.ac.cy.osslicense.model.editor.licensefile.LicenseFileGenerator;

public class LicenseModelWizard extends Wizard implements INewWizard {

	LicenseModelWizardPage wizardPage;
	ISelection selection;

	public LicenseModelWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "Create a License Model File";
	}

	@Override
	public void addPages() {
		wizardPage = new LicenseModelWizardPage("License Model Wizard");
		addPage(wizardPage);
	}

	@Override
	public boolean performFinish() {
		
		File licenseFile = null;

		try {
			licenseFile = LicenseFileGenerator.createFile(wizardPage.getLicenseTitle(), wizardPage.getLicenseRights(),
					wizardPage.getLicenseObligations(), wizardPage.hasLimitedLiability(),
					wizardPage.hasProvidedWithoutWarranty(), extractSelection(selection).getLocation().toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			ResourcesPlugin.getWorkspace().getRoot().getProject(extractSelection(selection).getName())
					.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}

//		Path path = new Path(licenseFile.getPath());
//		IFile fileInput = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
//			IDE.openEditor(page, fileInput);
			IDE.openEditor(page, licenseFile.toURI(),"cs.ucy.ac.cy.osslicense.model.editor.LicenseModelEditor",true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	private IResource extractSelection(ISelection sel) {
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
