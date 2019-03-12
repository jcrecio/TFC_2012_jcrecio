import java.awt.Graphics;

// Clase que implementa un laberinto
public class cLaberinto {
	private int ID_Laberinto;
	private String nombre;
	private cTableroLaberinto tablero;
	private cPosicionLaberinto posJugador;
	private cPosicionLaberinto posEntrada;
	private cPosicionLaberinto[] posSalidas;
	private int numObstaculos;
	private int contenidoVacio;
	private int dificultad;
	private cMovimiento[] movimientosDisponibles;
	private espacioSoluciones soluciones;
	private static final int _MAXIMA_DIFICULTAD=2;
	private static final int _INFINITO=Integer.MAX_VALUE;
	private int modoBusqueda = 0; // profundidad 0, amplitud 1
	private long tiempoHastaMejorSolucion;
	private long comienzo;
	private int nopasar;
	private int iteracion;
	private int stopAbsoluto; //Con valor igual a 1 detiene todo el proceso recursivo de backtracking

	private boolean bloqueado; // Bloquea el laberinto para otras operaciones cuando está en proceso de resolución
	private cVentanaCarga vCarga; //asociación opcional con ventana de carga

	public cLaberinto(int dimX, int dimY, cPosicionLaberinto posJugador, cPosicionLaberinto posEntrada,
			cPosicionLaberinto[] posSalidas, int numObstaculos, int dificultad, cMovimiento[] movimientosDisponibles) {
		setNombre("Sin titulo"+System.currentTimeMillis());
		inicializar(dimX,dimY,posJugador,posEntrada,posSalidas,numObstaculos,dificultad,movimientosDisponibles);
	}
	
	public cLaberinto(cTableroLaberinto tablero, cPosicionLaberinto posJugador, cPosicionLaberinto posEntrada,
			cPosicionLaberinto[] posSalidas, int numObstaculos, cMovimiento[] movimientosDisponibles){
		setNombre("Sin titulo"+System.currentTimeMillis());
		this.tablero=tablero;
		this.posJugador=posJugador;
		posJugador.setPisado(true);
		this.posEntrada=posEntrada;
		posEntrada.setPisado(true);
		this.posSalidas=posSalidas;
		this.numObstaculos=numObstaculos;
		this.movimientosDisponibles=movimientosDisponibles;
	}
	
	public cLaberinto(String nombre, cTableroLaberinto t, cMovimiento[] movs, int numObstaculos){
		setNombre(nombre);
		if(t==null) t = cParametrizadorLaberinto.generaTablero();
		setTablero(t);
		int x = t.getIndiceMax_X(), y = t.getIndiceMax_Y();
		if(movs==null) movs = cParametrizadorLaberinto.generaMovimientos();
		setMovimientosDisponibles(movs);
		posSalidas=cParametrizadorLaberinto.generaPosicionesApertura(x,y,cPosicion._SALIDA,dificultad);
		posEntrada=cParametrizadorLaberinto.generaPosicionApertura(x,y,cPosicion._ENTRADA/*,dificultad*/);
		posEntrada=cParametrizadorLaberinto.generaPosicionApertura(x,y,cPosicion._ENTRADA/*,dificultad*/);
		posJugador=posEntrada;
		posEntrada.setPisado(true);
		posJugador.setContenido(cPosicion._JUGADOR);
		posJugador.setPisado(true);
		if(numObstaculos==0)numObstaculos=cParametrizadorLaberinto.generaNumeroObstaculosValido(x,y,posSalidas,posJugador);
		tablero.setPosicion(posEntrada);
		tablero.setPosicion(posSalidas);
		tablero.setPosicion(posJugador);
		tablero.rellenar(numObstaculos,cVector.unirPosiciones(posEntrada, posSalidas));
	}
	
	public cLaberinto(cTableroLaberinto tablero, cPosicionLaberinto posJugador, cPosicionLaberinto posEntrada,
			cPosicionLaberinto[] posSalidas, int numObstaculos, cMovimiento[] movimientosDisponibles, espacioSoluciones sols){
		setNombre("Sin titulo"+System.currentTimeMillis());
		setTablero(tablero);
		setPosJugador(posJugador);
		posJugador.setPisado(true);
		this.posEntrada=posEntrada;
		posEntrada.setPisado(true);
		this.posSalidas=posSalidas;
		this.numObstaculos=numObstaculos;
		this.movimientosDisponibles=movimientosDisponibles;
		this.soluciones=sols;
	}
	
