import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Ventana de búsqueda de las soluciones de un laberinto
public class cVentanaCarga extends JDialog {
 
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")

        private boolean repintado;
        private String nombreLaberinto;
        private cLaberinto lab;
        private JTextField txtMovimientos;
        private JLabel labelMovimientos,labelSoluciones;
        final private JLabel soluciones = new JLabel();
        private JLabel titulo;
    	private JPanel panel;
    	private JButton botonParar;
    	private ventanaGestorLaberintos v;
    	Image image2;
 
        public cVentanaCarga(cLaberinto l, ventanaGestorLaberintos v) throws Exception {
        		this.setV(v);
        		lab = l;
        		setNombreLaberinto(lab.getNombre());
        		repintado = false;
        		titulo = new JLabel("Buscando en "+ nombreLaberinto);
        		labelSoluciones = new JLabel("Soluciones encontradas:");
        		soluciones.setText("0");
        		txtMovimientos = new JTextField(15);
        		txtMovimientos.setEditable(false);
                labelMovimientos = new JLabel("Movimientos:");
                botonParar = new JButton("PARAR");
        		panel = new JPanel();
        		panel.setBackground(new Color(Integer.parseInt("66bbff", 16)));
        		panel.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
        		panel.add(titulo);
        		panel.add(labelMovimientos);
        		panel.add(txtMovimientos);
        		panel.add(botonParar);
        		panel.add(labelSoluciones);
        		panel.add(soluciones);
        		this.add(panel);
        		botonParar.addMouseListener(new MouseListener(){
					@Override
					public void mouseClicked(MouseEvent arg0) {
						lab.parar();
						soluciones.setText(Integer.toString(lab.getSoluciones().getNumCaminos()));
						panel.add(new JLabel("Tiempo de búsqueda: "+ Long.toString(lab.getTiempoHastaMejorSolucion())+" mS"));
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {

					}

					@Override
					public void mouseExited(MouseEvent arg0) {

					}

					@Override
					public void mousePressed(MouseEvent arg0) {

					}

					@Override
					public void mouseReleased(MouseEvent arg0) {

					}
        			
        		});
        		class WindowAdapterExtendido extends WindowAdapter {
        			private ventanaGestorLaberintos v;
        			
        			public WindowAdapterExtendido(ventanaGestorLaberintos v){
        				setV(v);
        			}
					public ventanaGestorLaberintos getV() {
						return v;
					}

					public void setV(ventanaGestorLaberintos v) {
						this.v = v;
					}
        		}
        		this.addWindowListener(new WindowAdapterExtendido(v) {
        			public void windowClosing(WindowEvent e)
        		  {
        		    this.getV().getBtnResolver().setEnabled(true);
        		  }
        		});
        		setSize(new Dimension(300,170));
                setVisible(true);

        }
        public void escribirMovimiento(int mov){
        	txtMovimientos.setText(Integer.toString(mov));
        }
        public void escribirSolucionesDesdeFuera(int s){
        	soluciones.setText(Integer.toString(s));
        }
		public String getNombreLaberinto() {
			return nombreLaberinto;
		}
		public void setNombreLaberinto(String nombreLaberinto) {
			this.nombreLaberinto = nombreLaberinto;
		}
		public cLaberinto getLab() {
			return lab;
		}
		public void setLab(cLaberinto lab) {
			this.lab = lab;
		}
		public void cerrar(){
			setVisible(false);
			dispose();
		}
		public ventanaGestorLaberintos getV() {
			return v;
		}
		public void setV(ventanaGestorLaberintos v) {
			this.v = v;
		}
}