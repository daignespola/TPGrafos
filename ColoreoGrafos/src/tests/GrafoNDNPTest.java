package tests;

import org.junit.Assert;
import org.junit.Test;

import coloreoGrafos.Generador;
import coloreoGrafos.GrafoNDNP;
import coloreoGrafos.ProbadorColoreo;

public class GrafoNDNPTest {
	@Test
	public void coloreoCorrectoPorSecuencialAleatorio(){
		String dirGrafo="C:\\Users\\Daiana\\Documents\\UnLaM\\Programación Avanzada\\tp4-master\\ColoreoGrafos\\generado5x05.in";
		String dirColoreo="C:\\Users\\Daiana\\Documents\\UnLaM\\Programación Avanzada\\tp4-master\\ColoreoGrafos\\generado5x05.out";
		
		GrafoNDNP grafo = new GrafoNDNP(dirGrafo);
		grafo.secuencialAleatorio();
		grafo.escribirArchivo(dirColoreo);
		
		ProbadorColoreo probador=new ProbadorColoreo();
		probador.leerGrafos(dirGrafo);
		probador.leerColoreo(dirColoreo);
		Assert.assertTrue(probador.verificarColoreo()/* && probador.contarColores()==probador.getCantColores()*/);
	}
	@Test
	public void coloreoCorrectoPorMatula(){
		String dirGrafo="C:\\Users\\Daiana\\Documents\\UnLaM\\Programación Avanzada\\tp4-master\\ColoreoGrafos\\generado5x05.in";
		String dirColoreo="C:\\Users\\Daiana\\Documents\\UnLaM\\Programación Avanzada\\tp4-master\\ColoreoGrafos\\generado5x05.out";
		
		GrafoNDNP grafo = new GrafoNDNP(dirGrafo);
		grafo.matula();
		grafo.escribirArchivo(dirColoreo);
		
		ProbadorColoreo probador=new ProbadorColoreo();
		probador.leerGrafos(dirGrafo);
		probador.leerColoreo(dirColoreo);
		Assert.assertTrue(probador.verificarColoreo()/* && probador.contarColores()==probador.getCantColores()*/);
	}
	/*
	@Test
	public void coloreoCorrectoPorWelshPowell(){
		String dirGrafo="C:\\Users\\Daiana\\Documents\\UnLaM\\Programación Avanzada\\tp4-master\\ColoreoGrafos\\generado5x05.in";
		String dirColoreo="C:\\Users\\Daiana\\Documents\\UnLaM\\Programación Avanzada\\tp4-master\\ColoreoGrafos\\generado5x05.out";
		
		GrafoNDNP grafo = new GrafoNDNP(dirGrafo);
		grafo.welshPowell();
		grafo.escribirArchivo(dirColoreo);
		
		ProbadorColoreo probador=new ProbadorColoreo();
		probador.leerGrafos(dirGrafo);
		probador.leerColoreo(dirColoreo);
		Assert.assertTrue(probador.verificarColoreo());
	}*/
}
