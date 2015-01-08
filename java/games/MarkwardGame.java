import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;


public class MarkwardGame extends BasicGame {
	
	public MarkwardGame() {
		super("Mr. Markward's Game");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MarkwardGame(), 1280, 1024, false);
		app.start();
	}

	public void init(GameContainer gc) throws SlickException {
	
	}

	public void update(GameContainer gc, int delta) throws SlickException {
	
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
	
	}
}
