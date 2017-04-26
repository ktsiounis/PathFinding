import java.util.ArrayList;

/**
 * Created by dtsiounis on 21/04/2017.
 */
public class Node implements  Comparable<Node>{
    private int i, j, type;
    private double gCost;
    private Node parent;
    private ArrayList<Node> adjencyList;

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node(double gCost, int i, int j, Node parent) {
        this.gCost = gCost;
        this.i = i;
        this.j = j;
        this.parent = parent;
        this.adjencyList = new ArrayList<>();

    }

    public double getgCost() {
        return gCost;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getAdjencyList() {
        return adjencyList;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setgCost(double gCost) {
        this.gCost = gCost;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.gCost, other.getgCost());
    }
}
