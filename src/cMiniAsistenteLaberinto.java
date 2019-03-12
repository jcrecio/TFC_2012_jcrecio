import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
//import javax.swing.JFrame;
import javax.swing.JPanel;

public class cMiniAsistenteLaberinto extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private Dimension maxDimension;
	private boton btnPosiciones;
	private boton btnCaminos;
	private boton btnAyuda;
	private cLaberinto lab;
	
	public cMiniAsistenteLaberinto(cLaberinto lab){
		this.lab = lab;
		maxDimension = new Dimension();
		maxDimension.setSize(500,500);
        //JFrame ventana = new JFrame("TFC - Edición de laberintos"); 
		JPanel panel = new JPanel();
		panel.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		panel.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		btnPosiciones = new boton("EDITAR ENTRADA Y SALIDA");
		btnPosiciones.addActionListener(this);
		panel.add(btnPosiciones);
		btnCaminos = new boton("EDITAR CAMINOS");
		btnCaminos.addActionListener(this);
		panel.add(btnCaminos);
		btnAyuda = new boton("AYUDA");
		btnAyuda.addActionListener(this);
		panel.add(btnAyuda);
		this.add(panel);
		this.setVisible(true);
		this.setMaximumSize(maxDimension);
		this.setBounds(400, 200, 500, 80);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	Object origen = e.getSource();
    	if(origen == btnPosiciones){
        	cVentanaLaberinto v = new cVentanaLaberinto(lab);
        	lab.pintarLaberinto(v);
    	}
    	else if(origen == btnCaminos){
        	cVentanaLaberinto v = new cVentanaLaberinto(lab,"caminos");
        	lab.pintarLaberinto(v);
    	}
    	else if(origen == btnAyuda){
    		new ventanaAyudaLaberinto();
    	}
    	cerrar();
	}
	private void cerrar(){
    	this.setVisible(false);
    	this.dispose();
	}
}
