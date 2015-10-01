package cs.ucy.ac.cy.osslicense.model.editor.model;

import java.util.EventObject;

public class ModelChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ModelChangeEvent(LicenseModel licenseModel) {
		super(licenseModel);
	}

}
