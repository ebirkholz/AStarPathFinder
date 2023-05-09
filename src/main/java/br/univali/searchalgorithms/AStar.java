package br.univali.searchalgorithms;

import br.univali.Mapa_Grid;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStar {

    public Mapa_Grid map;
    public Node destiny;
    public PriorityQueue<Node> queue;
    public LinkedList<Node> visitedNodes;
    public int startX;
    public int startY;

    public AStar(Mapa_Grid map) {
        this.map = map;
        this.cleanSearch();
    }
    
    public void execute(HeuristicEnum heuristic, int endX, int endY) {
        this.cleanSearch();

        this.queue.add(new Node(this.startX, this.startY, 0, 0, null));
    
        while (!this.queue.isEmpty()) {
            Node actual = this.queue.poll();

            synchronized (visitedNodes) {
                visitedNodes.add(actual);
            }

            if (actual.x == endX && actual.y == endY) {
                this.destiny = actual;
                return;
            }

            Node[] newNodes = new Node[4];
            newNodes[0] = new Node(actual.x + 1, actual.y, actual.dist + 1, actual.heuristica, actual);
            newNodes[1] = new Node(actual.x - 1, actual.y, actual.dist + 1, actual.heuristica, actual);
            newNodes[2] = new Node(actual.x, actual.y + 1, actual.dist + 1, actual.heuristica, actual);
            newNodes[3] = new Node(actual.x, actual.y - 1, actual.dist + 1, actual.heuristica, actual);
        
            for (int i = 0; i < 4; i++) {
                if (newNodes[i].x < 0 || newNodes[i].y < 0 ||
                        newNodes[i].x >= this.map.Largura || newNodes[i].y >= this.map.Largura) {
                    continue;
                }
                if (this.map.mapa[newNodes[i].y][newNodes[i].x] == 1) {
                    continue;
                }
                if (this.map.mapa2[newNodes[i].y][newNodes[i].x] == 1) {
                    continue;
                }


                if (newNodes[i].x == endX && newNodes[i].y == endY) {
                    this.destiny = newNodes[i];
                    System.out.println("ACHOW ");
//                    osNodos.addAll(newNodesNodos);
                    return;
                }

                this.map.mapa2[newNodes[i].y][newNodes[i].x] = 1;
                int heuristica = HeuristicEnum.calculate(heuristic, newNodes[i].x, newNodes[i].y, endX, endY);
                this.queue.add(new Node(newNodes[i].x, newNodes[i].y, newNodes[i].dist + 1, heuristica, newNodes[i].parent));
            }
        }
    }

    public int[] geraPath() {
        LinkedList<Integer> listaInts = new LinkedList<>();
        Node atual = this.destiny;
        listaInts.addFirst(atual.y);
        listaInts.addFirst(atual.x);

        while (atual.parent != null) {
            atual = atual.parent;

            listaInts.addFirst(atual.y);
            listaInts.addFirst(atual.x);
        }

        int path[] = new int[listaInts.size()];
        int index = 0;

        for (Iterator iterator = listaInts.iterator(); iterator.hasNext(); ) {
            Integer integer = (Integer) iterator.next();
            path[index] = integer;
            index++;
        }

        return path;
    }

    public void cleanSearch() {
        for(int iy = 0; iy < this.map.Altura;iy++) {
            for(int ix = 0; ix < this.map.Largura;ix++) {
                this.map.mapa2[iy][ix] = 0;
            }
        }

        this.destiny = null;
        this.visitedNodes = new LinkedList<>();
        this.queue = new PriorityQueue<>();
        this.startX = 0;
        this.startY = 0;
    }
}
