package cs.ucy.ac.cy.osslicense.model.editor.extractor;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;

public class LicenseExtractor {
	private String filename;
	private Model model;

	public LicenseExtractor(String rdfFilename) {
		this.setFilename(rdfFilename);
		this.model = ModelFactory.createDefaultModel();
		FileManager.get().readModel(model, rdfFilename);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<String> extractRights() {
		ArrayList<String> rights = new ArrayList<>();
		String queryString = Prolog.LICENSE_ONTOLOGY + Prolog.NL + Prolog.RDF_SYNTAX + Prolog.NL + Prolog.RDF_SCHEMA
				+ Prolog.NL + "SELECT DISTINCT ?right WHERE {?x ol:hasRight ?right}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		try {
			final ResultSet rs = qexec.execSelect();
			for (; rs.hasNext();) {
				final QuerySolution rb = rs.nextSolution();
				final Resource individual = rb.getResource("right");
				rights.add(individual.getLocalName());
			}
		} finally {
			qexec.close();
		}

		return rights;
	}

	public List<String> extractObligations() {
		ArrayList<String> obligations = new ArrayList<>();
		String queryString = Prolog.LICENSE_ONTOLOGY + Prolog.NL + Prolog.RDF_SYNTAX + Prolog.NL + Prolog.RDF_SCHEMA
				+ Prolog.NL + "SELECT DISTINCT ?obligation WHERE {?x ol:hasObligation ?obligation}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		try {
			final ResultSet rs = qexec.execSelect();
			for (; rs.hasNext();) {
				final QuerySolution rb = rs.nextSolution();
				final Resource individual = rb.getResource("obligation");
				obligations.add(individual.getLocalName());
			}
		} finally {
			qexec.close();
		}

		return obligations;
	}

	public List<String> extractAdditionalConditions() {
		ArrayList<String> additionalConditions = new ArrayList<>();
		String queryString = Prolog.LICENSE_ONTOLOGY + Prolog.NL + Prolog.RDF_SYNTAX + Prolog.NL + Prolog.RDF_SCHEMA
				+ Prolog.NL
				+ "SELECT DISTINCT ?additionalCondition WHERE {?x ol:hasAdditionalCondition ?additionalCondition}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		try {
			final ResultSet rs = qexec.execSelect();
			for (; rs.hasNext();) {
				final QuerySolution rb = rs.nextSolution();
				final Resource individual = rb.getResource("additionalCondition");
				additionalConditions.add(individual.getLocalName());
			}
		} finally {
			qexec.close();
		}

		return additionalConditions;
	}

	public String extractLicenseIdentifier() {
		String licenseIdentifier = null;
		String queryString = Prolog.LICENSE_ONTOLOGY + Prolog.NL + Prolog.RDF_SCHEMA + Prolog.NL + Prolog.RDF_SYNTAX
				+ Prolog.NL + Prolog.OWL + Prolog.NL
				+ "SELECT DISTINCT ?license WHERE { ?license rdf:type owl:NamedIndividual . MINUS {?license rdf:type ol:Right . } MINUS {?license rdf:type ol:Obligation .} MINUS {?license rdf:type ol:AdditionalCondition .}}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		try {
			final ResultSet rs = qexec.execSelect();
			for (; rs.hasNext();) {
				final QuerySolution rb = rs.nextSolution();
				final Resource individual = rb.getResource("license");
				licenseIdentifier = individual.getLocalName();
			}
		} finally {
			qexec.close();
		}

		return licenseIdentifier;
	}
}
