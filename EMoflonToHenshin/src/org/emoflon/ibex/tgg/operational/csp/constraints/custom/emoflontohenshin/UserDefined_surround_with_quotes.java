package org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class UserDefined_surround_with_quotes extends RuntimeTGGAttributeConstraint {

	/**
	 * Constraint surround_with_quotes(word, result)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -SURROUND_WITH_QUOTES- needs exactly 2 variables");

		RuntimeTGGAttributeConstraintVariable word = variables.get(0);
		RuntimeTGGAttributeConstraintVariable result = variables.get(1);
		String bindingStates = getBindingStates(word, result);

		if (bindingStates.equals("BB")) {
			String wordString = (String) word.getValue();
			setSatisfied(result.getValue().equals("\"" + wordString + "\""));

		} else if (bindingStates.equals("BF")) {
			String wordString = (String) word.getValue();
			result.bindToValue("\"" + wordString + "\"");
			setSatisfied(true);

		} else if (bindingStates.equals("FB")) {
			String resultString = (String) result.getValue();
			word.bindToValue(resultString.replace('"', ' ').trim());
			setSatisfied(true);

		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}
}
