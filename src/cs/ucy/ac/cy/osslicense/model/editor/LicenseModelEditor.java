package cs.ucy.ac.cy.osslicense.model.editor;


import org.eclipse.core.runtime.IProgressMonitor;
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
import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;
import cs.ucy.ac.cy.osslicense.model.editor.visualizer.LicenseVisualizer;

public class LicenseModelEditor extends MultiPageEditorPart {

	private TextEditor turtleEditor;
	
	@Override
	protected void createPages() {
		try {
			createTurtlePage();
			createGraphPage();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	private void createGraphPage() {
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

		frame.add(lVizualizer.generateGraphView());
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
		turtleEditor.doSave(monitor);
		this.removePage(1);
		this.createGraphPage();
	}

	@Override
	public void doSaveAs() {
		turtleEditor.doSaveAs();

	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

}

