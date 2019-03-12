// Parametrizador de todos los valores que hay que generar en un laberinto
public final class cParametrizadorLaberinto {
	
	public static int MAX_X_TABLERO=15;
	public static int MAX_Y_TABLERO=15;
	public static int MIN_DIM_TABLERO=3;
	public static int DIFICULTAD_NODEFINIDA=-1;
	public static int MAX_MOVIMIENTOS=7;
	
	public static int generaNumeroObstaculosValido(int x, int y, cPosicionLaberinto[] p, cPosicionLaberinto pj){
		int minimo=0;
		for(int i=0;i<p.length;i++){
			int aux = p[i].getX()-pj.getX()+p[i].getY()-pj.getY();
			if(minimo<aux)minimo=aux;
		}
		return x*y-minimo;
	}
	public static cPosicionLaberinto generaPosicionApertura(int x, int y, int tipo, cPosicionLaberinto p){
		cPosicionLaberinto ps = generaPosicionApertura(x,y,tipo);
		while((p.getX()==ps.getX())&&(p.getX()==ps.getX()))ps = generaPosicionApertura(x,y,tipo);
		return ps;
	}
	public static cPosicionLaberinto generaPosicionApertura(int x, int y, int tipo){
		cAleatorio aux=new cAleatorio();
		int borde=aux.generaEntero(3),x_entrada=0,y_entrada=0;
		//borde 0 arriba, 1 dcha, 2 abj, 3 izq
		switch(borde) {
		case 0: {
				x_entrada=aux.generaEntero(0,x);
				y_entrada=0;
				break;
			}
		case 1: {
				x_entrada=x;
				y_entrada=aux.generaEntero(0,y);
				break;
			}
		case 2: {
				x_entrada=aux.generaEntero(0,x);
				y_entrada=y;
				break;
			}
		case 3: {
				x_entrada=0;
				y_entrada=aux.generaEntero(0,y);
				break;
			}
		}
		return new cPosicionLaberinto(x_entrada,y_entrada, tipo);
	}
	public static cPosicionLaberinto[] generaPosicionesApertura(int x, int y, int tipo){
		return generaPosicionesApertura(x,y,tipo,DIFICULTAD_NODEFINIDA);
	}
	public static cPosicionLaberinto[] generaPosicionesApertura(int x, int y, int tipo, int opcional_dificultad){
		int z = cMatematicas.menor(x,y);
		cAleatorio a_ = new cAleatorio();
		int limiteSalidas;
		if(opcional_dificultad == DIFICULTAD_NODEFINIDA)limiteSalidas = a_.generaEntero(z);
		else limiteSalidas = opcional_dificultad;
		limiteSalidas=1;
		cPosicionLaberinto[] aux = new cPosicionLaberinto[limiteSalidas];
		for(int i=0;i<limiteSalidas;i++)
			aux[i]=generaPosicionApertura(x,y,tipo);
		return aux;
	}
	public static cPosicionLaberinto generaPosicionJugador(int x, int y, cPosicionLaberinto[] posOcupadas){
		cPosicionLaberinto aux = generaPosicionApertura(x,y,cPosicion._JUGADOR);
		while(aux.estaEn(posOcupadas))aux=generaPosicionApertura(x,y,cPosicion._JUGADOR);
		aux.setPisado(true);
		return aux;
	}
	public static cPosicionLaberinto generaPosicionJugador(int x, int y, cPosicionLaberinto posEntrada, cPosicionLaberinto[] posSalidas){
		cPosicionLaberinto[] todasPosiciones = cVector.unirPosiciones(posEntrada, posSalidas);
		cPosicionLaberinto aux = generaPosicionApertura(x,y,cPosicion._JUGADOR);
		while(aux.estaEn(todasPosiciones))aux=generaPosicionApertura(x,y,cPosicion._JUGADOR);
		aux.setPisado(true);
		return aux;
	}
	public static cPosicionLaberinto generaPosicionAleatoria(int x, int y){
		cAleatorio a_ = new cAleatorio();
		int a = a_.generaEntero(x);
		cAleatorio b_ = new cAleatorio();
		int b = b_.generaEntero(y);
		return new cPosicionLaberinto(a,b);
	}
	public static cPosicionLaberinto generaPosicionAleatoria(int x, int y, int contenido){
		cPosicionLaberinto aux = generaPosicionAleatoria(x,y);
		aux.setContenido(contenido);
		return aux;
	}
	public static cTableroLaberinto generaTablero() {
		cAleatorio a_ = new cAleatorio();
		int a = a_.generaEntero(MIN_DIM_TABLERO,MAX_X_TABLERO);
		cAleatorio b_ = new cAleatorio();
		int b = b_.generaEntero(MIN_DIM_TABLERO,MAX_Y_TABLERO);
		return new cTableroLaberinto(a,b);
	}
	public static cTableroLaberinto generaTablero(int txtX, int txtX_, int txtY, int txtY_) {
		cAleatorio aux = new cAleatorio();
		int x = aux.generaEntero(txtX, txtX_);
		aux.generaEntero(txtY,txtY_);
		int y = aux.getContenido();
		return new cTableroLaberinto(x,y);
	}
	public static int generaDificultad(int minimo){
		cAleatorio aux = new cAleatorio();
		return aux.generaEntero(minimo);
	}
	public static int generaDificultad(int minimo, int maximo){
		cAleatorio aux = new cAleatorio();
		return aux.generaEntero(minimo,maximo);
	}
	public static cMovimiento[] generaMovimientos(){
		cMovimiento[] movs=new cMovimiento[(new cAleatorio()).generaEntero(MAX_MOVIMIENTOS)];
		int z=movs.length;
		Boolean[] ocupados=new Boolean[MAX_MOVIMIENTOS+1];
		for(int i=0;i<MAX_MOVIMIENTOS;i++){
			ocupados[i]=false;
		}
		for(int i=0;i<z;i++) {
			int dir = (new cAleatorio()).generaEntero(MAX_MOVIMIENTOS-1);
			while(dir>=z)dir = (new cAleatorio()).generaEntero(MAX_MOVIMIENTOS-1);
			while(ocupados[dir]) {
				dir = (new cAleatorio()).generaEntero(MAX_MOVIMIENTOS-1);
			}
			ocupados[dir]=true;
			movs[i]=new cMovimiento(dir);
		}
		return movs;
	}
}