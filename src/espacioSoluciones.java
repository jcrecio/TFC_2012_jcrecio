import java.util.ArrayList;
import java.util.Hashtable;

// Espacio de soluciones asociado a un laberinto
public class espacioSoluciones {
	private ArrayList<cCamino> listaCaminos;
	private Hashtable<Integer,Boolean> controlCaminos;

	public espacioSoluciones(){
		listaCaminos=new ArrayList<cCamino>();
		controlCaminos=new Hashtable<Integer,Boolean>();
	}
	public void addCamino(cCamino camino){
		controlCaminos.put(camino.getNumMovimientos(), true);
		listaCaminos.add(camino);
	}
	public boolean permitirCamino(int numElementos){
		try {
			return !controlCaminos.get(numElementos).equals(true);
		}
		catch(Exception e){
			return true;
		}
	}
	public cCamino getCamino(int i){
		return listaCaminos.get(i);
	}
	public int getNumCaminos(){
		return listaCaminos.size();
	}
	public cCamino getPrimerCaminoMasCorto(){
		int pasos = 0;
		cCamino c = null;
		for(int i=0;i<listaCaminos.size();i++){
			c = getCamino(i);
			int valor = c.getNumMovimientos();
			if(pasos==0)pasos = valor;
			else
				if(valor<pasos)
					pasos = valor;
		}
		if(pasos>0)return c;
		else return null;
	}

	public void imprimir() {
		System.out.println();
		System.out.println("Espacio de soluciones:");
		for(int i=0;i<listaCaminos.size();i++){
			getCamino(i).imprimir();
			System.out.println();
		}
	}
}
