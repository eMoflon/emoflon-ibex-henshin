package org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin;

import language.TGGAttributeConstraintOperators;

/**
 * Utility class for user defined attribute constraints
 */
class UserDefConstrUtil {

	/**
	 * Counter for generated parameters. Is needed to create unique parameter names
	 * for Henshin rules.
	 */
	private static int generatedParamCounter = 0;

	/**
	 * Returns a new unique parameter name.
	 * 
	 * @return the parameter name
	 */
	static String getParamName() {
		return "v" + generatedParamCounter++;
	}

	/**
	 * Returns the equivalent java sign for the given operator.
	 * 
	 * @param operator
	 * @return sign
	 */
	static String getOpSign(TGGAttributeConstraintOperators operator) {
		if (operator.equals(TGGAttributeConstraintOperators.EQUAL)) {
			return "==";
		} else if (operator.equals(TGGAttributeConstraintOperators.GR_EQUAL)) {
			return ">=";
		} else if (operator.equals(TGGAttributeConstraintOperators.GREATER)) {
			return ">";
		} else if (operator.equals(TGGAttributeConstraintOperators.LE_EQUAL)) {
			return "<=";
		} else if (operator.equals(TGGAttributeConstraintOperators.LESSER)) {
			return "<";
		} else if (operator.equals(TGGAttributeConstraintOperators.UNEQUAL)) {
			return "!=";
		} else {
			return "--UNKNOWN--";
		}
	}

	/**
	 * Splits the given condition text into parameter, operator and value. If
	 * <code>trimValue</code> is set to <code>true</code>, quotes around the value
	 * will be ignored.
	 * 
	 * @param condText
	 *            - condition text
	 * @param trimValue
	 *            - trims the value
	 * @return an Instance of {@link ResolvedCondText} containing parameter,
	 *         operator and value
	 * @throws RuntimeException
	 *             - if the condition text is not correctly formatted
	 */
	static ResolvedCondText resolveCondText(String condText, boolean trimValue) throws RuntimeException {
		TGGAttributeConstraintOperators op;
		String opSign;

		if (condText.contains("==")) {
			opSign = "==";
			op = TGGAttributeConstraintOperators.EQUAL;
		} else if (condText.contains(">=")) {
			opSign = ">=";
			op = TGGAttributeConstraintOperators.GR_EQUAL;
		} else if (condText.contains(">")) {
			opSign = ">";
			op = TGGAttributeConstraintOperators.GREATER;
		} else if (condText.contains("<=")) {
			opSign = "<=";
			op = TGGAttributeConstraintOperators.LE_EQUAL;
		} else if (condText.contains("<")) {
			opSign = "<";
			op = TGGAttributeConstraintOperators.LESSER;
		} else if (condText.contains("!=")) {
			opSign = "!=";
			op = TGGAttributeConstraintOperators.UNEQUAL;
		} else {
			throw new RuntimeException("The condition text is not correctly formatted: " + condText);
		}

		String[] splittedCondText = condText.split(opSign);
		if (trimValue)
			splittedCondText[1].replaceAll("\"", "");
		return new ResolvedCondText(splittedCondText[0], op, splittedCondText[1]);
	}

}
