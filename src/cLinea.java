
//Clase que define el comportamiento de un objeto que porta informaci�n de una l�nea de fichero
public abstract class cLinea {
	
	protected String contenido;
	protected int tipo;
	
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo){
		this.tipo=tipo;
	}
	public int getDimX() {
		return Integer.parseInt(contenido.split(",")[0]);
	}
	public int getDimY() {
		return Integer.parseInt(contenido.split(",")[1]);
	}
	public int getX() {
		return Integer.parseInt(contenido.split(",")[0]);
	}
	public int getY() {
		return Integer.parseInt(contenido.split(",")[1]);
	}
}
