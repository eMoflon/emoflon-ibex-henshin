package tool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class UpdatePolicyData {
	
	static final Set<String> BASE_RULES = new HashSet<String>(Arrays.asList(new String[] {
			"AttrConstr_eq_Attr_Base_ConCre_RULE",
			"AttrConstr_eq_Attr_Base_Con_RULE",
			"AttrExpr_Attr_Base_ConObj_RULE"
		}));
	
	static final Set<String> LIT_ENUM_OTHER_RULES = new HashSet<String>(Arrays.asList(new String[] {
			"AttrExpr_Lit_other_Cre_RULE",
			"AttrExpr_Lit_other_Con_RULE",
			"AttrExpr_Enum_other_Cre_RULE",
			"AttrExpr_Enum_other_Con_RULE"
		}));
	
	static final Set<String> LIT_ENUM_EQ_RULES = new HashSet<String>(Arrays.asList(new String[] {
			"AttrConstr_eq_Lit_Cre_RULE",
			"AttrConstr_eq_Lit_Con_RULE",
			"AttrExpr_Lit_eq_Cre_RULE",
			"AttrExpr_Lit_eq_Con_RULE",
			"AttrExpr_Enum_eq_Cre_RULE",
			"AttrExpr_Enum_eq_Con_RULE"
		}));

}
