package eMoflonToHenshin.util;

import org.benchmarx.emf.Comparator;

import language.TGG;

public class TGGComparator implements Comparator<TGG> {

	@Override
	public void assertEquals(TGG expected, TGG actual) {
		Util.compareModels(expected, actual);
	}

}
