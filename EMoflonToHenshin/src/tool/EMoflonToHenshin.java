package tool;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;

import language.TGG;

/**
 * Class for simple eMoflon to Henshin transformations. For more opportunities
 * use {@link SyncAppl} directly.
 */
public class EMoflonToHenshin {

	private final static Logger logger = Logger.getLogger(EMoflonToHenshin.class);

	private EMoflonToHenshinPrefs prefs;

	private SyncAppl sync;

	/**
	 * @param tggFilePath
	 *            - if set, the application will load the TGG model from the
	 *            specified path. Can be <code>null</code>.
	 * @param henshinFilePath
	 *            - if set, the application will load the Henshin model from the
	 *            specified path. Can be <code>null</code>.
	 * @param prefs
	 *            - Preferences
	 * @throws IOException
	 */
	public EMoflonToHenshin(String tggFilePath, String henshinFilePath, EMoflonToHenshinPrefs prefs)
			throws IOException {
		logger.info("Starting init");
		long tic = System.currentTimeMillis();

		if (prefs == null)
			this.prefs = new EMoflonToHenshinPrefs();
		else
			this.prefs = prefs;

		sync = new SyncAppl(tggFilePath, henshinFilePath, prefs);

		long toc = System.currentTimeMillis();
		logger.info("Completed init in: " + (toc - tic) + " ms");
	}

	private void apply(boolean isForward) throws IOException {
		logger.info("Starting TGG preprocess");

		if (prefs.getPreprocessing() != null) {
			prefs.getPreprocessing().accept(getEMoflonTGG());
		}

		logger.info("Starting eMoflon to Henshin translation");
		long tic = System.currentTimeMillis();

		if (isForward)
			sync.forward();
		else
			sync.backward();

		if (prefs.savesModels())
			sync.saveModels();

		long toc = System.currentTimeMillis();
		logger.info("Completed translation in: " + (toc - tic) + " ms");
	}

	/**
	 * Applies forward transformation
	 * 
	 * @throws IOException
	 */
	public void forward() throws IOException {
		apply(true);
	}

	/**
	 * Applies backward transformation
	 * 
	 * @throws IOException
	 */
	public void backward() throws IOException {
		apply(false);
	}

	/**
	 * Terminates the instance of this application
	 * 
	 * @throws IOException
	 */
	public void terminate() throws IOException {
		if (!prefs.savesModels())
			sync.saveModels();
		sync.terminate();
	}

	public TGG getEMoflonTGG() {
		return (TGG) getSrcContents().get(0);
	}

	public Module getHenshinModule() {
		return (Module) getTrgContents().get(0);
	}

	public EMoflonToHenshinPrefs getPrefs() {
		return prefs;
	}

	private EList<EObject> getSrcContents() {
		return sync.getResourceHandler().getSourceResource().getContents();
	}

	private EList<EObject> getTrgContents() {
		return sync.getResourceHandler().getTargetResource().getContents();
	}
}
