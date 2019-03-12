import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;


public class cAsistenteSopaPrincipal extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private Dimension maxDimension;
	private boton btnTablero;
	private boton btnDiccionario;
	private cSopaLetras sopa;
	
	
	public cAsistenteSopaPrincipal(cSopaLetras sopa){
		this.sopa = sopa;
		maxDimension = new Dimension();
		maxDimension.setSize(500,500);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		panel.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		btnTablero = new boton("EDITAR TABLERO");
		btnTablero.addActionListener(this);
		panel.add(btnTablero);
		btnDiccionario = new boton("EDITAR PALABRAS DE BÚSQUEDA");
		btnDiccionario.addActionListener(this);
		panel.add(btnDiccionario);
		this.add(panel);
		this.setVisible(true);
		this.setMaximumSize(maxDimension);
		this.setBounds(400, 200, 500, 80);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	Object origen = e.getSource();
    	if(origen == btnTablero){
    		cVentanaSopa v = new cVentanaSopa(sopa, true);
        	sopa.pintarSopa(v, false);
    	}
    	else if(origen == btnDiccionario){
    		new cAsistenteSopa(sopa.getNombre(),sopa.getDiccionario(),sopa);
    	}
    	cerrar();
	}
	private void cerrar(){
    	this.setVisible(false);
    	this.dispose();
	}
}
