import java.awt.FileDialog;
import java.util.ArrayList;

//Gestor interno de la funcionalidad de una sopa de letras
//Se asocia a un fichero y a su sopa de letras asociada y a la interfaz gráfica de control
public class gestorSopas {

	private ventanaGestorSopas ventanaGestor;
	private ArrayList<cFicheroSopaLetras> sopas;

	public gestorSopas() {
		sopas=new ArrayList<cFicheroSopaLetras>();
	}
	public gestorSopas(ventanaGestorSopas ventanaGestor){
		this();
		setVentanaGestor(ventanaGestor);
	}
	public void setVentanaGestor(ventanaGestorSopas ventanaGestor)  {
		this.ventanaGestor = ventanaGestor;
	}
	public cFicheroSopaLetras getSopa(int i) {
		return sopas.get(i);
	}
	public ArrayList<cFicheroSopaLetras> getContenido() {
		return sopas;
	}
	public void addSopa(cFicheroSopaLetras sopa) {
		sopas.add(sopa);
		ventanaGestor.bind(sopas);
	}
	public void cambiarSopa(int i, cFicheroSopaLetras sopa){
		sopas.set(i, sopa);
		ventanaGestor.bind(sopas);
	}
	public void borrarSopa(int i){
		sopas.remove(i);
		ventanaGestor.bind(sopas);
	}
	public void cargarSopa(){
    	try {
            FileDialog cargarFichero = new FileDialog(ventanaGestor,"c\\");
            cargarFichero.setVisible(true);
            cFicheroSopaLetras nuevo = new cFicheroSopaLetras(cargarFichero.getDirectory()+cargarFichero.getFile());
            nuevo.cargarFicheroASopa();
			addSopa(nuevo);
    	}
    	catch(Exception ex){
    		System.out.println(ex);
    	}
	}
	public void guardarSopa(int sel){
		try {
			FileDialog dialogGuardar = new FileDialog(ventanaGestor,"C:\\");
			dialogGuardar.setMode(1);
			dialogGuardar.setVisible(true);
			guardarSopa(dialogGuardar.getDirectory()+dialogGuardar.getFile(),sel);
		}
		catch (Exception ex){
			
		}
	}
	private void guardarSopa(String ruta, int sel){
		cFicheroSopaLetras fl = getSopa(sel);
		fl.setFichero(ruta);
		fl.cargarSopaAFichero();
	}
}
