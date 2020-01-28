import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}
	
	private void updatePath(Maze maze, State currentState) {
		if(currentState != null && currentState.getParent() != null) {
			maze.setOneSquare(new Square(currentState.getX(), currentState.getY()), '.');
			updatePath(maze, currentState.getParent());
		}
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// ...
		State frontState;
		int i;
		
		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		ArrayList<State> neighborList;
		
		queue.add(new State(new Square(maze.getPlayerSquare().X, maze.getPlayerSquare().Y), null, 0, 0));
		explored[maze.getPlayerSquare().X][maze.getPlayerSquare().Y] = true;
		

		while (!queue.isEmpty()) {
			// TODO return true if find a solution
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found
		
			
			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue
			
			maxSizeOfFrontier = Math.max(maxSizeOfFrontier, queue.size());
			frontState = queue.pop();
			noOfNodesExpanded++;
			if(frontState.isGoal(maze)) {
				maxDepthSearched = frontState.getDepth();
				cost = frontState.getGValue();
				updatePath(maze, frontState.getParent());
				return true;
			}
			
			neighborList = frontState.getSuccessors(explored, maze);
			for(i=0; i<neighborList.size(); i++) {
				queue.add(neighborList.get(i));
				explored[neighborList.get(i).getX()][neighborList.get(i).getY()] = true;
			}
			
		}

		// TODO return false if no solution
		return false;
	}
}
