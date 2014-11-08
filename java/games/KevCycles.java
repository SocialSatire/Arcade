import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.BasicGame;
import KevCycles.*;

public class KevCycles extends BasicGame{

	private Cycle[] players = new Cycle[2];
	private Input buttons;
	private int[] iPos = new int[4];
	public KevCycles(){
		super("Cool Game.exe");
	}
	public static void main(String[] args)throws SlickException{
		AppGameContainer app = new AppGameContainer(new KevCycles(),640,480,false);
		app.start();
	}
	public void init(GameContainer gc)throws SlickException {
		players[0]=(new Cycle("Red", 100, 100));
		players[1]=(new Cycle("Blue", 200, 200));
		buttons = gc.getInput();
		gc.setTargetFrameRate(60);
	}
	public void update(GameContainer gc, int delta)throws SlickException{
		iPos[0] = players[0].getX();
		iPos[1] = players[0].getY();
		iPos[2] = players[1].getX();
		iPos[3] = players[1].getY();
		movePlayers();
		checkBoundaries();
		
		// |||                  |||
		// VVV Questionable bit VVV
		int xDiff = players[0].getX() - players[1].getX();
		int yDiff = players[0].getY() - players[1].getY();
		if((Math.abs(xDiff) <= 32) && (Math.abs(yDiff) <= 16)) {
			players[0].setX(iPos[0]);
			players[0].setY(iPos[1]);
			players[1].setX(iPos[2]);
			players[2].setY(iPos[3]);
		}
	}
	public void movePlayers(){
		if(buttons.isKeyDown(Input.KEY_RIGHT)){players[0].setX(players[0].getX()+5);};
		if(buttons.isKeyDown(Input.KEY_LEFT)){players[0].setX(players[0].getX()-5);};
		if(buttons.isKeyDown(Input.KEY_UP)){players[0].setY(players[0].getY()-5);};
		if(buttons.isKeyDown(Input.KEY_DOWN)){players[0].setY(players[0].getY()+5);};
		if(buttons.isKeyDown(Input.KEY_D)){players[1].setX(players[1].getX()+5);};
		if(buttons.isKeyDown(Input.KEY_A)){players[1].setX(players[1].getX()-5);};
		if(buttons.isKeyDown(Input.KEY_W)){players[1].setY(players[1].getY()-5);};
		if(buttons.isKeyDown(Input.KEY_S)){players[1].setY(players[1].getY()+5);};
	}
	public void checkBoundaries(){
		if(players[0].getX() <= 0){players[0].setX(0);};
		if(players[0].getX()+32 >= 640){players[0].setX(640-32);};
		if(players[0].getY() <= 0){players[0].setY(0);};
		if(players[0].getY()+16 >= 480){players[0].setY(480-16);};
		if(players[1].getX() <= 0){players[1].setX(0);};
		if(players[1].getX()+32 >= 640){players[1].setX(640-32);};
		if(players[1].getY() <= 0){players[1].setY(0);};
		if(players[1].getY()+16 >= 480){players[1].setY(480-16);};
	}
	public void render(GameContainer gc, Graphics g)throws SlickException{
		for(Cycle p : players)p.getImage().draw(p.getX(),p.getY());
	}
}