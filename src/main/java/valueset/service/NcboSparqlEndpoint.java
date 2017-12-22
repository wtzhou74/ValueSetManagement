package valueset.service;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

/**
 * This is an example built on top of the Jena ARQ library.
 * See: http://jena.sourceforge.net/ARQ/documentation.html
 */
public class NcboSparqlEndpoint {
	
	private String service = null;
	private String apikey = null;
	
	public NcboSparqlEndpoint(String service, String apikey) {
		this.service = service;
		this.apikey = apikey;
	}
	public ResultSet executeQuery(String queryString) throws Exception {
		 Query query = QueryFactory.create(queryString) ;

		 QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(this.service, query);
		 qexec.addParam("apikey", this.apikey);
		 ResultSet results = qexec.execSelect() ;
		 return results;

	}
	public static void main(String[] args) throws Exception {
		String sparqlService = "http://sparql.bioontology.org/sparql";
		String apikey = "5e72f33e-aa16-40d1-b18b-59dd0f7a61c1";

		/*
		 * More query examples here:
		 * http://sparql.bioontology.org/examples
		 */
		/*String query = "PREFIX omv: <http://omv.ontoware.org/2005/05/ontology#> " +
					   "SELECT ?ont ?name ?acr " +
					   "WHERE { ?ont a omv:Ontology; " +
					   "omv:acronym ?acr; " +
					   "omv:name ?name . " +
					   "}";*/
		String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX snomed-term: <http://purl.bioontology.org/ontology/CPT/> "
				+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
				+ "SELECT DISTINCT ?label "
				+ "FROM <http://bioportal.bioontology.org/ontologies/CPT> "
				+ "WHERE { "
				//+ "snomed-term:1021130 rdfs:subClassOf ?x ."
				+ "snomed-term:1021130 skos:prefLabel  ?label. "
				+ "}";
		
		NcboSparqlEndpoint test = new NcboSparqlEndpoint(sparqlService,apikey);
		ResultSet results = test.executeQuery(query);
		    for ( ; results.hasNext() ; ) {
		      QuerySolution soln = results.nextSolution() ;
		      /*RDFNode ontUri = soln.get("ont") ;
		      Literal name = soln.getLiteral("name") ;
		      Literal acr = soln.getLiteral("acr") ;*/
		      //RDFNode ontUri = soln.get("x") ;
		      Literal name = soln.getLiteral("label") ;
		      System.out.println(name + " ---- " + "name" + " ---- " );
		    }
	}
}
