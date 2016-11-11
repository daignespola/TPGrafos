package coloreoGrafos;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class ProbadorColoreo {
	private int cantNodos;
	private int cantAristas;
	private double porcAdy;
	private int gradoMax;
	private int gradoMin;
	private MatrizSimetrica matrizSim;
	private Nodo nodos[];
	private int cantColores;
	
	public ProbadorColoreo() {}

	public void leerGrafos(String path){
		Scanner sc = null;
		try{
			sc = new Scanner(new File(path));
			sc.useLocale(Locale.US);
			this.cantNodos = sc.nextInt();
			this.nodos=new Nodo[cantNodos];
			this.cantAristas = sc.nextInt();
			this.porcAdy = sc.nextDouble();
			this.gradoMax = sc.nextInt();
			this.gradoMin = sc.nextInt();
			this.matrizSim=new MatrizSimetrica(cantNodos);
				
			for(int i=0;i<cantAristas;i++){
				matrizSim.grabarVal(sc.nextInt(), sc.nextInt());
			}
			sc.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean leerColoreo(String path){
		Scanner sc = null;
		try{
			sc = new Scanner(new File(path));			
			sc.useLocale(Locale.US);
			int  cNodos= sc.nextInt();
			this.setCantColores(sc.nextInt());
			int aristas = sc.nextInt();
			double porcAdy = sc.nextDouble();
			int gradoMax = sc.nextInt();
			int gradoMin = sc.nextInt();
			if(this.cantNodos==cNodos && this.cantAristas==aristas && this.porcAdy==porcAdy && this.gradoMax==gradoMax &&  this.gradoMin==gradoMin){	
				//Si los datos leidos coinciden...
				iniciarNodos();
				for(int i=0;i<cantNodos;i++){
					nodos[i]=new Nodo((sc.nextInt()),sc.nextInt());
				}
			}else{
				System.out.println("No coinciden los archivos");
				sc.close();
				return false;
			}
			sc.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	public void iniciarNodos(){
		this.nodos=new Nodo[cantNodos];
		for(int i=0;i<nodos.length;i++){
			nodos[i]=new Nodo(i);
		}
	}
	
	public boolean verificarColoreo(){
		int cont=0;
		for(int i=0;i<cantNodos;i++)
			for(int j=0;j<cantNodos;j++)
				if(i!=j && matrizSim.leerVal(i, j) && nodos[j].getColor()!=nodos[i].getColor())//si son adyacentes y tienen distinto color
					cont++;
		
		if(cont==(cantAristas*2)&& contarColores() == getCantColores())//Todas los extremos de cada arista esta pintado
			return true;
		else
			return false;
	}
	
	public int contarColores(){
		int max=0;
		for(int i=0;i<nodos.length;i++){//Recorro los nodos
			int col=nodos[i].getColor();
			if(col>max) //Me fijo cual tiene el color mas alto
				max=col;
		}
		return max;
	}

	public int getCantColores() {
		return cantColores;
	}

	public void setCantColores(int cantColores) {
		this.cantColores = cantColores;
	}
	
	
}


