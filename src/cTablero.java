// Define la funcionalidad básica de un tablero
public abstract class cTablero {
	
	private int dimX;
	private int dimY;
	
	public cTablero(int dimX, int dimY){
		setDimX(dimX);
		setDimY(dimY);
	}
	
	public int getDimX() {
		return dimX;
	}
	public void setDimX(int dimX) {
		this.dimX = dimX;
	}
	public int getDimY() {
		return dimY;
	}
	public void setDimY(int dimY) {
		this.dimY = dimY;
	}
	public int getIndiceMax_X(){
		return dimX-1;
	}
	public int getIndiceMax_Y(){
		return dimY-1;
	}
	public boolean coordenadaFueraRango(cPosicion pos){
		return coordenadaFueraRango(pos.getX(),pos.getY(),getIndiceMax_X(),getIndiceMax_Y());
	}
	public boolean coordenadaFueraRango(int x,int y,int maxX, int maxY){
		return x>maxX||x<0||y>maxY||y<0;
	}
}