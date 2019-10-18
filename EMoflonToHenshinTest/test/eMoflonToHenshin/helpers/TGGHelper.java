package eMoflonToHenshin.helpers;

import org.eclipse.emf.ecore.EClass;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGG;
import language.TGGAttributeConstraintLibrary;
import language.TGGRule;
import language.TGGRuleNode;

public class TGGHelper {
	
	public TGGRule createRule(TGG tgg, String name) {
		TGGRule r = LanguageFactory.eINSTANCE.createTGGRule();
		TGGAttributeConstraintLibrary l = LanguageFactory.eINSTANCE.createTGGAttributeConstraintLibrary();
		r.setName(name);
		r.setAttributeConditionLibrary(l);
		tgg.getRules().add(r);
		return r;
	}

	public TGGRuleNode createNode(TGGRule rule, DomainType domain, BindingType binding, EClass type, String name) {
		TGGRuleNode n = LanguageFactory.eINSTANCE.createTGGRuleNode();
		n.setDomainType(domain);
		n.setBindingType(binding);
		n.setType(type);
		n.setName(name);
		rule.getNodes().add(n);
		return n;
	}
}
