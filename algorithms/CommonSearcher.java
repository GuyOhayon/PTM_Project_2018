package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import classes.Solution;
import classes.State;
import interfaces.Searchable;
import interfaces.Searcher;

public abstract class CommonSearcher<T> implements Searcher<T> {

	protected Collection<State<T>> openList;
	private int evaluatedNodes;

	public CommonSearcher() {
		searchInitializer();
	}

	protected abstract boolean addToOpenList(State<T> initialState);

	protected abstract State<T> popOpenList();

	protected abstract void searchInitializer();

	public int getEvaluatedNodes() {
		return evaluatedNodes;
	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return getEvaluatedNodes();
	}

	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}

	protected Solution<T> backTrace(State<T> startState, State<T> goalState) throws Exception {
		ArrayList<State<T>> arrayList = new ArrayList<>();
		arrayList.add(goalState);
		while (arrayList.get(0) != null && !arrayList.get(0).equals(startState)
				&& arrayList.get(0).getCameFrom() != null) {
			arrayList.add(0, arrayList.get(0).getCameFrom());
		}
		if (arrayList.isEmpty()) {
			throw new Exception("backTrace is empty");
		}
		return new Solution<T>(arrayList);
	}

	@Override
	public Solution<T> Search(Searchable<T> s) {
		searchInitializer();
		addToOpenList(s.getInitialState());
		Set<State<T>> closedSet = new HashSet<State<T>>();
		setEvaluatedNodes(0);
		while (!openList.isEmpty()) {
			State<T> currentState = popOpenList();// dequeue
			closedSet.add(currentState);
			if (s.IsGoalState(currentState)) {
				try {
					// private method, back traces through the parents
					return backTrace(s.getInitialState(), currentState);
				} catch (Exception e) {
					/// e.printStackTrace();
				}
			}

			Collection<State<T>> successors = s.getAllPossibleStates(currentState); // however it is implemented
			for (State<T> state : successors) {
				if (!closedSet.contains(state)) {
					if (!openList.contains(state) || openList.removeIf(new Predicate<State<T>>() {
						@Override
						public boolean test(State<T> t) {
							return t.equals(state) && t.getCost() > state.getCost();
						}
					})) {
						addToOpenList(state);
					}

				}
			}
		}
		return null;
	}
}
