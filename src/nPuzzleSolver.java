import static java.lang.System.exit;
import java.util.*;

public class nPuzzleSolver {

    private Node startNode;
    private List<Node> solution = new ArrayList<>();
    public int nodesExpanded = 0, nodesExplored = 1;
    public String choice;

    private class workItem {

        private workItem prevNode;
        private Node node;

        private workItem(workItem prevNode, Node node) {
            this.prevNode = prevNode;
            this.node = node;
        }

        public Node getNode() {
            return node;
        }
    }

    public nPuzzleSolver(Node startNode, String choice) {
        this.startNode = startNode;
        this.choice = choice;

        if (!isSolvable()) {
            System.out.println("Unsolvable");
            exit(0);
        }

        PriorityQueue<workItem> priorityQueue = new PriorityQueue<workItem>(10, new Comparator<workItem>() {
            @Override
            public int compare(workItem o1, workItem o2) {
                return new Integer(cost(o1)).compareTo(new Integer(cost(o2)));
            }
        });

        priorityQueue.add(new workItem(null, startNode));

        while (true) {
            workItem wi = priorityQueue.poll();
            nodesExpanded++;
            if (wi.node.isGoalNode()) {
                makeSolution(new workItem(wi, wi.node));
                return;
            }

            Iterator itr = wi.node.getNeighbours().iterator();
            while (itr.hasNext()) {
                Node node1 = (Node) itr.next();
                if (node1 != null && !inPath(wi, node1)) {
                    priorityQueue.add(new workItem(wi, node1));
                }
                nodesExplored++;
            }
        }
    }

    public int cost(workItem item) {
        workItem item2 = item;
        int costg = 0;
        int costhamming = item.getNode().getHammingDistance();
        int costManhattan = item.getNode().getManhattanDistance();
        int costWithLinearConflicts = item.getNode().getDistanceWithLinearConflicts();

        while (true) {
            costg++;
            item2 = item2.prevNode;
            if (item2 == null) {
                if (choice.equalsIgnoreCase("Hamming")) {
                    return costhamming + costg;
                } else if (choice.equalsIgnoreCase("Manhattan")) {
                    return costManhattan + costg;
                } else if (choice.equalsIgnoreCase("LinearConflicts")) {
                    return costWithLinearConflicts + costg;
                }
            }
        }
    }

    private void makeSolution(workItem item) {
        workItem item2 = item;
        while (true) {
            item2 = item2.prevNode;
            if (item2 == null) {
                Collections.reverse(solution);
                return;
            }
            solution.add(item2.node);
        }
    }

    private boolean inPath(workItem item, Node node) {
        workItem item2 = item;
        while (true) {
            if (item2.node.equals(node)) {
                return true;
            }
            item2 = item2.prevNode;
            if (item2 == null) {
                return false;
            }
        }
    }

    public boolean isSolvable() {
        int parity = 0;
        int gridWidth = startNode.n;
        int blankRow = 0;
        int l = 0, temp = 0;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if (startNode.node[i][j] == 0) {
                    blankRow = i;
                    continue;
                }
                for (int k = i; k < gridWidth; k++) {
                    if (k == i) {
                        temp = j + 1;
                    } else {
                        temp = 0;
                    }
                    for (l=temp; l < gridWidth; l++) {
                        if (startNode.node[i][j] > startNode.node[k][l] && startNode.node[k][l] != 0) {
                            parity++;
                        }
                    }
                }
            }
        }
        if (gridWidth % 2 == 0) {
            return (blankRow+parity) % 2 != 0;
        } else {
            return parity % 2 == 0;
        }
    }

    public int getNumOfMoves() {
        if (!isSolvable()) {
            return -1;
        }
        return solution.size() - 1;
    }

    public Iterable<Node> getSolution() {
        return solution;
    }

}