	public cLaberinto(){
		setNombre("Sin titulo_"+System.currentTimeMillis());
		inicializar();
	}
	private void inicializar(int dimX, int dimY, cPosicionLaberinto posJugador, cPosicionLaberinto posEntrada,
			cPosicionLaberinto[] posSalidas, int numObstaculos, int dificultad, cMovimiento[] movimientosDisponibles){
		tablero=new cTableroLaberinto(dimX,dimY);
		this.posJugador=posEntrada;
		posJugador.setPisado(true);
		this.posEntrada=posEntrada;
		posEntrada.setPisado(true);
		this.posSalidas=posSalidas;
		this.numObstaculos=numObstaculos;
		this.dificultad=dificultad;
		setMovimientosDisponibles(movimientosDisponibles);
		tablero.rellenar(numObstaculos,cVector.unirPosiciones(posEntrada, posSalidas));
	}
	private void inicializar(){
		tablero=cParametrizadorLaberinto.generaTablero();
		int x = tablero.getIndiceMax_X(), y = tablero.getIndiceMax_Y();
		dificultad=cParametrizadorLaberinto.generaDificultad(cMatematicas.menor(x,y),_MAXIMA_DIFICULTAD);
		posEntrada=cParametrizadorLaberinto.generaPosicionApertura(x,y,cPosicion._ENTRADA/*,dificultad*/);
		posSalidas=cParametrizadorLaberinto.generaPosicionesApertura(x,y,cPosicion._SALIDA,dificultad);
		posJugador=posEntrada;
		posEntrada.setPisado(true);
		posJugador.setContenido(cPosicion._JUGADOR);
		posJugador.setPisado(true);
		numObstaculos=cParametrizadorLaberinto.generaNumeroObstaculosValido(x,y,posSalidas,posJugador);
		tablero.setPosicion(posEntrada);
		tablero.setPosicion(posSalidas);
		tablero.setPosicion(posJugador);
		tablero.rellenar(numObstaculos,cVector.unirPosiciones(posEntrada, posSalidas));
		movimientosDisponibles = cParametrizadorLaberinto.generaMovimientos();
	}
	
	// Algoritmo para forzar la existencia de soluciones
	public void forzarSolucion(){
		forzarSolucion(-1,-1,-1,-1);
	}
	public void forzarSolucion(int excesoX, int excesoY, int x_, int y_){
		cPosicionLaberinto auxSalida = posSalidas[0].replicar();
		
		if(x_==-1)x_ = posEntrada.getX();
		if(y_==-1)y_ = posEntrada.getY();
		
		int dx = auxSalida.getX() - x_;
		int dy = auxSalida.getY() - y_;
		
		int creditosx = Math.abs(dx);
		int creditosy = Math.abs(dy);
		int total = creditosx+creditosy;
		
		int i = 0;

		while((total>i)){
			cAleatorio a = new cAleatorio();
			a.generaEntero(1);
			if((a.getContenido()==0)){
				if(creditosx>0){
					if(dx<0)x_--;
					else if(dx>0)x_++;
					creditosx--;
				}
				else {
					if(excesoX != -1){
						excesoX--;
						if(excesoX==0){
							forzarSolucion(-1,-1,x_,y_);
						}
						break;
					}
				}
			}
			else {
				if(creditosy>0){
					if(dy<0)y_--;
					else if(dy>0)y_++;
					creditosy--;
				}
				else {
					if(excesoY != -1){
						excesoY--;
						if(excesoY==0){
							forzarSolucion(-1,-1,x_,y_);
						}
						break;
					}
				}
			}
			total--;
			if(total>0)tablero.setPosicion(x_,y_,new cPosicionLaberinto(x_,y_,cPosicion._VACIO));
		}
	}
	
	public void imprimirTablero(){
		tablero.imprimir();
	}
	public void imprimirMovimientos(){
		for(int i=0;i<movimientosDisponibles.length;i++){
			System.out.print(movimientosDisponibles[i].getDireccion()+",");
		}
	}
	
	// Establecimiento de todas las referencias al aplicar/desplicar movimientos
	public void establecerPosJugadorTrasAplicar(cPosicionLaberinto posNueva, boolean pisado){
		posJugador.setContenido(cPosicion._VACIO);
		posJugador.setPisado(true);
		tablero.setPosicion(posJugador);
		posJugador = posNueva.replicar();
		posJugador.setContenido(cPosicion._JUGADOR);
		posJugador.setPisado(true);
		tablero.setPosicion(posJugador);
	}

	public void establecerPosJugadorTrasDesaplicar(cMovimiento mov){
		posJugador.setPisado(false);
		tablero.setPosicion(posJugador);
		cPosicionLaberinto posNueva = (cPosicionLaberinto)mov.dCast(posJugador);
		posNueva.setContenido(cPosicion._JUGADOR);
		posNueva.setPisado(true);
		tablero.setPosicion(posNueva);
		posJugador = posNueva;
	}

