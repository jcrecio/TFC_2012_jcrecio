import java.util.ArrayList;

// Clase que implementa caminos con funcionalidad de pila para almacenar una solución
public class cCamino {

	private ArrayList<cPosicionLaberinto> posicionesRecorridas;

	public cCamino() {
		posicionesRecorridas=new ArrayList<cPosicionLaberinto>();
	}
	public cPosicionLaberinto getPosicion(int i){
		return posicionesRecorridas.get(i);
	}
	public int getNumMovimientos() {
		return posicionesRecorridas.size();
	}
	public void setMovimientosRealizados(ArrayList<cPosicionLaberinto> p){
		posicionesRecorridas=p;
	}
	public void apilaPosicion(cPosicionLaberinto pos){
		posicionesRecorridas.add(pos);
	}
	public cPosicion desapilaPosicion(){
		int i = posicionesRecorridas.size()-1;
		cPosicion pos = getPosicion(i);
		posicionesRecorridas.remove(i);
		return pos;
	}
	public void eliminaUltimaPosicion(){
		posicionesRecorridas.remove(posicionesRecorridas.size()-1);
	}
	public cCamino replicar() {
		cCamino nuevo = new cCamino();
		replicarPosiciones(nuevo);
		return nuevo;
	}
	public void imprimir(){
		System.out.println();
		for(int i=0;i<posicionesRecorridas.size();i++){
			getPosicion(i).imprimir();
		}
		System.out.println();
	}
	private void replicarPosiciones(cCamino n){
		int t = posicionesRecorridas.size();
		for(int i=0;i<t;i++){
			n.apilaPosicion(getPosicion(i).replicar());
		}
	}

}
