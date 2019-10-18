package eMoflonToHenshin.util;

import org.benchmarx.BXTool;
import org.benchmarx.util.BenchmarxUtil;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class CustomBenchmarxUtil<S, T, D> extends BenchmarxUtil<S, T, D> {
	
	private BXTool<S, T, D> tool;

	public CustomBenchmarxUtil(BXTool<S, T, D> tool) {
		super(tool);
		this.tool = tool;
	}

	@Override
	public void assertPrecondition(String srcPath, String trgPath) {
		tool.assertPrecondition(Util.loadEMoflonTGGFromResource(srcPath), Util.loadHenshinModuleFromResource(trgPath, false));
	}

	@Override
	public void assertPostcondition(String srcPath, String trgPath) {
		tool.assertPostcondition(Util.loadEMoflonTGGFromResource(srcPath), Util.loadHenshinModuleFromResource(trgPath, false));
	}

	@Override
	public void assertPrecondition(ResourceSet rs, String srcPath, String trgPath) {
		tool.assertPrecondition(Util.loadEMoflonTGGFromResource(srcPath), Util.loadHenshinModuleFromResource(trgPath, false));
	}

	@Override
	public void assertPostcondition(ResourceSet rs, String srcPath, String trgPath) {
		tool.assertPostcondition(Util.loadEMoflonTGGFromResource(srcPath), Util.loadHenshinModuleFromResource(trgPath, false));
	}
	
}
