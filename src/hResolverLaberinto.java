
// Proceso concurrente para resolver de forma independiente cada laberinto
public class hResolverLaberinto implements Runnable {

	private cLaberinto lab;
	private ventanaGestorLaberintos v;
	private cVentanaCarga vResolver;
	
	public hResolverLaberinto(cLaberinto lab, ventanaGestorLaberintos v, cVentanaCarga vResolver){
		this.lab = lab;
		this.v = v;
		this.vResolver = vResolver;
	}
	
	@Override
	public void run() {
		try {
			lab.resolver();
			
			lab.setBloqueado(false);
			if(lab.getStopAbsoluto()==0){
				vResolver.cerrar();
				v.getBtnResolver().setEnabled(true);
			}
			v.bindSoluciones(lab);
			lab.setStopAbsoluto(0);
		}
		catch(Exception e){
			v.getBtnResolver().setEnabled(true);
		}
	}

}
