import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Interfaz gráfica para establecer parámetros para crear un laberinto
public class cAsistenteLaberinto extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Dimension maxDimension;
	private JLabel textDimensionX,textDimensionX_,textDimensionY,textDimensionY_,textObstaculos;
	private JTextField txtX,txtX_,txtY,txtY_,txtO;
	private JCheckBox dimensionAleatorio,obstaculoAleatorio;
	private JCheckBox[] movs;
	private JCheckBox forzarSolucion;
	private boton botonGenerar;
	private JPanel panel;
	private String nombreLab;
	private ventanaGestorLaberintos refGestor;
	
	public cAsistenteLaberinto(String nombreLab, ventanaGestorLaberintos refGestor){
		this.nombreLab = nombreLab;
		this.refGestor = refGestor;
		maxDimension = new Dimension(1000,500);
		inicializarInterface();
	}
	private void inicializarInterface(){
		panel = new JPanel();
		panel.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		panel.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		dimensionAleatorio = new JCheckBox("Generar laberinto con dimensiones aleatorias");
		dimensionAleatorio.setSelected(true);
		dimensionAleatorio.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		dimensionAleatorio.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		dimensionAleatorio.addActionListener(this);
		panel.add(dimensionAleatorio);
		textDimensionX = new JLabel("Minima dimensión X:");
		panel.add(textDimensionX);
		txtX = new JTextField(5);
		txtX.setText("3");
		txtX.setEnabled(false);
		panel.add(txtX);
		textDimensionX_ = new JLabel("Máxima dimensión X:");
		panel.add(textDimensionX_);
		txtX_ = new JTextField(5);
		txtX_.setText("3");
		txtX_.setEnabled(false);
		panel.add(txtX_);
		textDimensionY = new JLabel("Minima dimensión Y:");
		panel.add(textDimensionY);
		txtY = new JTextField(5);
		txtY.setText("10");
		txtY.setEnabled(false);
		panel.add(txtY);
		textDimensionY_ = new JLabel("Máxima dimensión Y:");
		panel.add(textDimensionY_);
		txtY_ = new JTextField(5);
		txtY_.setText("10");
		txtY_.setEnabled(false);
		panel.add(txtY_);
		obstaculoAleatorio = new JCheckBox("Generar número de obstáculos aleatorio");
		obstaculoAleatorio.setSelected(true);
		obstaculoAleatorio.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		obstaculoAleatorio.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		obstaculoAleatorio.addActionListener(this);
		panel.add(obstaculoAleatorio);
		textObstaculos = new JLabel("Indicar el número de obstáculos:");
		panel.add(textObstaculos);
		txtO = new JTextField(5);
		txtO.setText("35");
		txtO.setEnabled(false);
		panel.add(txtO);
		JLabel textMovimientos = new JLabel("Movimientos disponibles para este laberinto:");
		panel.add(textMovimientos);
		movs = new JCheckBox[8];
		for(int i = 0; i<8; i++){
			movs[i] = new JCheckBox(""+i);
			movs[i].setSelected(true);
			movs[i].setSelected(true);
			movs[i].setBackground(new Color(Integer.parseInt("66bbff", 16)));
			movs[i].setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
			panel.add(movs[i]);
		}
		forzarSolucion = new JCheckBox("Forzar solución");
		forzarSolucion.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		forzarSolucion.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		panel.add(forzarSolucion);
		botonGenerar = new boton("Generar laberinto");
		botonGenerar.addActionListener(this);
		panel.add(botonGenerar);
		this.add(panel);
		this.setVisible(true);
		this.setMaximumSize(maxDimension);
		this.setBounds(400, 200, 300, 300);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object opcion = e.getSource();
		if(opcion==dimensionAleatorio){
			Boolean estado = dimensionAleatorio.isSelected();
			txtX.setEnabled(!estado);
			txtX_.setEnabled(!estado);
			txtY.setEnabled(!estado);
			txtY_.setEnabled(!estado);
		}
		else if(opcion==obstaculoAleatorio){
			Boolean estado = obstaculoAleatorio.isSelected();
			txtO.setEnabled(!estado);
		}
		else if(opcion==botonGenerar){
			Boolean estadoDimension = dimensionAleatorio.isSelected();
			Boolean estadoObstaculo = obstaculoAleatorio.isSelected();
			cTableroLaberinto t = null;
			cMovimiento[] movs_ = null;
			int numObstaculos = 0;
			if(!estadoDimension) {
				try {
					t = cParametrizadorLaberinto.generaTablero(
							Integer.parseInt(txtX.getText()),
							Integer.parseInt(txtX_.getText()),
							Integer.parseInt(txtY.getText()),
							Integer.parseInt(txtY_.getText()));
				}
				catch(Exception ex){
					t = null;
				}
			}
			if(!estadoObstaculo){
				try {
					numObstaculos = Integer.parseInt(txtO.getText());
				}
				catch(Exception ex){
					numObstaculos = 0;
				}
			}
			int numMovimientos = 0;
			for(int i=0;i<8;i++){
				if(movs[i].isSelected())numMovimientos++;
			}
			if(numMovimientos>0){
				movs_ = new cMovimiento[numMovimientos];
				int n = 0;
				for(int i = 0; i<8; i++){
					if(movs[i].isSelected()){
						movs_[n]=new cMovimiento(i);
						n++;
					}
				}
			}
			try {
		    	cFicheroLaberinto nuevo = 
		    			new cFicheroLaberinto(new cLaberinto(nombreLab, t,movs_,numObstaculos));
		    	
		    	if(forzarSolucion.isSelected()){
		    		nuevo.getLaberinto().forzarSolucion();
		    	}
		    	nuevo.getLaberinto().setBloqueado(false);
		    	refGestor.getGestor().addLaberinto(nuevo);
			}
			catch(Exception ex){
				System.out.println();
			}
			this.setVisible(false);
			this.dispose();
		}

	}
}