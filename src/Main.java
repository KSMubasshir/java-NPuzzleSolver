import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        int n,N;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter N");
        
        System.out.println("Then Enter the initial state: \n");
        
        N = scanner.nextInt();
        n=(int) Math.sqrt(N+1);
        int[][] start = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                start[i][j] = scanner.nextInt();
            }
        } 
        Node startNode = new Node(start);
        
        nPuzzleSolver solverHamming = new nPuzzleSolver(startNode,"Hamming");
        System.out.println("Using Hamming Distance");
        System.out.println("Minimum steps : " + solverHamming.getNumOfMoves());
        System.out.println("Nodes Expanded : "+solverHamming.nodesExpanded);
        System.out.println("Nodes Explored : "+solverHamming.nodesExplored);
        for (Node node : solverHamming.getSolution()) {
            System.out.println(node);
        }
        
        
        nPuzzleSolver solverManhattan = new nPuzzleSolver(startNode,"Manhattan");
        System.out.println("Using Manhattan Distance");
        System.out.println("Minimum steps : " + solverManhattan.getNumOfMoves());
        System.out.println("Nodes Expanded : "+solverManhattan.nodesExpanded);
        System.out.println("Nodes Explored : "+solverManhattan.nodesExplored);
        for (Node node : solverManhattan.getSolution()) {
            System.out.println(node);
        }
        
        nPuzzleSolver solverLC = new nPuzzleSolver(startNode,"LinearConflicts");
        System.out.println("Using Linear Conflicts");
        System.out.println("Minimum steps : " + solverLC.getNumOfMoves());
        System.out.println("Nodes Expanded : "+solverLC.nodesExpanded);
        System.out.println("Nodes Explored : "+solverLC.nodesExplored);
        for (Node node : solverLC.getSolution()) {
            System.out.println(node);
        }
    }
}
