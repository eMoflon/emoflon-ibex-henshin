package tool;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.emoflon.ibex.tgg.runtime.engine.DemoclesTGGEngine;

import language.impl.LanguagePackageImpl;
import tool.EMoflonToHenshinPrefs.OperationMode;

/**
 * Custom synchronization application for eMoflon to Henshin transformations.
 */
public class SyncAppl extends SYNC {

	private Resource ecore;

	private EMoflonToHenshinPrefs prefs;
	private String tggFilePath;
	private String henshinFilePath;

	/**
	 * @param tggFilePath     - if set, the application will load the TGG model from
	 *                        the specified path. Can be <code>null</code>.
	 * @param henshinFilePath - if set, the application will load the Henshin model
	 *                        from the specified path. Can be <code>null</code>.
	 * @param prefs           - Preferences
	 * @throws IOException
	 */
	public SyncAppl(String tggFilePath, String henshinFilePath, EMoflonToHenshinPrefs prefs) throws IOException {
		super(EMoflonToHenshinPrefs.getIbexOptions());
		this.prefs = prefs;
		this.tggFilePath = tggFilePath;
		this.henshinFilePath = henshinFilePath;
		registerBlackInterpreter(new DemoclesTGGEngine());
		setUpdatePolicy(UpdatePolicyUtil.getUpdatePolicy());
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		if (prefs.getOperationMode().equals(OperationMode.INIT_FWD))
			return patternName.endsWith(PatternSuffixes.FWD);
		else if (prefs.getOperationMode().equals(OperationMode.INIT_BWD))
			return patternName.endsWith(PatternSuffixes.BWD);
		else
			return patternName.endsWith(PatternSuffixes.FWD) || patternName.endsWith(PatternSuffixes.BWD)
					|| patternName.endsWith(PatternSuffixes.CONSISTENCY);
	}

	@Override
	public void loadModels() throws IOException {
		if (tggFilePath == null || tggFilePath.equals(""))
			s = createResource(options.projectPath() + prefs.getSrcPath());
		else {
			s = loadResource(tggFilePath);
			s.setURI(URI.createURI(options.projectPath() + prefs.getSrcPath()).resolve(base));
		}

		boolean valIsEmpty = henshinFilePath == null || henshinFilePath.equals("");
		if (prefs.usesHenshinResource()) {
			if (valIsEmpty) {
				t = new HenshinResourceFactory()
						.createResource(URI.createURI(options.projectPath() + prefs.getTrgPath()).resolve(base));
			} else {
				HenshinResourceSet resSet = new HenshinResourceSet("foo");
				t = resSet.getResource("../../" + henshinFilePath);
				resSet.getResources().clear();
				t.setURI(URI.createURI(options.projectPath() + prefs.getTrgPath()).resolve(base));
			}
			rs.getResources().add(t);
		} else {
			if (valIsEmpty)
				t = createResource(options.projectPath() + prefs.getTrgPath());
			else {
				t = loadResource(henshinFilePath);
				t.setURI(URI.createURI(options.projectPath() + prefs.getTrgPath()).resolve(base));
			}
		}

		c = createResource(options.projectPath() + prefs.getCorrPath());
		p = createResource(options.projectPath() + prefs.getProtocolPath());

		ecore = createResource(options.projectPath() + prefs.getEcorePath());
		ecore.getContents().add(EcorePackage.eINSTANCE);

		EcoreUtil.resolveAll(rs);
	}

	@Override
	public void saveModels() throws IOException {
		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
		ecore.save(null);
	}

	@Override
	protected void registerUserMetamodels() throws IOException {
		LanguagePackageImpl.init();
		HenshinPackageImpl.init();
		EcorePackageImpl.init();

		rs.getPackageRegistry().put("platform:/resource/org.eclipse.emf.henshin.model/model/henshin.ecore",
				HenshinPackageImpl.eINSTANCE);
		rs.getPackageRegistry().put("platform:/plugins/eMoflonToHenshin/src/tool/models/Language.ecore",
				LanguagePackageImpl.eINSTANCE);
		rs.getPackageRegistry().put("http://www.eclipse.org/emf/2002/Ecore", EcorePackageImpl.eINSTANCE);

		loadAndRegisterCorrMetamodel(options.projectPath() + "/model/" + options.projectName() + ".ecore");
	}
}
