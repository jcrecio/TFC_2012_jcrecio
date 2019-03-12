// Implementa un tablero que contiene las posiciones de un laberinto
public class cTableroLaberinto extends cTablero {

	// Por defecto las posiciones seran casillas vacias
	
	private cPosicionLaberinto[][] posiciones;

	public cTableroLaberinto(int dimX, int dimY){
		super(dimX,dimY);
		posiciones=new cPosicionLaberinto[dimX][];
		for(int i=0;i<dimX;i++){
			posiciones[i]=new cPosicionLaberinto[dimY];
			for(int j=0;j<dimY;j++){
				posiciones[i][j]=new cPosicionLaberinto(i,j,0);
			}
		}
	}
	public cTableroLaberinto(int dimX, int dimY, cPosicionLaberinto posEntrada, cPosicionLaberinto[] posSalidas, cPosicionLaberinto posJugador){
		this(dimX,dimY);
		setPosicion(posEntrada);
		setPosicion(posSalidas);
		setPosicion(posJugador);
	}
	public cPosicionLaberinto getPosicion(int x, int y){
		return posiciones[x][y];
	}
	public void setPosicion(int x, int y, cPosicionLaberinto nueva){
		if((nueva.getX()==x)&&(nueva.getY()==y))
			setPosicion(nueva);
	}
	public void setPosicion(cPosicionLaberinto nueva){
		try {
			posiciones[nueva.getX()][nueva.getY()] = nueva.replicar();
		}
		catch(Exception e){
			
		}
	}
	public void setPosicion(cPosicionLaberinto[] p){
		for(int i=0;i<p.length;i++)
			setPosicion(p[i]);
	}

	public void rellenar(int numObstaculos, cPosicionLaberinto[] posOcupadas){
		cPosicionLaberinto aux = null;
		for(int i=0;i<numObstaculos;i++){
			aux = posOcupadas[0];
			while(aux.estaEn(posOcupadas)) 
				aux = cParametrizadorLaberinto.generaPosicionAleatoria(getIndiceMax_X(),getIndiceMax_Y(),cPosicion._OBSTACULO);
			setPosicion(aux);
		}
	}
	public void rellenar(int numObstaculos, cPosicionLaberinto[] posOcupadas, cPosicionLaberinto[] posProhibidas){
		cPosicionLaberinto aux = null;
		posOcupadas = cVector.unirPosiciones(posOcupadas, posProhibidas);
		for(int i=0;i<numObstaculos;i++){
			aux = posOcupadas[0];
			while(aux.estaEn(posOcupadas)) 
				aux = cParametrizadorLaberinto.generaPosicionAleatoria(getIndiceMax_X(),getIndiceMax_Y(),cPosicion._OBSTACULO);
			setPosicion(aux);
		}
	}
	public Boolean esAplicable(cMovimiento mov, cPosicionLaberinto pos){
		if(mov == null)return false;// SI NO HAY MOV DEFINIDO NO ACEPT.
		cPosicionLaberinto p = mov.cast(pos);
		int x = p.getX(), y = p.getY();
		Boolean fuera = coordenadaFueraRango(p);
		if(fuera)return false;
		p.setContenido(getPosicion(x,y).getContenido());
		Boolean pisado = getPosicion(x,y).getPisado();
		return (!pisado)&&(p.getContenido()!=cPosicion._OBSTACULO);
	}
	
	public cMovimiento[] getMovimientosAplicables(cMovimiento[] movs_, cPosicionLaberinto posJugador)
	{
		int nulos=0;
		int max=movs_.length;
		cMovimiento[] movs=new cMovimiento[max];
		for(int i=0;i<max;i++){
			movs[i]=new cMovimiento(movs_[i].getDireccion());
			if(!esAplicable(movs[i],posJugador))
			{
				movs[i]=null;
				nulos++;
			}
		}
		cMovimiento[] m = new cMovimiento[max-nulos];
		int j=0;
		for(int i=0;i<max;i++){
			if(movs[i]!=null){
				m[j]=movs[i];
				j++;
			}
		}
		return m;
	}	
	public cCola getPosicionesAdyacentesVacias(cMovimiento[] movs, cPosicionLaberinto posJugador){
		cCola colaPosicionesValidas = new cCola();
		for(int i=0;i<movs.length;i++){
			if(esAplicable(movs[i],posJugador)){
				/*if((!posRes.getPisado())&&(posRes.getContenido()!=cPosicion._OBSTACULO)){*/
					cPosicionLaberinto p = movs[i].cast(posJugador);
					p.setContenido(cPosicion._VACIO);
					colaPosicionesValidas.encolar(p);
				//}
			}
		}
		return colaPosicionesValidas;
	}
	/***/
	public void imprimir(){
		for(int i=0;i<getDimX();i++){
			System.out.println(" ");
			System.out.print("| ");
			for(int j=0;j<getDimY();j++){
				System.out.print(posiciones[i][j].getContenido()+" | ");
			}
		}
		System.out.println();
	}
	public void imprimirPisados(){
		for(int i=0;i<getDimX();i++){
			System.out.println(" ");
			System.out.print(i+" | ");
			for(int j=0;j<getDimY();j++){
				String a = "";
				boolean b = posiciones[i][j].getPisado();
				if(b==true)a="truee";
				else a="false";
				System.out.print(a+" | ");
			}
		}
		System.out.println();
	}
}
