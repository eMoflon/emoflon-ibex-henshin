package org.emoflon.ibex.tgg.run.emoflontohenshin;

import java.io.IOException;

import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.emoflontohenshin.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import EMoflonToHenshin.EMoflonToHenshinPackage;
import EMoflonToHenshin.impl.EMoflonToHenshinPackageImpl;
import language.impl.LanguagePackageImpl;

public class _RegistrationHelper {

	/** Load and register source and target metamodels */
	public static void registerMetamodels(ResourceSet rs, OperationalStrategy strategy) throws IOException {
		LanguagePackageImpl.init();
		HenshinPackageImpl.init();
		EcorePackageImpl.init();
		rs.getPackageRegistry().put("platform:/resource/org.eclipse.emf.henshin.model/model/henshin.ecore",
				HenshinPackageImpl.eINSTANCE);
		rs.getPackageRegistry().put("platform:/resource/org.emoflon.ibex.tgg.core.language/model/Language.ecore",
				LanguagePackageImpl.eINSTANCE);
		rs.getPackageRegistry().put("http://www.eclipse.org/emf/2002/Ecore", EcorePackageImpl.eINSTANCE);
		
		EMoflonToHenshinPackageImpl.init();
		
		rs.getPackageRegistry().put("platform:/resource/FeatureModelConciseToSafe/model/FeatureModelConciseToSafe.ecore", EMoflonToHenshinPackage.eINSTANCE);
		rs.getPackageRegistry().put("platform:/plugin/FeatureModelConciseToSafe/model/FeatureModelConciseToSafe.ecore", EMoflonToHenshinPackage.eINSTANCE);
		
	}

	/** Create default options **/
	public static IbexOptions createIbexOptions() {
		IbexOptions options = new IbexOptions();
		options.projectName("EMoflonToHenshin");
		options.projectPath("eMoflonToHenshin");
		options.debug(false);
		options.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
		return options;
	}
}
