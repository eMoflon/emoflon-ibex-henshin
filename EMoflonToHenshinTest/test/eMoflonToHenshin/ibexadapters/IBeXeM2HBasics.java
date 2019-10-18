package eMoflonToHenshin.ibexadapters;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.emf.henshin.model.Module;

import eMoflonToHenshin.util.ModuleComparator;
import eMoflonToHenshin.util.TGGComparator;
import language.TGG;
import testsuite.ibex.testUtil.IbexAdapter;
import tool.EMoflonToHenshinPrefs;
import tool.EMoflonToHenshinPrefs.OperationMode;
import tool.SyncAppl;

public class IBeXeM2HBasics extends IbexAdapter<TGG, Module> {

	public IBeXeM2HBasics(String projectName) {
		super(new TGGComparator(), new ModuleComparator(), projectName);
	}

	@Override
	public void initiateSynchronisationDialogue() {
		BasicConfigurator.configure();
		try {			
			EMoflonToHenshinPrefs prefs = new EMoflonToHenshinPrefs();
			prefs.setSaveModels(true);
			prefs.setOperationMode(OperationMode.SYNC);
			prefs.setUseHenshinResource(false);
			
			synchroniser = new SyncAppl(projectName + "/resources/pre/BasicTgg.tgg.xmi", null, prefs);
			synchroniser.forward();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
