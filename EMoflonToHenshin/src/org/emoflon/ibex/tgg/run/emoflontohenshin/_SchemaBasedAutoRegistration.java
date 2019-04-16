package org.emoflon.ibex.tgg.run.emoflontohenshin;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

/**
 * Generated by eMoflon::IBeX.
 * 
 * Do not edit this class. It is automatically generated and is kept in sync
 * with the imports in your Schema.tgg file.
 */
public class _SchemaBasedAutoRegistration {
	
	public static void register(OperationalStrategy strategy) throws IOException {
		strategy.loadAndRegisterMetamodel("platform:/plugin/org.emoflon.ibex.tgg.core.language/model/Language.ecore");
		strategy.loadAndRegisterMetamodel("platform:/resource/org.eclipse.emf.henshin.model/model/henshin.ecore");
		strategy.loadAndRegisterMetamodel("http://www.eclipse.org/emf/2002/Ecore");
	}
	
}