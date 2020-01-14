package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import classes.Solution;
import classes.State;
import interfaces.Searchable;
import interfaces.Searcher;

public class HillClimbing<T> implements Searcher<T> {

	private Random random = new Random();
	private long timeToRun = 5000;

	@Override
	public Solution<T> Search(Searchable<T> s) {
		// Define the current state as an initial state
		State<T> next = s.getInitialState();
		Solution<T> result = new Solution<T>();
		long time0 = System.currentTimeMillis();
		// Loop until the goal state is achieved or no more operators can be applied on
		// the current state:
		while (System.currentTimeMillis() - time0 < timeToRun) {

			Collection<State<T>> neighbors = s.getAllPossibleStates(next);
			if (Math.random() < 0.7 && !s.IsGoalState(next)) { // with a high probability
				// find the best one
				int grade = 0;
				for (State<T> state : neighbors) {
					int g = (int) (state.getCost() * random.nextInt());
					if (g > grade) {
						grade = g;
						next = state;
						// add this step to the solution
						result.add(state);
					}
				}
			} else {
				ArrayList<State<T>> states = new ArrayList<>(neighbors);
				next = states.get(new Random().nextInt(neighbors.size()));
			}
		}
		return result;

	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return -1;
	}
}
