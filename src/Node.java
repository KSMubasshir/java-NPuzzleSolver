import java.util.HashSet;
import java.util.Set;

public class Node {

    public int[][] node;
    private int blankX;
    private int blankY;
    private int hammingDistance, manhattanDistance;
    public int n;

    public Node(int[][] node) {
        this.node = node;
        this.n = node.length;
        this.hammingDistance = 0;
        this.manhattanDistance = 0;

        for (int i = 0; i < node.length; i++) {
            for (int j = 0; j < node[i].length; j++) {
                if (node[i][j] != (i * n + j + 1) && node[i][j] != 0) {
                    hammingDistance += 1;
                    manhattanDistance += distanceFromOriginalPos(node[i][j], i, j);
                }
                if (node[i][j] == 0) {
                    blankX = (int) i;
                    blankY = (int) j;
                }
            }
        }
    }

    public int getHammingDistance() {
        return hammingDistance;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }
    
    public int getDistanceWithLinearConflicts(){
        return manhattanDistance+2*(linearHorizontalConflict()+linearVerticalConflict());
    }

    public int distanceFromOriginalPos(int num, int r, int c) {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (node[i][j] == num) {
                    return Math.abs(i - r) + Math.abs(j - c);
                }
            }
        }
        return 0;
    }

    public boolean isGoalNode() {  // if no disorder then puzzle is solved
        return hammingDistance == 0;
    }

    @Override
    public boolean equals(Object o) {
        Node board = (Node) o;
        for (int i = 0; i < node.length; i++) {
            for (int j = 0; j < node[i].length; j++) {
                if (node[i][j] != board.node[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Node> getNeighbours() {
        Set<Node> neighbours = new HashSet<>();
        Node left, right, up, down;
        up = moveUp(this);
        if (up != null) {
            neighbours.add(up);
        }
        down = moveDown(this);
        if (down != null) {
            neighbours.add(down);
        }
        left = moveLeft(this);
        if (left != null) {
            neighbours.add(left);
        }
        right = moveRight(this);
        if (right != null) {
            neighbours.add(right);
        }
        return neighbours;
    }

    public Node moveUp(Node parent) {
        if (blankX == 0) {
            return null;
        }
        int[][] array1 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array1[i][j] = parent.node[i][j];
            }
        }
        int temp = array1[blankX - 1][blankY];
        array1[blankX - 1][blankY] = 0;
        array1[blankX][blankY] = temp;
        return new Node(array1);
    }

    public Node moveDown(Node parent) {
        if (blankX == (n - 1)) {
            return null;
        }
        int[][] array1 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array1[i][j] = parent.node[i][j];
            }
        }
        int temp = array1[blankX + 1][blankY];
        array1[blankX + 1][blankY] = 0;
        array1[blankX][blankY] = temp;
        return new Node(array1);
    }

    public Node moveLeft(Node parent) {
        if (blankY == 0) {
            return null;
        }
        int[][] array1 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array1[i][j] = parent.node[i][j];
            }
        }
        int temp = array1[blankX][blankY - 1];
        array1[blankX][blankY - 1] = 0;
        array1[blankX][blankY] = temp;
        return new Node(array1);
    }

    public Node moveRight(Node parent) {
        if (blankY == (n - 1)) {
            return null;
        }
        int[][] array1 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array1[i][j] = parent.node[i][j];
            }
        }
        int temp = array1[blankX][blankY + 1];
        array1[blankX][blankY + 1] = 0;
        array1[blankX][blankY] = temp;
        return new Node(array1);
    }

    private int linearVerticalConflict() {
        int state[][] = node;
        int dimension = n;
        int linearConflict = 0;
        for (int row = 0; row < dimension; row++) {
            int max = -1;
            for (int column = 0; column < dimension; column++) {
                int cellValue = state[row][column];
                if (cellValue != 0 && (cellValue - 1) / dimension == row) {
                    if (cellValue > max) {
                        max = cellValue;
                    } else {
                        linearConflict += 1;
                    }
                }
            }
        }
        return linearConflict;
    }

    private int linearHorizontalConflict() {
        int[][] state = node;
        int dimension = n;
        int linearConflict = 0;
        for (int column = 0; column < dimension; column++) {
            int max = -1;
            for (int row = 0; row < dimension; row++) {
                int cellValue = state[row][column];
                if (cellValue != 0 && cellValue % dimension == column + 1) {
                    if (cellValue > max) {
                        max = cellValue;
                    } else {
                        linearConflict += 1;
                    }
                }
            }
        }
        return linearConflict;
    }

    @Override
    public String toString() {
        String s = "";
        for (int[] node1 : node) {
            for (int j = 0; j < node.length; j++) {
                s += node1[j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}
