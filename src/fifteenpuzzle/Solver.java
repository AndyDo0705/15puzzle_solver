package fifteenpuzzle;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.*;


public class Solver {

	public static void main(String[] args) throws BadBoardException, IOException {
/**    System.out.println("number of arguments: " + args.length);
 //		for (int i = 0; i < args.length; i++) {
 //			System.out.println(args[i]);
 //		}
 */
/**
 if (args.length < 2) {
 System.out.println("File names are not specified");
 System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
 return;
 }
 */

		// TODO
		//File input = new File(args[0]);
		// solve...
		//File output = new File(args[1]);
		int[][] boardGame = Solver("board1.txt");
		FifteenNode node = new FifteenNode(boardGame);
		PriorityQueue<FifteenNode> queue = new PriorityQueue<>();
		queue.add(node);

		// Create a set to keep track of visited puzzle states
		HashMap<FifteenNode,FifteenNode> visited = new HashMap();
		visited.put(node,node);
		while (!queue.isEmpty()) {
			// Get the next puzzle node with the lowest cost (priority)
			FifteenNode currentNode = queue.poll();

			// If the current node is the goal state, we're done!
			if (currentNode.isGoalState()) {
				System.out.println("Puzzle solved!");
				currentNode.printNode();
				return;
			}
			// Generate the child nodes of the current node and add them to the queue
			List<FifteenNode> childNodes = currentNode.neighborState();
			for (FifteenNode childNode : childNodes) {
				// If the child node has not been visited yet, add it to the queue
				if (!visited.containsKey(childNode)) {
					queue.add(childNode);
					visited.put(childNode,childNode);
				}
			}
		}
	}

	public static int[][] Solver(String fileName) throws IOException, BadBoardException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		int size = Integer.parseInt(br.readLine());
		int[][] boardGame = new int[size][size];
		int c1, c2, s;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				c1 = br.read();
				c2 = br.read();
				s = br.read(); // skip the space
				if (s != ' ' && s != '\n') {
					br.close();
					throw new BadBoardException("error in line " + i);
				}
				if (c1 == ' ')
					c1 = '0';
				if (c2 == ' ')
					c2 = '0';
				boardGame[i][j] = 10 * (c1 - '0') + (c2 - '0');
			}
		}
/**
		int[] val = new int[size * size];

		// check that the board contains all number 0...15
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (boardGame[i][j]<0 || boardGame[i][j]>=size*size)
					throw new BadBoardException("found tile " + boardGame[i][j]);
				val[boardGame[i][j]] += 1;
			}
		}

		for (int i = 0; i < val.length; i++)
			if (val[i] != 1)
				throw new BadBoardException("tile " + i +  " appears " + val[i] + "");*/
		return boardGame;
	}

}
