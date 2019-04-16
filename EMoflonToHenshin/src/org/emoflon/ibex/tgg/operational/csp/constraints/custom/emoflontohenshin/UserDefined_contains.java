package org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class UserDefined_contains extends RuntimeTGGAttributeConstraint {

	/**
	 * Constraint contains(word, sequence)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -CONTAINS- needs exactly 2 variables");

		RuntimeTGGAttributeConstraintVariable word = variables.get(0);
		RuntimeTGGAttributeConstraintVariable sequence = variables.get(1);
		String bindingStates = getBindingStates(word, sequence);
		
		if(bindingStates.equals("BB")) {
			String wordString = (String) word.getValue();
			String sequenceString = (String) sequence.getValue();
			setSatisfied(wordString.contains(sequenceString));
			
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}
}
