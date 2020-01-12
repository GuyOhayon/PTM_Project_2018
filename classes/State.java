package classes;

import java.io.Serializable;

/**
 * @author @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 301851713
 *
 * @param <T> type parameter of a general "state"
 *            
 */
public class State<T> implements Comparable<State<T>>, Serializable {

	private static final long serialVersionUID = 42L;
	private static final double INFINITY = Double.POSITIVE_INFINITY;

	private T state; // the state represented by a T
	private double cost; // cost to reach this state
	private State<T> cameFrom; // the state we came from to this state
	
	// C'tor chaining 
	public State() {  
		this(null, INFINITY, null);
	}

	public State(T state) {
		this(state, 0.0, null);
	}

	public State(T state, double cost, State<T> cameFrom) {
		this.state = state;
		this.cost = cost;
		this.cameFrom = cameFrom;
	}
	
	// Getters and Setters
	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	@SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object obj) { // we override Object's equals method
        if(this == obj) {
            return true;
        }
		if ((obj == null) || (!(obj instanceof State))) {
			return false;
		}
		
		return this.equals(((State<T>) obj));
	}
	
	public boolean equals(State<T> s) { // It's easier to simply overload
		return this.state.equals(s.state);
	}
	
	@Override
	public String toString() {
		return this.state.toString();
	}
	
	@Override
	public int hashCode() {
		return this.state.hashCode() + 57; // may cause a problem
	}

    @Override
    public int compareTo(State<T> anotherState) {
        if(this.getCost() < anotherState.getCost()) {
            return -1;
        } else if (this.getCost() > anotherState.getCost()) {
            return 1;
        } else {
            return 0;
        }
    }
}