	public void establecerPosJugadorTrasDesaplicar_A(cMovimiento mov){
		posJugador.setPisado(false);
		posJugador.setContenido(cPosicion._VACIO);
		tablero.setPosicion(posJugador);
		cPosicionLaberinto posNueva = (cPosicionLaberinto)mov.dCast(posJugador);
		posNueva.setContenido(cPosicion._JUGADOR);
		posNueva.setPisado(true);
		tablero.setPosicion(posNueva);
		posJugador = posNueva;
	}
	 // Aplicación de los operadores de movimiento
	public void aplicarOperador(cMovimiento mov) {
		cPosicionLaberinto posNueva = (cPosicionLaberinto)mov.cast(posJugador);
		establecerPosJugadorTrasAplicar(posNueva,true);
	}
	// DES-aplicación de los operadores de movimiento
	public void desAplicarOperador(cMovimiento mov){
		posJugador.setContenido(cPosicion._VACIO);
		establecerPosJugadorTrasDesaplicar(mov);
	}
	// DES-aplicación de los operadores de movimiento ( en amplitud )
	public void desAplicarOperador(cMovimiento mov, boolean b){
		posJugador.setContenido(cPosicion._SALIDA);
		establecerPosJugadorTrasDesaplicar(mov);
	}
	public Boolean esSolucion(cPosicionLaberinto pos){
		if(pos.estaEn(getPosSalidas()))
			return true;
		return false;
	}
	
	// Comienzo el proceso de resolución
	public void resolver() {
		soluciones = new espacioSoluciones();
		contenidoVacio = tablero.getDimX()*tablero.getDimY()-numObstaculos;
		int profundidad=0;
		nopasar = _INFINITO;
		stopAbsoluto = 0;
		setBloqueado(true);
		setTiempoHastaMejorSolucion(0);
        comienzo = System.currentTimeMillis();
        if(modoBusqueda==0){
    		cCamino c = new cCamino();
    		iteracion = 0;
        	realizarSols(c,profundidad);
        }
        else {
        	cCola c = new cCola();
     		cCamino s = new cCamino();
        	realizarSolsAmplitud(c,s,profundidad);
        }
		posJugador=posEntrada;
	}
	
	public espacioSoluciones getSoluciones(){
		if(soluciones==null) return new espacioSoluciones();
		return soluciones;
	}
	public void pintarLaberinto(cVentanaLaberinto v) {
		v.paint(v.getGraphics());
	}
    public void pintarSolucion(cVentanaLaberinto v, int i){
    	Graphics g = v.getGraphics();
    	v.paint(g,i);
    }
    public void pintarSolucion(cVentanaLaberinto v, cCamino c){
    	Graphics g = v.getGraphics();
    	v.paint(g,c);
    }
    
