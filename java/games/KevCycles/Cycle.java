package KevCycles;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
 
public class Cycle{
 
	private Image cycleImage;
	private int x, y;
	public Cycle(String color, int x, int y)throws SlickException{
		this.cycleImage = new Image("KevCycles/sprites/"+color+"_cycle.png");
		this.x = x;
		this.y = y;
	}
	public Image getImage(){return cycleImage;}
	public int getX(){return x;};
	public int getY(){return y;};
	public void setX(int n){x=n;}
	public void setY(int n){y=n;}
}