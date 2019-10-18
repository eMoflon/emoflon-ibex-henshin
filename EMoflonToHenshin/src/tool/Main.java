package tool;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import tool.TggOperationalization.OperationalizationOpt;

public class Main {

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);

//		String tggPath = "LearningBoxToDictionaryIntegration/model/LearningBoxToDictionaryIntegration_flattened.tgg.xmi";
//		String tggPath = "TestTGG/model/TestTGG.tgg.xmi";
		String tggPath = "TrainToVehicle/model/TrainToVehicle.tgg.xmi";

		EMoflonToHenshinPrefs prefs = new EMoflonToHenshinPrefs();
		prefs.setPreprocessing(tgg -> {
			TggOperationalization.operationalizate(tgg, OperationalizationOpt.BACKWARD);
		});

		EMoflonToHenshin transformer = new EMoflonToHenshin(tggPath, null, prefs);
		transformer.forward();
		transformer.terminate();
	}

}
