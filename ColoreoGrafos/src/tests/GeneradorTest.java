package tests;

import org.junit.*;

import coloreoGrafos.Generador;


public class GeneradorTest {

	@Test
	public void queGeneraCorrectamenteDadosNyProb(){
		Generador.genRanProb(5, 0.5,"generado1.in");
	}
	
	@Test
	public void queGeneraCorrectamenteDadosNyPorcAdy(){
		Generador.genRanPorcAdy(5, 0.4,"generado2.in");
	}
	
	@Test
	public void queGueneraCorrectamenteRegDadosNyGrado(){
		Generador.genRegGrado(11, 2, "generado3.in");
	}
}