package br.univali.searchalgorithms;

public class Node implements Comparable<Node> {
    public int x;
    public int y;
    public int dist;
    public int heuristica;
    public Node parent;

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node(int x, int y, int dist, int heuristica, Node parent) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.heuristica = heuristica;
        this.parent = parent;
    }

    public int compareTo(Node other) {
        return (this.dist + this.heuristica) - (other.dist + other.heuristica);
    }
}
