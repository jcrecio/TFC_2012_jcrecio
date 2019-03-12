import java.awt.Canvas; 
import java.awt.Color;
import java.awt.Dimension; 
import java.awt.Font;
import java.awt.Graphics; 
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame; 
import javax.swing.JPanel;
// Ventana de todas las operaciones de un laberinto (ver, editar,..)
public class cVentanaLaberinto extends Canvas {
	
	private static final long serialVersionUID = 1L;
    private static final int NOPULS=-1;
    private int id_ventana;
    private int longitud,altura;
    private cLaberinto lab;
    private int ls;
    private String modo;
    private int tamCasilla;
    private JFrame ventana;
    private Boolean mZoom;
    public cVentanaLaberinto(cLaberinto lab) {
    	ls=NOPULS;
    	mZoom = true;
    	setLab(lab);
        ventana = new JFrame("TFC - Laberinto"); 
        JPanel panel = (JPanel)ventana.getContentPane(); 
        cTableroLaberinto t = lab.getTablero();
        int tamX = t.getDimX();
        int tamY = t.getDimY();
        if((tamX>=40)||(tamY>=40)){
        	if((tamX>=130)||(tamY>=130))tamCasilla=5;
        	else {
	        	if((tamX>=90)||(tamY>=90))tamCasilla=7;
	        	else tamCasilla=10;
        	}
        }
        else tamCasilla=25;
        longitud=(tamY*tamCasilla)+(tamCasilla*5);
        altura=(tamX*tamCasilla)+(tamCasilla*5)+(tamCasilla/5);
        setBounds(0,0,longitud,altura); 
        panel.setPreferredSize(new Dimension(longitud,altura)); 
        panel.setLayout(null); 
        panel.add(this);
        ventana.setBounds(0,0,longitud,altura+40); 
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
      }
    public cVentanaLaberinto(cLaberinto lab, String modo){
    	this(lab);
    	setModo(modo);
    }
    public void paint(Graphics g) {
    	  dibujarTablero(g);
    	  dibujarMovimientosDisponibles(g);
    	  dibujarControlesZoom(g);
    	  if(ls!=-1)paint(g,ls);
    	  ls=NOPULS;
    	  if(modo=="lectura"){
    		  this.addMouseListener(new MouseAdapter(){
    	    		 public void mousePressed(MouseEvent evt) {
    	    			 int x = evt.getX(), y = evt.getY();
 	    				if((x<=20)&&(y<=20)){
 	    					if(tamCasilla<50) {
 	    						if(mZoom){
 	    							tamCasilla+=1;
 	    							paint(getGraphics());
 	    							mZoom = false;
 	    						}
 	    					}
 	    				}
 	    				else {
 	    					if( (x>20)&&(x<=20*2)&&(y<=20)){
 	    						if(tamCasilla>5){
 	    							if(mZoom){
 	    								tamCasilla-=1;
 	    								paint(getGraphics());
 	    								mZoom = false;
 	    							}
 	    						}
 	    					}
 	    				}
    			  }
    		  });

    	  }
    	  if(modo!="lectura"){
    		  this.addMouseListener(new MouseAdapter(){
    			  public void mouseReleased(MouseEvent e) {
    				  mZoom = true;
    			  }
    		  });
    		  this.addMouseListener(new MouseAdapterExtendido(g) {
    			
    			public void mousePressed(MouseEvent evt) {
    				try {
    	        	if(modo == "caminos"){
    	        		if (!evt.isMetaDown())  {
    	        			Boolean zoom = false;
    	    				cTableroLaberinto t = lab.getTablero();
    	    				int x = evt.getX(), y = evt.getY();
    	    				if((x<=20)&&(y<=20)){
    	    					zoom=true;
    	    					if(tamCasilla<50) {
    	    						if(mZoom){
    	    							tamCasilla+=1;
    	    							paint(getGraphics());
    	    							mZoom = false;
    	    						}
    	    					}
    	    				}
    	    				else {
    	    					if( (x>20)&&(x<=20*2)&&(y<=20)){
    	    						zoom=true;
    	    						if(tamCasilla>5){
    	    							if(mZoom){
    	    								tamCasilla-=1;
    	    								paint(getGraphics());
    	    								mZoom = false;
    	    							}
    	    						}
    	    					}
    	    					
    	    					else if(!zoom) {
    	    						int pI = getPosDesdeCoord(x);
    	    						int pJ = getPosDesdeCoord(y);
    	    						int LI = longitud/tamCasilla;
    	    						int LJ = altura/tamCasilla;
    	    						if((pI<=LI)&&(pJ<=LJ)){
    	    							cPosicionLaberinto p = t.getPosicion(pJ,pI);
    	    							if((p.getContenido()!=cPosicion._SALIDA)&&(p.getContenido()!=cPosicion._JUGADOR)){
    	    								p.setContenido(cPosicion._OBSTACULO);
    	    								lab.getTablero().setPosicion(p);
    	    								Graphics g = getGraphics();
    	    								g.setColor(new Color(0,0,0));
    	    								g.fillRect(pI*tamCasilla+tamCasilla, pJ*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
    	    								}
    	    							}
    	    						}
    	    					}
    	        			}
    	    				else {
    	    					cTableroLaberinto t = lab.getTablero();
    	    					int pI = getPosDesdeCoord(evt.getX());
    	    					int pJ = getPosDesdeCoord(evt.getY());
    	    					int LI = longitud/tamCasilla;
    	    					int LJ = altura/tamCasilla;
    	    					if((pI<=LI)&&(pJ<=LJ)){
    	    						cPosicionLaberinto p = t.getPosicion(pJ,pI);
    	    						if((p.getContenido()!=cPosicion._SALIDA)&&(p.getContenido()!=cPosicion._JUGADOR)){
    	    							p.setContenido(cPosicion._VACIO);
    	    							lab.getTablero().setPosicion(p);
    	    							Graphics g = getGraphics();
    	    							g.setColor(new Color(255,255,255));
    	    							g.fillRect(pI*tamCasilla+tamCasilla, pJ*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
    	    						}
    	    					}    	        		
    	    				}
    	        		}
    				
    	        	else {
    	        		if (evt.isMetaDown())  {
    	        			
    	        			cTableroLaberinto t = lab.getTablero();
    	    				int pI = getPosDesdeCoord(evt.getX());
    	    				int pJ = getPosDesdeCoord(evt.getY());
    	    				int LI = longitud/tamCasilla;
    	    				int LJ = altura/tamCasilla;
    	    				if((pI<=LI)&&(pJ<=LJ)){
    	    					cPosicionLaberinto p = t.getPosicion(pJ,pI);
    	    					if(p.getContenido()!=cPosicion._ENTRADA){
    		    					cPosicionLaberinto posSalidaAnterior = lab.getPosSalidas()[0];
    		    					posSalidaAnterior.setContenido(cPosicion._VACIO);
    		    					p.setContenido(cPosicion._SALIDA);
    		    					int i_ = posSalidaAnterior.getY();
    		    					int j_ = posSalidaAnterior.getX();
    		    					lab.getTablero().setPosicion(posSalidaAnterior);
    		    					lab.setPosSalida(p, 0);
    		    					lab.getTablero().setPosicion(p);
    		    					Graphics g = getGraphics();
    		    				  	g.setColor(new Color(100,100,255));
    		    				  	g.fillRect(pI*tamCasilla+tamCasilla, pJ*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
    		    				  	g.setColor(new Color(255,255,255));
    		    				  	g.fillRect(i_*tamCasilla+tamCasilla, j_*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
    	    					}
    	    				}  

    	        		}
    	        		else {
    	        			
    	    				cTableroLaberinto t = lab.getTablero();
    	    				int pI = getPosDesdeCoord(evt.getX());
    	    				int pJ = getPosDesdeCoord(evt.getY());
    	    				int LI = longitud/tamCasilla;
    	    				int LJ = altura/tamCasilla;
    	    				if((pI<=LI)&&(pJ<=LJ)){
    	    					cPosicionLaberinto p = t.getPosicion(pJ,pI);
    	    					if(p.getContenido()!=cPosicion._SALIDA){
    		    					p.setContenido(cPosicion._JUGADOR);
    		    					cPosicionLaberinto posEntradaAnterior = lab.getPosEntrada();
    		    					cPosicionLaberinto posJugadorAnterior = lab.getPosJugador();
    		    					posEntradaAnterior.setContenido(cPosicion._VACIO);
    		    					posJugadorAnterior.setContenido(cPosicion._VACIO);
    		    					int i_ = posEntradaAnterior.getY();
    		    					int j_ = posEntradaAnterior.getX();
    		    					lab.getTablero().setPosicion(posEntradaAnterior);
    		    					lab.getTablero().setPosicion(posJugadorAnterior);
    		    					lab.getTablero().setPosicion(p);
    		    					lab.setPosJugador(p);
    		    					lab.setPosEntrada(p);
    		    					Graphics g = getGraphics();
    		    				  	g.setColor(new Color(255,1,1));
    		    				  	g.fillRect(pI*tamCasilla+tamCasilla, pJ*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
    		    				  	g.setColor(new Color(255,255,255));
    		    				  	g.fillRect(i_*tamCasilla+tamCasilla, j_*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
    	    					}
    	    				}                       
    	        		}

    	        }
    		}catch(Exception e){}
    			}});
    		  }
    	  mZoom=true;
    	  }
    public void paint(Graphics g, int i){
    	if(lab.getSoluciones().getNumCaminos()>0){
    		cCamino caminoSolucion = lab.getSoluciones().getCamino(i);
    		int total = caminoSolucion.getNumMovimientos();
    		for(int x=0; x<total;x++){
    			g.setColor(new Color(0,255,0));
    			cPosicionLaberinto pos = caminoSolucion.getPosicion((total-x-1));
    			g.fillRect(tamCasilla+(pos.getY()*tamCasilla+2), tamCasilla+(pos.getX()*tamCasilla+2), tamCasilla-(tamCasilla/5), tamCasilla-(tamCasilla/5));
    			g.setColor(new Color(255,255,255));
    			g.setFont(new Font("SansSerif", Font.BOLD, 10) );
    			g.drawString(""+(total-x), (pos.getY()*tamCasilla+9)+tamCasilla, (pos.getX()*tamCasilla+tamCasilla-(3*tamCasilla/5))+tamCasilla);
    		}
    	}
    	ls=i;
    }
    public void paint(Graphics g, cCamino caminoSolucion){
    		for(int x=0; x<caminoSolucion.getNumMovimientos();x++){
    			Color col = new Color(0,255,0);
    			g.setColor(col);
    			cPosicionLaberinto pos = caminoSolucion.getPosicion(x);
    			g.fillRect(tamCasilla+(pos.getX()*tamCasilla+2), tamCasilla+(pos.getY()*tamCasilla+2), tamCasilla-(tamCasilla/5), tamCasilla-(tamCasilla/5));
    		}
    }
    public void dibujarTablero(Graphics g){
      if(mZoom){
    	  g.setColor(java.awt.Color.white); 
    	  g.fillRect(0,0,getWidth(),getHeight()); 
      }
      cTableroLaberinto tabAux=lab.getTablero();
  	  int x = tabAux.getDimX(), y = tabAux.getDimY();

  	  g.setColor(new Color(0,0,0));
  	  g.fillRect(0, 0, tamCasilla*y+tamCasilla, tamCasilla);
  	  g.fillRect(0, 0, tamCasilla, tamCasilla*x+tamCasilla);
  	  g.fillRect(0, tamCasilla*x+tamCasilla, tamCasilla*y+tamCasilla*2, tamCasilla);
  	  g.fillRect(tamCasilla*y+tamCasilla, 0, tamCasilla, tamCasilla*x+tamCasilla*2);
  	  for(int i=0;i<x;i++){
  		  for(int j=0;j<y;j++){
  			  cPosicionLaberinto p = tabAux.getPosicion(i, j);
  			  if(p.esObstaculo()) {
  				  Color c = new Color(0,0,0);
      			  g.setColor(c);
  				  g.fillRect(j*tamCasilla+tamCasilla, i*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
  			  }
  			  else {
  				  Color c;
  				  boolean pisado = p.getPisado();
  				  int contenido = p.getContenido();
  				  if(contenido==cPosicion._JUGADOR) {
  					  c = new Color(240,1,1);
  				  }
  				  else {
  					  if(contenido==cPosicion._SALIDA) c = new Color(100,100,255);
  					  else {
  						  if(!pisado) c = new Color(215,215,215);
  						  else {
  							  c = new Color(0,255,0);
  						  }
  					  }
  				  }
  				  if(contenido!=cPosicion._VACIO){
  					  g.setColor(c);
  					  g.fillRect(j*tamCasilla+tamCasilla, i*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
  				  }
  				  else if(contenido==cPosicion._VACIO){
  					  c = new Color(255,255,255);
  					  g.setColor(c);
  					  g.fillRect(j*tamCasilla+tamCasilla, i*tamCasilla+tamCasilla, tamCasilla, tamCasilla);
  				  }
  			  }
  		  }
  	  }
  	  g.setColor(new Color(1,1,1));
  	  g.drawRect(tamCasilla,tamCasilla, y*tamCasilla, x*tamCasilla);
    }
    public void dibujarMovimientosDisponibles(Graphics g){
    	Boolean[] movsApp = new Boolean[8];
    	for(int i=0; i<8; i++){
    		movsApp[i]=false;
    	}
    	cMovimiento[] m = lab.getMovimientosDisponibles();
    	for(int i=0; i<m.length; i++){
    		int dir = m[i].getDireccion();
    		movsApp[dir]=true;
    	}
    	for(int i=0;i<8;i++){
    		  String flecha;
    		  switch(i){
    		  case 0:{
    			  flecha="0";
    			  break;
    		  	}
    		  case 1:{
    			  flecha="1";
    			  break;
    		  	}
    		  case 2:{
    			  flecha="2";
    			  break;
    		  	}
    		  case 3:{
    			  flecha="3";
    			  break;
    		  	}
    		  case 4:{
    			  flecha="4";
    			  break;
    		  	}
    		  case 5:{
    			  flecha="5";
    			  break;
    		  	}
    		  case 6:{
    			  flecha="6";
    			  break;
    		  	}
    		  case 7:{
    			  flecha="7";
    			  break;
    		  	}
    		  default:{
    			  flecha="8";
    		  	}
    		  }
			  Color c=null;
			  c = Color.BLUE;
			  g.setColor(c);
			  g.drawRect(i*20+7,getHeight()-18,100,20);
			  if(movsApp[i]) c = new Color(0,155,0);
			  else c = new Color(255,0,0);
  			  g.setColor(c);
  			  g.drawString(flecha,i*20+12,getHeight()-6);
    	}
    }
    public void dibujarControlesZoom(Graphics g){
    	Image imageMas = null;
    	Image imageMenos = null;
    	try {
    		if(modo!="lectura"){
	    		imageMas = ImageIO.read(new FileInputStream("imagenes/masZoom.png"));
	    		imageMenos = ImageIO.read(new FileInputStream("imagenes/menosZoom.png"));
    		}
    	}
    	catch(Exception e){}
    	g.drawImage(imageMas, 0, 0, 20, 20, this);
    	g.drawImage(imageMenos, 20, 0, 20, 20, this);
    }
    public void cambiarCasilla(){
    	
    }
	public cLaberinto getLab() {
		return lab;
	}
	public void setLab(cLaberinto lab) {
		this.lab = lab;
	}
	public cMovimiento[] getMovimientos() {
		return getLab().getMovimientosDisponibles();
	}
	public String getModo() {
		return modo;
	}
	public void setModo(String modo) {
		this.modo = modo;
	}
	private int getPosDesdeCoord(int coord){
		return (coord-1)/tamCasilla - 1;
	}
	public int getId_ventana() {
		return id_ventana;
	}
	public void setId_ventana(int id_ventana) {
		this.id_ventana = id_ventana;
	}
	public JFrame getFrame() {
		return ventana;
	}
}
