// Implementa el concepto más básico de posición de cualquier área, tablero, etc
public class cPosicion {
	
	public static int _VACIO = 0;
	public static int _OBSTACULO = 1;
	public static int _JUGADOR = 2;
	public static int _ENTRADA = 3;
	public static int _SALIDA = 4;
	public static int _PALABRA = 5;
	public static int _LETRA = 6;
	
	private int x;
	private int y;
	protected Object contenido;
	protected boolean pisado;

	public cPosicion(int x, int y){
		setPosicion(x,y);
	}
	
	public void setPosicion(int x, int y){
		setX(x);
		setY(y);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public Boolean mismaUbicacion(cPosicion p){
		return (x!=p.getX())&&(y!=p.getY());
	}
	public void setPisado(boolean pisado){
		this.pisado=pisado;
	}
	
	public boolean getPisado(){
		return this.pisado;
	}
}
