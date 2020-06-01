package tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

class UpdatePolicyUtil {

	private static UpdatePolicyUtil updatePolicyTool;

	private final IUpdatePolicy updatePolicy;

	UpdatePolicyUtil() {
		this.updatePolicy = new IUpdatePolicy() {
			@Override
			public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
				List<ITGGMatch> rankedMatches = getRankedMatches(matchContainer.getMatches());
				if (rankedMatches.isEmpty())
					return matchContainer.getNext();
				return rankedMatches.get(0);
			}
		};
	}

	private List<ITGGMatch> getRankedMatches(Set<ITGGMatch> matches) {
		LinkedList<ITGGMatch> rankedMatches = new LinkedList<>();

		LinkedList<ITGGMatch> baseMatches = new LinkedList<>();
		LinkedList<ITGGMatch> litEnumOtherMatches = new LinkedList<>();
		LinkedList<ITGGMatch> litEnumEqMatches = new LinkedList<>();

		for (ITGGMatch match : matches) {
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

	private boolean contains(Set<String> set, ITGGMatch match) {
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
