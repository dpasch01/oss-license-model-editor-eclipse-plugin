package cs.ucy.ac.cy.osslicense.model.editor;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import cs.ucy.ac.cy.osslicense.model.editor.extractor.LicenseExtractor;
import cs.ucy.ac.cy.osslicense.model.editor.form.FormEditor;
import cs.ucy.ac.cy.osslicense.model.editor.licensefile.LicenseFileGenerator;
import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;
import cs.ucy.ac.cy.osslicense.model.editor.visualizer.Edge;
import cs.ucy.ac.cy.osslicense.model.editor.visualizer.LicenseVisualizer;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class LicenseModelEditor extends MultiPageEditorPart {

	private TextEditor turtleEditor;
	private FormEditor formEditor;
	private LicenseModel licenseModel;

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
			createGraphPage();
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

	private void createGraphPage() throws PartInitException {
		Composite composite = new Composite(getContainer(), SWT.EMBEDDED);
		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		java.awt.Frame frame = SWT_AWT.new_Frame(composite);
		javax.swing.JPanel panel = new javax.swing.JPanel();
		frame.add(panel);

		IEditorInput input = turtleEditor.getEditorInput();
		FileEditorInput turtleInput = (FileEditorInput) input;

		LicenseExtractor lExtractor = new LicenseExtractor(turtleInput.getPath().toString());
		LicenseModel lModel = new LicenseModel(lExtractor.extractLicenseIdentifier());
		lModel.setRights(lExtractor.extractRights());
		lModel.setObligations(lExtractor.extractObligations());
		lModel.setAdditionalConditions(lExtractor.extractAdditionalConditions());
		LicenseVisualizer lVizualizer = new LicenseVisualizer(lModel);

		VisualizationViewer<String, Edge> vv = lVizualizer.generateGraphView();

		frame.add(vv);
		frame.pack();
		frame.setVisible(true);

		int index = this.addPage(composite);
		this.setPageText(index, "Preview");
	}

	private void createTurtlePage() throws PartInitException {
		turtleEditor = new TextEditor();
		int index = this.addPage(turtleEditor, getEditorInput());
		this.setPageText(index, "License model");
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
		} else if (turtleEditor.isDirty()) {
			turtleEditor.doSave(monitor);
			LicenseExtractor lExtractor = new LicenseExtractor(turtleInput.getPath().toString());
			LicenseModel lModel = new LicenseModel(lExtractor.extractLicenseIdentifier());
			lModel.setRights(lExtractor.extractRights());
			lModel.setObligations(lExtractor.extractObligations());
			lModel.setAdditionalConditions(lExtractor.extractAdditionalConditions());
			System.out.println(lModel.getLicenseIdentifier());
			formEditor.update(lModel);
		}
		this.removePage(2);
		try {
			this.createGraphPage();
		} catch (PartInitException e) {
			e.printStackTrace();
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
