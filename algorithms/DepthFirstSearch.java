package algorithms;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import classes.Solution;
import classes.State;
import interfaces.Searchable;

public class DepthFirstSearch<T> extends CommonSearcher<T>{

	protected void searchInitializer()
	{
		openList=new Stack<State<T>>();
	}
	protected boolean addToOpenList(State<T> initialState) {

		((Stack<State<T>>) openList).push(initialState);
		return true;
		
	}
	protected State<T> popOpenList() {
		setEvaluatedNodes(getEvaluatedNodes() + 1);
		State<T> state=((Stack<State<T>>) openList).pop();
		return state;
	}

	@Override
	public Solution<T> Search(Searchable<T> s) {
		searchInitializer();
		addToOpenList(s.getInitialState());
		setEvaluatedNodes(0);
		Set<State<T>> closedSet=new HashSet<State<T>>();
		while(!openList.isEmpty()){
			State<T> currentState=popOpenList();// dequeue
			closedSet.add(currentState);
			if(s.IsGoalState(currentState))
			{
				try {
					return backTrace(s.getInitialState(),currentState);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// private method, back traces through the parents
			Collection<State<T>> successors=s.getAllPossibleStates(currentState);//however it is implemented
			for(State<T> state: successors){
				if(!closedSet.contains(state)){
					if(!openList.contains(state)){
						addToOpenList(state);
					}
				}
				
			}	
		}
		return null;
	}
}
