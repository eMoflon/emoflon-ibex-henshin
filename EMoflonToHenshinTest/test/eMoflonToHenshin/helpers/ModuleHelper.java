package eMoflonToHenshin.helpers;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

public class ModuleHelper {
	
	public Module createModule() {
		Module m = HenshinFactory.eINSTANCE.createModule();
		
		EPackage p1 = EcoreFactory.eINSTANCE.createEPackage();
		p1.setName("TrainLanguage");
		p1.setNsURI("platform:/resource/TrainLanguage/model/TrainLanguage.ecore");
		p1.setNsPrefix("TrainLanguage");
		
		EPackage p2 = EcoreFactory.eINSTANCE.createEPackage();
		p2.setName("TrainToVehicle");
		p2.setNsURI("platform:/resource/TrainToVehicle/model/TrainToVehicle.ecore");
		p2.setNsPrefix("TrainToVehicle");
		
		EPackage p3 = EcoreFactory.eINSTANCE.createEPackage();
		p2.setName("VehicleLanguage");
		p2.setNsURI("platform:/resource/VehicleLanguage/model/VehicleLanguage.ecore");
		p2.setNsPrefix("VehicleLanguage");
		
		m.getImports().add(p1);
		m.getImports().add(p2);
		m.getImports().add(p3);

		return m;
	}
	
	public Rule createRule(Module module, String name) {
		Rule r = HenshinFactory.eINSTANCE.createRule(name);
		Graph lg = HenshinFactory.eINSTANCE.createGraph("Lhs");
		r.setLhs(lg);
		Graph rg = HenshinFactory.eINSTANCE.createGraph("Rhs");
		r.setRhs(rg);
		module.getUnits().add(r);
		return r;
	}
	
}
