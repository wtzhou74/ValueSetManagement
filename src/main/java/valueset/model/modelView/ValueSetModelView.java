package valueset.model.modelView;

import java.util.List;

import valueset.model.dbModel.ValueSet;
import valueset.model.dbModel.ValueSetCategory;

/**
 * A model view class used to describe value set and sensitive category
 * @author ZHOU WENTAO
 *
 */
public class ValueSetModelView {

	private List<ValueSet> valueSets;
	private ValueSetCategory valueSetCategory;
	
	
	public ValueSetModelView() {
	}
	public List<ValueSet> getValueSets() {
		return valueSets;
	}
	public void setValueSets(List<ValueSet> valueSets) {
		this.valueSets = valueSets;
	}
	public ValueSetCategory getValueSetCategory() {
		return valueSetCategory;
	}
	public void setValueSetCategory(ValueSetCategory valueSetCategory) {
		this.valueSetCategory = valueSetCategory;
	}
	
	
	
}
