import java.util.*;

public class AStarPathFinder
{
    // Tamanho da Matriz teste
    public int M = 70;
    public int N = 100;

    // Arrays com os 4 movimentos possiveis a partir de uma celula
    private static final int row[] = { -1, 0, 0, 1 };
    private static final int col[] = { 0, -1, 1, 0 };



    // Função que verifica se é possível ir para a nova posição (row, col)
    // a partir da posição atual. Ela retorna falso se (row, col) nova
    // não for valido (ex fora do range da matriz) ou o valor é 0 (parede)
    // ou já esta na lista de visitados.
    private boolean isValid(int mat[][],  boolean visited[][], int row, int col)
    {
        return (row >= 0) && (row < M) && (col >= 0) && (col < N)
                && mat[row][col]==0 && !visited[row][col];
    }

    // Busca o menor caminho possivel na matriz com inicio em
    // celula (i, j) e destino cell (x, y)
    public void AStarPathFinder(int mat[][],int altura, int largura,int i, int j, int x, int y)
    {
        this.M = altura;
        this.N = largura;
        Node destino = new Node(i,j,0,null);
        // matriz que vai armazenar celular já visitadas
        boolean[][] visited = new boolean[M][N];

        //Cria uma lista de Nodos para reconstruir o caminho.
        LinkedList<Node> caminho = new LinkedList<Node>();

        //Cria uma lista de visitados
        LinkedList<Node> visitados = new LinkedList<Node>();

        // cria uma PILHA vazia
        Queue<Node> fila = new LinkedList<>();

        // marca a celula atual como visitada e empilha na pilha
        visited[i][j] = true;
        fila.add(new Node(i, j, 0, null));
        visitados.add(new Node(i,j, 0, null));

        // armazena o maior caminho possível desde o inicio até o destino
        int min_dist = Integer.MAX_VALUE;

        // roda até a pilha não estar vazia
        while (!fila.isEmpty())
        {
            //tira o Node do top e processa ele
            Node node = fila.poll();

            // (i, j) representa a celula atual e armazena na variavel dist
            // a menor distancia desde o começo
            i = node.x;
            j = node.y;
            int dist = node.dist;

            // se a celula destino é achada, atualiza a variavel min_dist e para.
            if (i == x && j == y)
            {
                min_dist = dist;
                destino = new Node(i,j,min_dist,node.parent);
                break;
            }

            // checa pelas 4 possibilidades de movimento a partir da celula atual
            // e empilha cada movimento valido (usa o metodo para validar)
            for (int k = 0; k < 4; k++)
            {
                // checa se é possível ir para a posição
                // (i + row[k], j + col[k]) a partir da celula atual
                if (isValid(mat, visited, i + row[k], j + col[k]))
                {
                    // marca a proxima possivel como visitada e empilha ela para ir pro loop tbm
                    visited[i + row[k]][j + col[k]] = true;
                    //TODO Antes de empilhar, validar heuristica
                    fila.add(new Node(i + row[k], j + col[k], dist + 1, node));
                    visitados.add(new Node(i + row[k], j + col[k], dist + 1, node));
                }
            }
        }

        if (min_dist != Integer.MAX_VALUE)
        {
            System.out.println("O caminho mais curto da origem até o destino " +
                    "tem o tamanho: " + min_dist);


            while(destino.parent != null) {
                System.out.println("X:"+destino.x + " Y:"+destino.y);
                destino = destino.parent;
            }
        }
        else
        {
            System.out.println("A partir do inicio especificado não é possível chegar ao destino");
        }
    }

    // Classe Node
    static class Node
    {
        int x;
        int y;
        int dist;
        Node parent;

        public void setParent(Node parent){
            this.parent = parent;
        }
        Node(int x, int y, int dist, Node parent)
        {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.parent = parent;
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
        // o destino (7, 5)

        //BuscaAStar(mat, 0, 0, 5, 5);
    }
}