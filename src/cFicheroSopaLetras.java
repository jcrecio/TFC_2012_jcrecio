import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

//Clase que enlaza un fichero con una sopa de letras para intercambiar contenido
public class cFicheroSopaLetras extends cFichero{

	private cSopaLetras sopaLetras;
	
	public cFicheroSopaLetras(String fichero){
		setFichero(fichero);
	}
	public cFicheroSopaLetras(cSopaLetras sopaLetras){
		setSopaLetras(sopaLetras);
	}

	public cSopaLetras getSopaLetras() {
		return sopaLetras;
	}
	public void setSopaLetras(cSopaLetras sopaLetras) {
		this.sopaLetras = sopaLetras;
	}
	public void cargarSopaAFichero(){
		try {
			String lineaPosicion="";
			cTableroSopa t = sopaLetras.getTablero();
			int dimX = t.getDimX(), dimY = t.getDimY();
			FileWriter escritorFichero=new FileWriter(getFichero());
			BufferedWriter bufferFichero=new BufferedWriter(escritorFichero);
			bufferFichero.write("8 " + sopaLetras.getNombre()+"\r\n"); // 2 nombre
			bufferFichero.write("9 "+t.getDimX()+","+t.getDimY()+"\r\n"); // 3 tamaño x,y
			for(int i=0;i<dimX;i++){
				for(int j=0;j<dimY;j++){
					cPosicionSopa p = t.getPosicion(i,j);
					lineaPosicion="0 "+i+","+j+","+p.getLetra()+","+p.getMarcado()+"\r\n"; // 0 x,y,<letra>
					bufferFichero.write(lineaPosicion);
				}
			}
			cDiccionario dic = sopaLetras.getDiccionario();
			Enumeration<String> claves = dic.getClaves();
			while(claves.hasMoreElements()){
				String clave = claves.nextElement();
				Boolean valor = dic.getElemento(clave);
				lineaPosicion = "1 "+clave+","+valor+"\r\n"; // 1 palabra,true/false
				bufferFichero.write(lineaPosicion);
			}
			bufferFichero.close();
			escritorFichero.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void cargarFicheroASopa(){
		String nombre="";
		cTableroSopa tablero=null;
		cDiccionario diccionario=new cDiccionario();
		FileReader lectorFichero;
		try {
			String linea;
			lectorFichero = new FileReader(getFichero());
			BufferedReader bufferFichero = new BufferedReader(lectorFichero);
			while ((linea = bufferFichero.readLine())!=null) {
				   cLineaSopa lineaLeida = new cLineaSopa(linea);
				   int t = lineaLeida.getTipo();
				   switch(t){
				   case 0:
					   String letra = lineaLeida.getLetra();
					   cPosicionSopa posicionNueva = new cPosicionSopa(lineaLeida.getX(),lineaLeida.getY(),letra);
					   posicionNueva.setMarcado(lineaLeida.getMarcado());
					   tablero.setPosicion(posicionNueva.replicar());
					   break;
				   case 1:
					   diccionario.setElemento(lineaLeida.getPalabra(),lineaLeida.getValor());
					   break;
				   case 8:
					   nombre=lineaLeida.getNombreSopa();
					   break;
				   case 9:
					   tablero=new cTableroSopa(lineaLeida.getDimX(),lineaLeida.getDimY());
					   break;
				   }
		}
		sopaLetras = new cSopaLetras(tablero,diccionario);
		sopaLetras.setNombre(nombre);
		bufferFichero.close();
		lectorFichero.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
