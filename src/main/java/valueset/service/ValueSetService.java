package valueset.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import valueset.dao.ConceptCodeRepository;
import valueset.dao.ValueSetCategoryRepository;
import valueset.dao.ValueSetRepository;
import valueset.model.dbModel.ConceptCode;
import valueset.model.dbModel.ValueSet;
import valueset.model.dbModel.ValueSetCategory;
import valueset.model.modelView.ValueSetModelView;

/**
 * It is the service used to handle concept code, value set, sensitive category and their relationships
 * @author ZHOU WENTAO
 *
 */
@Service
@Transactional
public class ValueSetService {

	private final ValueSetRepository valueSetRepo;
	private final ValueSetCategoryRepository valueSetCategoryRepo;
	private final ConceptCodeRepository conceptCodeRepo;

	public ValueSetService(ValueSetRepository valueSetRepo, ValueSetCategoryRepository valueSetCategoryRepo, 
			ConceptCodeRepository conceptCodeRepo) {
		this.valueSetRepo = valueSetRepo;
		this.valueSetCategoryRepo = valueSetCategoryRepo;
		this.conceptCodeRepo = conceptCodeRepo;
	}

	/**
	 * Find all value set
	 * @return
	 */
	public List<ValueSet> findAllValueSet() {
		List<ValueSet> valueSets = new ArrayList<ValueSet>();
		valueSets = (List<ValueSet>) valueSetRepo.findAll();
		return valueSets;
	}
	
	/**
	 * Find all sensitive categories
	 * @return
	 */
	public List<ValueSetCategory> findAllSensiveCategory() {
		List<ValueSetCategory> valueSetCategories = new ArrayList<ValueSetCategory>();
		valueSetCategories = (List<ValueSetCategory>) valueSetCategoryRepo.findAll();
		
		return valueSetCategories;
	}
	
	/**
	 * Find Value set and sensitive categories
	 * @return
	 */
	public List<ValueSetModelView> findValueSetAndCategory() {
		List<ValueSetModelView> valueSetModelViews = new ArrayList<ValueSetModelView>();
		Iterator<ValueSetCategory> iterator = valueSetCategoryRepo.findAll().iterator();
		while (iterator.hasNext()) {
			ValueSetModelView valueSetModelView = new ValueSetModelView();
			ValueSetCategory valueSetCategory = iterator.next();
			valueSetModelView.setValueSetCategory(valueSetCategory);
			List<ValueSet> valueSets = valueSetCategory.getValueSets();
			valueSetModelView.setValueSets(valueSets);
			
			valueSetModelViews.add(valueSetModelView);
		}
		return valueSetModelViews;
	}
	
	/**
	 * Find value set by giving category id
	 * @param categoryID
	 * @return
	 */
	public List<ValueSetModelView> findValueSetByCategoryID(Long categoryID) {
		List<ValueSetModelView> valueSetModelViews = new ArrayList<ValueSetModelView>();
		ValueSetCategory valueSetCategory = valueSetCategoryRepo.findOne(categoryID);
		if (null != valueSetCategory) {
			ValueSetModelView valueSetModelView = new ValueSetModelView();
			valueSetModelView.setValueSetCategory(valueSetCategory);
			List<ValueSet> valueSets = valueSetCategory.getValueSets();
			valueSetModelView.setValueSets(valueSets);
			
			valueSetModelViews.add(valueSetModelView);
		}
		
		return valueSetModelViews;
	}
	
	/**
	 * Find value set and sensitive category by giving a concept code ID
	 * @param conceptCodeID
	 * @return
	 */
	public List<ValueSetModelView> findValueSetAndCategoryByConceptCodeID(Long conceptCodeID) {
		List<ValueSetModelView> valueSetModelViews = new ArrayList<ValueSetModelView>();
		//find concept code by id
		ConceptCode conceptCode = conceptCodeRepo.findOne(conceptCodeID);
		List<ValueSet> valueSets = conceptCode.getValueSets();
		//The results of following code are get all value set of a specific cagegory
		/*for (ValueSet valueSet : valueSets) {
			if (!categories.contains(valueSet.getValueSetCategory().getCode())) {
				valueSetCategories.add(valueSet.getValueSetCategory());
			}
		}*/
		
		//TODO re-write the following code to support one concept code refers to multiply categories
		ValueSetModelView valueSetModelView = new ValueSetModelView();
		valueSetModelView.setValueSets(valueSets);
		valueSetModelView.setValueSetCategory(valueSets.get(0).getValueSetCategory());
		
		valueSetModelViews.add(valueSetModelView);
		
		return valueSetModelViews;
	}
}
