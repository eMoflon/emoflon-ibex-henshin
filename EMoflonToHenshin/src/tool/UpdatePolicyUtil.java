package tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

class UpdatePolicyUtil {

	private static UpdatePolicyUtil updatePolicyTool;

	private final IUpdatePolicy updatePolicy;

	UpdatePolicyUtil() {
		this.updatePolicy = new IUpdatePolicy() {
			@Override
			public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
				List<IMatch> rankedMatches = getRankedMatches(matchContainer.getMatches());
				if (rankedMatches.isEmpty())
					return matchContainer.getNext();
				return rankedMatches.get(0);
			}
		};
	}

	private List<IMatch> getRankedMatches(Set<IMatch> matches) {
		LinkedList<IMatch> rankedMatches = new LinkedList<>();

		LinkedList<IMatch> baseMatches = new LinkedList<>();
		LinkedList<IMatch> litEnumOtherMatches = new LinkedList<>();
		LinkedList<IMatch> litEnumEqMatches = new LinkedList<>();

		for (IMatch match : matches) {
			if (contains(UpdatePolicyData.BASE_RULES, match))
				baseMatches.add(match);
			else if (contains(UpdatePolicyData.LIT_ENUM_OTHER_RULES, match))
				litEnumOtherMatches.add(match);
			else if (contains(UpdatePolicyData.LIT_ENUM_EQ_RULES, match))
				litEnumEqMatches.add(match);
			else
				rankedMatches.add(match);

			if (!rankedMatches.isEmpty())
				return rankedMatches;
		}

		rankedMatches.addAll(baseMatches);
		rankedMatches.addAll(litEnumOtherMatches);
		rankedMatches.addAll(litEnumEqMatches);

		return rankedMatches;
	}

	private boolean contains(Set<String> set, IMatch match) {
		return set.contains(match.getRuleName());
	}

	/**
	 * Returns a custom {@link IUpdatePolicy} for eMoflon to Henshin transformations
	 * 
	 * @return update policy
	 */
	public static IUpdatePolicy getUpdatePolicy() {
		if (updatePolicyTool == null)
			updatePolicyTool = new UpdatePolicyUtil();
		return updatePolicyTool.updatePolicy;
	}

}
