import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

// Botón personalizado para la aplicación, admite colores, por defecto rojo Google
public class boton extends JButton {
	
	private static final long serialVersionUID = 1L;;
	public boton(String texto, Color c){
		this.setText(texto);
		this.setFont(new Font("Tahoma", Font.BOLD, 9));
		this.setBackground(c);
		this.setForeground(new Color(Integer.parseInt("FFFFFF", 16)));
	}
	public boton(String texto){
		this(texto,new Color(Integer.parseInt("D54937", 16)));
	}
	public void cambiarTamanio(int w, int h){
		this.setSize(w,h);
	}
	public void cambiarTexto(String texto){
		this.setText(texto);
	}
}
