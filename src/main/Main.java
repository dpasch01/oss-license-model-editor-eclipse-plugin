
package main;

import cs.ucy.ac.cy.osslicense.model.editor.extractor.LicenseExtractor;
import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;
import cs.ucy.ac.cy.osslicense.model.editor.visualizer.LicenseVisualizer;

public class Main {

	public static void main(String[] args) {
		LicenseExtractor lExtractor= new LicenseExtractor("examples/apache-2.0.ttl");
		LicenseModel lModel = new LicenseModel(lExtractor.extractLicenseIdentifier());
		lModel.setRights(lExtractor.extractRights());
		lModel.setObligations(lExtractor.extractObligations());
		lModel.setAdditionalConditions(lExtractor.extractAdditionalConditions());
		LicenseVisualizer lVizualizer = new LicenseVisualizer(lModel);
		lVizualizer.displayLicenseGraph();
	}

}
