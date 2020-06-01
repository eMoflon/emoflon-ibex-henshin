package tool;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.emoflontohenshin.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.run.emoflontohenshin.config._DefaultRegistrationHelper;

import language.LanguagePackage;
import language.TGG;
import language.TGGRuleNode;

/**
 * Preferences for the classes {@link SyncAppl} and {@link EMoflonToHenshin}.
 */
public class EMoflonToHenshinPrefs {
	
	public static IRegistrationHelper registrationHelper = new _DefaultRegistrationHelper();

	private final String srcResourcePath = "/src.tgg.xmi";
	private final String trgResourcePath = "/trg.henshin";
	private final String corrResourcePath = "/corr.xmi";
	private final String protocolResourcePath = "/protocol.xmi";
	private final String ecoreResourcePath = "/ecore.xmi";

	private static IbexOptions ibexOptions = setIbexOptions();
	private static EClass tggParamDefClass = LanguagePackage.eINSTANCE.getTGGAttributeConstraintParameterDefinition();
	private static List<EObject> ecoreClasses = EcorePackage.eINSTANCE.eContents();

	private String instancesFolderPath;
	
	private Consumer<TGG> preProcessor;

	private boolean saveModels;
	private boolean useHenshinResource;

	private OperationMode operationMode;

	/**
	 * Operation modes:
	 * <li>INIT_FWD</li>
	 * <li>INIT_BWD (unstable)</li>
	 * <li>SYNC (unstable)</li>
	 */
	public enum OperationMode {
		INIT_FWD, INIT_BWD, SYNC;
	}

	public EMoflonToHenshinPrefs() {
		setInstancesFolderPath("/instances");
		setPreprocessing(null);
		setSaveModels(true);
		setOperationMode(OperationMode.INIT_FWD);
		setUseHenshinResource(true);
	}

	private static IbexOptions setIbexOptions() {
		IbexOptions options = registrationHelper.createIbexOptions();
		options.project.name("EMoflonToHenshin");
		options.project.path("eMoflonToHenshin");
		options.debug.ibexDebug(false);
		options.csp.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
		options.patterns.ignoreDomainConformity(true);
		options.patterns.ignoreInjectivity((node0, node1) -> ignoreInjectivityRules(node0, node1));
		return options;
	}

	private static boolean ignoreInjectivityRules(TGGRuleNode node0, TGGRuleNode node1) {
		if (EMoflonToHenshinPrefs.ecoreClasses.contains(node0.getType())
				&& EMoflonToHenshinPrefs.ecoreClasses.contains(node1.getType()))
			return true;
		if (node0.getType().equals(EMoflonToHenshinPrefs.tggParamDefClass)
				&& node1.getType().equals(EMoflonToHenshinPrefs.tggParamDefClass))
			return true;
		return false;
	}

	static IbexOptions getIbexOptions() {
		return ibexOptions;
	}

	/**
	 * @return project relative source file path
	 */
	public String getSrcPath() {
		return instancesFolderPath + srcResourcePath;
	}

	/**
	 * @return project relative target file path
	 */
	public String getTrgPath() {
		return instancesFolderPath + trgResourcePath;
	}

	/**
	 * @return project relative correspondence file path
	 */
	public String getCorrPath() {
		return instancesFolderPath + corrResourcePath;
	}

	/**
	 * @return project relative protocol file path
	 */
	public String getProtocolPath() {
		return instancesFolderPath + protocolResourcePath;
	}

	/**
	 * @return project relative ecore file path
	 */
	public String getEcorePath() {
		return instancesFolderPath + ecoreResourcePath;
	}

	/**
	 * @return project relative instances folder path
	 */
	public String getInstancesFolderPath() {
		return instancesFolderPath;
	}

	/**
	 * Sets the instances folder path
	 * 
	 * @param instancesFolderPath
	 *            - project relative
	 */
	public void setInstancesFolderPath(String instancesFolderPath) {
		this.instancesFolderPath = instancesFolderPath;
	}

	/**
	 * See {@link #setPreprocessing(Consumer)}.
	 * 
	 * @return the preprocessor. Can be <code>null</code>.
	 */
	public Consumer<TGG> getPreprocessing() {
		return preProcessor;
	}

	/**
	 * Sets a preprocessor for the TGG model.<br>
	 * See {@link #getPreprocessing()}.
	 * <p>
	 * <b>Note:</b> This option will only be applied if class
	 * {@link EMoflonToHenshin} is used for transformation.
	 * </p>
	 * 
	 * @param preProcessor
	 */
	public void setPreprocessing(Consumer<TGG> preProcessor) {
		this.preProcessor = preProcessor;
	}

	/**
	 * See {@link #setSaveModels(boolean)}.
	 * 
	 * @return <code>true</code> if models will be saved as file
	 */
	public boolean savesModels() {
		return saveModels;
	}

	/**
	 * If set to <code>true</code>, all models will be saved in the instances folder
	 * after transformation. Default value is <code>true</code>.<br>
	 * See {@link #savesModels()}.
	 * <p>
	 * <b>Note:</b> This option will only be applied if class
	 * {@link EMoflonToHenshin} is used for transformation.
	 * </p>
	 * 
	 * @param saveModels
	 */
	public void setSaveModels(boolean saveModels) {
		this.saveModels = saveModels;
	}

	/**
	 * See {@link #setOperationMode(OperationMode).}
	 * 
	 * @return operation mode
	 */
	public OperationMode getOperationMode() {
		return operationMode;
	}

	/**
	 * Sets the operation mode. Default value is <code>INIT_FWD</code>.<br>
	 * See {@link #getOperationMode()}, {@link OperationMode}.
	 * 
	 * @param mode
	 */
	public void setOperationMode(OperationMode mode) {
		this.operationMode = mode;
	}

	/**
	 * See {@link #setUseHenshinResource(boolean)}.
	 * 
	 * @return <code>true</code> if Henshin resource is used for loading
	 */
	public boolean usesHenshinResource() {
		return useHenshinResource;
	}

	/**
	 * If set to <code>true</code>, the Henshin model will be loaded using the
	 * {@link HenshinResourceFactory}. Default value is <code>true</code>.<br>
	 * See {@link #usesHenshinResource()}.
	 * 
	 * @param useHenshinRes
	 */
	public void setUseHenshinResource(boolean useHenshinRes) {
		this.useHenshinResource = useHenshinRes;
	}
}