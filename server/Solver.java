package server;

import classes.Solution;
import interfaces.Searchable;

/**
 * @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 301851713 
 * This interface defines the common behavior needed for solving the problem at hand. 
 * 
 * @param <T> the type of objects that may be solved           
 */
public interface Solver<T> {
	
	/**
	 * @param problem of a searchable domain. Concrete implementations may use object adapter design pattern.  
	 * @return a solution of the given problem
	 */
	public Solution<T> solve(Searchable<T> s);
}
