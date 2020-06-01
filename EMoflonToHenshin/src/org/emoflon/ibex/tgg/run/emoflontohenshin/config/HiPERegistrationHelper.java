package org.emoflon.ibex.tgg.run.emoflontohenshin.config;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.emoflontohenshin.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.operational.strategies.opt.BWD_OPT;
import org.emoflon.ibex.tgg.operational.strategies.opt.FWD_OPT;
import org.emoflon.ibex.tgg.runtime.hipe.HiPETGGEngine;
import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;

import EMoflonToHenshin.EMoflonToHenshinPackage;
import EMoflonToHenshin.impl.EMoflonToHenshinPackageImpl;
import language.impl.LanguagePackageImpl;

public class HiPERegistrationHelper implements IRegistrationHelper {
	
	/** Create default options **/
	public final void setWorkspaceRootDirectory(ResourceSet resourceSet) throws IOException {
		final String root = "../";
		URI key = URI.createPlatformResourceURI("/", true);
		URI value = URI.createFileURI(new File(root).getCanonicalPath() + File.separatorChar);
		resourceSet.getURIConverter().getURIMap().put(key, value);
	}

	/** Load and register source and target metamodels */
	public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException {
		
		// Set correct workspace root
		setWorkspaceRootDirectory(rs);
		
		// Load and register source and target metamodels
		EPackage languagePack = null;
		EPackage henshinPack = null;
		EPackage ecorePack = null;
		EPackage emoflontohenshinPack = null;
		
		if(executable instanceof FWD_OPT) {
			Resource res = executable.getResourceHandler().loadResource("platform:/resource/org.eclipse.emf.henshin.model/model/henshin.ecore");
			henshinPack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
			
			res = executable.getResourceHandler().loadResource("http://www.eclipse.org/emf/2002/Ecore");
			ecorePack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
			
			res = executable.getResourceHandler().loadResource("platform:/resource/EMoflonToHenshin/model/EMoflonToHenshin.ecore");
			emoflontohenshinPack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
		}
				
		if(executable instanceof BWD_OPT) {
			Resource res = executable.getResourceHandler().loadResource("platform:/plugin/org.emoflon.ibex.tgg.language/model/Language.ecore");
			languagePack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
			
			res = executable.getResourceHandler().loadResource("http://www.eclipse.org/emf/2002/Ecore");
			ecorePack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
			
			res = executable.getResourceHandler().loadResource("platform:/resource/EMoflonToHenshin/model/EMoflonToHenshin.ecore");
			emoflontohenshinPack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
		}

		if(languagePack == null)
			languagePack = LanguagePackageImpl.init();
				
		if(henshinPack == null)
			henshinPack = HenshinPackageImpl.init();
		
		if(ecorePack == null)
			ecorePack = EcorePackageImpl.init();
		
		if(emoflontohenshinPack == null) {
			emoflontohenshinPack = EMoflonToHenshinPackageImpl.init();
			rs.getPackageRegistry().put("platform:/resource/EMoflonToHenshin/model/EMoflonToHenshin.ecore", EMoflonToHenshinPackage.eINSTANCE);
			rs.getPackageRegistry().put("platform:/plugin/EMoflonToHenshin/model/EMoflonToHenshin.ecore", EMoflonToHenshinPackage.eINSTANCE);
		}
			
		rs.getPackageRegistry().put("platform:/resource/org.emoflon.ibex.tgg.language/model/Language.ecore", languagePack);
	    rs.getPackageRegistry().put("platform:/plugin/org.emoflon.ibex.tgg.language/model/Language.ecore", languagePack);	
			
		rs.getPackageRegistry().put("platform:/resource/org.eclipse.emf.henshin.model/model/henshin.ecore", henshinPack);
		rs.getPackageRegistry().put("platform:/plugin/org.eclipse.emf.henshin.model/model/henshin.ecore", henshinPack);
		
		rs.getPackageRegistry().put("http://www.eclipse.org/emf/2002/Ecore", henshinPack);
	}

	/** Create default options **/
	public IbexOptions createIbexOptions() {
		IbexOptions options = new IbexOptions();
		options.blackInterpreter(new HiPETGGEngine());
		options.project.name("EMoflonToHenshin");
		options.project.path("EMoflonToHenshin");
		options.debug.ibexDebug(false);
		options.csp.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
		options.registrationHelper(this);
		return options;
	}
}
