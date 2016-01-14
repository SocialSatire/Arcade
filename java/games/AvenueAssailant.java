import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import avenueAssailant.*;

public class AvenueAssailant extends BasicGame {
	

	public AvenueAssailant() {
		super("JACQUES IS A ");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new AvenueAssailant(), 400, 300, false);
		app.start();
	}

	public void init(GameContainer gc) throws SlickException {
		

		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setDrawMode(g.MODE_NORMAL);
		g.fillRect(0, (1024-60), 1280, 10);
		//g.fillRect(878,502,35,35);
	}
}
