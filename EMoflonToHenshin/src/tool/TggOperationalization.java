package tool;

import language.BindingType;
import language.DomainType;
import language.TGG;

/**
 * Use this class to operationalize a TGG model.
 */
public class TggOperationalization {

	public enum OperationalizationOpt {
		GENERATE, FORWARD, BACKWARD, CONSISTENCY;
	}

	/**
	 * Transforms a TGG model into an operationalized model defined by the
	 * <code>option</code> parameter.
	 * 
	 * @param tgg
	 *            - TGG model to be operationalized
	 * @param option
	 *            - defines with operationalization
	 */
	public static void operationalizate(TGG tgg, OperationalizationOpt option) {
		if (option == null || option.equals(OperationalizationOpt.GENERATE))
			return;

		else if (option.equals(OperationalizationOpt.CONSISTENCY)) {
			tgg.getRules().forEach(rule -> {
				rule.getNodes().forEach(node -> {
					if (node.getBindingType().equals(BindingType.CREATE)) {
						node.setBindingType(BindingType.CONTEXT);
					}
				});
				rule.getEdges().forEach(edge -> {
					if (edge.getBindingType().equals(BindingType.CREATE)) {
						edge.setBindingType(BindingType.CONTEXT);
					}
				});
			});

		} else {
			DomainType domainCondition;
			if (option.equals(OperationalizationOpt.FORWARD))
				domainCondition = DomainType.SRC;
			else if (option.equals(OperationalizationOpt.BACKWARD)) {
				domainCondition = DomainType.TRG;
			} else {
				throw new UnsupportedOperationException("This Option is not jet supported: " + option.toString());
			}
			tgg.getRules().forEach(rule -> {
				rule.getNodes().forEach(node -> {
					if (node.getDomainType().equals(domainCondition)
							&& node.getBindingType().equals(BindingType.CREATE)) {
						node.setBindingType(BindingType.CONTEXT);
					}
				});
				rule.getEdges().forEach(edge -> {
					if (edge.getDomainType().equals(domainCondition)
							&& edge.getBindingType().equals(BindingType.CREATE)) {
						edge.setBindingType(BindingType.CONTEXT);
					}
				});
			});
		}
	}

}
