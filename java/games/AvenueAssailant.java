import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import AvenueAssailant.*;

public class AvenueAssailant extends BasicGame {
	
	private Player[] players = new Player[2];

	public AvenueAssailant() {
		super("JACQUES IS A ");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new AvenueAssailant(), 1280, 1024, false);//true when it's done obviously
		app.start();
	}

	public void init(GameContainer gc) throws SlickException {
		players[0] = new Sommar(0);
		players[1] = new Sommar(1);		//this will change too once we have character selection and more characters

		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		// since different characters are going to have different combos and 
		// move structures I'm passing all of the current keys down to the
		// player object to decide what to do
		
		players[0].update(delta, new boolean[]{
			input.isKeyDown(Input.KEY_W),
			input.isKeyDown(Input.KEY_A),
			input.isKeyDown(Input.KEY_S),
			input.isKeyDown(Input.KEY_D),
			input.isKeyDown(Input.KEY_Z),
			input.isKeyDown(Input.KEY_X),
			input.isKeyDown(Input.KEY_C),
			input.isKeyDown(Input.KEY_V),
			input.isKeyDown(Input.KEY_B),
			input.isKeyDown(Input.KEY_N)
		});
		players[1].update(delta, new boolean[]{
			input.isKeyDown(Input.KEY_UP),
			input.isKeyDown(Input.KEY_LEFT),
			input.isKeyDown(Input.KEY_DOWN),
			input.isKeyDown(Input.KEY_RIGHT),
			input.isKeyDown(Input.KEY_1),
			input.isKeyDown(Input.KEY_2),
			input.isKeyDown(Input.KEY_3),
			input.isKeyDown(Input.KEY_4),
			input.isKeyDown(Input.KEY_5),
			input.isKeyDown(Input.KEY_6)
		});
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setDrawMode(g.MODE_NORMAL);
		g.fillRect(0, (1024-60), 1280, 10);
		g.fillRect(players[0].getX(),players[0].getY(),players[0].getWidth(),players[0].getHeight());
	}
}
