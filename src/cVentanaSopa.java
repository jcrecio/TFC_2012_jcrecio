import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//Ventana de todas las operaciones de una sopa de letras (ver, editar,..)
public class cVentanaSopa extends Canvas{
	
	private static final long serialVersionUID = 1L;
    private static final int TAMANIO_CELDA = 25;
    private static final int TAMANIO_PALABRA = 15;
    private Boolean _ESTADO;
    private Boolean _MODO;
    private Boolean _HAND;
    private int longitud,altura;
    private cSopaLetras sopa;
    private cPosicionSopa tempBold;
    JFrame ventana;
    
    public cVentanaSopa(cSopaLetras sopa) {
    	setSopa(sopa);
        ventana = new JFrame("TFC - Sopa de letras"); 
        JPanel panel = (JPanel)ventana.getContentPane(); 
        cTableroSopa t = sopa.getTablero();
        longitud=t.getDimY()*TAMANIO_CELDA+50;
        altura=t.getDimX()*TAMANIO_CELDA+60;
        setBounds(0,0,longitud,altura); 
        panel.setPreferredSize(new Dimension(longitud,altura)); 
        panel.setLayout(null); 
        panel.add(this);
        ventana.setBounds(0,0,longitud,altura); 
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _HAND=true;
    }
    public cVentanaSopa(cSopaLetras sopa, Boolean modo){
    	this(sopa);
    	_MODO=modo;
    	manejadorContenidoTablero();
    }
    public void paint(Graphics g) {
  	  dibujarTablero(g);
  	  if(_MODO!=null)if(!_MODO)dibujarPalabras(g);
  	  if(!_HAND){
  		_HAND=true;
  		this.addMouseListener(new MouseAdapterExtendido(g) {
  			public void mousePressed(MouseEvent evt) {
  				cTableroSopa t = sopa.getTablero();
  				int i = evt.getX();
  				int j = evt.getY();
  				int pI = (i-12)/TAMANIO_CELDA;
  				int LI = longitud/TAMANIO_CELDA;
  				int pJ = (j-12)/TAMANIO_CELDA;
  				int LJ = altura/TAMANIO_CELDA;
  				if((pI<=LI)&&(pJ<=LJ)){
  					cPosicionSopa p = t.getPosicion(pJ,pI);
  					String opcion = JOptionPane.showInputDialog("Posición: "+pI+","+pJ+" - Contenido: "+p.getLetra().toUpperCase()+"\nNueva letra a insertar en esta posición\n",p.getLetra().toUpperCase());
  					if((opcion!=null)&&(opcion!="")&&(opcion.length()<=1)){
  						p.setLetra(opcion);
  						dibujarTablero(this.getGraphics());
  						dibujarPalabras(this.getGraphics());
  						sopa.setPendiente(true);
  						tempBold = new cPosicionSopa(pI,pJ,"",true);
  						paint(getGraphics());
  					}
  					else {
  						JOptionPane.showMessageDialog(null, "El valor introducido no es válido","Error en la sopa de letras", JOptionPane.INFORMATION_MESSAGE);
  					}
  				}
  			}});
  	  	}
    }
    public void paint(Graphics g, Boolean sol) {
        _ESTADO = sol;
        paint(g);
    }
    public void dibujarTablero(Graphics g){
          cTableroSopa tabAux=sopa.getTablero();
          Boolean bold;
          if(tempBold != null) bold = tempBold.getMarcado();
          else bold = false;
		  cAleatorio a = new cAleatorio();
		  a.generaEntero(255);
		  int r = a.getContenido();
		  a.generaEntero(255);
		  int g_ = a.getContenido();
		  a.generaEntero(255);
		  int b = a.getContenido();
    	  int x = tabAux.getDimX(), y = tabAux.getDimY();
    	  for(int i=0;i<x;i++){
    		  for(int j=0;j<y;j++){
    			  cPosicionSopa p = tabAux.getPosicion(i, j);
    			  Color c;
    			  boolean marca = p.getMarcado();
 			  		 if((_ESTADO == true)&&(marca)) {
 			  			 	c = new Color(r,g_,b);
  	          				g.setColor(c);
  	          				g.fillRect(j*TAMANIO_CELDA+12,i*TAMANIO_CELDA+12, TAMANIO_CELDA-2, TAMANIO_CELDA-2);
            			  }
          			  else {
          			  	if(bold){
          			  		if((tempBold.getX()==p.getY())&&(tempBold.getY()==p.getX())){
        	          			c = new Color(1,240,1);
        	          			g.setColor(c);
        	                	g.fillRect(j*TAMANIO_CELDA+12,i*TAMANIO_CELDA+12, TAMANIO_CELDA-2, TAMANIO_CELDA-2);
        		  			  	bold = false;
        		  			  	tempBold.setPisado(false);
          			  		}

          			  	else {
            				c = new Color(175,175,255);
                			g.setColor(c);
                			g.fillRect(j*TAMANIO_CELDA+12,i*TAMANIO_CELDA+12, TAMANIO_CELDA-2, TAMANIO_CELDA-2);
          			  	}
          			  	}
          			  	else {
            				c = new Color(175,175,255);
                			g.setColor(c);
                			g.fillRect(j*TAMANIO_CELDA+12,i*TAMANIO_CELDA+12, TAMANIO_CELDA-2, TAMANIO_CELDA-2);
          			  	}
          			  }
    			 
	    		g.setColor(Color.BLACK);

  			  	g.drawString(p.getLetra().toUpperCase(),j*TAMANIO_CELDA+21,i*TAMANIO_CELDA+28);
	    		g.drawRect(j*TAMANIO_CELDA+12,i*TAMANIO_CELDA+12, 1, TAMANIO_CELDA);
	      		g.drawRect(j*TAMANIO_CELDA+12,i*TAMANIO_CELDA+12, TAMANIO_CELDA, 1);
    		  }
    	  }
    	  g.setColor(new Color(1,1,1));
    	  g.drawRect(12,12, y*TAMANIO_CELDA, x*TAMANIO_CELDA);
    }
    
    public void dibujarPalabras(Graphics g){
    	cDiccionario d = sopa.getDiccionario();
    	Enumeration<String> elementos = d.getClaves();
    	int y = sopa.getTablero().getDimY();
    	int i = 0;
    	while(elementos.hasMoreElements()){
        	g.drawString(elementos.nextElement(),1,i*TAMANIO_PALABRA+y*TAMANIO_CELDA);
      		i++;
    	}
    }
    
	public cSopaLetras getSopa() {
		return sopa;
	}

	public JFrame getFrame() {
		return ventana;
	}

	public void setSopa(cSopaLetras sopa) {
		this.sopa = sopa;
	}
	
	public void manejadorContenidoTablero(){
		_HAND=false;
	}
    
}
