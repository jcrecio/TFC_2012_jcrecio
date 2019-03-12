import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ventanaAyudaLaberinto extends Canvas {
	private static final long serialVersionUID = 6075917614638760616L;
	private String rutaImgAyuda = "imagenes/edicion.png";
	private JFrame ventana;
	public ventanaAyudaLaberinto(){
		this.setBounds(0, 0, 443, 500);
        ventana = new JFrame("Ayuda");
        JPanel panel = (JPanel)ventana.getContentPane(); 
        panel.setPreferredSize(new Dimension(443,500)); 
        panel.setLayout(null); 
        panel.add(this);
        ventana.setSize(new Dimension(443,500));
        ventana.setBounds(0, 0, 443,500);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        paint(getGraphics());
	}
	public void paint(Graphics g) {
          Image imgAyuda = null;
			try {
				imgAyuda = ImageIO.read(new FileInputStream(rutaImgAyuda));
		        g.drawImage(imgAyuda, 0, 0 ,this);
			} catch (Exception e) {
				e.printStackTrace();
			}

  }
	
}
