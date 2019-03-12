import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ventanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String ruta1="imagenes/laberintos.png";
	private static final String ruta2="imagenes/sopas.png";
	private static final String rutaTitulo="imagenes/titulo.png";
	private static final String elige="imagenes/elige.png";
	
	Image image1,image1Over;
	Image image2,image2Over;
	Image imageTitulo;
	Image image3;
	private int stop;
	public ventanaPrincipal(){
		stop = 0;
		try {
			imageTitulo = ImageIO.read(new FileInputStream(rutaTitulo));
			image1 = ImageIO.read(new FileInputStream(ruta1));
			image2 = ImageIO.read(new FileInputStream(ruta2));
			image3 = ImageIO.read(new FileInputStream(elige));
		}
		catch(Exception e){}
        setDefaultCloseOperation(3);
        insertadorImagen canvas = new insertadorImagen(this);
        add(canvas);
        setSize(new Dimension(500,500));
		this.setBounds(400, 200, 510, 330);
        setVisible(true);
	}
	class insertadorImagen extends Canvas {

		private static final long serialVersionUID = 1L;
		ventanaPrincipal v;
		public insertadorImagen(ventanaPrincipal v){
			this.v = v;
		}
        @Override
        public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(imageTitulo, 0, 0,this);
                g.drawImage(image1, 80, 105, 152, 162, this);
                g.drawImage(image2, 265, 105, 152, 162, this);
                g.drawImage(image3, 105, 60, 269, 30, this);

                this.addMouseListener(new MouseAdapterExtendido(g) {
          			public void mousePressed(MouseEvent evt) {
	    				int pX = evt.getX();
	    				int pY = evt.getY();
	    				if((pX>=80)&&(pX<=245)&&(pY>=100)&&(pY<=272)){
	    					if(stop==0)new ventanaGestorLaberintos(new gestorLaberintos());
	    					stop = 1;
	    					v.dispose();
	    				}
	    				if((pX>=265)&&(pX<=419)&&(pY>=100)&&(pY<=272)){
	    					if(stop==0)new ventanaGestorSopas(new gestorSopas());
	    					stop = 1;
	    					v.dispose();
	    				}
          			}

                });
        }
	}
        
}
