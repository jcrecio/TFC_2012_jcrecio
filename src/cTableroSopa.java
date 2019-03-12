// Implementa un tablero que contiene las posiciones de una sopa de letras
public class cTableroSopa extends cTablero {
	
	private cPosicionSopa[][] posiciones;
	
	public cTableroSopa(int dimX, int dimY){
		super(dimX,dimY);
		posiciones=new cPosicionSopa[dimX][];
		for(int i=0;i<dimX;i++){
			posiciones[i]=new cPosicionSopa[dimY];
			for(int j=0;j<dimY;j++){
				posiciones[i][j]=new cPosicionSopa(i,j,"");
			}
		}
	}
	
	public cPosicionSopa getPosicion(int x, int y){
		return posiciones[x][y];
	}
	public cPosicionSopa getPosicion(cPosicionSopa ps){
		return getPosicion(ps.getX(),ps.getY());
	}
	public void setPosicion(cPosicionSopa pos){
		posiciones[pos.getX()][pos.getY()]=pos.replicar();
	}
	public void setPosicion(int x, int y, String letra){
		setPosicion(new cPosicionSopa(x,y,letra));
	}
	public void desmarcarTodo(){
		for(int i=0;i<getDimX();i++)
			for(int j=0;j<getDimY();j++)
				getPosicion(i,j).setMarcado(false);
	}
	public void imprimir(){
		for(int i=0;i<getDimX();i++){
			System.out.println(" ");
			System.out.print("| ");
			for(int j=0;j<getDimY();j++){
				System.out.print(posiciones[i][j].getLetra()+" | ");
			}
		}
		System.out.println();
	}
	public void imprimirMarcados(){
		for(int i=0;i<getDimX();i++){
			System.out.println(" ");
			System.out.print("| ");
			for(int j=0;j<getDimY();j++){
				System.out.print(posiciones[i][j].getMarcado().toString().substring(0,1)+" | ");
			}
		}
		System.out.println();
	}
}
