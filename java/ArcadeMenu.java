import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import games.*;

public class ArcadeMenu extends BasicGame {
	Image bg = null;
	byte credits = 0;
	int targetFPS = 60;
	long timeCounter = 0L;
	int playerChoice = 0;
	private static boolean isRunning = false;

	BasicGame game[] = {
		new KevCycles(),
		new Sirtet(),
		new Slick0170(),
		new MarkwardGame()
	};
	String gameName[] = {
		"KevCycles",
		"Sirtet",
		"Slick0170",
		"Mr. Markward's Game"
	};

	public ArcadeMenu() {
		super("Arcade Menu");
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ArcadeMenu(), 1280, 1024, false);
		app.start();
	}

	public void init(GameContainer gc) throws SlickException {
		bg = new Image("ArcadeMenu\\gray_background.png");

		gc.setTargetFrameRate(targetFPS);
		gc.setShowFPS(false);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if(!ArcadeMenu.getRunning()) {
			if(input.isKeyDown(Input.KEY_C) && (credits < 127) && (timeCounter > 500)) {
				credits++;
				timeCounter = 0;
			}

			if(input.isKeyDown(Input.KEY_Z) && (timeCounter > 200)) {
				playerChoice += 1;
				timeCounter = 0;
				if(playerChoice > game.length - 1) {
					playerChoice = 0;
				}
			} else if(input.isKeyDown(Input.KEY_X) && (timeCounter > 200)) {
				playerChoice -= 1;
				timeCounter = 0;
				if(playerChoice < 0) {
					playerChoice = game.length - 1;
				}
			}

			if((credits >= 2) && input.isKeyDown(Input.KEY_ENTER)) {
				ArcadeMenu.setRunning(true);
				game[playerChoice].init(gc);
				credits = 0;
			}

			timeCounter += delta;
			if(timeCounter >= 9223372036854774807L) {
				timeCounter = 0;
			}
		} else {
			game[playerChoice].update(gc, delta);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(!ArcadeMenu.getRunning()) {
			g.setDrawMode(g.MODE_NORMAL);
			bg.draw(0.0f, 0.0f);

			String dispCredit = Byte.toString(credits);
			String dispPlayerChoice = Integer.toString(playerChoice);

			g.drawString("Credits: " + dispCredit, 40.0f, 760.0f);
			g.drawString(Long.toString(timeCounter), 40.0f, 40.0f);
			g.drawString(("Game: " + gameName[playerChoice]), 200.0f, 200.0f);
			
			Image preview = new Image("games\\"+gameName[playerChoice]+"\\sprites\\title.png");
			if(preview.equals(null)) preview = new Image("ArcadeMenu\\title.png");
			preview.draw(200,100); //mwahahahaha
		} else {
			game[playerChoice].render(gc, g);
		}
	}
	
	public static void setRunning(boolean b) {
		isRunning = b;
	}

	public static boolean getRunning() {
		return isRunning;
	}
}
