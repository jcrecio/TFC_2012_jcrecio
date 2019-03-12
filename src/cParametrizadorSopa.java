// Parametrizador de todos los valores que hay que generar en una sopa de letras
public class cParametrizadorSopa {
	
	
	private static int _LONGITUD_MAXIMA_PALABRA=15;
	private static int _LONGITUD_ALFABETO=27;
	private static String _ALFABETO="ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	
	private cTableroSopa tablero;
	private cDiccionario diccionario;
	
	public cParametrizadorSopa(cTableroSopa tablero){
		setTablero(tablero);
		generarContenidoTablero();
	}
	
	public void setTablero(cTableroSopa tablero){
		this.tablero = tablero;
	}
	public cTableroSopa getTablero() {
		return tablero;
	}
	public cDiccionario getDiccionario() {
		return diccionario;
	}
	public void generarContenido(int num){
		diccionario = new cDiccionario();
		for(int i=0;i<num;i++){
			insertarNuevaPalabra_Diccionario();
		}
	}
	private String generarLetra(){
		cAleatorio a = new cAleatorio();
		a.generaEntero(1,_LONGITUD_ALFABETO);
		int n = a.getContenido();
		return _ALFABETO.substring(n-1,n);
	}
	public String generarPalabra(){
		String palabra="";
		cAleatorio a = new cAleatorio();
		a.generaEntero(1,_LONGITUD_MAXIMA_PALABRA);
		int n = a.getContenido();
		for(int i=0;i<n;i++){
			palabra+=generarLetra();
		}
		return palabra;
	}
	public void generarContenidoTablero(){
		int x = tablero.getDimX();
		int y = tablero.getDimY();
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				tablero.setPosicion(new cPosicionSopa(i,j,generarLetra()));
			}
		}
	}
	private void insertarNuevaPalabra_Diccionario(){
		boolean fin = false;
		String x="";
		while(!fin){
			x = generarPalabra();
			if(!diccionario.existeClave(x))fin=true;
		}
		diccionario.setElemento(x);
	}
	/*private cMovimiento obtenerDireccionValida(String palabra, int l, cPosicionSopa p_){
		cPosicionSopa p = p_.replicar();
		Boolean[] mD = new Boolean[8];
		for(int i = 0; i < 8; i++){
			mD[i] = false;
		}
		cAleatorio nA = new cAleatorio();
		for(int m=0;m<8;m++){
			int dir = nA.generaEntero(7);
			while(mD[dir]==true){
				dir=nA.generaEntero(7);
			}
			mD[dir]=true;
			cMovimiento mov = new cMovimiento(dir);
			Boolean seguir = true;
			int j = 0;
			while((j<l)&&(seguir)) {
				mov.cast(p);
				if((tablero.coordenadaFueraRango(p))||(!p.getPisado()))seguir=false;
				j++;
			}
			if((j==l)&&(seguir))return mov;
		}
		return null;
	}*/
	/*private void insertarNuevaPalabra_Tablero(String palabra, int l, cMovimiento mov, cPosicionSopa p){
		for(int i=0;i<l;i++){
			p = mov.cast(p);
			p.setPisado(true);
			tablero.setPosicion(p);
		}
	}*/
}
