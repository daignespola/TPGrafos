package coloreoGrafos;

public class Arista implements Comparable<Arista>{
	private int fila;
	private int col;
	private double valOrd;
	
	public Arista(int f, int c, double v){
		this.fila = f;
		this.col = c;
		this.valOrd = v;
	}
	
	public int compareTo(Arista a){
		if(this.valOrd > a.valOrd)
			return 1;
		else
			if(this.valOrd < a.valOrd)
				return -1;
			else
				return 0;
	}

	public int getFila() {
		return fila;
	}

	public int getCol() {
		return col;
	}
	
}
