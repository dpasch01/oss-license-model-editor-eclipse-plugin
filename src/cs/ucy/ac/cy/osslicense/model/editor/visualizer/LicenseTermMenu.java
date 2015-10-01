package cs.ucy.ac.cy.osslicense.model.editor.visualizer;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class LicenseTermMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> licenseTerms;
	private ArrayList<JMenuItem> licenseTermItems;

	public LicenseTermMenu(String title, ArrayList<String> arrayList) {
		super(title);
		this.setLicenseTerms(new ArrayList<String>());
		this.licenseTermItems = new ArrayList<JMenuItem>();

		for (String term : arrayList) {
			JMenuItem item = new JMenuItem(term);
			this.add(item);
			this.licenseTermItems.add(item);
		}
	}

	public ArrayList<String> getLicenseTerms() {
		return licenseTerms;
	}

	public void setLicenseTerms(ArrayList<String> licenseTerms) {
		this.licenseTerms = licenseTerms;
	}

	public ArrayList<JMenuItem> getLicenseTermItems() {
		return licenseTermItems;
	}

	public void setLicenseTermItems(ArrayList<JMenuItem> licenseTermItems) {
		this.licenseTermItems = licenseTermItems;
	}
}
