package KevCycles;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;

public class Trace{
 
	private static ArrayList<Trace> traces = new ArrayList<Trace>();
	private Image cycleImage;
	private int x, y;
	private boolean horizontal;
	private int speed;
	
	// The playernum is to get the correct color, I'll fix that too
	// once all the sprites are done
	public Trace(int x, int y, boolean horizontal, int speed/*, int playerNum*/){
		this.x = x;this.y = y;this.horizontal = horizontal;this.speed = speed;traces.add(this);
	}
	public int getX(){return x;}
	public int getY(){return y;}
	public boolean getH(){return horizontal;}
	public int getSpeed(){return speed;}
	public static ArrayList<Trace> getTraces(){return traces;}
}