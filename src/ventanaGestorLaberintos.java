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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ventanaGestorLaberintos extends JDialog implements ActionListener {
	private static final long serialVersionUID = 4418483031157260028L;
	private static int NO_PULSADO = -1;
	private gestorLaberintos gestor; // Asociación con el gestor de contenido que controla la ventana
	private Dimension maxDimension;
	// Controles
	private Box contenedor,contenedor2,contenedor3,contenedor4,contenedor5, contenedorTotal;
	/***/
	private JPanel panel;
	private JPanel panelOpciones;
	private JPanel panelLista,panelLista2;
	private JPanel panelSoluciones,panelSoluciones2;
	private JLabel labelSoluciones,labelLaberintos,labelMostrarSolucion;
	/**/
	private DefaultListModel<String> dlm; // Actualizador del enlace datos->control lista
	private DefaultListModel<String> dlmSoluciones;
	private boton btnNuevo;
	private boton btnCargar;
	private boton btnGuardar;
	private boton btnResolver;
	private boton btnBorrar;
	private boton btnSolucionNormal;
	private boton btnSolucionAnimacion;
	private boton btnVer;
	private boton btnVisualizar;
	private JButton btnHome;
	private JList<String> listaLaberintos;
	private JList<String> listaSoluciones;
	private JScrollPane scrollLaberintos;
	private JScrollPane scrollSoluciones;
	private int lp,l_p;
	private MouseListener mL1,mL2;
	
	public boton getBtnResolver(){
		return btnResolver;
	}
	
	public ventanaGestorLaberintos(gestorLaberintos gestor) {
		maxDimension = new Dimension();
		maxDimension.setSize(800,500);
		dlm = new DefaultListModel<String>();
		dlmSoluciones = new DefaultListModel<String>();
		this.gestor = gestor;
		gestor.setVentanaGestor(this);
		labelSoluciones = new JLabel("Soluciones");
		labelLaberintos = new JLabel("Laberintos");
		setTitle("Gestor de laberintos");
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
		btnBorrar = new boton("BORRAR");
		btnBorrar.addActionListener(this);
		panel.add(btnBorrar);
		btnVer = new boton("CONFIGURAR",new Color(10,10,255));
		btnVer.addActionListener(this);
		btnVisualizar = new boton("VER LABERINTO",new Color(10,10,255));
		btnVisualizar.addActionListener(this);
		btnHome = new boton("ATRÁS");
		btnHome.addActionListener(this);
		btnHome.show();
		panel.add(btnHome);
		panelOpciones = new JPanel();
		panel.add(btnVer);
		btnVer.hide();
		btnResolver = new boton("RESOLVER",new Color(10,10,255));
		btnResolver.addActionListener(this);
		panelOpciones = new JPanel();
		panel.add(btnResolver);
		btnResolver.hide();
		panelLista2 = new JPanel();
        bind(gestor.getContenido());
		scrollLaberintos = new JScrollPane(listaLaberintos);
		scrollLaberintos.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("55bbee", 16))));
		dlmSoluciones = new DefaultListModel<String>();
		bindSoluciones(null);
		scrollSoluciones = new JScrollPane(listaSoluciones);
		scrollSoluciones.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("55bbee", 16))));
		contenedor3.add(labelSoluciones);
        contenedor3.add(scrollSoluciones);
        Dimension maxPanelOpciones = new Dimension(800,50);
		panel.setMaximumSize(maxPanelOpciones);
        contenedor.add(panel);
		contenedor4.add(labelLaberintos);
		contenedor4.add(scrollLaberintos);
		contenedor.add(contenedor4);
		btnSolucionNormal = new boton("MOSTRAR SOLUCIÓN",new Color(10,10,255));
		btnSolucionNormal.addActionListener(this);
		btnSolucionAnimacion = new boton("VER LABERINTO",new Color(10,10,255));
		btnSolucionAnimacion.addActionListener(this);
		labelMostrarSolucion = new JLabel("Opciones: ");
		contenedor5.add(labelMostrarSolucion);
		contenedor5.add(btnSolucionNormal);
		contenedor5.add(btnSolucionAnimacion);
		contenedor3.add(contenedor5);
		mostrarBotonesSoluciones(false);
		contenedor.add(contenedor3);
		contenedor2.add(panelOpciones);
		contenedorTotal.add(contenedor);
		this.add(contenedor);
		this.setBounds(0, 0, 800, 300);
		this.setLocationRelativeTo(null);
		mostrar();
		
	}
	private void mostrar(){
		this.setVisible(true);
		this.setMaximumSize(maxDimension);
	}
	public void mostrarBotonesSoluciones(boolean mostrar){
		if(mostrar==false){
			btnSolucionNormal.hide();
			btnSolucionAnimacion.hide();
			labelMostrarSolucion.hide();
		}
		else {
			btnSolucionNormal.show();
			btnSolucionAnimacion.show();
			labelMostrarSolucion.show();
		}
	}
	
	/* Como binding() en C# */
	public void bind(ArrayList<cFicheroLaberinto> laberintos) {
		dlm.clear();
		if(laberintos!=null)cargarContenidoALista(laberintos);
		crear_adaptar_lista(dlm);
	}
	public void bindSoluciones(cLaberinto laberinto){
		dlmSoluciones.clear();
		if(laberinto!=null)cargarSoluciones(laberinto);
		crear_adaptar_soluciones(dlmSoluciones);
	}
	public void crear_adaptar_lista(DefaultListModel<String> dlm){
		lp=NO_PULSADO;
		listaLaberintos = new JList<String>(dlm);
		mL1 = new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				lp = listaLaberintos.locationToIndex(e.getPoint());
				if(lp>=0){
					dlmSoluciones.clear();
					cLaberinto lab = gestor.getLaberinto(lp).getLaberinto();
					mostrarOpciones(true);
					mostrarBotonesSoluciones(true);
					if(lab.isBloqueado())btnResolver.setEnabled(false);
					else btnResolver.setEnabled(true);
					cargarSoluciones(lab);
				}
			}
		};
		listaLaberintos.addMouseListener(mL1);
	}
	public void crear_adaptar_soluciones(DefaultListModel<String> dlmSoluciones){
		l_p=NO_PULSADO;
		listaSoluciones = new JList<String>(dlmSoluciones);
		mL2 = new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				l_p = listaSoluciones.locationToIndex(e.getPoint());
			}
		};
		listaSoluciones.addMouseListener(mL2);
	}
	private void cargarContenidoALista(ArrayList<cFicheroLaberinto> laberintos){
		for(int i=0;i<laberintos.size();i++){
			dlm.addElement("Laberinto "+(i+1)+": "+laberintos.get(i).getLaberinto().getNombre());
		}
	}
	public void cargarSoluciones(cLaberinto lab){
		labelSoluciones.setText("Soluciones para el laberinto "+lab.getNombre());
		for(int i=0;i<lab.getSoluciones().getNumCaminos();i++){
			dlmSoluciones.addElement("Solución "+(i+1)+": "+lab.getSoluciones().getCamino(i).getNumMovimientos()+" movimientos");
		}
	}
    public void actionPerformed(ActionEvent e) {
    	Object origen = e.getSource();
        if (origen==btnCargar) {
        	gestor.cargarLaberinto();
        }
        else if(origen==btnGuardar){
        	if(lp!=NO_PULSADO){
        		gestor.guardarLaberinto(lp);
        	}
        }
        else if(origen==btnBorrar){
        	if(lp!=NO_PULSADO){
        		gestor.borrarLaberinto(lp);
        	}
        }
        else if(origen==btnNuevo){
        	String nombre = nombreNuevoLab(this);
        	if(nombre != null) addNuevoLab(nombre);
        }
        else if(origen==btnVer){
        	if(lp!=-1){
        		cLaberinto lab = gestor.getLaberinto(lp).getLaberinto();
        		mostrarVisualizador(lab);
        	}
        }
        else if(origen==btnResolver){
        	try {
        		cLaberinto l = gestor.resolverLaberinto(lp);
        		bindSoluciones(l);
        		btnResolver.setEnabled(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

        }
        else if(origen==btnSolucionNormal){
        	if(l_p>=0){
        		mostrarVisualizador(gestor.getLaberinto(lp).getLaberinto(),l_p);
        	}
        }
        else if(origen==btnSolucionAnimacion){
        	cLaberinto lab = gestor.getLaberinto(lp).getLaberinto();
           	cVentanaLaberinto v = new cVentanaLaberinto(lab,"caminos");
        	lab.pintarLaberinto(v);
        }
        else if(origen==btnHome){
        	int seleccion = JOptionPane.showOptionDialog(
        		    this,
        		    "Si vuelves al menú principal sin haber guardado los laberintos, los perderás. \n¿Seguro que quieres volver al menú principal?",
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
    public gestorLaberintos getGestor() {
    	return gestor;
    }
    private String nombreNuevoLab(Component v){
    	  String lab = JOptionPane.showInputDialog(v, "Nombre para el nuevo laberinto");
    	  if(lab==null)return null;
    	  if(lab.equals(""))lab = "Sin titulo"+System.currentTimeMillis();
    	  return lab;
    }
    private void mostrarOpciones(boolean mostrar){
		btnResolver.hide();
		btnVer.hide();
		if(mostrar){
			btnResolver.show();
			btnVer.show();
		}
		mostrar();
	}
    public void mostrarVisualizador(cLaberinto lab){
    	cMiniAsistenteLaberinto v = new cMiniAsistenteLaberinto(lab);
    }
    public void mostrarVisualizador(cLaberinto lab, int numSolucion){
    	cVentanaLaberinto v = new cVentanaLaberinto(lab, "lectura");
		lab.pintarSolucion(v,numSolucion);
    }
	private void addNuevoLab(String nombre){
		cAsistenteLaberinto v = new cAsistenteLaberinto(nombre, this);
	}
}
