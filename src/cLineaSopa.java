//Clase que implementa el comportamiento de un objeto que porta información de una línea de fichero
//de un fichero de una sopa de letras
public class cLineaSopa extends cLinea {
	
	public cLineaSopa(String linea){
		setTipo(linea.substring(0,1));
		setContenido(linea.substring(2));
	}
	public void setTipo(String tipo) {
		setTipo(Integer.parseInt(tipo));
	}
	public String getLetra(){
		return contenido.split(",")[2];
	}
	public String getPalabra(){
		return contenido.split(",")[0];
	}
	public Boolean getValor(){
		if(contenido.split(",")[1]=="true")return true;
		return false;
	}
	public Boolean getMarcado(){
		if(contenido.split(",")[3].equals("true"))return true;
		return false;
	}
	public String getNombreSopa(){
		return getContenido();
	}
}
