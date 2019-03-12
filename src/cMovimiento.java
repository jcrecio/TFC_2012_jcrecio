// Clase que implementa los movimientos que se realizan en un laberinto
public class cMovimiento {
	
	private int aplicadorHorizontal;
	private int aplicadorVertical;
	private int direccion;

	// Sentido agujas del reloj empezando por 0 (12:00), las coordenadas Y crecen hacia abajo
	// y las coordenadas X crecen hacia la derecha
	
	public cMovimiento(int direccion){
		this.setDireccion(direccion);
		switch(direccion){
			case 0:{
				aplicadorHorizontal=-1;
				aplicadorVertical=0;
				break;
			}
			case 1:{
				aplicadorHorizontal=-1;
				aplicadorVertical=1;
				break;
			}
			case 2:{
				aplicadorHorizontal=0;
				aplicadorVertical=1;
				break;
			}
			case 3:{
				aplicadorHorizontal=1;
				aplicadorVertical=1;
				break;
			}
			case 4:{
				aplicadorHorizontal=1;
				aplicadorVertical=0;
				break;
			}
			case 5:{
				aplicadorHorizontal=1;
				aplicadorVertical=-1;
				break;
			}
			case 6:{
				aplicadorHorizontal=0;
				aplicadorVertical=-1;
				break;
			}
			case 7:{
				aplicadorHorizontal=-1;
				aplicadorVertical=-1;
				break;
			}
		}
	}
	
	public cMovimiento(cPosicion p1, cPosicion p2){
		aplicadorHorizontal = p2.getX()-p1.getX();
		if(aplicadorHorizontal!=0)aplicadorHorizontal = 
				aplicadorHorizontal / Math.abs(aplicadorHorizontal);
		aplicadorVertical = p2.getY()-p1.getY();
		if(aplicadorVertical!=0)aplicadorVertical = 
				aplicadorVertical / Math.abs(aplicadorVertical);
		if((aplicadorHorizontal==-1)&&(aplicadorVertical==0))direccion=0;
		else if((aplicadorHorizontal==-1)&&(aplicadorVertical==1))direccion=1;
		else if((aplicadorHorizontal==0)&&(aplicadorVertical==1))direccion=2;
		else if((aplicadorHorizontal==1)&&(aplicadorVertical==1))direccion=3;
		else if((aplicadorHorizontal==1)&&(aplicadorVertical==0))direccion=4;
		else if((aplicadorHorizontal==1)&&(aplicadorVertical==-1))direccion=5;
		else if((aplicadorHorizontal==-0)&&(aplicadorVertical==-1))direccion=6;
		else if((aplicadorHorizontal==-1)&&(aplicadorVertical==-1))direccion=7;
	}
	
	public cPosicion aplicar(cPosicion pos){
		int x = pos.getX(), y = pos.getY();
		cPosicion nuevaPos = new cPosicion/*Laberinto*/(x,y);
		nuevaPos.setX(x+aplicadorHorizontal);
		nuevaPos.setY(y+aplicadorVertical);
		return nuevaPos;
	}
	
	public cPosicion desaplicar(cPosicion pos){
		int x = pos.getX(), y = pos.getY();
		cPosicion nuevaPos = new cPosicion/*Laberinto*/(x,y);
		nuevaPos.setX(x-aplicadorHorizontal);
		nuevaPos.setY(y-aplicadorVertical);
		return nuevaPos;
	}
	
	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	
	public cMovimiento replicar(){
		return new cMovimiento(direccion);
	}
	
	public cPosicionLaberinto cast(cPosicionLaberinto pL){
		cPosicion p = aplicar(new cPosicion(pL.getX(),pL.getY()));
		return new cPosicionLaberinto(p.getX(),p.getY(),pL.getContenido(),pL.getPisado());
	}
	
	public cPosicionSopa cast(cPosicionSopa pS){
		cPosicion p = aplicar(new cPosicion(pS.getX(),pS.getY()));
		return new cPosicionSopa(p.getX(),p.getY(),pS.getLetra());
	}
	
	public cPosicionLaberinto dCast(cPosicionLaberinto pL){
		cPosicion p = desaplicar(new cPosicion(pL.getX(),pL.getY()));
		return new cPosicionLaberinto(p.getX(),p.getY(),pL.getContenido(),pL.getPisado());
	}
	
	public cPosicionSopa dCast(cPosicionSopa pS){
		cPosicion p = desaplicar(new cPosicion(pS.getX(),pS.getY()));
		return new cPosicionSopa(p.getX(),p.getY(),pS.getLetra());
	}
}