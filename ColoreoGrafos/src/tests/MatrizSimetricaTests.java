package tests;

import org.junit.*;

import coloreoGrafos.MatrizSimetrica;

public class MatrizSimetricaTests {
	
	@Test
	public void queConstruyecorrectamente(){
		MatrizSimetrica mSim = new MatrizSimetrica(5);
		MatrizSimetrica mSim2 = new MatrizSimetrica(10);
		Assert.assertTrue(mSim.getMatriz().length == 10);
		Assert.assertTrue(mSim2.getMatriz().length == 45);
	}
	
	@Test
	public void queGrabaValorCorrectamente(){
		MatrizSimetrica mSim = new MatrizSimetrica(5);
		mSim.grabarVal(0,1);
		Assert.assertTrue(mSim.getMatriz()[0]);
		MatrizSimetrica mSim2 = new MatrizSimetrica(5);
		mSim2.grabarVal(1,2);
		Assert.assertTrue(mSim2.getMatriz()[4]);
		MatrizSimetrica mSim3 = new MatrizSimetrica(5);
		mSim3.grabarVal(3,4);
		Assert.assertTrue(mSim3.getMatriz()[9]);
	}
	
	@Test
	public void queLeeValorCorrectamente(){
		MatrizSimetrica mSim = new MatrizSimetrica(5);
		mSim.grabarVal(0,1);
		Assert.assertTrue(mSim.leerVal(0,1));
		MatrizSimetrica mSim2 = new MatrizSimetrica(5);
		mSim2.grabarVal(1,2);
		Assert.assertTrue(mSim2.leerVal(1,2));
		MatrizSimetrica mSim3 = new MatrizSimetrica(5);
		mSim3.grabarVal(3,4);
		Assert.assertTrue(mSim3.leerVal(3,4));
	}
	
	
}
