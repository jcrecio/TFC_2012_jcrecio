import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

// Interfaz gráfica para controlar el diccionario asociado a una sopa de letras
public class cAsistenteSopa extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private cDiccionario diccionario;
	private Dimension maxDimension;
	private DefaultListModel<String> dlm;
	private JList<String> listaDiccionario;
	private JScrollPane scrollDiccionario;
	private int _S_PLS;
	private boton btnAgregar;
	private boton btnEliminar;
	private JTextField txtPalabra;
	private JPanel panel;
	private MouseListener mL1;
	private cSopaLetras refSopa;
	
	@SuppressWarnings("unused")
	private Box contenedor,contenedor2,contenedorTotal;
	
	public cAsistenteSopa(String nombreSopa, cDiccionario diccionario, cSopaLetras refSopa){
		this.setRefSopa(refSopa);
		maxDimension = new Dimension(1000,500);
		setDiccionario(diccionario);
		setTitle(nombreSopa+"- Editando");
		inicializarInterface();
	}
	private void inicializarInterface(){
		panel = new JPanel();
		panel.setBackground(new Color(Integer.parseInt("66bbff", 16)));
		panel.setBorder(BorderFactory.createLineBorder(new Color(Integer.parseInt("33aacc", 16))));
		btnAgregar = new boton("Añadir palabra");
		btnAgregar.addActionListener(this);
		panel.add(btnAgregar);
		txtPalabra = new JTextField(10);
		panel.add(txtPalabra);
		btnEliminar = new boton("Borrar seleccionada");
		btnEliminar.addActionListener(this);
		panel.add(btnEliminar);
		_S_PLS = -1;
		dlm = new DefaultListModel<String>();
		bindDiccionario(getDiccionario());
		scrollDiccionario = new JScrollPane(listaDiccionario);
		contenedor = Box.createHorizontalBox();
		contenedor2 = Box.createHorizontalBox();
		contenedorTotal = Box.createVerticalBox();
		contenedor2.add(scrollDiccionario);
        Dimension maxPanelOpciones = new Dimension(1000,50);
        panel.setMaximumSize(maxPanelOpciones);
		contenedorTotal.add(panel);
		contenedorTotal.add(contenedor2);
		this.add(contenedorTotal);
		this.setVisible(true);
		this.setMaximumSize(maxDimension);
		this.setBounds(400, 200, 450, 300);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	Object origen = e.getSource();
    	if (origen == btnAgregar){
    		String contenido = txtPalabra.getText();
    		if((contenido!="")&&(contenido!=null)){
    			try {
    				diccionario.setElemento(contenido);
    				bindDiccionario(diccionario);
    				txtPalabra.setText("");
    			}
    			catch(Exception ex){
    				
    			}
    		}
    	}
    	else if (origen == btnEliminar){
    		if(_S_PLS >=0){
    			//refSopa.getTablero().desmarcarTodo();
    			String x = listaDiccionario.getSelectedValue();
    			diccionario.quitarElemento(x);
    			dlm.removeElementAt(_S_PLS);
    			
    		}
    	}
	}
	
	public cDiccionario getDiccionario() {
		return diccionario;
	}

	public void setDiccionario(cDiccionario diccionario) {
		this.diccionario = diccionario;
	}
	
	public void bindDiccionario(cDiccionario diccionario){ 
		_S_PLS = -1;
		bindDLM(diccionario);
		listaDiccionario = new JList<String>(dlm);
		mL1 = new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				_S_PLS = listaDiccionario.locationToIndex(e.getPoint());
			}
		};
		listaDiccionario.addMouseListener(mL1);
	}
	private void bindDLM(cDiccionario diccionario){
		dlm.clear();
		Enumeration<String> x = getDiccionario().getClaves();
    	while(x.hasMoreElements()){
        	dlm.addElement(x.nextElement());
    	}
	}
	public cSopaLetras getRefSopa() {
		return refSopa;
	}
	public void setRefSopa(cSopaLetras refSopa) {
		this.refSopa = refSopa;
	}

}
