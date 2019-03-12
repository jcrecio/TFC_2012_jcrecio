import java.util.ArrayList;

// Clase que implementa colas que guardan caminos usados en la búsqueda de amplitud
public class cCola {
	
	private ArrayList<cPosicionLaberinto> posiciones;
	
	public cCola(){
		posiciones = new ArrayList<cPosicionLaberinto>();
	}
	public cCola(cPosicionLaberinto[] posiciones){
		this();
		for(int i=0;i<posiciones.length;i++){
			this.posiciones.add(posiciones[i]);
		}
	}
	
	public void encolar(cPosicionLaberinto pos){
		posiciones.add(pos);
	}
	public cPosicionLaberinto desencolar(){
		return posiciones.get(0);
	}
	
	// Encola una cola de posiciones
	public void encolarCola(cCola c){
		int l = c.longitud();
		for(int i=0;i<l;i++){
			encolar(c.desencolar());
		}
	}
	
	public int longitud(){
		return posiciones.size();
	}
}
