package interfaces;

import classes.Solution;

/**
 * @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 301851713 
 * This interface specifies the expected common functionality of any searcher
 * 
 * @param <T> searcher type parameter
 *            
 */
public interface Searcher<T> {
	
	// the search method
	public Solution<T> Search(Searchable<T> s);

	// get how many nodes were evaluated by the algorithm
	public int getNumberOfNodesEvaluated();
}
