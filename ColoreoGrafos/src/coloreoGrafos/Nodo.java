package coloreoGrafos;

public class Nodo {
	private int nro;
	private int color;
	private int grado;
	private int adyacentes[];
	
	
	public Nodo(){}
	
	 public Nodo(int nro)
	 {
		 this.color=0;
		 this.nro=nro;
	 }

	 public Nodo(int nro, int color)
	 {
		 this.color=color;
		 this.nro=nro;
	 }
	 
	 public void setAyacentes(int vec[]) {
			this.adyacentes=vec;
		} 
	 
	 public int[] getAyacentes() {
			return this.adyacentes;
		} 
	 
	public int getNro() {
		return nro;
	}

	public void setNro(int nro) {
		this.nro = nro;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}
	 
}
