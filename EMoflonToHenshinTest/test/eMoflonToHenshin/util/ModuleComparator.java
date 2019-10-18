package eMoflonToHenshin.util;

import org.benchmarx.emf.Comparator;
import org.eclipse.emf.henshin.model.Module;

public class ModuleComparator implements Comparator<Module> {

	@Override
	public void assertEquals(Module expected, Module actual) {
		Util.compareModels(expected, actual);		
	}

}
