import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	private double getHValue(final int u, final int v) {
		final int p = maze.getGoalSquare().X;
		final int q = maze.getGoalSquare().Y;
		return Math.sqrt(((u-p)*(u-p)) + ((v-q)*(v-q)));
	}
	
	private void updatePath(Maze maze, State currentState) {
		if(currentState != null && currentState.getParent() != null) {
			maze.setOneSquare(new Square(currentState.getX(), currentState.getY()), '.');
			updatePath(maze, currentState.getParent());
		}
	}
	
	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...
		StateFValuePair frontStateFValuePair, tempStateFValuePair;
		Iterator<StateFValuePair> iterator;
		int i;

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		ArrayList<State> neighborList;

		// TODO initialize the root state and add
		// to frontier list
		// ...
		State sourceState = new State(new Square(maze.getPlayerSquare().X, maze.getPlayerSquare().Y), null, 0, 0);
		frontier.add(new StateFValuePair(sourceState, getHValue(sourceState.getX(), sourceState.getY())));

		while (!frontier.isEmpty()) {
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
			
			maxSizeOfFrontier = Math.max(maxSizeOfFrontier, frontier.size());
			frontStateFValuePair = frontier.poll();
			explored[frontStateFValuePair.getState().getX()][frontStateFValuePair.getState().getY()] = true;
			noOfNodesExpanded++;
			maxDepthSearched = Math.max(maxDepthSearched, frontStateFValuePair.getState().getDepth());
			if(frontStateFValuePair.getState().isGoal(maze)) {
				cost = frontStateFValuePair.getState().getGValue();
				updatePath(maze, frontStateFValuePair.getState().getParent());
				return true;
			}
			
			neighborList = frontStateFValuePair.getState().getSuccessors(explored, maze);
			for(i=0; i<neighborList.size(); i++) {
				iterator = frontier.iterator();
				boolean shouldInsert = true;
				while(iterator.hasNext()) {
					tempStateFValuePair = iterator.next();
					if(tempStateFValuePair.getState().getX() == neighborList.get(i).getX() && tempStateFValuePair.getState().getY() == neighborList.get(i).getY()) {
						if(tempStateFValuePair.getState().getGValue() > neighborList.get(i).getGValue()) {
							frontier.remove(tempStateFValuePair);
						} else {
							shouldInsert = false;
						}
						break;
					}
				}
				if(shouldInsert) {
					frontier.add(new StateFValuePair(neighborList.get(i), getHValue(neighborList.get(i).getX(), neighborList.get(i).getY()) + neighborList.get(i).getGValue()));
				}
			}
			
		}

		// TODO return false if no solution
		return false;
	}

}
