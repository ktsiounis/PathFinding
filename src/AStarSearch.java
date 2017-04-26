import java.util.*;

/**
 * Created by dtsiounis on 21/04/2017.
 */
public class AStarSearch {

    private static int N;
    private static final int EMPTY = 0;
    private static final int BLACK = 1;
    private static final int START = 2;
    private static final int FINAL1 = 3;
    private static final int FINAL2 = 4;
    private static final int PATH = 5;
    private static final double p=0.25;
    private static Node[][] maze;
    private static PriorityQueue<Node> unexplored = new PriorityQueue<>();
    private static ArrayList<Node> explored = new ArrayList<>();

    public static int estimateDistance(Node node1, Node node2){
        return Math.abs(node1.getJ() - node2.getJ()) + Math.abs(node1.getI() - node2.getI());
    }

    public static void createMaze() {
        int i,j;
        double x;

        for(i=0; i<N; i++){
            for(j=0; j<N; j++){
                x = Math.random();
                maze[i][j] = new Node(0, i, j, null);

                if(x<p){
                    maze[i][j].setType(BLACK);
                }
                else{
                    maze[i][j].setType(EMPTY);
                }
            }
        }
    }

    public static void initializeMaze(){
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(maze[i][j].getType() != BLACK) {
                    if (i == 0 && j == 0) {
                        maze[i][j].getAdjencyList().add(maze[i+1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j+1]);
                    }
                    else if(i==0 && j == N-1){
                        maze[i][j].getAdjencyList().add(maze[i+1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j-1]);
                    }
                    else if(i==N-1 && j==0){
                        maze[i][j].getAdjencyList().add(maze[i-1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j+1]);
                    }
                    else if(i==N-1 && j==N-1){
                        maze[i][j].getAdjencyList().add(maze[i-1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j-1]);
                    }
                    else if(i==0){
                        maze[i][j].getAdjencyList().add(maze[i+1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j+1]);
                        maze[i][j].getAdjencyList().add(maze[i][j-1]);
                    }
                    else if(j==0){
                        maze[i][j].getAdjencyList().add(maze[i+1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j+1]);
                        maze[i][j].getAdjencyList().add(maze[i-1][j]);
                    }
                    else if(i==N-1){
                        maze[i][j].getAdjencyList().add(maze[i-1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j+1]);
                        maze[i][j].getAdjencyList().add(maze[i][j-1]);
                    }
                    else if(j==N-1){
                        maze[i][j].getAdjencyList().add(maze[i+1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j-1]);
                        maze[i][j].getAdjencyList().add(maze[i-1][j]);
                    }
                    else {
                        maze[i][j].getAdjencyList().add(maze[i+1][j]);
                        maze[i][j].getAdjencyList().add(maze[i][j+1]);
                        maze[i][j].getAdjencyList().add(maze[i][j-1]);
                        maze[i][j].getAdjencyList().add(maze[i-1][j]);
                    }
                }
            }
        }
    }

    public static void printMaze() {
        System.out.println();
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(maze[i][j].getType() == BLACK){
                    System.out.print("#");
                }
                else if(maze[i][j].getType() == START){
                    System.out.print("S");
                }
                else if(maze[i][j].getType() == FINAL1){
                    System.out.print("G1");
                }
                else if(maze[i][j].getType() == FINAL2){
                    System.out.print("G2");
                }
                else if(maze[i][j].getType() == PATH){
                    System.out.print("*");
                }
                else{
                    System.out.print("_");
                }
            }
            System.out.println();
        }
    }

    public static void fillSolution(Node start, Node end) {
        end = end.getParent();
        while(end.getParent() != start){
            maze[end.getI()][end.getJ()].setType(PATH);
            end = end.getParent();
        }
        maze[end.getI()][end.getJ()].setType(PATH);
    }

    public static double AStarSearch(Node start, Node end){
        double cost, gCost, fScore;
        Node currentNode;

        unexplored.clear();
        explored.clear();

        start.setgCost(0);
        unexplored.add(start);
        currentNode = start;

        while (!unexplored.isEmpty()) {

            currentNode = unexplored.poll();
            explored.add(currentNode);

            if (currentNode.getI() == end.getI() && currentNode.getJ() == end.getJ()){
                fillSolution(start, currentNode);
                return currentNode.getgCost();
            }

            for(Node childNode : currentNode.getAdjencyList()){

                if(Math.abs(childNode.getI()) > Math.abs(currentNode.getI())){
                    cost = 1.0;
                }
                else {
                    cost = 0.5;
                }

                gCost = currentNode.getgCost() + cost;
                fScore = gCost + estimateDistance(childNode, end);

                if (explored.contains(childNode) && (fScore >= childNode.getgCost())) {
                    continue;
                } else if (!unexplored.contains(childNode) || (fScore < childNode.getgCost())) {
                    childNode.setParent(currentNode);
                    childNode.setgCost(gCost);

                    if (unexplored.contains(childNode)) {
                        unexplored.remove(childNode);
                    }
                    unexplored.add(childNode);
                }
            }
        }
        return currentNode.getgCost();
    }

    public static void main(String[] args) {
        int g1i = 3, g1j = 0;
        int g2i = 2, g2j = 7;
        Scanner keyboard = new Scanner(System.in);
        double totalCost1, totalCost2;


        System.out.print("N = ");
        N = keyboard.nextInt();

        maze = new Node[N][N];
        createMaze();

        maze[0][0].setType(START);
        maze[g1i][g1j].setType(FINAL1);
        maze[g2i][g2j].setType(FINAL2);

        initializeMaze();

        if(estimateDistance(maze[0][0],maze[g1i][g1j]) < estimateDistance(maze[0][0],maze[g2i][g2j])){
            totalCost1 = AStarSearch(maze[0][0], maze[g1i][g1j]);
            printMaze();
            totalCost2 = AStarSearch(maze[g1i][g1j], maze[g2i][g2j]);
        }
        else{
            totalCost1 = AStarSearch(maze[0][0], maze[g2i][g2j]);
            printMaze();
            totalCost2 = AStarSearch(maze[g2i][g2j], maze[g1i][g1j]);
        }

        printMaze();
        System.out.println("\nTotal cost of 1st path is: " + totalCost1 + ", for the 2nd is: " + totalCost2);
    }

}
