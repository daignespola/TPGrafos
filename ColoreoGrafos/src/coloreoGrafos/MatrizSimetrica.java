package coloreoGrafos;

public class MatrizSimetrica {
	private boolean[] matriz;
	private int dim;
	
	public MatrizSimetrica(int dim){
		this.matriz = new boolean[(int) (Math.pow(dim, 2) - dim)/2];
		this.dim = dim;
	}
	
	public void grabarVal(int fila, int col){
		int pos = (int) (fila * dim + col - (Math.pow(fila, 2) + 3*fila +2) / 2);
		this.matriz[pos] = true;
	}
	
	public boolean leerVal(int fila, int col){
		int pos = (int) (fila * dim + col - (Math.pow(fila, 2) + 3*fila +2) / 2);
		return this.matriz[pos];
	}

	public boolean[] getMatriz() {
		return matriz;
	}


	public int getDim() {
		return dim;
	}
	
	
}
