// Implementación de la posición de una sopa de letras
public class cPosicionSopa extends cPosicion {
	
	private String letra;
	private Boolean marcado;
	
	public cPosicionSopa(int x, int y, String letra){
		super(x,y);
		setLetra(letra);
		setMarcado(false);
	}
	public cPosicionSopa(int x, int y, String contenido, Boolean marcado){
		this(x,y,contenido);
		setMarcado(marcado);
	}
	
	public String getLetra() {
		return letra.toUpperCase();
	}
	
	public void setPosicion(int x, int y, String contenido){
		setPosicion(x,y);
		setLetra(contenido);
	}
	public void setLetra(String letra) {
		this.letra = letra.toUpperCase();
	}

	public cPosicionSopa replicar(){
		return new cPosicionSopa(getX(),getY(),getLetra(),getMarcado());
	}

	public Boolean getMarcado() {
		return marcado;
	}

	public void setMarcado(Boolean marcado) {
		this.marcado = marcado;
	}
}
