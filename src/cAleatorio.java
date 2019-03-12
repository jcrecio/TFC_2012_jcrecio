import java.util.Random;

// Clase para generar contenidos aleatorios
public class cAleatorio {
	
	private int contenido;
	
	// Genera un entero aleatorio dado un tope
	public int generaEntero(int limite){
		contenido = (int)(Math.random()*(limite+1));
		return contenido;
	}
	
	// Genera un entero aleatorio dado un rango
	public int generaEntero(int limiteInferior, int limite){
		int valor = generaEntero(limite);
		if(valor<=limiteInferior){
			contenido=limiteInferior;
		}
		return contenido;
	}
	
	// Genera un booleano aleatorio
	public boolean generaBooleano(){
		Random num=new Random();
		return num.nextInt(1)==0;
	}
	
	// Escoge un entero al azar de entre un conjunto de enteros dado
	public int eligeEntero(int[] enteros){
		generaEntero(0,enteros.length-1);
		return enteros[getContenido()];
	}
	public int getContenido(){
		return contenido;
	}
}