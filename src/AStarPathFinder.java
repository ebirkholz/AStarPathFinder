import java.util.*;

public class AStarPathFinder
{
    // Tamanho da Matriz teste
    public int M = 70; //70
    public int N = 100; //100

    public Node nodeDestiny = null;
    public LinkedList<Node> nodeVisitados = new LinkedList<>();


    // Função que verifica se é possível ir para a nova posição (row, col)
    // a partir da posição atual. Ela retorna falso se (row, col) nova
    // não for valido (ex fora do range da matriz) ou o valor é 0 (parede)
    // ou já esta na lista de visitados.
    public boolean isValid(int mat[][],  boolean visited[][], int x, int y)
    {
        return (x >= 0) && (x < M) && (y >= 0) && (y < N)
                && mat[x][y]==0 && !visited[x][y];
    }

    // Busca o menor caminho possivel na matriz com inicio em
    // celula (i, j) e destino cell (x, y)
    public void AStarPathFinder(int mat[][],int altura, int largura,int inicioX, int inicioY, int fimX, int fimY)
    {
        this.M = altura;
        this.N = largura;
        // matriz que vai armazenar celular já visitadas
        boolean[][] visitados = new boolean[M][N];

        // cria uma PILHA vazia
        PriorityQueue<Node> fila = new PriorityQueue<Node>();

        // marca a celula atual como visitada e empilha na pilha

        fila.add(new Node(inicioX, inicioY, 0, 0,null));

        // roda até a pilha não estar vazia
        while (!fila.isEmpty())
        {
            System.out.println("entrou");
            //tira o Node do top e processa ele
            Node atual = fila.poll();
            visitados[inicioX][inicioY] = true;

            synchronized (nodeVisitados) {
                nodeVisitados.add(atual);
            }

            // se a celula destino é achada, atualiza a variavel min_dist e para.
            if (atual.x == fimX && atual.y == fimY)
            {
                System.out.println("achou");
                this.nodeDestiny = atual;
                return;
            }

            // checa pelas 4 possibilidades de movimento a partir da celula atual
            // e empilha cada movimento valido (usa o metodo para validar)
            for (int i = -1; i <= 1; i++)
            {
                for (int j = -1; j <= 1 ; j++) {
                    //Pula o nodo atual
                    if(i==0 && j==0){
                        continue;
                    }
                    int x = atual.x + i;
                    int y = atual.y + j;

                    if (isValid(mat, visitados, x, y))
                    {
                        // Calcula heuristica
                        int heuristica = Math.abs(x - fimX) + Math.abs(y - fimY);
                        // Create new node and add it to the queue
                        fila.add(new Node(x, y, atual.dist + 1, heuristica, atual));
                        System.out.println("adiciounou --> X:"+x+" Y:"+y);
                    }
                }
            }
        }

        System.out.println("A partir do inicio especificado não é possível chegar ao destino");
    }

    public int [] geraPath() {
        LinkedList<Integer> listaInts = new LinkedList<>();
        Node atual = this.nodeDestiny;
        listaInts.addFirst(atual.y);
        listaInts.addFirst(atual.x);

        while (atual.parent != null) {
            atual = atual.parent;

            listaInts.addFirst(atual.y);
            listaInts.addFirst(atual.x);
        }

        int path[] = new int[listaInts.size()];
        int index= 0;

        for (Iterator iterator = listaInts.iterator(); iterator.hasNext();) {
            Integer integer = (Integer) iterator.next();
            path[index] = integer;
            index++;
        }

        return path;
    }

    //Trace back the path from the given node
    private void printaCaminho(Node node) {
        if (node.parent != null)
            printaCaminho(node.parent);
        System.out.println("(X: " + node.x + ", Y:" + node.y + ")");
    }


    // Classe Node
    public class Node implements Comparable<Node> {
        int x;
        int y;
        int dist;
        int heuristica;
        Node parent;

        public void setParent(Node parent){
            this.parent = parent;
        }
        Node(int x, int y, int dist, int heuristica, Node parent)
        {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.heuristica = heuristica;
            this.parent = parent;
        }

        public int compareTo(Node other){
            return (this.dist + this.heuristica) - (other.dist + other.heuristica);
        }
    };

    // Driver code
    public static void main(String[] args)
    {
        int mat[][] =
                {
                        { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
                        { 1, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
                        { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
                        { 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 },
                        { 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 },
                        { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                        { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
                        { 1, 1, 0, 0, 0, 0, 1, 0, 0, 1 },
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
                };

        // Busca o menor caminho saindo de (0, 0) ate
        // o destino (5, 5)
        AStarPathFinder asfind = new AStarPathFinder();
        asfind.AStarPathFinder(mat, 10, 10, 0, 0, 5, 5);
    }
}
