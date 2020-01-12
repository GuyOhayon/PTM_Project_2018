package adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

import classes.PipeGameMap;
import classes.State;
import classes.Tile;
import classes.Tile.Direction;
import interfaces.Searchable;

public class PipeGameMapSearchable implements Searchable<PipeGameMap> {

	private PipeGameMap pipeGameMap;
	private HashSet<String> hashSet;

	public PipeGameMapSearchable(final PipeGameMap pipegamemap) {
		this.pipeGameMap = pipegamemap.clone();
		hashSet = new HashSet<>();
	}

	@Override
	public State<PipeGameMap> getInitialState() {
		this.hashSet = new HashSet<>();
		return new State<PipeGameMap>(this.pipeGameMap, 0, null);
	}

	@Override
	public Collection<State<PipeGameMap>> getAllPossibleStates(State<PipeGameMap> s) {
		PipeGameMap gameMap = s.getState();
		Collection<State<PipeGameMap>> collection = new ArrayList<State<PipeGameMap>>();
		Collection<Tile> starts = gameMap.getStarts();
		Collection<Tile> goals = gameMap.getGoals();
		double distancestart = Double.POSITIVE_INFINITY;
		double distancegoal = Double.POSITIVE_INFINITY;
		for (Tile start : starts) {
			distancestart = Math.min(distancestart, gameMap.getAllPossibleExits(start));

		}
		for (Tile goal : goals) {
			distancegoal = Math.min(distancestart, gameMap.getAllPossibleExits(goal));

		}
		if (distancestart <= distancegoal) {
			collection.addAll(getAllPossibleStatesHelper(s, starts, new Predicate<Tile>() {

				@Override
				public boolean test(Tile t) {
					return t.isStart();
				}
			}));
		} else {
			collection.addAll(getAllPossibleStatesHelper(s, goals, new Predicate<Tile>() {

				@Override
				public boolean test(Tile t) {
					return t.isGoal();
				}
			}));

		}
		return collection;
	}

	@Override
	public Boolean IsGoalState(State<PipeGameMap> s) {
		return s.getState().isSolved();
	}
	
	@Override
    public boolean equals(Object obj) { // we override Object's equals method
        if(this == obj) {
            return true;
        }
		if ((obj == null) || (!(obj instanceof PipeGameMapSearchable))) {
			return false;
		}

		return this.equals(((PipeGameMapSearchable) obj));
	}

	public boolean equals(PipeGameMapSearchable obj) {
		return this.pipeGameMap.toString().equals(obj.pipeGameMap.toString());
	}

	@Override
	public int hashCode() {
		return this.pipeGameMap.toString().hashCode();
	}

	@Override
	public String toString() {
		return this.pipeGameMap.toString();
	}

	private Collection<State<PipeGameMap>> getAllPossibleStatesHelper(State<PipeGameMap> s, Collection<Tile> Tiles,
			Predicate<Tile> predicate) {
		PipeGameMap gameMap = s.getState();
		Collection<State<PipeGameMap>> collection = new ArrayList<State<PipeGameMap>>();
		Queue<Tile> queue = new LinkedList<>();
		HashSet<Tile> closeset = new HashSet<>();
		queue.addAll(Tiles);
		for (Tile p : Tiles) {
			queue.addAll(gameMap.getAllPathsfromTile(p));
		}
		while (!queue.isEmpty()) {
			Tile n = queue.poll();// dequeue
			closeset.add(n);

			// private method, back traces through the parents
			Collection<Direction> directions = n.getDirections();
			for (Direction d : directions) {
				try {
					Tile n2 = null;
					switch (d) {
					case up: {
						n2 = (gameMap.getTile(n.getRow() - 1, n.getColumn()));
						break;
					}
					case down: {
						n2 = (gameMap.getTile(n.getRow() + 1, n.getColumn()));
						break;
					}
					case left: {
						n2 = (gameMap.getTile(n.getRow(), n.getColumn() - 1));
						break;
					}
					case right: {
						n2 = (gameMap.getTile(n.getRow(), n.getColumn() + 1));
						break;
					}
					default: {
						// tile2=null;
						break;
					}
					}
					if (n2 != null && !n2.isBlank() && !n2.isStart() && !n2.isGoal()) {
						closeset.add(n2);

					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
				Collection<Tile> successors = gameMap.getNeighbors(n);// however it is implemented
				for (Tile t : successors) {
					if (!closeset.contains(t)) {
						if (!queue.contains(t)) {
							queue.add(t);
						}

					}
				}
			}
		}
		closeset.removeAll(Tiles);
		// System.out.println(closeset.toString());

		for (Tile cstile : closeset) {
			int i = cstile.getRow(), j = cstile.getColumn();
			try {
				Tile tile = gameMap.getTile(i, j);
				if (!tile.isStart() && !tile.isGoal() && !tile.isBlank()) {
					PipeGameMap pipeGameMap = gameMap.clone();
					for (int r = 0; r < tile.numofrotates(); r++) {
						pipeGameMap.rotateTile(i, j);
						if (!pipeGameMap.isTileLinkToOutOfBounds(pipeGameMap.getTile(i, j))
								&& !pipeGameMap.isTileNeighborIsBlank(pipeGameMap.getTile(i, j))
								&& !pipeGameMap.equals(gameMap)
								&& pipeGameMap.isTilesConnected(pipeGameMap.getTile(i, j), predicate)) {
							boolean flag = true;
							for (Tile tile2 : Tiles) {
								String string = "";
								ArrayList<Tile> collection2 = pipeGameMap.getAllPathsfromTile(tile2);
								for (Tile tile3 : collection2) {
									string += tile3.getCh() + tile3.getColumn() * 100 + tile3.getRow() * 10000 + "";
								}
								if (!hashSet.contains(string)) {
									hashSet.add(string);
									flag = false;
									break;
								}
							}
							if (flag) {
								continue;
							}
							double cost = Double.POSITIVE_INFINITY;
							for (Tile t : Tiles) {
								cost = Math.min(cost, t.getDistance(tile));
							}
							State<PipeGameMap> state = new State<PipeGameMap>(pipeGameMap.clone(), cost, s);
							collection.add(state);
						}
					}
				}

			} catch (Exception e) {}
		}

		return collection;
	}

}