	// Busqueda en profundidad
	private void realizarSols(cCamino c,int profundidad) {
		if((profundidad<nopasar-1)&&(profundidad<contenidoVacio)&&(stopAbsoluto==0)) {
			cMovimiento[] movsNivelActual=getMovimientosAplicables();
				for(int i=0;i<movsNivelActual.length;i++){
						aplicarOperador(movsNivelActual[i]);
						iteracion++;
                    	if((iteracion % 1000) == 0) {
                    		vCarga.escribirMovimiento(iteracion);
                    	}
						c.apilaPosicion(posJugador);
						if(esSolucion(posJugador)){
		                    int numMovs = c.getNumMovimientos();
		                    if (numMovs != 0) {
		                    	nopasar = numMovs;
			                    vCarga.escribirSolucionesDesdeFuera(soluciones.getNumCaminos());
		                    }
		                    if(soluciones.permitirCamino(profundidad)){
			                    soluciones.addCamino(c.replicar());
			                    setTiempoHastaMejorSolucion(System.currentTimeMillis() - comienzo);
		                    }
		                    desAplicarOperador(movsNivelActual[i],true);
		                    c.eliminaUltimaPosicion();
						}
						else {
							realizarSols(c,profundidad+1);
							c.eliminaUltimaPosicion();
			                desAplicarOperador(movsNivelActual[i]);
						}
					}
		}
	}
	// Busqueda en amplitud
	private void realizarSolsAmplitud(cCola colaPosiciones, cCamino camino, int profundidad){
		if((profundidad<nopasar-1)&&(profundidad<contenidoVacio)&&(stopAbsoluto==0)) {
			cCola c = getPosicionesAceptablesAdyacentes();
			colaPosiciones.encolarCola(c);
			int tam = c.longitud();
			for(int i=0;i<tam;i++){
				cPosicionLaberinto posAnterior = posJugador.replicar();
				cPosicionLaberinto pos = c.desencolar();
				int contenidoNueva = pos.getContenido();
				establecerPosJugadorTrasAplicar(pos,true);
				camino.apilaPosicion(pos);
				if(esSolucion(pos)){
	                int numMovs = camino.getNumMovimientos();
	                if (numMovs != 0) nopasar = numMovs;
	                if(soluciones.permitirCamino(profundidad)){
	                    soluciones.addCamino(camino.replicar());
	                    vCarga.escribirMovimiento(numMovs);
	                    setTiempoHastaMejorSolucion(System.currentTimeMillis() - comienzo);
                    }
					cPosicionLaberinto pa = (cPosicionLaberinto)camino.desapilaPosicion();
					pa.setContenido(contenidoNueva);
					pa.setPisado(false);
					tablero.setPosicion(pa);
					setPosJugador(posAnterior);
					tablero.setPosicion(posAnterior);
				}
				else {
					realizarSolsAmplitud(colaPosiciones,camino,profundidad+1);
					cPosicionLaberinto pa = (cPosicionLaberinto)camino.desapilaPosicion();
					pa.setContenido(contenidoNueva);
					pa.setPisado(false);
					tablero.setPosicion(pa);
					posAnterior.setContenido(cPosicion._JUGADOR);
					setPosJugador(posAnterior);
					tablero.setPosicion(posAnterior);
				}
			}
		}
	}
	// Detener proceso de resolución
	public void parar(){
		stopAbsoluto = 1;
		setBloqueado(false);
	}
	public void asociarCarga(cVentanaCarga v){
		vCarga = v;
	}
	public void setNumObstaculos(int numObstaculos) {
		this.numObstaculos = numObstaculos;
	}
	public int getNumObstaculos() {
		return numObstaculos;
	}
	public void setMovimientosDisponibles(cMovimiento[] movimientosDisponibles) {
		this.movimientosDisponibles = movimientosDisponibles;
	}
	public cMovimiento[] getMovimientosDisponibles() {
		return movimientosDisponibles;
	}
	public void setTablero(cTableroLaberinto tablero) {
		this.tablero = tablero;
	}
	public cTableroLaberinto getTablero() {
		return tablero;
	}
	public void setPosJugador(cPosicionLaberinto posJugador) {
		this.posJugador = posJugador;
	}
	public cPosicionLaberinto getPosJugador() {
		return posJugador;
	}
	public void setPosEntrada(cPosicionLaberinto posEntrada) {
		this.posEntrada = posEntrada;
	}
	public cPosicionLaberinto getPosEntrada() {
		return posEntrada;
	}
	public void setPosSalidas(cPosicionLaberinto[] posSalidas) {
		this.posSalidas = posSalidas;
	}
	public void setPosSalida(cPosicionLaberinto pos, int i){
		this.posSalidas[i]=pos.replicar();
	}
	public cPosicionLaberinto[] getPosSalidas() {
		return posSalidas;
	}
	public long getTiempoHastaMejorSolucion() {
		return tiempoHastaMejorSolucion;
	}
	public void setTiempoHastaMejorSolucion(long tiempoHastaMejorSolucion) {
		this.tiempoHastaMejorSolucion = tiempoHastaMejorSolucion;
	}
	public void setNombre(String nombre){
    	this.nombre=nombre;
    }
    public String getNombre() {
    	return nombre;
    }
	public int getModoBusqueda() {
		return modoBusqueda;
	}
	public void setModoBusqueda(int modoBusqueda) {
		this.modoBusqueda = modoBusqueda;
	}
	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public int getStopAbsoluto() {
		return stopAbsoluto;
	}
	public void setStopAbsoluto(int stopAbsoluto) {
		this.stopAbsoluto = stopAbsoluto;
	}
	public cMovimiento[] getMovimientosAplicables(){
		return getTablero().getMovimientosAplicables(getMovimientosDisponibles(),getPosJugador());
	}
	public cCola getPosicionesAceptablesAdyacentes(){
		return getTablero().getPosicionesAdyacentesVacias(getMovimientosDisponibles(),getPosJugador());
	}
	public void imprimirMovimientos(cMovimiento[] m){
		System.out.println();
		for(int i=0;i<m.length;i++)
			System.out.print(m[i].getDireccion()+",");
	}

	public int getID_Laberinto() {
		return ID_Laberinto;
	}

	public void setID_Laberinto(int iD_Laberinto) {
		ID_Laberinto = iD_Laberinto;
	}
}