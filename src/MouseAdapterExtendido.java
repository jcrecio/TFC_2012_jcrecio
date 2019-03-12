import java.awt.Graphics;
import java.awt.event.MouseAdapter;

public class MouseAdapterExtendido extends MouseAdapter {
	private Graphics g;
	
	public MouseAdapterExtendido(Graphics g){
		this.g = g;
	}
	public Graphics getGraphics(){
		return g;
	}
}
