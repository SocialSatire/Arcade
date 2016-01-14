import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import java.io.File;
import games.*;

public class ArcadeMenu extends BasicGame {
	Image bg = null;
	private static int credits = 0;
	int targetFPS = 60;
	long timeCounter = 0L;
	int playerChoice = 0;
	private static boolean isRunning = false;
	private Image preview, nextPrev, prevPrev; //hehe
	
	private static final int numGames = 3;

	BasicGame game[] = {
		new PhotonBikes(),
		new SpaceInvaders(),
		new Pong(),
		new Slick0170()
	};
	// Actual folder names
	String gameName[] = {
		"PhotonBikes",
		"SpaceInvaders",
		"Pong",
		"Slick0170"
	};
	// Titles that will be displayed
	String gameTitle[] = {
		"PhotonBikes",
		"Interstellar Interlopers",
		"Pong",
		"Slick0170"
	};
	public ArcadeMenu() {
		super("Arcade Menu");
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ArcadeMenu(), 1280, 1024, true);
		app.start();
	}

	public void init(GameContainer gc) throws SlickException {
		bg = new Image("ArcadeMenu\\background.png");

		gc.setTargetFrameRate(targetFPS);
		gc.setShowFPS(false);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if(!ArcadeMenu.getRunning()) {
			/*if(input.isKeyDown(Input.KEY_SPACE) && (timeCounter > 300)) {
				ArcadeMenu.credits++;
				timeCounter = 0;
			}
			if(input.isKeyDown(Input.KEY_P) && (timeCounter > 300)) {
				ArcadeMenu.credits+=100;
				timeCounter = 0;
			}*/
			if(input.isKeyDown(Input.KEY_W) && (timeCounter > 200)) {
				playerChoice += 1;
				timeCounter = 0;
				if(playerChoice > game.length - 1) {
					playerChoice = 0;
				}
			} else if(input.isKeyDown(Input.KEY_S) && (timeCounter > 200)) {
				playerChoice -= 1;
				timeCounter = 0;
				if(playerChoice < 0) {
					playerChoice = game.length - 1;
				}
			}

			if(/*(ArcadeMenu.credits >= 2) && */input.isKeyDown(Input.KEY_Z)) {
				ArcadeMenu.setRunning(true);
				if(input.isKeyDown(Input.KEY_B)&&input.isKeyDown(Input.KEY_N)) game[playerChoice] = restartGame(-1);
				else game[playerChoice] = restartGame(playerChoice);
				game[playerChoice].init(gc);
			}

			timeCounter += delta;
			if(timeCounter >= 9223372036854774807L) {
				timeCounter = 1000;
			}
		} else {
			game[playerChoice].update(gc, delta);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(!ArcadeMenu.getRunning()) {
			g.setColor(new Color(0,1f,0));
			g.setDrawMode(g.MODE_NORMAL);
			bg.draw(0.0f, 0.0f);

			//String dispCredit = Integer.toString(ArcadeMenu.credits);
			String dispPlayerChoice = Integer.toString(playerChoice);

			//g.drawString("Credits: " + dispCredit, 1150, 950);
			g.drawString(Long.toString(timeCounter), 40.0f, 40.0f);
			//temporary g.drawString(("Game: " + gameTitle[playerChoice]), 200.0f, 200.0f);
			
			File f = new File("games\\"+gameName[playerChoice]+"\\sprites\\title.png");
			if(!f.exists()||f.isDirectory())
				preview = new Image("ArcadeMenu\\title.png");
			else preview = new Image("games\\"+gameName[playerChoice]+"\\sprites\\title.png");
			
			f = new File("games\\"+gameName[(playerChoice+1)%ArcadeMenu.numGames]+"\\sprites\\title.png");
			if(!f.exists()||f.isDirectory())
				nextPrev = new Image("ArcadeMenu\\title.png");
			else nextPrev = new Image("games\\"+gameName[(playerChoice+1)%ArcadeMenu.numGames]+"\\sprites\\title.png");
			
			f = new File("games\\"+gameName[(playerChoice+(ArcadeMenu.numGames-1))%ArcadeMenu.numGames]+"\\sprites\\title.png");
			if(!f.exists()||f.isDirectory())
				prevPrev = new Image("ArcadeMenu\\title.png");
				
			else prevPrev = new Image("games\\"+gameName[(playerChoice+(ArcadeMenu.numGames-1))%ArcadeMenu.numGames]+"\\sprites\\title.png");
			
			nextPrev.setFilter(Image.FILTER_NEAREST);
			prevPrev.setFilter(Image.FILTER_NEAREST);
			preview.setFilter(Image.FILTER_NEAREST);
			nextPrev.draw(640,520,1.6f,new Color(1,1,1,0.5f));
			prevPrev.draw(640,760,1.6f,new Color(1,1,1,0.5f));
			preview.draw(600,600,2f);
			
		} else {
			game[playerChoice].render(gc, g);
		}
	}
	
	/*public static int getCredits(){
		return ArcadeMenu.credits;
	}
	public static void setCredits(int c){
		ArcadeMenu.credits = c;
	}*/
	
	public static void setRunning(boolean b) {
		isRunning = b;
	}

	public static boolean getRunning() {
		return isRunning;
	}
	
	public BasicGame restartGame(int game){
		switch(game){
			case 0: return new PhotonBikes();
			case 1: return new SpaceInvaders();
			case 2: return new Pong();
			default: return new Slick0170(); //you know something is wrong
		}
	}
}
