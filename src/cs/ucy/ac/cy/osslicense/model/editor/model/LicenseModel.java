package cs.ucy.ac.cy.osslicense.model.editor.model;

import java.util.ArrayList;
import java.util.List;

public class LicenseModel {
	private String licenseIdentifier;
	private List<String> rights;
	private List<String> obligations;
	private List<String> additionalConditions;
	private ModelChangeListener changeListener;

	public LicenseModel(String identifier) {
		this.setRights(new ArrayList<String>());
		this.setObligations(new ArrayList<String>());
		this.setAdditionalConditions(new ArrayList<String>());
		this.setLicenseIdentifier(identifier);
	}

	private synchronized void fireChangeEvent() {
		ModelChangeEvent changeEvent = new ModelChangeEvent(this);
		this.changeListener.modelChanged(changeEvent);
	}

	public synchronized void addModelChangeListener(ModelChangeListener listener) {
		this.setChangeListener(listener);
	}

	public synchronized void removeRight(String right) {
		this.rights.remove(right);
		fireChangeEvent();
	}

	public synchronized void removeObligation(String obligation) {
		this.obligations.remove(obligation);
		fireChangeEvent();
	}

	public synchronized void removeAdditionalCondition(String additionalCondition) {
		this.additionalConditions.remove(additionalCondition);
		fireChangeEvent();
	}

	public synchronized void addRight(String right) {
		this.rights.add(right);
		fireChangeEvent();
	}

	public synchronized void addObligation(String obligation) {
		this.obligations.add(obligation);
		fireChangeEvent();
	}

	public synchronized void addAdditionalCondition(String additionalCondition) {
		this.additionalConditions.add(additionalCondition);
		fireChangeEvent();
	}

	public List<String> getAdditionalConditions() {
		return additionalConditions;
	}

	public void setAdditionalConditions(List<String> additionalConditions) {
		this.additionalConditions = additionalConditions;
	}

	public List<String> getObligations() {
		return obligations;
	}

	public void setObligations(List<String> obligations) {
		this.obligations = obligations;
	}

	public List<String> getRights() {
		return rights;
	}

	public void setRights(List<String> rights) {
		this.rights = rights;
	}

	public String getLicenseIdentifier() {
		return licenseIdentifier;
	}

	public void setLicenseIdentifier(String licenseIdentifier) {
		this.licenseIdentifier = licenseIdentifier;
	}

	public ModelChangeListener getChangeListener() {
		return changeListener;
	}

	public void setChangeListener(ModelChangeListener changeListener) {
		this.changeListener = changeListener;
	}

}
