package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Solution<T> implements Serializable{

	private static final long serialVersionUID = 42L;
	Collection<State<T>> states;
	
	public Solution() {
		this(new ArrayList<State<T>>());
	}

	public Solution(Collection<State<T>> states) {
		this.states = states;
	}

	public Collection<State<T>> getStates() { 
		return states;
	}
	
	public void setStates(Collection<State<T>> states) {
		this.states = states;
	}
	
	public ArrayList<State<T>> statesToArrayList() {
		ArrayList<State<T>> arrayList=new ArrayList<>(states);
		return arrayList;
	}
	
	public boolean add(State<T> state)
	{
		return this.states.add(state);
	}
}
