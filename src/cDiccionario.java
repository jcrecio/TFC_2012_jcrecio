import java.util.Enumeration;
import java.util.Hashtable;


// Clase que implementa el diccionario de palabras-solución de una sopa de letras
public class cDiccionario {
	
	private Hashtable<String,Boolean> elementos;
	
	public cDiccionario() {
		elementos = new Hashtable<String,Boolean>();
	}
	public cDiccionario(String[] palabras){
		this();
		for(int i=0;i<palabras.length;i++){
			setElemento(palabras[i],false);
		}
	}
	public cDiccionario(cDiccionario diccionario){
		elementos = diccionario.getElementos();
	}
	public Boolean getElemento(String clave) {
		return elementos.get(clave);
	}
	public int getNumElementos(){
		return elementos.size();
	}
	public String getClave(int pos){
		Enumeration<String> claves = getClaves();
		int i=0;
		while(claves.hasMoreElements()){
			if((i==pos)&&claves.nextElement() !=null) return (String)claves.nextElement();
		}
		return null;
	}
	public Enumeration<String> getClaves(){
		return elementos.keys();
	}
	public void setElemento(String clave){
		setElemento(clave,false);
	}
	public void setElemento(String clave, Boolean valor){
		elementos.put(clave, valor);
	}
	public void quitarElemento(String clave){
		elementos.remove(clave);
	}
	public Hashtable<String,Boolean> getElementos(){
		return elementos;
	}
	public Boolean existeClave(String clave){
		if(elementos.get(clave)==null)return false;
		return true;
	}
}
