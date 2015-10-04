package cs.ucy.ac.cy.osslicense.model.editor;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import cs.ucy.ac.cy.osslicense.model.editor.extractor.LicenseExtractor;
import cs.ucy.ac.cy.osslicense.model.editor.form.FormEditor;
import cs.ucy.ac.cy.osslicense.model.editor.graph.GraphEditor;
import cs.ucy.ac.cy.osslicense.model.editor.licensefile.LicenseFileGenerator;
import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;
import cs.ucy.ac.cy.osslicense.model.editor.validator.LicenseModelSyntaxException;
import cs.ucy.ac.cy.osslicense.model.editor.validator.Validator;
import cs.ucy.ac.cy.osslicense.model.editor.visualizer.Edge;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class LicenseModelEditor extends MultiPageEditorPart {

	private TextEditor turtleEditor;
	private FormEditor formEditor;
	private LicenseModel licenseModel;
	private GraphEditor graphEditor;
	Graph<String, Edge> licenseGraph;

	private void initCompatibilityVertices() {
		licenseGraph.addVertex("AFL-3.0");
		licenseGraph.addVertex("X11, MIT");
		licenseGraph.addVertex("BSD-2-Clause-FreeBSD, BSD-2-Clause");
		licenseGraph.addVertex("BSD-3-Clause");
		licenseGraph.addVertex("Apache-2.0");
		licenseGraph.addVertex("Zlib, Libpng");
		licenseGraph.addVertex("CDDL-1.1, CDDL-1.0");
		licenseGraph.addVertex("MPL-1.1");
		licenseGraph.addVertex("Artistic-2.0");
		licenseGraph.addVertex("MPL-2.0");
		licenseGraph.addVertex("LGPL-2.1");
		licenseGraph.addVertex("LGPL-2.1+");
		licenseGraph.addVertex("LGPL-3.0+, LGPL-3.0");
		licenseGraph.addVertex("GPL-2.0");
		licenseGraph.addVertex("GPL-2.0+");
		licenseGraph.addVertex("GPL-3.0, GPL-3.0+");
		licenseGraph.addVertex("AGPL-3.0");
		licenseGraph.addVertex("OSL-3.0");
		licenseGraph.addVertex("EUPL-1.1");
		licenseGraph.addVertex("CECILL-2.0");
		licenseGraph.addVertex("AGPL-1.0");
		licenseGraph.addVertex("Apache-1.0");
		licenseGraph.addVertex("APSL-1.0");
		licenseGraph.addVertex("ADSL");
	}

	private void initCompatibilityEdges() {
		licenseGraph.addEdge(new Edge("Transitive"), "AFL-3.0", "OSL-3.0");
		licenseGraph.addEdge(new Edge("Transitive"), "X11, MIT", "BSD-2-Clause-FreeBSD, BSD-2-Clause");
		licenseGraph.addEdge(new Edge("Transitive"), "X11, MIT", "BSD-3-Clause");
		licenseGraph.addEdge(new Edge("Transitive"), "Apache-1.0", "Apache-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "BSD-2-Clause-FreeBSD, BSD-2-Clause", "LGPL-2.1");
		licenseGraph.addEdge(new Edge("Transitive"), "BSD-2-Clause-FreeBSD, BSD-2-Clause", "LGPL-2.1+");
		licenseGraph.addEdge(new Edge("Transitive"), "BSD-2-Clause-FreeBSD, BSD-2-Clause", "LGPL-3.0+, LGPL-3.0");
		licenseGraph.addEdge(new Edge("Transitive"), "BSD-2-Clause-FreeBSD, BSD-2-Clause", "BSD-3-Clause");
		licenseGraph.addEdge(new Edge("Transitive"), "BSD-3-Clause", "LGPL-2.1+");
		licenseGraph.addEdge(new Edge("Transitive"), "BSD-3-Clause", "Apache-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "BSD-3-Clause", "LGPL-3.0+, LGPL-3.0");
		licenseGraph.addEdge(new Edge("Transitive"), "Apache-2.0", "LGPL-3.0+, LGPL-3.0");
		licenseGraph.addEdge(new Edge("Transitive"), "Zlib, Libpng", "Apache-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "Zlib, Libpng", "GPL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "Zlib, Libpng", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "MPL-1.1", "CDDL-1.1, CDDL-1.0");
		licenseGraph.addEdge(new Edge("Transitive"), "Artistic-2.0", "GPL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "Artistic-2.0", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "MPL-2.0", "GPL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "MPL-2.0", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "MPL-2.0", "LGPL-2.1");
		licenseGraph.addEdge(new Edge("Transitive"), "MPL-2.0", "LGPL-2.1+");
		licenseGraph.addEdge(new Edge("Transitive"), "LGPL-2.1", "GPL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "LGPL-2.1", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "LGPL-2.1+", "LGPL-2.1");
		licenseGraph.addEdge(new Edge("Transitive"), "LGPL-2.1+", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "LGPL-2.1+", "LGPL-3.0+, LGPL-3.0");
		licenseGraph.addEdge(new Edge("Transitive"), "LGPL-3.0+, LGPL-3.0", "GPL-3.0, GPL-3.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "GPL-2.0+", "GPL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "GPL-2.0+", "GPL-3.0, GPL-3.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "GPL-3.0, GPL-3.0+", "AGPL-3.0");
		licenseGraph.addEdge(new Edge("Transitive"), "EUPL-1.1", "OSL-3.0");
		licenseGraph.addEdge(new Edge("Transitive"), "EUPL-1.1", "GPL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "EUPL-1.1", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "EUPL-1.1", "CECILL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "CECILL-2.0", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Transitive"), "CECILL-2.0", "GPL-2.0+");
		licenseGraph.addEdge(new Edge("Non-transitive"), "Apache-2.0", "MPL-2.0");
		licenseGraph.addEdge(new Edge("Non-transitive"), "MPL-1.1", "MPL-2.0");
		licenseGraph.addEdge(new Edge("Transitive"), "APSL-1.0", "Apache-1.0");
	}

	public void createCompatibilityPage() {
		this.licenseGraph = new SparseMultigraph<String, Edge>();

		initCompatibilityVertices();
		initCompatibilityEdges();

		Composite composite = new Composite(getContainer(), SWT.EMBEDDED);
		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		java.awt.Frame frame = SWT_AWT.new_Frame(composite);
		javax.swing.JPanel panel = new javax.swing.JPanel();
		frame.add(panel);

		Layout<String, Edge> layoutCircle = new CircleLayout<String, Edge>(licenseGraph);
		layoutCircle.setSize(new Dimension(1024, 768));
		VisualizationViewer<String, Edge> vv = new VisualizationViewer<String, Edge>(layoutCircle);
		vv.setPreferredSize(new Dimension(1024, 768));

		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Edge>());

		AbstractModalGraphMouse gm = new DefaultModalGraphMouse<String, Edge>();
		vv.setGraphMouse(gm);

		frame.add(vv);
		frame.pack();
		frame.setVisible(true);

		int index = this.addPage(composite);
		this.setPageText(index, "Compatibility");
	}

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
			createCompatibilityPage();
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

	private void saveToTemporaryFile(IDocument document, File temp) throws FileNotFoundException {

		PrintWriter out = new PrintWriter(temp);
		out.print(document.get());
		out.close();

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

			try {
				File temp = File.createTempFile(licenseModel.getLicenseIdentifier(), ".ttl");
				String temporaryPath = temp.getAbsolutePath();
				IDocument document = turtleEditor.getDocumentProvider().getDocument(turtleEditor.getEditorInput());

				saveToTemporaryFile(document, temp);

				if (Validator.isValid(temporaryPath)) {
					turtleEditor.doSave(monitor);
					updateModel();
					formEditor.update(this.getLicenseModel());
					graphEditor.update(this.licenseModel);
				}

				temp.delete();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LicenseModelSyntaxException e) {
				System.err.println(e.getMessage());
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						MessageDialog.openError(activeShell, "Error", e.getMessage());
					}
				});
			}

		} else if (graphEditor.isDirty()) {

			graphEditor.doSave(monitor);
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
