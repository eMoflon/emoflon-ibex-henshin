package org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin;

import language.TGGAttributeConstraintOperators;

/**
 * Container class for <code>resolveCondText</code> in Class {@link UserDefConstrUtil}
 */
class ResolvedCondText {
	public String param;
	public TGGAttributeConstraintOperators op;
	public String val;

	public ResolvedCondText(String param, TGGAttributeConstraintOperators op, String val) {
		this.param = param;
		this.op = op;
		this.val = val;
	}
}
