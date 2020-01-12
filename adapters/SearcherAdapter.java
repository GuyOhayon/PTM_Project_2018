package adapters;

import classes.Solution;
import interfaces.Searchable;
import interfaces.Searcher;
import server.Solver;

public class SearcherAdapter<T> implements Solver<T> {

	private Searcher<T> searcher;

	public SearcherAdapter(Searcher<T> searcher) {
		this.searcher = searcher;
	}

	@Override
	public Solution<T> solve(Searchable<T> s) {
		return searcher.Search(s);
	}
}
