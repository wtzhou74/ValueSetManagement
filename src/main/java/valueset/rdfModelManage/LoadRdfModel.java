package valueset.rdfModelManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasonerFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.ReasonerVocabulary;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javassist.bytecode.Descriptor.Iterator;
import valueset.utils.ConstantUtil;

/**
 * RDF Model load-on-startup
 * @author ZHOU WENTAO
 *
 */
@Component
public class LoadRdfModel implements CommandLineRunner{
	
	private Map<String,Model> modelMap = new HashMap<String, Model>();
	/*private final String rxNorm = "RxNorm";
	private final String icd10 = "ICD-10";*/
	
	@Override
	public void run(String... arg0) throws Exception {
		
		System.out.println("Waitting for loading RDF models");
		Model rxnormModel = RDFDataMgr.loadModel("terminologies/RXNORM.ttl");// load RxNorm RDF Model in Memory
		Model icd10Model = RDFDataMgr.loadModel("terminologies/ICD10CM.ttl");// load ICD-10 RDF Model in Memory
		//Model icd9Model = RDFDataMgr.loadModel("terminologies/ICD9CM.ttl");// load ICD-9 RDF Model in Memory
		Model cptModel = RDFDataMgr.loadModel("terminologies/CPT.ttl");
		//Model snomedModel = RDFDataMgr.loadModel("terminologies/SNOMEDCT.ttl");
		//Model hcpcsModel = RDFDataMgr.loadModel("terminologies/HCPCS.ttl");
		Model loincModel = RDFDataMgr.loadModel("terminologies/LOINC.ttl");
		
		modelMap.put(ConstantUtil.RXNORM, rxnormModel);
		modelMap.put(ConstantUtil.ICD10CM, icd10Model);
		modelMap.put(ConstantUtil.CPT, cptModel);
		//modelMap.put(ConstantUtil.ICD9CM, icd9Model);
		//modelMap.put(ConstantUtil.SNOMED_CT, snomedModel);
		//modelMap.put(ConstantUtil.HCPCS, hcpcsModel);
		modelMap.put(ConstantUtil.LOINC, loincModel);
		
		//validateModel(rxnormModel);//validate model	
		
	}

	public Map<String, Model> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, Model> modelMap) {
		this.modelMap = modelMap;
	}

	/**
	 * Validate model
	 * 
	 * @param model
	 */
	public void validateModel (Model model) {
		//Set Reasoner
		Resource config = ModelFactory.createDefaultModel().createResource()
				.addProperty(ReasonerVocabulary.PROPsetRDFSLevel, "default");
		Reasoner reasoner = RDFSRuleReasonerFactory.theInstance().create(config);
		//InfModel infmodel = ModelFactory.createRDFSModel(rxnormModel);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model);
		ValidityReport validity = infmodel.validate();
		
		if (validity.isValid()) {
			System.out.println("OK");
		} else {
			System.out.print("Conflicts");
			for (Iterator i = (Iterator) validity.getReports(); i.hasNext();) {
				System.out.println(" - " + i.next());
			}
		}
	}
	
}
