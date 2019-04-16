package org.emoflon.ibex.tgg.operational.csp.constraints.factories.emoflontohenshin;

import java.util.HashMap;
import java.util.HashSet;			

import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;			

import org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin.UserDefined_cond_text;
import org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin.UserDefined_cond_text_enum;
import org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin.UserDefined_eq_param;
import org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin.UserDefined_surround_with_quotes;
import org.emoflon.ibex.tgg.operational.csp.constraints.custom.emoflontohenshin.UserDefined_contains;

public class UserDefinedRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {

	public UserDefinedRuntimeTGGAttrConstraintFactory() {
		super();
	}
	
	@Override
	protected void initialize() {
		creators = new HashMap<>();
		creators.put("cond_text", () -> new UserDefined_cond_text());
		creators.put("cond_text_enum", () -> new UserDefined_cond_text_enum());
		creators.put("eq_param", () -> new UserDefined_eq_param());
		creators.put("surround_with_quotes", () -> new UserDefined_surround_with_quotes());
		creators.put("contains", () -> new UserDefined_contains());

		constraints = new HashSet<String>();
		constraints.addAll(creators.keySet());
	}
}
