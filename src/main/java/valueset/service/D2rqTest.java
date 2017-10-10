package valueset.service;

import java.io.IOException;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class D2rqTest {

	public static void d2rqTest () {
		
		String sparql = "PREFIX vocab: <http://localhost:2020/resource/vocab/>\n"
				+ " SELECT DISTINCT * WHERE { "
				+ " ?s vocab:value_set_category_code ?o . "
				+ " }";
		
		Query query = QueryFactory.create(sparql);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:2020/sparql", query);
		ResultSet results = qexec.execSelect();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			if (solution.get("o").isLiteral()) {
				System.out.println(solution.get("o").asLiteral().getLexicalForm());
			}
		}
	}
	
	public static void main (String[] args) {
		d2rqTest();
	}
}
