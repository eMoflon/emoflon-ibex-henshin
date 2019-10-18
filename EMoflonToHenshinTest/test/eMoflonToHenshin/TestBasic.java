package eMoflonToHenshin;

import org.eclipse.emf.henshin.model.Module;
import org.junit.Test;

import TrainLanguage.TrainLanguagePackage;
import eMoflonToHenshin.helpers.ModuleHelper;
import eMoflonToHenshin.helpers.TGGHelper;
import eMoflonToHenshin.ibexadapters.IBeXeM2HBasics;
import eMoflonToHenshin.util.CustomBenchmarxUtil;
import language.BindingType;
import language.DomainType;
import language.TGG;

public class TestBasic extends AbstractTestCase<TGG, Module> {

	private static final String projectName = "TrainToVehicle";

	private TGGHelper helperTgg;
	private ModuleHelper helperModule;

	public TestBasic() {
		super(new IBeXeM2HBasics(projectName));
	}

	@Override
	protected void initHelpers() {
		util = new CustomBenchmarxUtil<>(tool);

		helperTgg = new TGGHelper();
		helperModule = new ModuleHelper();
	}

	@Override
	protected String getProjectName() {
		return projectName;
	}

	@Test
	public void rule_FWD() {
		assertPrecondition("pre/BasicTgg", "expected/BasicModule");

		tool.performAndPropagateSourceEdit(tgg -> {
			helperTgg.createRule(tgg, "TrainToVehicle");
		});

		assertPostcondition("in/BasicRule", "expected/BasicRule");
	}

	@Test
	public void rule_BWD() {
		assertPrecondition("pre/BasicTgg", "expected/BasicModule");

		tool.performAndPropagateTargetEdit(module -> {
			helperModule.createRule(module, "TrainToVehicle");
		});

		assertPostcondition("in/BasicRule", "expected/BasicRule");
	}

	@Test
	public void createNode_FWD() {
		assertPrecondition("pre/BasicTgg", "expected/BasicModule");

		tool.performAndPropagateSourceEdit(tgg -> {
			helperTgg.createRule(tgg, "TrainToVehicle");
		});

		tool.performAndPropagateSourceEdit(tgg -> {
			helperTgg.createNode(tgg.getRules().get(0), //
					DomainType.SRC, //
					BindingType.CREATE, //
					TrainLanguagePackage.eINSTANCE.getTrain(), //
					"train");
		});

		assertPostcondition("in/BasicCreNode", "expected/BasicCreNode");
	}
}
