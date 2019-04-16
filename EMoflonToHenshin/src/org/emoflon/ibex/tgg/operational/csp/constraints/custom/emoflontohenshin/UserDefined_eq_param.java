package org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class UserDefined_eq_param extends RuntimeTGGAttributeConstraint {
	
	/**
	 * Constraint eq_param(attrVal, paramName)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -EQ_PARAM- needs exactly 2 variables");

		RuntimeTGGAttributeConstraintVariable attrVal = variables.get(0);
		RuntimeTGGAttributeConstraintVariable paramName = variables.get(1);
		String bindingStates = getBindingStates(attrVal, paramName);

		if (bindingStates.equals("BB")) {
			setSatisfied(attrVal.getValue().equals(paramName.getValue()));
			
		} else if (bindingStates.equals("FB")) {
			attrVal.bindToValue(paramName.getValue());
			setSatisfied(true);
			
		} else if (bindingStates.equals("FF")) {
			String paramNameString = UserDefConstrUtil.getParamName();
			paramName.bindToValue(paramNameString);
			attrVal.bindToValue(paramNameString);
			setSatisfied(true);
			
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}
}
