package org.emoflon.ibex.tgg.run.emoflontohenshin.config;

import java.io.IOException;

import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.emoflontohenshin.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.runtime.democles.DemoclesTGGEngine;

import language.impl.LanguagePackageImpl;

public class DemoclesRegistrationHelper implements IRegistrationHelper {

	/** Load and register source and target metamodels */
	public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException {
		LanguagePackageImpl.init();
		HenshinPackageImpl.init();
		EcorePackageImpl.init();
		rs.getPackageRegistry().put("platform:/resource/org.eclipse.emf.henshin.model/model/henshin.ecore",
				HenshinPackageImpl.eINSTANCE);
		rs.getPackageRegistry().put("platform:/resource/org.emoflon.ibex.tgg.core.language/model/Language.ecore",
				LanguagePackageImpl.eINSTANCE);
		rs.getPackageRegistry().put("http://www.eclipse.org/emf/2002/Ecore", EcorePackageImpl.eINSTANCE);
	}

	/** Create default options **/
	public IbexOptions createIbexOptions() {
		IbexOptions options = new IbexOptions();
		options.blackInterpreter(new DemoclesTGGEngine());
		options.project.name("EMoflonToHenshin");
		options.project.path("EMoflonToHenshin");
		options.debug.ibexDebug(false);
		options.csp.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
		options.registrationHelper(this);
		return options;
	}
}
