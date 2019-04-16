package org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

import language.TGGAttributeConstraintOperators;

public class UserDefined_cond_text_enum extends RuntimeTGGAttributeConstraint {

	/**
	 * Constraint cond_text_enum(param, op, val, condText)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	@Override
	public void solve() {
		RuntimeTGGAttributeConstraintVariable param = variables.get(0);
		RuntimeTGGAttributeConstraintVariable op = variables.get(1);
		RuntimeTGGAttributeConstraintVariable val = variables.get(2);
		RuntimeTGGAttributeConstraintVariable condText = variables.get(3);
		String bindingStates = getBindingStates(param, op, val, condText);

		if (op.isBound())
			try {
				EEnumLiteral tmp = (EEnumLiteral) op.getValue();
				op.bindToValue(tmp.getInstance());
			} catch (ClassCastException e) {
			}

		if (bindingStates.equals("BBBB")) {
			String paramString = (String) param.getValue();
			TGGAttributeConstraintOperators operator = (TGGAttributeConstraintOperators) op.getValue();
			String valString = (String) val.getValue();
			String tmp = paramString + UserDefConstrUtil.getOpSign(operator) + "\"" + valString + "\"";
			this.setSatisfied(tmp.equals(condText.getValue()));

		} else if (bindingStates.equals("FBBB")) {
			ResolvedCondText resCondText = UserDefConstrUtil.resolveCondText((String) condText.getValue(), true);
			param.bindToValue(resCondText.param);
			setSatisfied(resCondText.op.equals(op.getValue()) && resCondText.val.equals(val.getValue()));

		} else if (bindingStates.equals("BFBB")) {
			ResolvedCondText resCondText = UserDefConstrUtil.resolveCondText((String) condText.getValue(), true);
			op.bindToValue(resCondText.op);
			setSatisfied(resCondText.param.equals(param.getValue()) && resCondText.val.equals(val.getValue()));

		} else if (bindingStates.equals("FFBB")) {
			ResolvedCondText resCondText = UserDefConstrUtil.resolveCondText((String) condText.getValue(), true);
			param.bindToValue(resCondText.param);
			op.bindToValue(resCondText.op);
			setSatisfied(resCondText.val.equals(val.getValue()));

		} else if (bindingStates.equals("BBFB")) {
			ResolvedCondText resCondText = UserDefConstrUtil.resolveCondText((String) condText.getValue(), true);
			val.bindToValue(resCondText.val);
			setSatisfied(resCondText.param.equals(param.getValue()) && resCondText.op.equals(op.getValue()));

		} else if (bindingStates.equals("FBFB")) {
			ResolvedCondText resCondText = UserDefConstrUtil.resolveCondText((String) condText.getValue(), true);
			param.bindToValue(resCondText.param);
			val.bindToValue(resCondText.val);
			setSatisfied(resCondText.op.equals(op.getValue()));

		} else if (bindingStates.equals("BFFB")) {
			ResolvedCondText resCondText = UserDefConstrUtil.resolveCondText((String) condText.getValue(), true);
			op.bindToValue(resCondText.op);
			val.bindToValue(resCondText.val);
			setSatisfied(resCondText.param.equals(param.getValue()));

		} else if (bindingStates.equals("FFFB")) {
			ResolvedCondText resCondText = UserDefConstrUtil.resolveCondText((String) condText.getValue(), true);
			param.bindToValue(resCondText.param);
			op.bindToValue(resCondText.op);
			val.bindToValue(resCondText.val);
			setSatisfied(true);

		} else if (bindingStates.equals("BBBF")) {
			String paramString = (String) param.getValue();
			TGGAttributeConstraintOperators operator = (TGGAttributeConstraintOperators) op.getValue();
			String valString = (String) val.getValue();
			condText.bindToValue(paramString + UserDefConstrUtil.getOpSign(operator) + "\"" + valString + "\"");
			setSatisfied(true);

		} else if (bindingStates.equals("FBBF")) {
			String paramString = UserDefConstrUtil.getParamName();
			TGGAttributeConstraintOperators operator = (TGGAttributeConstraintOperators) op.getValue();
			String valString = (String) val.getValue();

			param.bindToValue(paramString);
			condText.bindToValue(paramString + UserDefConstrUtil.getOpSign(operator) + "\"" + valString + "\"");
			setSatisfied(true);
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}
}
