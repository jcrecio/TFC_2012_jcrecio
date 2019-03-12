import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Clase que enlaza un fichero con un laberinto para intercambiar contenido
public class cFicheroLaberinto extends cFichero {
	
	private cLaberinto laberinto;
	
	public cFicheroLaberinto(String fichero){
		setFichero(fichero);
	}
	public cFicheroLaberinto(cLaberinto laberinto){
		setLaberinto(laberinto);
	}
	public void setLaberinto(cLaberinto laberinto){
		this.laberinto = laberinto;
	}
	public cLaberinto getLaberinto(){
		return laberinto;
	}

	public void cargarFicheroEnLaberinto() throws IOException {
		String nombre="";
		cTableroLaberinto tablero=null;
		cPosicionLaberinto posJugador=null;
		cPosicionLaberinto[] posSalidas=null;
		cMovimiento[] movDisponibles=null;
		espacioSoluciones soluciones=new espacioSoluciones();
		int numObstaculos=0;
		FileReader lectorFichero;
		try {
			String linea;
			lectorFichero = new FileReader(getFichero());
			BufferedReader bufferFichero = new BufferedReader(lectorFichero);
			while ((linea = bufferFichero.readLine())!=null) {
				   cLineaLaberinto lineaLeida = new cLineaLaberinto(linea);
				   int t = lineaLeida.getTipo();
				   switch(t){
				   case 0:
					   int contenidoPosicion = lineaLeida.getContenidoPosicion();
					   cPosicionLaberinto posicionNueva = new cPosicionLaberinto(lineaLeida.getX(),lineaLeida.getY(),contenidoPosicion,lineaLeida.getPisado());
					   tablero.setPosicion(posicionNueva.replicar());

					   if(contenidoPosicion==cPosicion._JUGADOR)posJugador=posicionNueva.replicar();
					   else 
						   if(contenidoPosicion==cPosicion._SALIDA){
						   if(posSalidas==null){
							   posSalidas=new cPosicionLaberinto[1];
							   posSalidas[0]=posicionNueva.replicar();
						   }
						   else {
							   cVector.unirPosiciones(posicionNueva.replicar(),posSalidas);						   }
					   		}
					   if(contenidoPosicion==cPosicion._OBSTACULO)numObstaculos++;
					   break;
				   case 1:
					   soluciones.addCamino(lineaLeida.getSolucion());
					   break;
				   case 2:
					   movDisponibles=lineaLeida.getMovimientos();
					   break;
				   case 3:
					   tablero=new cTableroLaberinto(lineaLeida.getDimX(),lineaLeida.getDimY());
					   break;
				   case 9:
					   nombre=lineaLeida.getNombreLaberinto();
					   break;
				   }
				}
			if(soluciones.getNumCaminos()==0)laberinto=new cLaberinto(tablero,posJugador,posJugador,posSalidas,numObstaculos,movDisponibles);
			else laberinto=new cLaberinto(tablero,posJugador,posJugador,posSalidas,numObstaculos,movDisponibles,soluciones);
			if(nombre!="")laberinto.setNombre(nombre);
			bufferFichero.close();
			lectorFichero.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void cargarLaberintoEnFichero() {
		try {
			String lineaPosicion="";
			cTableroLaberinto t = laberinto.getTablero();
			cMovimiento[] m = laberinto.getMovimientosDisponibles();
			int dimX = t.getDimX(), dimY = t.getDimY();
			FileWriter escritorFichero=new FileWriter(getFichero());
			BufferedWriter bufferFichero=new BufferedWriter(escritorFichero);
			bufferFichero.write("9 "+laberinto.getNombre()+"\r\n");
			bufferFichero.write("3 "+t.getDimX()+","+t.getDimY()+"\r\n");
			for(int i=0;i<dimX;i++){
				for(int j=1;j<dimY;j++){
					lineaPosicion="0 "+i+","+j+","+t.getPosicion(i,j).getContenido()+",0\r\n";
					bufferFichero.write(lineaPosicion);
				}
			}
			lineaPosicion="2 ";
			int nm = m.length;
			for(int i=0;i<nm-1;i++){
				lineaPosicion+=m[i].getDireccion()+",";
			}
			if(nm!=0) {
				lineaPosicion+=m[nm-1].getDireccion();
				bufferFichero.write(lineaPosicion+"\r\n");
			}
			espacioSoluciones sols = laberinto.getSoluciones();
			for(int i=0;i<sols.getNumCaminos();i++){
				lineaPosicion="1 ";
				int n = sols.getCamino(i).getNumMovimientos();
				for(int j=0;j<n-1;j++){
					lineaPosicion+=sols.getCamino(i).getPosicion(j).getX()+","+sols.getCamino(i).getPosicion(j).getY()+";";
				}
				if(n!=0)lineaPosicion+=sols.getCamino(i).getPosicion(n-1).getX()+","+sols.getCamino(i).getPosicion(n-1).getY()+"\r\n";
				bufferFichero.write(lineaPosicion);
			}
			bufferFichero.close();
			escritorFichero.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}