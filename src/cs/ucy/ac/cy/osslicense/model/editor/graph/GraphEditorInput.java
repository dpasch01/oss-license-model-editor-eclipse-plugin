package cs.ucy.ac.cy.osslicense.model.editor.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;

public class GraphEditorInput implements IEditorInput {

	private final String id;
	private LicenseModel associatedLicenseModel;
	private File associatedFilePath;
	private List<IResourceChangeListener> licenseModelChangeListeners = new ArrayList<IResourceChangeListener>();

	public GraphEditorInput(String id, LicenseModel licenseModel, File filePath) {
		this.id = id;
		this.setAssociatedLicenseModel(licenseModel);
		this.setAssociatedFilePath(filePath);
	}

	public LicenseModel getLicenseModel() {
		return this.associatedLicenseModel;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		if (this.getAssociatedLicenseModel() != null) {
			return getAssociatedLicenseModel().getLicenseIdentifier();
		} else {
			return "License not defined.";
		}
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return null;
	}

	public String getId() {
		return id;
	}

	public LicenseModel getAssociatedLicenseModel() {
		return associatedLicenseModel;
	}

	public void setAssociatedLicenseModel(LicenseModel associatedLicenseModel) {
		this.associatedLicenseModel = associatedLicenseModel;
	}

	public File getAssociatedFilePath() {
		return associatedFilePath;
	}

	public void setAssociatedFilePath(File associatedFilePath) {
		this.associatedFilePath = associatedFilePath;
	}

	public void dataModelChanged() {
		for (IResourceChangeListener listener : this.licenseModelChangeListeners) {
			listener.resourceChanged(null);
		}
	}

	public void addChangeListener(IResourceChangeListener listener) {
		this.licenseModelChangeListeners.add(listener);
	}

	public void removeChangeListener(IResourceChangeListener listener) {
		this.licenseModelChangeListeners.remove(listener);
	}

	public List<IResourceChangeListener> getLicenseModelChangeListeners() {
		return licenseModelChangeListeners;
	}

	public void setLicenseModelChangeListeners(List<IResourceChangeListener> licenseModelChangeListeners) {
		this.licenseModelChangeListeners = licenseModelChangeListeners;
	}

}
