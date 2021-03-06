package algorithms;

import java.util.LinkedList;

import classes.State;

public class BFS<T> extends CommonSearcher<T> {
	public BFS() {
		searchInitializer();
	}

	protected void searchInitializer() {
		openList = new LinkedList<State<T>>();
	}

	protected boolean addToOpenList(State<T> initialState) {
		return openList.add(initialState);

	}

	protected State<T> popOpenList() {
		setEvaluatedNodes(getEvaluatedNodes() + 1);
		State<T> state = ((LinkedList<State<T>>) openList).poll();
		return state;
	}
}
