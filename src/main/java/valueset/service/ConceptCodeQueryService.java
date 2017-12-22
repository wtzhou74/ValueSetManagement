package valueset.service;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ext.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import valueset.dao.JfcsLabsRepository;
import valueset.dao.JfcsMedicationRepository;
import valueset.model.dbModel.JfcsLab;
import valueset.model.dbModel.JfcsMedication;
import valueset.model.modelView.ConceptMappingResultModelView;
import valueset.model.wsModel.SearchResult;
import valueset.utils.ConstantUtil;
import valueset.utils.RestTicketClient;

@Service
public class ConceptCodeQueryService {

	@Autowired
	ConceptMappingService conceptMappingService;
	@Autowired
	JfcsMedicationRepository jfcsMedicationRepository;
	@Autowired
	JfcsLabsRepository jfcsLabsRepository;

	RestTicketClient restTicketClient = new RestTicketClient(ConstantUtil.APIKEY);
	private String tgt = restTicketClient.getTgt();

	public List<SearchResult> retrieveCuis(String term, String rsab) {
		// String version = System.getProperty("version") == null ? "current":
		// System.getProperty("version");

		// testSensitiveMedications();
		// testSensitiveDiagnosis();
		// getLabs();
		// testLabs();
		List<SearchResult> results = new ArrayList<SearchResult>();
		try {
			results = SearchTerm(term, rsab);
			if (results.size() == 0) {
				System.out.println("No results for " + term);
			}
			int num = 1;
			for (SearchResult result : results) {

				String cui = result.getUri();
				// Get the code
				if (!result.getUri().isEmpty()) {
					cui = StringUtils.substringAfterLast(result.getUri(), "/");
				}
				String name = result.getName();
				String rsab_result = result.getRootSource();
				result.setId(String.valueOf(num));
				System.out.println("Result " + num);
				System.out.println("cui:" + cui);
				System.out.println("name:" + name);
				System.out.println("Highest ranking source of cui:" + rsab_result);
				num++;

				// Check Sensitive category
				String sensitiveCategory = "";
				String ingredients = "";
				List<ConceptMappingResultModelView> conceptMappingResults = new ArrayList<ConceptMappingResultModelView>();
				if (ConstantUtil.RXNORM.equalsIgnoreCase(rsab)) {
					conceptMappingResults = conceptMappingService.findMappedConceptInVS(cui, ConstantUtil.RXNORM,
							ConstantUtil.INGREDIENT_QUERY_OPTION_1);
				} else if (ConstantUtil.PARAM_LOINC.equalsIgnoreCase(rsab)) {
					// skip the codes which start with MTHU***
					if (cui.startsWith("MTHU")) {
						continue;
					}
					conceptMappingResults = conceptMappingService.findMappedConceptInVS(cui, ConstantUtil.LOINC, "");
				}
				for (ConceptMappingResultModelView conceptMappingResult : conceptMappingResults) {
					// TODO update output format
					if (conceptMappingResult.getSensitiveCategories().size() != 0) {
						sensitiveCategory = sensitiveCategory
								+ conceptMappingResult.getSensitiveCategories().toString();
					}
					ingredients = ingredients + conceptMappingResult.getTargetConcept() + "/";
				}
				// break if sensitive category has been found.
				// TODO the logic could be updated if needed
				result.setSensitveCategory(sensitiveCategory);
				result.setIngredient(ingredients);
			}
			System.out.println("----------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;

	}

	public List<SearchResult> SearchTerm(String term, String rsab) throws Exception {

		String version = ConstantUtil.VERSION.equalsIgnoreCase("") ? "current" : ConstantUtil.VERSION;
		String paramSabs = rsab.equalsIgnoreCase("All") ? "" : rsab;
		int pageNumber = 0;
		SearchResult[] results;
		List<SearchResult> holder = new ArrayList<SearchResult>();
		do {
			pageNumber++;
			RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
			Response response = given()// .log().all()
					.request().with().param("ticket", restTicketClient.getST(tgt)).param("string", term)
					.param("pageNumber", pageNumber)
					// uncomment below to return only CUIs that have at least
					// one non-obsolete/non-suppressible atom (relevant to the
					// searchType) from the US Edition of SNOMED CT
					// .param("sabs","RXNORM,HCPCS,CPT,LNC")
					.param("sabs", paramSabs)
					// uncomment below to return CUIs that have at least one
					// non-obsolete/non-suppressible atom that is an exact match
					// with the search term
					// .param("searchType","exact") //valid values are
					// exact,words, approximate,leftTruncation,rightTruncation,
					// and normalizedString
					// uncomment below to return source-asserted identifiers
					// (from SNOMEDCT and other UMLS vocabularies) instead of
					// CUIs
					.param("returnIdType", "code").expect().statusCode(200).when().get("/rest/search/" + version);

			String output = response.getBody().asString();
			Configuration config = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
			results = JsonPath.using(config).parse(output).read("$.result.results", SearchResult[].class);

			// the /search endpoint returns an array of 'result' objects
			// See
			// http://documentation.uts.nlm.nih.gov/rest/search/index.html#sample-output
			// for a complete list of fields output under the /search endpoint
			for (SearchResult result : results) {

				if (!results[0].getUi().equals("NONE")) {
					holder.add(result);
				}
			}

		} while (!results[0].getUi().equals("NONE"));
		return holder;

	}

	/**
	 * Get all terms from test data
	 * 
	 * @return
	 */
	public Map<String, List<String>> getTerms() {
		Map<String, List<String>> tests = new HashMap<String, List<String>>();
		List<JfcsMedication> jfcsMedications = Lists.newArrayList(jfcsMedicationRepository.findAll());
		List<String> medications = new ArrayList<String>();
		List<String> diagnosis = new ArrayList<String>();
		for (JfcsMedication jfcsMedication : jfcsMedications) {
			String[] medicationStr = jfcsMedication.getMedications().split("/");
			String[] diagnosisStr = jfcsMedication.getDiagnosis().split("/");
			for (String diag : diagnosisStr) {
				diagnosis.add(diag.replaceAll(" ", ""));
			}
			medications.addAll(Arrays.asList(medicationStr));
			// diagnosis.addAll(Arrays.asList(diagnosisStr));
		}
		tests.put(ConstantUtil.RXNORM, medications);
		tests.put(ConstantUtil.ICD10CM, diagnosis);

		return tests;
	}

	/**
	 * Get all lab tests
	 * 
	 * @return
	 */
	public List<String> getLabs() {
		List<JfcsLab> jfcsLabs = Lists.newArrayList(jfcsLabsRepository.findAll());
		Map<String, List<String>> tests = new HashMap<String, List<String>>();
		List<String> resultDesc = new ArrayList<String>();

		for (JfcsLab jfcsLab : jfcsLabs) {
			if (!resultDesc.contains(jfcsLab.getResultDesc())) {
				resultDesc.add(jfcsLab.getResultDesc());
			}
		}

		/*
		 * Set<Map.Entry<String, List<String>>> entries = tests.entrySet(); int
		 * i = 0; for (Map.Entry<String, List<String>> entry: entries) {
		 * i+=entry.getValue().size(); }
		 */
		// System.out.println(i);
		return resultDesc;
	}

	public void testSensitiveMedications() {
		List<String> medications = getTerms().get(ConstantUtil.RXNORM);
		List<String> sensitiveCategory = new ArrayList<String>();
		for (String medication : medications) {
			List<SearchResult> searchResults = retrieveCuis(medication, "RXNORM");
			for (SearchResult searchResult : searchResults) {
				if (!searchResult.getSensitveCategory().isEmpty()) {
					sensitiveCategory.add(medication + "///" + searchResult.getSensitveCategory());
					System.out.println(
							"Medication: " + medication + " SensitiveCategory: " + searchResult.getSensitveCategory());
				}
			}
		}

		for (String str : sensitiveCategory) {
			System.out.println(str);
		}
	}

	public void testSensitiveDiagnosis() {
		List<String> diagnosis = getTerms().get(ConstantUtil.ICD10CM);
		Map<String, String> sensitive = new HashMap<String, String>();

		for (String diag : diagnosis) {
			String sensitiveCategory = "";
			List<ConceptMappingResultModelView> conceptMappingResults = conceptMappingService
					.findMappedConceptInVS(diag, ConstantUtil.ICD10CM, "");
			for (ConceptMappingResultModelView conceptMappingResult : conceptMappingResults) {
				// TODO update output format
				if (conceptMappingResult.getSensitiveCategories().size() != 0) {
					sensitiveCategory = sensitiveCategory + conceptMappingResult.getSensitiveCategories().toString();
				}
			}
			if (!sensitiveCategory.isEmpty()) {
				sensitive.put(diag, sensitiveCategory);
			}

		}

		Set<Map.Entry<String, String>> it = sensitive.entrySet();

		for (Map.Entry<String, String> map : it) {
			System.out.println("Source Code: " + map.getKey() + "----" + "Sensitive: " + map.getValue());
		}

		return;
	}

	public void testLabs() {
		List<String> resultDesc = getLabs();
		System.out.println(resultDesc);
		List<String> noResults = new ArrayList<String>();
		Map<String, String> sensitiveLab = new HashMap<String, String>();
		for (String lab : resultDesc) {
			List<SearchResult> searchResults = new ArrayList<SearchResult>();
			try {
				searchResults = SearchTerm(lab, ConstantUtil.PARAM_LOINC);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (searchResults.size() <= 0) {
				noResults.add(lab);
				// break;
			}
			for (SearchResult result : searchResults) {
				List<ConceptMappingResultModelView> conceptMappingResults = conceptMappingService.findMappedConceptInVS(
						StringUtils.substringAfterLast(result.getUri(), "/"), ConstantUtil.LOINC, "");
				for (ConceptMappingResultModelView conceptMappingResult : conceptMappingResults) {
					if (conceptMappingResult.getSensitiveCategories().size() <= 0) {
						break;
					} else {
						sensitiveLab.put(lab, conceptMappingResult.getSensitiveCategories().get(0));
					}

				}
			}

		}

		System.out.println("Cannot found corresponding code through UMLS API " + noResults);

		Set<Map.Entry<String, String>> it = sensitiveLab.entrySet();

		for (Map.Entry<String, String> map : it) {
			System.out.println("Source Code: " + map.getKey() + "----" + "Sensitive: " + map.getValue());
		}

		return;
	}
}
