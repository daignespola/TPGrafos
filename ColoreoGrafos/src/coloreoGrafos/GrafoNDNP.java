package coloreoGrafos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class GrafoNDNP {

	private int cantNodos;
	private int cantAristas;
	private double porcAdy;
	private int gradoMax;
	private int gradoMin;
	private MatrizSimetrica matrizSim;
	private Nodo nodos[];
	private int cantColores;
	private int numeros[];
	private List<Integer> cantG;//Cuenta cuantos nodo hay de cada grado
	
	//LECTURA Y ESCRITURA
	public GrafoNDNP(String path){
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
			numeros=new int[cantNodos];
			for(int i=0;i<cantNodos;i++)
			{
				numeros[i]=i;
			}
			cantG=new ArrayList<Integer>();
			iniciarNodos();
			sc.close();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void escribirArchivo(String dir){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(dir);
            pw = new PrintWriter(fichero);
            pw.println(cantNodos+" "+cantColores+" "+cantAristas+" "+porcAdy+" "+gradoMax+" "+gradoMin);
            for (int i = 0; i < nodos.length; i++){
            	pw.println((nodos[i].getNro())+" "+nodos[i].getColor());
            }
            fichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void iniciarNodos(){
		int cont=0;
		for(int i=0;i<nodos.length;i++){
			nodos[i]=new Nodo(i);
			for(int j=0;j<cantNodos;j++){//Cargo el grado
				if(i<j)
					if(matrizSim.leerVal(i, j))
						cont++;
				if(i>j)
					if(matrizSim.leerVal(j, i))
						cont++;
			}
			nodos[i].setGrado(cont);
			int ady[]=new int[cont];
			int pos=0;
			for(int j=0;j<cantNodos;j++){//Cargo los adyacentes
				if(i!=j){
					if(matrizSim.leerVal(i, j)){
						ady[pos]=j;
						pos++;
					}
				}
			}
			nodos[i].setAyacentes(ady);
			cont=0;
		}
	}
	
	private void reiniciarColores(){
		for(int i=0;i<cantNodos;i++)
			nodos[i].setColor(0);
	}
	
	//METODOS DE COLOREO
	private void colorear(int vec[]){
		reiniciarColores();
		SortedSet<Integer> colores=new TreeSet<Integer>();
		int color=1;
		int max=0;
		boolean band=false;
		int ady[];
		int nodo=0;
		
		for(int i=0;i<cantNodos;i++){
			nodo=vec[i]; //El numero de nodo por el que estoy
			
			if(nodos[nodo].getColor()==0) //Si no esta pintado lo pinto con el primer color
				nodos[nodo].setColor(1);
			
			ady=nodos[nodo].getAyacentes();
			
			for(int j : ady) //Mirando solo los nodos adyacentes
				colores.add(nodos[j].getColor());//Cargo los colores adyacentes (los que no voy a poder usar)

			Iterator<Integer> iterador=colores.iterator();
			
			while(iterador.hasNext() && band==false){//Mientras no se encuentre un valor valido
				color=iterador.next();
				if(color==nodos[nodo].getColor() || color==0)//Si el color coincide con alguno de los prohibidos o no este pintado
					nodos[nodo].setColor(color+1);
				else
					band=true;		
				if(color>max)
					max=color;
			}
			band=false;
			colores=new TreeSet<Integer>();//Reinicio el vector de prohibidos
		}
		cantColores=max+1;// porque el primer color es 0
	}
	//Algoritmo De Coloreo Secuencial Aleatorio
	public void secuencialAleatorio(){
		int vec[]=new int[cantNodos];
		vec=numeros;
		mezclarA(vec);
		colorear(vec);
	}

	public int[] mezclarA(int vec[]){
		int rand=(int)Math.round((Math.random()*vec.length*10)%(vec.length-1));
		int aux;
		for(int i=0;i<vec.length;i++){
			aux=vec[i];
			vec[i]=vec[rand];
			vec[rand]=aux;
			rand=(int)Math.round((Math.random()*vec.length*10)%(vec.length-1));
		}
		return vec;
	}

	//Algoritmo de Coloreo Matula
	public void matula(){ 
		int vec[]=ordenarMatula();
		mezclarO(vec);
		/*for(int i=0;i<vec.length;i++)
			System.out.print((vec[i]+1)+" ");
		System.out.println("\n");*/
		colorear(vec);
	}
	
	private int[] ordenarMatula()
	{ 
		SortedSet<Integer> grados=new TreeSet<Integer>();
		int vec[]=new int[cantNodos];
		int gra=0;
		int posAnt=0;
		for(int i=0;i<cantNodos;i++)
			grados.add(nodos[i].getGrado());//Obtengo todos los posibles grados
		
		Iterator<Integer> iterador=grados.iterator();
		int pos=0;
		
		while(iterador.hasNext()){
			gra=iterador.next();	//Voy cargando los grados de manera ascendente
			for(int i=0;i<cantNodos;i++){
				if(nodos[i].getGrado()==gra){ //Si el grado es el actual, guardo el numero de nodo en el vector
					vec[pos]=nodos[i].getNro();
					pos++;
				}
			}
			cantG.add(pos-posAnt);
			posAnt=pos;
		}
		return vec;
	}
	//Algoritmo de Coloreo WelshPowel	
	public void welshPowell(){
		int vec[]=ordenarWelshPowel();
		mezclarO(vec);
		/*for(int i=0;i<vec.length;i++)
			System.out.print((vec[i]+1)+" ");
		System.out.println("\n");*/
		colorear(vec);
	}

	public int[] ordenarWelshPowel(){	
		SortedSet<Integer> grados=new TreeSet<Integer>(Collections.reverseOrder());//Para que se orden los grados de manera decreciente
		int vec[]=new int[cantNodos];
		int gra=0;
		int posAnt=0;
		for(int i=0;i<cantNodos;i++)
			grados.add(nodos[i].getGrado());//Obtengo todos los posibles grados
		
		Iterator<Integer> iterador=grados.iterator();
		int pos=0;
		
		while(iterador.hasNext()){
			gra=iterador.next();
			for(int i=0;i<cantNodos;i++){
				if(nodos[i].getGrado()==gra){
					vec[pos]=nodos[i].getNro();
					pos++;
				}
			}
			cantG.add(pos-posAnt);
			posAnt=pos;
		}
		return vec;
	}
		
	public int[] mezclarO(int vec[]){
		Iterator<Integer> it=cantG.iterator();
		int rand;
		int aux=0;
		int cont=0;//va a llevar el inicio de cada grado
		int nro=0;
		System.out.println("vec.lenght--"+vec.length);
		while(it.hasNext()){
			nro=it.next();	//Asigno la cantidad de nodos de este grado
			System.out.println("dasdsa");
			if(nro>1){
				for(int i=cont;i<nro+cont;i++){//La cantidad de veces que haya nodos de ese grado
					System.out.println("cont--"+cont);
					rand=(int)Math.round((Math.random()*vec.length)%nro-1)+cont;//el numero debe estar entre la cantidad de ese grado y donde empieza esa cantidad
					System.out.println("rand--"+rand +"-i-"+i);
					
						System.out.println("cambio "+i+"<vec[rand]>"+vec[rand]+"<vec[i]>"+vec[i]);
						aux=vec[i];
						vec[i]=vec[rand];
						vec[rand]=aux;
				}
			}
			System.out.println("sale");
			cont+=nro;
		}
		cantG.clear();
		return vec;
	}
	
	//METODOS	
	public void mostrarNodos(){
		for(int i=0;i<nodos.length;i++){
			System.out.println((nodos[i].getNro()+1)+" "+nodos[i].getColor()+" "+nodos[i].getGrado());
		}
	}
	
	public int getCantColores(){
		return cantColores;
	}
	/*
	public static void main(String args[]){		
		GrafoNDNP col=new GrafoNDNP();//Instancio el Grafo
		int pasada=1;
		long minimo=9999999;
		
		int cantCorridas=1;
		List<Integer> cantidades=new ArrayList<Integer>();
		col.leerArchivo("C:\\Users\\Mauro\\Desktop\\TP5TMG5_Mamani\\IN\\n-partito.txt"); //LECTURA
		Calendar ini= Calendar.getInstance();
		for(int i=0;i<cantCorridas;i++){	
			
			col.matula();

			int cant=col.getCantColores();//Cuento la cantidad de colores que use
			cantidades.add(cant);	//Guardo las cantidades
			
			if(cant<minimo){//Busco el minimo
				minimo=cant; //Lo asigno
				pasada=i+1;
				//ESCRITURA
				col.escribirArchivo("C:\\Users\\Mauro\\Desktop\\TP5TMG5_Mamani\\OUT\\coloreoPartito.txt");
			}
			System.out.println(i+" "+cant);
		}
		Calendar fin= Calendar.getInstance();
		System.out.println("TIEMPO: "+(fin.getTimeInMillis()-ini.getTimeInMillis())+" ms");
		
		//CARGA DE LAS ESTADISTICAS
		Iterator<Integer> iterador=cantidades.iterator();
		Collections.sort(cantidades);//Ordeno la lista de cantidades
		
		List<Estadistica> est=new ArrayList<Estadistica>();
		int ant=0;
		int cant=0;
		int cont=0;
		int band=1;
		
		while(iterador.hasNext()){//Recorro la lista
			cant=iterador.next();
			
			if(band==1){//La primera vez
				ant=cant;
				band=0;
			}
				
			if(cant==ant)
				cont++;
			else{
				est.add(new Estadistica(ant,cont));
				cont=1;
				ant=cant;
			}	
		}
		est.add(new Estadistica(cant,cont));
		
		Iterator<Estadistica> it=est.iterator();
		
		while(it.hasNext()){
			Estadistica es=it.next();
			System.out.println(es.getCantColores()+" "+es.getCantVeces());
		}
		
		System.out.println("Minimo: "+minimo+"\nPasada: "+pasada);
	}
	*/

	public int getCantNodos() {
		return cantNodos;
	}

	public int getCantAristas() {
		return cantAristas;
	}

	public double getPorcAdy() {
		return porcAdy;
	}

	public int getGradoMax() {
		return gradoMax;
	}

	public int getGradoMin() {
		return gradoMin;
	}

	public MatrizSimetrica getMatrizSim() {
		return matrizSim;
	}

	public Nodo[] getNodos() {
		return nodos;
	}

	public int[] getNumeros() {
		return numeros;
	}

	public List<Integer> getCantG() {
		return cantG;
	}
	
}
