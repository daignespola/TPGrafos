package coloreoGrafos;

import java.io.*;
import java.util.*;

public class Generador {

	public static void genRanProb(int N, double prob, String path){
		int cA = 0;
		int grMax = 0;
		int grMin = N-1;
		int vecAuxGrado[] = new int[N];
		int gradoParcial = 0;
		int gradoTot = 0;
		MatrizSimetrica mat = new MatrizSimetrica(N);
		for(int i = 0; i < N; i++){
			gradoParcial = 0;
			for (int j = i+1; j < N; j++) {
				if (Math.random() <= prob){
					cA++;
					mat.grabarVal(i, j);
					vecAuxGrado[j]++;
					gradoParcial++;
				}
			}
			gradoTot = gradoParcial + vecAuxGrado[i];
			if (gradoTot > grMax)
				grMax = gradoTot;
			if (gradoTot < grMin)
				grMin = gradoTot;
		}
		double porcAdy = ((double)cA/((double)N*((double)N-1)/2));
		grabarArchIn(N,cA,porcAdy,grMax,grMin,mat,path);
	}
	
	public static void genRanPorcAdy(int N, double porcAdy, String path){
		int cA = 0;
		int grMax = 0;
		int grMin = N-1;
		int vecAuxGrado[] = new int[N];
		LinkedList<Arista> listArista = new LinkedList<Arista>();
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				listArista.add(new Arista(i,j,Math.random()));
			}
		}
		Collections.sort(listArista);
		int cant = (int)(porcAdy * ((double)N*((double)N-1)/2));
		MatrizSimetrica mat = new MatrizSimetrica(N);
		for (int i = 0; i < cant; i++) {
			Arista aris = listArista.pollFirst();
			mat.grabarVal(aris.getFila(), aris.getCol());
			if(aris.getFila() != aris.getCol()){
				vecAuxGrado[aris.getFila()]++;
				vecAuxGrado[aris.getCol()]++;
			}else{
				vecAuxGrado[aris.getFila()]++;
			}
			cA++;
		}
		for (int i = 0; i < N; i++){
			if(vecAuxGrado[i] < grMin)
				grMin = vecAuxGrado[i];
			if(vecAuxGrado[i] > grMax)
				grMax = vecAuxGrado[i];
		}
		grabarArchIn(N,cA,porcAdy,grMax,grMin,mat,path);
	}
	
	public static void genRegGrado(int N, int grado, String path){
		int cA = 0;
		MatrizSimetrica mat = new MatrizSimetrica(N);
		int salto = (N-1-grado)/2+1;
		if (grado != 0 && grado != 1 && grado != 2) {
			mat.grabarVal(0, N-1);
			cA++;
			for (int i = 0; i < N-1; i++) {
				int j = i+1;
				int contGrado = 1;
				mat.grabarVal(i, j);
				cA++;
				j+=salto;
				while(j<N && contGrado < grado/2){
					mat.grabarVal(i, j);
					cA++;
					j++;
					contGrado++;
				}
				if( N%2 == 0 && grado%2 == 0)
					j++;
				while(j<N && contGrado < grado-1){
					mat.grabarVal(i, j);
					cA++;
					j++;
					contGrado++;
				}
				
			}
		}
		else{
			if(grado == 1)
				for (int i = 0; i < N-1; i+=2) {
					mat.grabarVal(i, i+1);
					cA++;
				}
			else{
				if(grado == 2){
					mat.grabarVal(0, N-1);
					cA++;
					for (int i = 0; i < N-1; i++) {
						mat.grabarVal(i, i+1);
						cA++;
					}
				}
			}
			
		}
		double porcAdy = ((double)cA/((double)N*((double)N-1)/2));
		grabarArchIn(N,cA,porcAdy,grado,grado,mat,path);
	}
	
	public static void grabarArchIn(int N, int cA, double porcAdy, int grMax, int grMin, MatrizSimetrica mat, String path){
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(new File(path));
			pw = new PrintWriter(fw);
			pw.println(N + " " + cA + " " + porcAdy + " " + grMax + " " + grMin);
			for (int i = 0; i < N-1; i++) {
				for (int j = i+1; j < N; j++) {
					if(mat.leerVal(i, j))
						pw.println(i + " " + j);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			try {
				fw.close();
				pw.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}