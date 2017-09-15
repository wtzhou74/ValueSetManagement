package valueset.rdfModelManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * RDF Model load-on-startup
 * @author ZHOU WENTAO
 *
 */
@Component
public class LoadRdfModel implements CommandLineRunner{
	
	private Map<String,Model> modelMap = new HashMap<String, Model>();
	private final String rxNorm = "RxNorm";
	private final String icd10 = "ICD-10";
	
	@Override
	public void run(String... arg0) throws Exception {
		
		System.out.println("Waitting for loading RDF models");
		Model rxnormModel = RDFDataMgr.loadModel("terminologies/RXNORM.ttl");// load RxNorm RDF Model in Memory
		Model icd10Model = RDFDataMgr.loadModel("terminologies/ICD10CM.ttl");// load ICD-10 RDF Model in Memory
		modelMap.put(rxNorm, rxnormModel);
		modelMap.put(icd10, icd10Model);
	}

	public Map<String, Model> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, Model> modelMap) {
		this.modelMap = modelMap;
	}

	
}
