// Operaciones con vectores de posiciones 
public class cVector {
	
	public static cPosicionLaberinto[] unirPosiciones(cPosicionLaberinto p1, cPosicionLaberinto[] p){
		int len = p.length;
		cPosicionLaberinto[] nuevasPosiciones = new cPosicionLaberinto[len+1];
		System.arraycopy(p,0,nuevasPosiciones,0,len);
		nuevasPosiciones[len]=new cPosicionLaberinto();
		nuevasPosiciones[len].setPosicion(p1.getX(),p1.getY(),p1.getContenido());
		return nuevasPosiciones;
	}
	public static cPosicionLaberinto[] unirPosiciones(cPosicionLaberinto[] p1, cPosicionLaberinto[] p2){
		int len1 = p1.length, len2 = p2.length;
		cPosicionLaberinto[] nuevasPosiciones = new cPosicionLaberinto[len1+len2];
		System.arraycopy(p1,0,nuevasPosiciones,0,len1);
		System.arraycopy(p2,0,nuevasPosiciones,len1-1,len2);
		return nuevasPosiciones;
	}
	public static void imprimir(Object[] p) {
		for(int i=0;i<p.length;i++)
			System.out.println(" "+i);
	}
}
