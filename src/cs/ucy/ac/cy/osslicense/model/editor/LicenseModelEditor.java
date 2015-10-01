package cs.ucy.ac.cy.osslicense.model.editor;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import cs.ucy.ac.cy.osslicense.model.editor.extractor.LicenseExtractor;
import cs.ucy.ac.cy.osslicense.model.editor.form.FormEditor;
import cs.ucy.ac.cy.osslicense.model.editor.graph.GraphEditor;
import cs.ucy.ac.cy.osslicense.model.editor.licensefile.LicenseFileGenerator;
import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;

public class LicenseModelEditor extends MultiPageEditorPart {

	private TextEditor turtleEditor;
	private FormEditor formEditor;
	private LicenseModel licenseModel;
	private GraphEditor graphEditor;

	@Override
	protected void createPages() {
		IEditorInput input = this.getEditorInput();
		FileEditorInput fileInput = (FileEditorInput) input;

		LicenseExtractor lExtractor = new LicenseExtractor(fileInput.getPath().toString());
		licenseModel = new LicenseModel(lExtractor.extractLicenseIdentifier());
		licenseModel.setRights(lExtractor.extractRights());
		licenseModel.setObligations(lExtractor.extractObligations());
		licenseModel.setAdditionalConditions(lExtractor.extractAdditionalConditions());

		try {
			createFormEditor(-1);
			createTurtlePage();
			createGraphEditor();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	private void createFormEditor(int index) throws PartInitException {
		formEditor = new FormEditor(licenseModel);
		if (index < 0) {
			index = addPage(formEditor, getEditorInput());
		}
		setPageText(index, "Preferences");
	}

	private void createGraphEditor() throws PartInitException {
		graphEditor = new GraphEditor(licenseModel);
		int index = addPage(graphEditor, getEditorInput());
		setPageText(index, "Preview");
	}

	private void createTurtlePage() throws PartInitException {
		turtleEditor = new TextEditor();
		int index = this.addPage(turtleEditor, getEditorInput());
		this.setPageText(index, "License model");
	}

	public void updateModel() {
		IEditorInput input = this.getEditorInput();
		FileEditorInput fileInput = (FileEditorInput) input;

		LicenseExtractor lExtractor = new LicenseExtractor(fileInput.getPath().toString());
		LicenseModel lModel = new LicenseModel(lExtractor.extractLicenseIdentifier());
		lModel.setRights(lExtractor.extractRights());
		lModel.setObligations(lExtractor.extractObligations());
		lModel.setAdditionalConditions(lExtractor.extractAdditionalConditions());
		this.licenseModel = lModel;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		IEditorInput input = turtleEditor.getEditorInput();
		FileEditorInput turtleInput = (FileEditorInput) input;

		if (formEditor.isDirty()) {

			formEditor.doSave(monitor);
			try {
				LicenseFileGenerator.updateFile(formEditor.getLicenseIdentifierInput(),
						formEditor.getLicenseRightsInput(), formEditor.getLicenseObligationsInput(),
						formEditor.hasLimitedLiability(), formEditor.hasProvidedWithoutWarranty(),
						turtleInput.getPath().toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				turtleInput.getFile().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
			updateModel();
			graphEditor.update(this.licenseModel);

		} else if (turtleEditor.isDirty()) {

			turtleEditor.doSave(monitor);
			updateModel();
			formEditor.update(this.getLicenseModel());
			graphEditor.update(this.licenseModel);

		} else if (graphEditor.isDirty()) {

			graphEditor.doSave(monitor);
			System.out.println("Save Graph: " + graphEditor.getLicenseModel().getAdditionalConditions());
			try {
				LicenseFileGenerator.updateFile(graphEditor.getLicenseModel(), turtleInput.getPath().toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				turtleInput.getFile().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
			updateModel();
			formEditor.update(this.getLicenseModel());

		}
	}

	@Override
	public void doSaveAs() {
		turtleEditor.doSaveAs();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public LicenseModel getLicenseModel() {
		return licenseModel;
	}

	public void setLicenseModel(LicenseModel licenseModel) {
		this.licenseModel = licenseModel;
	}

}
