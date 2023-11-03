package fifteenpuzzle;

import java.util.*;
import java.util.Arrays;



public class FifteenNode implements Comparable<FifteenNode>{
    private final int [][] state;
    private int heuristic;
    public FifteenNode(){
        state = null;
    }
    public FifteenNode(int [][] mat){
        state = new int [mat.length][mat.length];
        for (int i = 0; i < mat.length;i++){
            for (int j = 0; j < mat.length; j++){
                state[i][j] = mat[i][j];
            }
        }
        heuristic = heuristicValue();
    }

    public void printNode (){
        System.out.println(Arrays.deepToString(state));
    }
    private int heuristicValue(){
        int distance = 0;
        int targetRow, targetCol;
        for (int row = 0; row < state.length; row++) {
            for (int col = 0; col < state.length; col++) {
                int value = state[row][col];
                if (value == 0) {
                    continue;
                }
                targetRow = (value - 1) / state.length;
                targetCol = (value - 1) % state.length;
                distance += Math.abs(row - targetRow) + Math.abs(col - targetCol);

                if (row == targetRow) {
                    for (int i = col + 1; i < state.length; i++) {
                        int otherValue = state[row][i];
                        if (otherValue != 0 && (otherValue - 1) / state.length == targetRow &&
                                otherValue < value) {
                            distance += 2;
                        }
                    }
                }
                if (col == targetCol) {
                    for (int i = row + 1; i < state.length; i++) {
                        int otherValue = state[i][col];
                        if (otherValue != 0 && (otherValue - 1) % state.length == targetCol &&
                                otherValue < value) {
                            distance += 2;
                        }
                    }
                }
            }
        }

        return distance;
    }
    public int getHeuristic(){
        return heuristic;
    }
    public List<FifteenNode> neighborState (){
        List<Integer> emptySlot = new ArrayList<>();
        List<FifteenNode> list = new ArrayList<>();
        for (int i = 0; i < state.length; i++){
            for (int j = 0; j < state.length;j++){
                if (state[i][j] == 0){
                    emptySlot.add(i);
                    emptySlot.add(j);
                    break;
                }
            }
        }

        if (emptySlot.get(0) > 0){
            int [][] temp = state;
            swap(temp,emptySlot.get(0),emptySlot.get(1),emptySlot.get(0) - 1, emptySlot.get(1));
            FifteenNode node = new FifteenNode(temp);
            list.add(node);
            swap(temp,emptySlot.get(0),emptySlot.get(1),emptySlot.get(0) - 1, emptySlot.get(1));
        }
        if (emptySlot.get(0) + 1 < state.length){
            int [][] temp = state;
            swap(temp,emptySlot.get(0),emptySlot.get(1), emptySlot.get(0) + 1, emptySlot.get(1));
            FifteenNode node = new FifteenNode(temp);
            list.add(node);
            swap(temp,emptySlot.get(0),emptySlot.get(1), emptySlot.get(0) + 1, emptySlot.get(1));
        }
        if (emptySlot.get(1) > 0){
            int [][] temp = state;
            swap(temp,emptySlot.get(0),emptySlot.get(1),emptySlot.get(0), emptySlot.get(1) - 1);
            FifteenNode node = new FifteenNode(temp);
            list.add(node);
            swap(temp,emptySlot.get(0),emptySlot.get(1),emptySlot.get(0), emptySlot.get(1) - 1);
        }
        if (emptySlot.get(1) + 1 < state.length){
            int [][] temp = state;
            swap(temp,emptySlot.get(0),emptySlot.get(1),emptySlot.get(0), emptySlot.get(1) + 1);
            FifteenNode node = new FifteenNode(temp);
            list.add(node);
            swap(temp,emptySlot.get(0),emptySlot.get(1),emptySlot.get(0), emptySlot.get(1) + 1);
        }
        return list;
    }

    private void swap(int [][] board, int i, int j, int rows, int cols) {
        int temp = board[i][j];
        board[i][j] = board[rows][cols];
        board[rows][cols] = temp;
        return;
    }
    public boolean isGoalState() {
        int n = state.length;
        int count = 1;
        for (int[] ints : state) {
            for (int j = 0; j < n; j++) {
                if (count == n * n && ints[j] == 0) {
                    return true;
                }
                if (ints[j] != count) {
                    return false;
                }
                count++;
            }
        }
        return false;
    }
    @Override
    public int compareTo(FifteenNode other) {
        return Integer.compare(this.heuristic, other.heuristic);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FifteenNode other)) {
            return false;
        }
        return Arrays.deepEquals(this.state, other.state);
    }
    @Override
    public int hashCode() {
        int hash = 0;
        int factor = 1;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                hash += factor * state[i][j];
                factor *= (state.length*state.length + 1);
            }
        }
        return hash;
    }
}
