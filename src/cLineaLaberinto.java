//Clase que implementa el comportamiento de un objeto que porta información de una línea de fichero
//de un fichero de laberinto
public class cLineaLaberinto extends cLinea {

	public cLineaLaberinto(String linea){
		setTipo(linea.substring(0,1));
		setContenido(linea.substring(2));
	}
	public void setTipo(String tipo) {
		setTipo(Integer.parseInt(tipo));
	}

	public int getContenidoPosicion() {
		return Integer.parseInt(contenido.split(",")[2]);
	}
	public Boolean getPisado() {
		if(contenido.split(",")[3]=="0")return false;
		return true;
	}
	public cCamino getSolucion(){
		String[] aux = contenido.split(";");
		cCamino nuevo=new cCamino();
		for(int i=0; i<aux.length; i++){
			nuevo.apilaPosicion(new cPosicionLaberinto(Integer.parseInt(aux[i].split(",")[0]),Integer.parseInt(aux[i].split(",")[1])));
		}
		return nuevo;
	}
	public cMovimiento[] getMovimientos() {
		String[] aux = contenido.split(",");
		int num = aux.length;
		cMovimiento[] movs = new cMovimiento[num];
		for(int i=0; i<num; i++){
			movs[i] = new cMovimiento(i);
		}
		return movs;
	}
	public String getNombreLaberinto(){
		return getContenido();
	}
}