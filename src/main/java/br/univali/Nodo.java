package br.univali;

public class Nodo {
	int x;
	int y;
	
	Nodo pai = null;
	
	int nivel = 0;

	public Nodo(int x, int y, Nodo pai,int nivel) {
		super();
		this.x = x;
		this.y = y;
		this.pai = pai;
		this.nivel = nivel;
	}	
}
