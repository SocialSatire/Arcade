package KevCycles;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;

public class AI{
 
	private int x1, y1, dir1, x2, y2, dir2; // x1, y1, and dir1 are for the ai; x2, y2, and dir2 are for the other player.
	
	/* Some other useful things:
	Trace.getTraces(); returns an ArrayList<Trace> of all traces
	for(Trace t : Trace.getTraces) will scan through all traces
	Trace.getX() and Trace.getY() both work
	Trace.getH() returns true if the trace is horizontal, false if it is vertical
	You can assume the traces are 5x2 rectangles
	Turns are not exact because of how the traces line up.
	*/
	
	
	public AI(int x1, int y1, int dir1, int x2, int y2, int dir2){
		this.x1 = x1;this.y1 = y1;this.dir1 = dir1;this.x2 = x2;this.y2 = y2;this.dir2 = dir2;
	}
	public int getChoice(){
		return 0;	// returns the AI's choice for direction
	}
}