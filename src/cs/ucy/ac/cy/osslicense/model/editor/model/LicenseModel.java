package cs.ucy.ac.cy.osslicense.model.editor.model;

import java.util.ArrayList;
import java.util.List;

public class LicenseModel {
	private String licenseIdentifier;
	private List<String> rights;
	private List<String> obligations;
	private List<String> additionalConditions;

	public LicenseModel(String identifier) {
		this.setRights(new ArrayList<String>());
		this.setObligations(new ArrayList<String>());
		this.setAdditionalConditions(new ArrayList<String>());
		this.setLicenseIdentifier(identifier);
	}

	public void removeRight(String right) {
		this.rights.remove(right);
	}

	public void removeObligation(String obligation) {
		this.rights.remove(obligation);
	}

	public void removeAdditionalCondition(String additionalCondition) {
		this.rights.remove(additionalCondition);
	}

	public void addRight(String right) {
		this.rights.add(right);
	}

	public void addObligation(String obligation) {
		this.rights.add(obligation);
	}

	public void addAdditionalCondition(String additionalCondition) {
		this.rights.add(additionalCondition);
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

}
