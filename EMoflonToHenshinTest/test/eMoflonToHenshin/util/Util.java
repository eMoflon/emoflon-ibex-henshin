package eMoflonToHenshin.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Assert;

public class Util {

	@SuppressWarnings("unchecked")
	public static <M> M loadEMoflonTGGFromResource(String filePath) {
		Path relativePath = FileSystems.getDefault().getPath("resources", filePath + ".tgg.xmi");
		Path absolutePath = relativePath.normalize();

		Resource res = new XMIResourceImpl(URI.createFileURI(absolutePath.toString()));
		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (M) res.getContents().get(0);
	}

	@SuppressWarnings("unchecked")
	public static <M> M loadHenshinModuleFromResource(String filePath, boolean useHenshinRes) {
		if (useHenshinRes) {
			HenshinResourceSet resSet = new HenshinResourceSet("foo");
			Resource res = resSet.getResource(filePath + ".henshin");
			EcoreUtil.resolveAll(res);
			return (M) res.getContents().get(0);
		} else {
			Path relativePath = FileSystems.getDefault().getPath("resources", filePath + ".henshin");
			Path absolutePath = relativePath.normalize();

			XMIResource res = new XMIResourceImpl(URI.createFileURI(absolutePath.toString()));
			try {
				res.load(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			EcoreUtil.resolveAll(res);
			
			for(TreeIterator<EObject> it = res.getAllContents(); it.hasNext();) {
				res.setID(it.next(), null);
			}
			
			return (M) res.getContents().get(0);
		}
	}

	public static void compareModels(Notifier expected, Notifier actual) {
		Comparison c = EMFCompare.builder().build().compare(new DefaultComparisonScope(expected, actual, null));
		EList<Diff> diffs = c.getDifferences();

		if (diffs.size() > 0) {
			System.out.println("Resulted differences from comparison of '" + expected.toString() + "' to '"
					+ actual.toString() + "':");
			diffs.forEach(diff -> {
				System.out.println(diff.toString());
			});
		}

		Assert.assertEquals(0, diffs.size());
	}
}
