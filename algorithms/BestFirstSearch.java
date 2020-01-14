package algorithms;

import java.util.PriorityQueue;

import classes.State;

public class BestFirstSearch<T> extends CommonSearcher<T> {

	protected void searchInitializer() {
		openList = new PriorityQueue<State<T>>();
	}

	protected boolean addToOpenList(State<T> initialState) {
		return openList.add(initialState);

	}

	protected State<T> popOpenList() {
		setEvaluatedNodes(getEvaluatedNodes() + 1);
		State<T> state = ((PriorityQueue<State<T>>) openList).poll();
		return state;
	}
}
