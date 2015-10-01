package cs.ucy.ac.cy.osslicense.model.editor.licensefile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LicenseFileGenerator {
	public static final String FILE_EXTENSION = "ttl";
	public static final String OWL = "@prefix owl:<http://www.w3.org/2002/07/owl#> .";
	public static final String RDF = "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .";
	public static final String OL = "@prefix ol: <http://www.cs.ucy.ac.cy/~dpasch01/license-modeling/ontologies/oss-license.owl#> .";

	public static File createFile(String licenseTitle, String[] rights, String[] obligations,
			boolean hasLimitedLiability, boolean hasProvidedWithoutWarranty, String path) throws FileNotFoundException {
		File newLicenseFile = new File(path + "\\" + licenseTitle + "." + FILE_EXTENSION);

		PrintWriter writer = new PrintWriter(newLicenseFile);
		writer.println(OWL);
		writer.println(RDF);
		writer.println(OL);

		writer.println("ol:" + licenseTitle);
		writer.println("rdf:type");
		writer.println("ol:OpenSourceLicense ,");
		writer.println("owl:NamedIndividual ;");

		writer.println("ol:hasAdditionalCondition");
		if (hasLimitedLiability && hasProvidedWithoutWarranty) {
			writer.println("ol:LimitedLiability ,");
			writer.println("ol:ProvideWithoutWarranty ;");
		} else if (hasLimitedLiability) {
			writer.println("ol:LimitedLiability ;");
		} else if (hasProvidedWithoutWarranty) {
			writer.println("ol:ProvideWithoutWarranty ;");
		}

		writer.println("ol:hasRight ");
		for (int i = 0; i < rights.length; i++) {
			writer.print("ol:"+rights[i]);
			if (i != (rights.length - 1)) {
				writer.println(" ,");
			} else {
				writer.println(" ;");
			}
		}

		writer.println("ol:hasObligation ");
		for (int i = 0; i < obligations.length; i++) {
			writer.print("ol:"+obligations[i]);
			if (i != (obligations.length - 1)) {
				writer.println(" ,");
			} else {
				writer.println(" .");
			}
		}

		writer.close();
		return newLicenseFile;
	}
	
	public static File updateFile(String licenseTitle, String[] rights, String[] obligations,
			boolean hasLimitedLiability, boolean hasProvidedWithoutWarranty, String path) throws FileNotFoundException {
		File newLicenseFile = new File(path);

		PrintWriter writer = new PrintWriter(newLicenseFile);
		writer.println(OWL);
		writer.println(RDF);
		writer.println(OL);

		writer.println("ol:" + licenseTitle);
		writer.println("rdf:type");
		writer.println("ol:OpenSourceLicense ,");
		writer.println("owl:NamedIndividual ;");

		writer.println("ol:hasAdditionalCondition");
		if (hasLimitedLiability && hasProvidedWithoutWarranty) {
			writer.println("ol:LimitedLiability ,");
			writer.println("ol:ProvideWithoutWarranty ;");
		} else if (hasLimitedLiability) {
			writer.println("ol:LimitedLiability ;");
		} else if (hasProvidedWithoutWarranty) {
			writer.println("ol:ProvideWithoutWarranty ;");
		}

		writer.println("ol:hasRight ");
		for (int i = 0; i < rights.length; i++) {
			writer.print("ol:"+rights[i]);
			if (i != (rights.length - 1)) {
				writer.println(" ,");
			} else {
				writer.println(" ;");
			}
		}

		writer.println("ol:hasObligation ");
		for (int i = 0; i < obligations.length; i++) {
			writer.print("ol:"+obligations[i]);
			if (i != (obligations.length - 1)) {
				writer.println(" ,");
			} else {
				writer.println(" .");
			}
		}

		writer.close();
		return newLicenseFile;
	}
}
