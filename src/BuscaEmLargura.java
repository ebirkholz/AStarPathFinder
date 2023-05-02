import java.util.Iterator;
import java.util.LinkedList;

public class BuscaEmLargura {
	int sX; //Srtart X
	int sY; //Srtart Y
	int eX; //End X
	int eY; //End Y
	
	Mapa m = null;
	
	Nodo nodoObjetivo = null;
	
	LinkedList<Nodo> osNodos;
	LinkedList<Nodo> osVisitados;
	LinkedList<Nodo> novosNodos;
	
	public BuscaEmLargura() {
		// TODO Auto-generated constructor stub
		osNodos = new LinkedList<>();
		osVisitados = new LinkedList<>();
		novosNodos = new LinkedList<>();
	}
	
	public void setaBusca(Mapa m,int sX,int sY,int eX,int eY) {
		this.m = m;
		this.sX = sX;
		this.sY = sY;
		this.eX = eX;
		this.eY = eY;
	}
	
	public void executaBusca() {
		for(int iy = 0; iy < m.Altura;iy++) {
			for(int ix = 0; ix < m.Largura;ix++) {
				m.mapa2[iy][ix] = 0;
			}
		}
		
		
		Nodo inicial = new Nodo(sX, sY, null,0);
		m.mapa2[sX][sY] = 1;
		
		nodoObjetivo = null;
		
		int nivelAtual = 0;
		osNodos.add(inicial);
		
		boolean nachou = true;
		
		do {
			
			synchronized (osNodos) {
				for (Iterator iterator = osNodos.iterator(); iterator.hasNext();) {
					Nodo nodo = (Nodo) iterator.next();
					
					if(nodo.nivel==nivelAtual) {
						synchronized (osVisitados) {
							osVisitados.add(nodo);
						}
						//expandir
						Nodo novos[] = new Nodo[4];
						novos[0] = new Nodo(nodo.x+1, nodo.y, nodo, nodo.nivel+1);
						novos[1] = new Nodo(nodo.x-1, nodo.y, nodo, nodo.nivel+1);
						novos[2] = new Nodo(nodo.x, nodo.y+1, nodo, nodo.nivel+1);
						novos[3] = new Nodo(nodo.x, nodo.y-1, nodo, nodo.nivel+1);
						
						for(int i = 0; i < 4; i++) {
							if(novos[i].x<0||novos[i].y<0||novos[i].x>=m.Largura||novos[i].y>=m.Largura) {
								continue;
							}	
							if(m.mapa[novos[i].y][novos[i].x]==1) {
								continue;
							}
							if(m.mapa2[novos[i].y][novos[i].x]==1) {
								continue;
							}
							
							
							if(novos[i].x==eX && novos[i].y==eY) {
								nodoObjetivo = novos[i];
								System.out.println("ACHOW ");
								osNodos.addAll(novosNodos);
								return;
							}
	
							m.mapa2[novos[i].y][novos[i].x] = 1;
							novosNodos.addLast(novos[i]);
						}
						iterator.remove();
					}
				}
				
				osNodos.addAll(novosNodos);
				novosNodos.clear();
			}
			
			nivelAtual++;
			System.out.println(""+nivelAtual+" "+osNodos.size());
		}while(nachou);	
	}
	
	public int[] geraPath() {
		LinkedList<Integer> listaInts = new LinkedList<>();
		Nodo atual = nodoObjetivo;
		listaInts.addFirst(atual.y);
		listaInts.addFirst(atual.x);
		
		while(atual.pai!=null) {
			atual = atual.pai;
			
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
}
