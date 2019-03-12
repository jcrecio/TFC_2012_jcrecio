import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ventanaGestorSopas extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static int NO_PULSADO = -1;
	cVentanaSopa vAsoc;
	private gestorSopas gestor; // Asociación con el contenido que gestiona
	private Dimension maxDimension;
	private Box contenedor,contenedor2,contenedor3,contenedor4,contenedor5, contenedorTotal;
	private JPanel panel;
	private JPanel panelOpciones;
	@SuppressWarnings("unused")
	private JPanel panelLista,panelLista2,panelSoluciones,panelSoluciones2;
	private JLabel labelSopas,labelMostrarSolucion;
	private DefaultListModel<String> dlm; // Actualizador del enlace datos->control lista
	private boton btnNuevo;
	private boton btnCargar;
	private boton btnGuardar;
	private boton btnGestionar;
	private boton btnBorrar;
	private boton btnSolucionNormal;
	private boton btnVer;
	private boton btnHome;
	private JList<String> listaSopas;
	private JScrollPane scrollSopas;
	@SuppressWarnings("unused")
	private JScrollPane scrollSoluciones;
	@SuppressWarnings("unused")
	private int lp,l_p;
	@SuppressWarnings("unused")
	private MouseListener mL1,mL2;
	
	public ventanaGestorSopas(gestorSopas gestor) {
		maxDimension = new Dimension();
		maxDimension.setSize(800,500);
		dlm = new DefaultListModel<String>();
		this.gestor = gestor;
		gestor.setVentanaGestor(this);
		labelSopas = new JLabel("Sopas de letras");
		setTitle("Gestor de sopas de letras");
		inicializarInterface();
	}
	private void inicializarInterface() {
		contenedor = Box.createVerticalBox();
		contenedor2 = Box.createHorizontalBox();
		contenedor3 = Box.createVerticalBox();
		contenedor4 = Box.createVerticalBox();
		contenedor5 = Box.createHorizontalBox();
		contenedorTotal = Box.createHorizontalBox();
		panel = new JPanel();
		panel.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		panel.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		btnNuevo = new boton("NUEVO");
		btnNuevo.addActionListener(this);
		panel.add(btnNuevo);
		btnCargar = new boton("IMPORTAR");
		btnCargar.addActionListener(this);
		panel.add(btnCargar);
		btnGuardar = new boton("EXPORTAR");
		btnGuardar.addActionListener(this);
		panel.add(btnGuardar);
		btnVer = new boton("VISUALIZAR");
		btnVer.addActionListener(this);
		panelOpciones = new JPanel();
		btnVer.setVisible(false);
		btnGestionar = new boton("EDITAR");
		btnGestionar.addActionListener(this);
		panelOpciones = new JPanel();
		panel.add(btnGestionar);
		
		btnBorrar = new boton("BORRAR");
		btnBorrar.addActionListener(this);
		panelOpciones = new JPanel();
		panel.add(btnBorrar);
		
		btnHome = new boton("ATRÁS");
		btnHome.addActionListener(this);
		panel.add(btnHome);
		btnGestionar.setVisible(false);
		panelLista2 = new JPanel();
        bind(gestor.getContenido());
		scrollSopas = new JScrollPane(listaSopas);
		scrollSopas.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("55bbee", 16))));
        Dimension maxPanelOpciones = new Dimension(800,50);
		panel.setMaximumSize(maxPanelOpciones);
        contenedor.add(panel);
		contenedor4.add(labelSopas);
		contenedor4.add(scrollSopas);
		contenedor.add(contenedor4);
		btnSolucionNormal = new boton("MOSTRAR SOLUCIÓN",new Color(10,10,255));
		btnSolucionNormal.addActionListener(this);
		labelMostrarSolucion = new JLabel("Opciones:");
		contenedor5.add(labelMostrarSolucion);
		contenedor5.add(btnVer);
		contenedor5.add(btnSolucionNormal);

		contenedor3.add(contenedor5);
		mostrarBotonesSoluciones(false);
		contenedor.add(contenedor3);
		contenedor2.add(panelOpciones);
		contenedorTotal.add(contenedor);
		this.add(contenedor);
		this.setBounds(400, 200, 800, 300);
		this.setLocationRelativeTo(null);
		mostrar();
	}
	private void mostrar(){
		this.setVisible(true);
		this.setMaximumSize(maxDimension);

	}
	public void mostrarBotonesSoluciones(boolean mostrar){
		if(mostrar==false){
			btnSolucionNormal.setVisible(false);
			
			labelMostrarSolucion.setVisible(false);
		}
		else {
			btnSolucionNormal.setVisible(true);
			labelMostrarSolucion.setVisible(true);
		}
	}
	public void bind(ArrayList<cFicheroSopaLetras> sopas) {
		dlm.clear();
		if(sopas!=null)cargarContenidoALista(sopas);
		crear_adaptar_lista(dlm);
	}
	public void crear_adaptar_lista(DefaultListModel<String> dlm){
		lp=NO_PULSADO;
		listaSopas = new JList<String>(dlm);
		mL1 = new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				lp = listaSopas.locationToIndex(e.getPoint());
				if(lp>=0){
					mostrarOpciones(true);
					mostrarBotonesSoluciones(true);
				}
			}
		};
		listaSopas.addMouseListener(mL1);
	}

	private void cargarContenidoALista(ArrayList<cFicheroSopaLetras> sopas){
		for(int i=0;i<sopas.size();i++){
			dlm.addElement("Sopa de letras "+(i+1)+": "+sopas.get(i).getSopaLetras().getNombre());
		}
	}
    public void actionPerformed(ActionEvent e) {
    	Object origen = e.getSource();
        if (origen==btnCargar) {
        	gestor.cargarSopa();
        }
        else if(origen==btnGuardar){
        	if(lp!=NO_PULSADO){
        		gestor.guardarSopa(lp);
        	}
        }
        else if(origen==btnNuevo){
        	String nombre = nombreNuevoSopa(this);
        	if(nombre!=null){
        		int x = nuevaDimension("altura",this);
        		int y = nuevaDimension("longitud",this);
        		int numPalabras = preguntarGenerarPalabras(this);
        		if((x!=-1)&&(y!=-1)&&(numPalabras!=-1))addNuevoSopa(nombre,x,y,numPalabras);
        	}
        }
        else if(origen==btnVer){
        	cSopaLetras sopa = gestor.getSopa(lp).getSopaLetras();
        	mostrarVisualizador(sopa,0);
        }
        else if(origen==btnGestionar){
        	cSopaLetras nuevoSopa = gestor.getSopa(lp).getSopaLetras();
        	mostrarVisualizador(nuevoSopa,2);
        }
        else if(origen==btnBorrar){
        	if(lp!=NO_PULSADO){
        		gestor.borrarSopa(lp);
        	}
        }
        else if(origen==btnSolucionNormal){
        		cSopaLetras nuevoSopa = gestor.getSopa(lp).getSopaLetras();
            	nuevoSopa.marcar();
        		mostrarVisualizador(nuevoSopa,1);
        }
        else if(origen==btnHome){
        	int seleccion = JOptionPane.showOptionDialog(
        		    this,
        		    "Si vuelves al meún principal sin haber guardado las sopas de letras, las perderás. \n¿Seguro que quieres volver al menú principal?",
        		    "Seleccione una opción",
        		    JOptionPane.YES_NO_CANCEL_OPTION,
        		    JOptionPane.QUESTION_MESSAGE,
        		    null,
        		    new Object[] { "Si", "No"},
        		    "Si");
        	if (seleccion != -1)
        	{
        	   if((seleccion + 1)==1)
        	   {
               	this.dispose();
            	new ventanaPrincipal();
        	   }
        	}
        }
    }
    public gestorSopas getGestor() {
    	return gestor;
    }
    private String nombreNuevoSopa(Component v){
    	  String nombre = JOptionPane.showInputDialog(v, "Nombre para la nueva sopa de letras");
    	  if(nombre!=null)return nombre;
    	  return null;
    }
    private int preguntarGenerarPalabras(Component v){
  	  String decision = JOptionPane.showInputDialog(this, "Nº de palabras auto-generadas (0 para no generar ninguna)", "Auto-Generar diccionario", 1);
  	  int d;
  	  try {
  		  d = Integer.parseInt(decision);
  	  }
  	  catch(Exception e){
  		  d = 0;
  	  }
  	  return d;
  }
    private void mostrarOpciones(boolean mostrar){
		btnGestionar.setVisible(false);
		btnVer.setVisible(false);
		if(mostrar){
			btnGestionar.setVisible(true);
			btnVer.setVisible(true);
			btnHome.setVisible(true);
		}
		mostrar();
	}
    public void mostrarVisualizador(cSopaLetras sopa){
    	vAsoc = new cVentanaSopa(sopa);
    	sopa.pintarSopa(vAsoc);
    }
    public void mostrarVisualizador(cSopaLetras sopa, int formaResuelto){
    	if(formaResuelto==0)mostrarVisualizador(sopa);
    	else if(formaResuelto==2) {
    		new cAsistenteSopaPrincipal(sopa);
    	}
    	else {
        	vAsoc = new cVentanaSopa(sopa);
        	sopa.pintarSopa(vAsoc, true);
    	}
    }
	private void addNuevoSopa(String nombre, int x, int y, int num){
    	cFicheroSopaLetras nuevo = new cFicheroSopaLetras(generarSopa(nombre, x, y, num));
    	gestor.addSopa(nuevo);
    }
    private cSopaLetras generarSopa(String nombre, int x, int y, int num){
    	if(nombre.length()==0)nombre="";
    	cSopaLetras nuevoSopa = new cSopaLetras(nombre, x, y, num);
    	return nuevoSopa;
    }
    private int nuevaDimension(String dimension, Component v){
  	  String dim = JOptionPane.showInputDialog(v, "Dimensión "+dimension);
  	  try {
  	  	  int dim_ = Integer.parseInt(dim);
  	  	  return dim_;
  	  }
  	  catch(Exception ex){
  	  	  return -1;
  	  }
    }
}