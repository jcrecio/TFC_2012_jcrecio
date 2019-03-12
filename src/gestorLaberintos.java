import java.awt.FileDialog;
import java.io.IOException;
import java.util.ArrayList;

// Gestor interno de la funcionalidad de un laberinto
// Se asocia a un fichero y su laberinto asociado y a la interfaz gráfica de control
public class gestorLaberintos {
	
	private ventanaGestorLaberintos ventanaGestor; // Asociación con el entorno gráfico
	private ArrayList<cFicheroLaberinto> laberintos;

	public gestorLaberintos() {
		laberintos=new ArrayList<cFicheroLaberinto>();
	}
	public gestorLaberintos(ventanaGestorLaberintos ventanaGestor){
		this();
		setVentanaGestor(ventanaGestor);
	}
	public void setVentanaGestor(ventanaGestorLaberintos ventanaGestor)  {
		this.ventanaGestor = ventanaGestor;
	}
	public cFicheroLaberinto getLaberinto(int i) {
		return laberintos.get(i);
	}
	public ArrayList<cFicheroLaberinto> getContenido() {
		return laberintos;
	}
	public void addLaberinto(cFicheroLaberinto laberinto) {
		laberintos.add(laberinto);
		ventanaGestor.bind(laberintos);
	}
	public void cambiarLaberinto(int i, cFicheroLaberinto laberinto){
		laberintos.set(i, laberinto);
		ventanaGestor.bind(laberintos);
	}
	public void borrarLaberinto(int i){
		laberintos.remove(i);
		ventanaGestor.bind(laberintos);
	}
	public void cargarLaberinto(){
    	try {
            FileDialog cargarFichero = new FileDialog(ventanaGestor,"c\\");
            cargarFichero.setVisible(true);
            cFicheroLaberinto nuevo = new cFicheroLaberinto(cargarFichero.getDirectory()+cargarFichero.getFile());
            try {
				nuevo.cargarFicheroEnLaberinto();
				addLaberinto(nuevo);
			} catch (IOException e1) {
			}
    	}
    	catch(Exception ex){
    		System.out.println(ex);
    	}
	}
	public void guardarLaberinto(int sel){
		try {
			FileDialog dialogGuardar = new FileDialog(ventanaGestor,"C:\\");
			dialogGuardar.setMode(1);
			dialogGuardar.setVisible(true);
			guardarLaberinto(dialogGuardar.getDirectory()+dialogGuardar.getFile(),sel);
		}
		catch (Exception ex){
			
		}
	}
	private void guardarLaberinto(String ruta, int sel){
		cFicheroLaberinto fl = getLaberinto(sel);
		fl.setFichero(ruta);
		fl.cargarLaberintoEnFichero();
	}
	public cLaberinto resolverLaberinto(int sel){
		cVentanaCarga vResolver=null;
    	cLaberinto nuevoLab = getLaberinto(sel).getLaberinto();
		try {
			vResolver = new cVentanaCarga(nuevoLab,ventanaGestor);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	nuevoLab.asociarCarga(vResolver);
		hResolverLaberinto hiloResolucion = new hResolverLaberinto(nuevoLab,this.ventanaGestor, vResolver);
		Thread hiloResolucionLaberinto = new Thread(hiloResolucion,"hilo");
		hiloResolucionLaberinto.start();
		/*nuevoLab.resolver();
		vResolver.cerrar();*/
		return nuevoLab;
	}
}
