import java.util.Enumeration;


public class cSopaLetras {
	
	private cTableroSopa tablero;
	private cDiccionario diccionario;
	private String nombre;
	private Boolean pendiente;
	
	public cSopaLetras(String nombre, int x, int y){
		this(nombre,x,y,0);
	}
	
	public cSopaLetras(cTableroSopa tablero, cDiccionario diccionario){
		this(tablero,diccionario,"Sin titulo_"+System.currentTimeMillis());
	}
	public cSopaLetras(cTableroSopa tablero, cDiccionario diccionario, String nombre){
		setNombre(nombre);
		setTablero(tablero);
		setDiccionario(diccionario);
	}
	public cSopaLetras(String nombre, int x, int y, int num){
		tablero = new cTableroSopa(x,y);
		cParametrizadorSopa parametrizador = new cParametrizadorSopa(tablero);
		parametrizador.generarContenido(num);
		if((nombre==null)||(nombre==""))nombre= "Sopa_"+parametrizador.generarPalabra();
		setNombre(nombre);
		setTablero(parametrizador.getTablero());
		setDiccionario(parametrizador.getDiccionario());
		setPendiente(false);
	}
	public cTableroSopa getTablero() {
		return tablero;
	}
	public void setTablero(cTableroSopa tablero) {
		this.tablero = tablero;
	}
	public cDiccionario getDiccionario() {
		return diccionario;
	}
	public void setDiccionario(cDiccionario diccionario) {
		this.diccionario = diccionario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getPendiente() {
		return pendiente;
	}
	public void setPendiente(Boolean pendiente) {
		this.pendiente = pendiente;
	}
	public void marcar() {
		Enumeration<String> claves = diccionario.getClaves();
		String elemClave="";
		try {
			while(claves.hasMoreElements()){
				while((elemClave = claves.nextElement()) !=null){
					int l = elemClave.length();
					Object[] x = buscar(elemClave);
					if((Boolean)x[0]==true){
						diccionario.setElemento(elemClave,true);
						cPosicionSopa p = tablero.getPosicion((int)x[1],(int)x[2]);
						for(int i=0;i<l;i++){
							tablero.getPosicion(p).setMarcado(true);
							p = ((cMovimiento) x[3]).cast(p);
						}
					}
				}
			}
		}
		catch(Exception ex) {
			
		}
	}
	
	public Object[] buscar(String palabra){
		palabra = palabra.toUpperCase();
		int longitudPalabra = palabra.length();
		if(longitudPalabra==1)buscar(palabra,1);
		else {
		String letraPrimera = palabra.substring(0,1);
			String segundaLetra = palabra.substring(1,2);
			int dimX = tablero.getDimX(), dimY = tablero.getDimY();
			if(!(longitudPalabra>dimX)&&!(longitudPalabra>dimY)){
				for(int i=0;i<dimX;i++){
					for(int j=0;j<dimY;j++){
						cPosicionSopa pos = (cPosicionSopa)tablero.getPosicion(i,j);
						pos.setMarcado(false);
						if(tablero.getPosicion(i,j).getLetra().equals(letraPrimera)){
							for(int m=0;m<8;m++){
								cMovimiento mov = new cMovimiento(m);
								cPosicionSopa posAdy = mov.cast(pos);
								if(!tablero.coordenadaFueraRango(posAdy)) {
									posAdy = tablero.getPosicion((cPosicionSopa)mov.cast(pos));
									if(posAdy.getLetra().equals(segundaLetra)){
										Boolean seguir=true;
										int c=2;
										while((c<longitudPalabra)&&(seguir)){
											posAdy = (cPosicionSopa)mov.cast(posAdy);
											if(!tablero.coordenadaFueraRango(posAdy)) {
												posAdy = tablero.getPosicion(posAdy);
												if(!posAdy.getLetra().equals(palabra.substring(c,c+1)))
													seguir=false;
												c++;
											}
											else if(c<longitudPalabra) seguir=false;
										}
										if((c==longitudPalabra)&&(seguir)){
											Object[] v = new Object[4];
											v[0]=true;
											v[1]=i;
											v[2]=j;
											v[3]=mov;
											return v;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		Object[] v = new Object[4];
		v[0]=false;
		v[3]=null;
		return v;
	}
	public Object[] buscar(String palabra, int longitud){
		return null;
	}
	
	public void pintarSopa(cVentanaSopa v) {
		v.paint(v.getGraphics(),false);
	}	
	public void pintarSopa(cVentanaSopa v, Boolean sol) {
		v.paint(v.getGraphics(),sol);
	}
}