import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;


/*
===============================================

PLACEHOLDER CLASS

===============================================
*/

public class Sirtet extends BasicGame {
	
	public Sirtet() {
		super("Sirtet");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Sirtet(), 1280, 1024, false);
		app.start();
	}

	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		int hail_the_geometry = 6;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("MOIST FETUS", 10, 10);
	}
}
