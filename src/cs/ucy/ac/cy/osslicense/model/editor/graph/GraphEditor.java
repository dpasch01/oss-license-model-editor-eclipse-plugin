package cs.ucy.ac.cy.osslicense.model.editor.graph;

import java.io.File;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import cs.ucy.ac.cy.osslicense.model.editor.extractor.LicenseExtractor;
import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;
import cs.ucy.ac.cy.osslicense.model.editor.model.ModelChangeEvent;
import cs.ucy.ac.cy.osslicense.model.editor.model.ModelChangeListener;
import cs.ucy.ac.cy.osslicense.model.editor.visualizer.Edge;
import cs.ucy.ac.cy.osslicense.model.editor.visualizer.LicenseVisualizer;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class GraphEditor extends EditorPart {

	private Composite container;
	private LicenseModel licenseModel;
	private LicenseVisualizer licenseVisualizer;
	private boolean isDirty = false;
	private VisualizationViewer<String, Edge> vv;

	public GraphEditor(LicenseModel licenseModel) {
		this.setLicenseModel(licenseModel);
	}

	private void setLicenseModel(LicenseModel licenseModel) {
		this.licenseModel = licenseModel;

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		setDirtyFlag(false);
	}

	@Override
	public void doSaveAs() {

	}

	private IPath getPathFromEditorInput(IEditorInput input) {
		IPath path = null;
		if (input instanceof FileStoreEditorInput) {
			FileStoreEditorInput fileStoreEditorInput = (FileStoreEditorInput) input;
			path = new Path(fileStoreEditorInput.getURI().getPath());
		} else if (input instanceof FileEditorInput) {
			FileEditorInput newInput = (FileEditorInput) input;
			path = newInput.getFile().getLocation();
		}
		return path;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		IPath path = this.getPathFromEditorInput(input);
		if (path == null) {
			throw new PartInitException("Invalid Editor Input.");
		}

		File licenseModelFile = path.toFile();
		LicenseExtractor licenseExtractor = new LicenseExtractor(licenseModelFile.getAbsolutePath());

		LicenseModel licenseModel = new LicenseModel(licenseExtractor.extractLicenseIdentifier());
		licenseModel.setRights(licenseExtractor.extractRights());
		licenseModel.setObligations(licenseExtractor.extractObligations());
		licenseModel.setAdditionalConditions(licenseExtractor.extractAdditionalConditions());

		GraphEditorInput formEditorInput = new GraphEditorInput("", licenseModel, licenseModelFile.getAbsoluteFile());
		this.setInput(formEditorInput);
		this.setSite(site);
	}

	public GraphEditorInput getInput() {
		return (GraphEditorInput) this.getEditorInput();
	}

	@Override
	public boolean isDirty() {
		return this.isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	private void setDirtyFlag(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void createPartControl(Composite parent) {
		container = new Composite(parent, SWT.EMBEDDED);
		FillLayout layout = new FillLayout();
		container.setLayout(layout);
		java.awt.Frame frame = SWT_AWT.new_Frame(container);
		javax.swing.JPanel panel = new javax.swing.JPanel();
		frame.add(panel);

		this.licenseModel.addModelChangeListener(new ModelChangeListener() {

			@Override
			public void modelChanged(ModelChangeEvent event) {
				licenseModel = ((LicenseModel) event.getSource());
				setDirtyFlag(true);
			}

		});

		licenseVisualizer = new LicenseVisualizer(this.licenseModel);
		vv = licenseVisualizer.generateGraphView();

		frame.add(vv);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void setFocus() {

	}

	public Composite getContainer() {
		return container;
	}

	public void setContainer(Composite container) {
		this.container = container;
	}

	public LicenseVisualizer getLicenseVisualizer() {
		return licenseVisualizer;
	}

	public void setLicenseVisualizer(LicenseVisualizer licenseVisualizer) {
		this.licenseVisualizer = licenseVisualizer;
	}

	public LicenseModel getLicenseModel() {
		return this.licenseModel;
	}

	@Override
	public String getPartName() {
		return getInput().getName();
	}

	public void update(LicenseModel lModel) {
		this.licenseModel = lModel;
		this.licenseVisualizer.setPopupMenuModel(lModel);

		this.licenseModel.addModelChangeListener(new ModelChangeListener() {

			@Override
			public void modelChanged(ModelChangeEvent event) {
				licenseModel = ((LicenseModel) event.getSource());
				setDirtyFlag(true);
			}

		});

		vv.repaint();
	}
}
