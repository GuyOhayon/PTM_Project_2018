package interfaces;

import java.util.Collection;

import classes.State;

public interface Searchable<T> {

	public State<T> getInitialState();
	public Collection<State<T>> getAllPossibleStates(State<T> s);
	public Boolean IsGoalState(State<T> s);
}
