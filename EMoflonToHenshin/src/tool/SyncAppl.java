package tool;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.emoflon.ibex.tgg.run.emoflontohenshin.config._DefaultRegistrationHelper;

import tool.EMoflonToHenshinPrefs.OperationMode;

/**
 * Custom synchronization application for eMoflon to Henshin transformations.
 */
public class SyncAppl extends SYNC {

	private EMoflonToHenshinPrefs prefs;
	
	public static IRegistrationHelper registrationHelper = new _DefaultRegistrationHelper();

	/**
	 * @param tggFilePath     - if set, the application will load the TGG model from
	 *                        the specified path. Can be <code>null</code>.
	 * @param henshinFilePath - if set, the application will load the Henshin model
	 *                        from the specified path. Can be <code>null</code>.
	 * @param prefs           - Preferences
	 * @throws IOException
	 */
	public SyncAppl(String tggFilePath, String henshinFilePath, EMoflonToHenshinPrefs prefs) throws IOException {
		super(EMoflonToHenshinPrefs.getIbexOptions().resourceHandler(new TGGResourceHandler() {

			private Resource ecore;
	
			@Override
			public void loadModels() throws IOException {
				if (tggFilePath == null || tggFilePath.equals(""))
					source = createResource(options.project.path() + prefs.getSrcPath());
				else {
					source = loadResource(tggFilePath);
					source.setURI(URI.createURI(options.project.path() + prefs.getSrcPath()).resolve(base));
				}
			
				boolean valIsEmpty = henshinFilePath == null || henshinFilePath.equals("");
				if (prefs.usesHenshinResource()) {
					if (valIsEmpty) {
						target = new HenshinResourceFactory()
								.createResource(URI.createURI(options.project.path() + prefs.getTrgPath()).resolve(base));
					} else {
						HenshinResourceSet resSet = new HenshinResourceSet("foo");
						target = resSet.getResource("../../" + henshinFilePath);
						resSet.getResources().clear();
						target.setURI(URI.createURI(options.project.path() + prefs.getTrgPath()).resolve(base));
					}
					rs.getResources().add(target);
				} else {
					if (valIsEmpty)
						target = createResource(options.project.path() + prefs.getTrgPath());
					else {
						target = loadResource(henshinFilePath);
						target.setURI(URI.createURI(options.project.path() + prefs.getTrgPath()).resolve(base));
					}
				}
			
				corr = createResource(options.project.path() + prefs.getCorrPath());
				protocol = createResource(options.project.path() + prefs.getProtocolPath());
			
				ecore = createResource(options.project.path() + prefs.getEcorePath());
				ecore.getContents().add(EcorePackage.eINSTANCE);
			
				EcoreUtil.resolveAll(rs);
			}

			@Override
			public void saveModels() throws IOException {
				source.save(null);
				target.save(null);
				corr.save(null);
				protocol.save(null);
				ecore.save(null);
			}
			
		}));
		this.prefs = prefs;
		setUpdatePolicy(UpdatePolicyUtil.getUpdatePolicy());
	}

	@Override
	public boolean isPatternRelevantForInterpreter(PatternType type) {
		if (prefs.getOperationMode().equals(OperationMode.INIT_FWD))
			return type == PatternType.FWD;
		else if (prefs.getOperationMode().equals(OperationMode.INIT_BWD))
			return type == PatternType.BWD;
		else
			return type == PatternType.FWD || type == PatternType.BWD
					|| type == PatternType.CONSISTENCY;
	}

	@Override
	public Collection<PatternType> getPatternRelevantForCompiler() {
		Collection<PatternType> list = new LinkedList<>();
		
		
		
		return list;
	}
}
