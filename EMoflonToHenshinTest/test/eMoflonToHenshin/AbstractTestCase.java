package eMoflonToHenshin;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import testsuite.ibex.testUtil.IbexAdapter;
import testsuite.ibex.testUtil.SyncTestCase;

public abstract class AbstractTestCase<S extends EObject, T extends EObject> extends SyncTestCase<S, T> {
	
	private static long tmpTimeMemory;

	protected AbstractTestCase(IbexAdapter<S, T> tool) {
		super(tool);
	}

	@Override
	public void initIbexOptions() {
		System.err.println("-> Executing test: " + this.getClass().getCanonicalName() + "." + name.getMethodName());
		try {
			ilpSolver = SupportedILPSolver.valueOf(System.getenv("ilpSolver"));
		} catch (Exception e) {
//			System.out.println("   - ILP solver is not specified. Defaulting to SAT4J");
			ilpSolver = SupportedILPSolver.Sat4J;
		}
	}

	@BeforeClass
	public static void init() {
		Logger.getRootLogger().setLevel(Level.ERROR);
		tmpTimeMemory = System.currentTimeMillis();
		System.err.println("Starting tests:");
	}

	@AfterClass
	public static void printTestFinished() {
		long time = (System.currentTimeMillis() - tmpTimeMemory) / 1000;
		System.err.println("Finished tests in " + time + " seconds.\n");
	}
}
