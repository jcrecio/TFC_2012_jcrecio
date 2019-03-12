// Implementación de la posición de un laberinto
public class cPosicionLaberinto extends cPosicion {
	
	public cPosicionLaberinto() {
		this(cPosicion._VACIO,cPosicion._VACIO,cPosicion._VACIO,false);
	}
	public cPosicionLaberinto(int x, int y){
		super(x,y);
	}
	public cPosicionLaberinto(int x, int y, int contenido){
		super(x,y);
		setContenido(contenido);
	}
	public cPosicionLaberinto(int x, int y, int contenido, boolean pisado){
		this(x,y,contenido);
		setPisado(pisado);
	}
	public cPosicionLaberinto(cPosicionLaberinto pos){
		this(pos.getX(),pos.getY(),pos.getContenido(),pos.getPisado());
	}

	public void setPosicion(int x, int y, int contenido){
		setPosicion(x,y);
		setContenido(contenido);
	}
	public void setPosicion(int x, int y, int contenido, boolean pisado){
		setPosicion(x,y,contenido);
		super.pisado = pisado;
	}

	public void setObstaculo(int obstaculo) {
		super.contenido = obstaculo;
	}
	public boolean esObstaculo() {
		return ((int)super.contenido==_OBSTACULO);
	}
	public int getContenido(){
		return (int)super.contenido;
	}
	public void setContenido(int contenido){
		this.contenido=contenido;
	}
	public void setPisado(boolean pisado){
		super.pisado=pisado;
	}
	public boolean getPisado(){
		return super.pisado;
	}
	public cPosicionLaberinto replicar() {
		return new cPosicionLaberinto(getX(),getY(),getContenido(),getPisado());
	}
	public boolean estaEn(cPosicionLaberinto[] p){
		for(int i=0;i<p.length;i++)
			if((p[i].getX()==getX())&&(p[i].getY()==getY())) return true;
		return false;
	}
	public cPosicionLaberinto getPosAdyacente(cMovimiento mov){
		return (cPosicionLaberinto)mov.aplicar(replicar());
	}
	public void imprimir() {
		System.out.print("("+getX()+","+getY()+","+getContenido()+")");
	}
}