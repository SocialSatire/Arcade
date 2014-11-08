import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import KevCycles.*;

public class KevCycles extends BasicGame{

	private Cycle[] players = new Cycle[2];
	private Input buttons;
	private int[] iPos = new int[4];
	private int moveDist;
	private boolean colorSelect;
	private int[] playerColors = new int[2];
	// I WILL USE MORE PRECISE VALUES WHEN THE SPRITES ARE DONE
	private final Color[] bikeColors = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.pink};
	private boolean[] playersChosen = new boolean[2];
	private int[] timesSinceChoice = new int[2];
	public KevCycles(){
		super("Cool Game.exe");
	}
	public static void main(String[] args)throws SlickException{
		AppGameContainer app = new AppGameContainer(new KevCycles(),640,480,false);
		app.start();
	}
	public void init(GameContainer gc)throws SlickException {
		buttons = gc.getInput();
		gc.setTargetFrameRate(60);
		new Trace(-10,-10,true,moveDist,0);//shut up
		colorSelect=true;
		playerColors[0] = 0;playerColors[1] = 4;
	}
	public void update(GameContainer gc, int delta)throws SlickException{
		if(colorSelect){chooseColor(delta);timeToStart();}else{		//IT'S NOT GREAT YET BUT WHATEVER
		moveDist = 5*delta*60/1000;
		iPos[0] = players[0].getX();
		iPos[1] = players[0].getY();
		iPos[2] = players[1].getX();
		iPos[3] = players[1].getY();
		turnPlayers();
		checkBoundaries();
		movePlayers();
		int xDiff = players[0].getX() - players[1].getX();
		int yDiff = players[0].getY() - players[1].getY();
		// This will also be changed when x and y are different
		if((Math.abs(xDiff) <= 32) && (Math.abs(yDiff) <= 16)){
			players[0].setX(iPos[0]);
			players[0].setY(iPos[1]);
			players[1].setX(iPos[2]);
			players[1].setY(iPos[3]);}}
	}
	public void turnPlayers(){
		if(buttons.isKeyDown(Input.KEY_RIGHT)){players[0].turn(0);};
		if(buttons.isKeyDown(Input.KEY_LEFT)){players[0].turn(2);};
		if(buttons.isKeyDown(Input.KEY_UP)){players[0].turn(1);};
		if(buttons.isKeyDown(Input.KEY_DOWN)){players[0].turn(3);};
		if(buttons.isKeyDown(Input.KEY_D)){players[1].turn(0);};
		if(buttons.isKeyDown(Input.KEY_A)){players[1].turn(2);};
		if(buttons.isKeyDown(Input.KEY_W)){players[1].turn(1);};
		if(buttons.isKeyDown(Input.KEY_S)){players[1].turn(3);};
	}
	public void checkBoundaries(){
		//THIS WILL CONDENSE WHEN WE HAVE FINAL SPRITE SIZES AND STORE
		//THEM IN CLASS VARS
		
		// also I'm not sure why this doesn't work help
		if(players[0].getDir()%2==0){
		if(players[0].getX() <= 0){players[0].setX(0);};
		if(players[0].getX()+32 >= 640){players[0].setX(640-32);};
		if(players[0].getY() <= 0){players[0].setY(0);};
		if(players[0].getY()+16 >= 480){players[0].setY(480-16);};}
		else{if(players[0].getX() <= 0){players[0].setX(0);};
		if(players[0].getX()+16 >= 640){players[0].setX(640-16);};
		if(players[0].getY() <= 0){players[0].setY(0);};
		if(players[0].getY()+32 >= 480){players[0].setY(480-32);};};
		if(players[1].getDir()%2==1){
		if(players[1].getX() <= 0){players[1].setX(0);};
		if(players[1].getX()+32 >= 640){players[1].setX(640-32);};
		if(players[1].getY() <= 0){players[1].setY(0);};
		if(players[1].getY()+16 >= 480){players[1].setY(480-16);};}
		else{if(players[1].getX() <= 0){players[1].setX(0);};
		if(players[1].getX()+16 >= 640){players[1].setX(640-16);};
		if(players[1].getY() <= 0){players[1].setY(0);};
		if(players[1].getY()+32 >= 480){players[1].setY(480-32);};};
	}
	public void movePlayers(){
		// Somebody please fix the corners
		if(players[0].getDir()==0){players[0].setX(players[0].getX()+moveDist);new Trace(iPos[0],iPos[1]+7,true,moveDist,0);};
		if(players[0].getDir()==2){players[0].setX(players[0].getX()-moveDist);new Trace(players[0].getX()+16,iPos[1]+7,true,moveDist,0);};
		if(players[0].getDir()==1){players[0].setY(players[0].getY()-moveDist);new Trace(iPos[0]+15,players[0].getY()+16,false,moveDist,0);};
		if(players[0].getDir()==3){players[0].setY(players[0].getY()+moveDist);new Trace(iPos[0]+15,iPos[1],false,moveDist,0);};
		if(players[1].getDir()==0){players[1].setX(players[1].getX()+moveDist);new Trace(iPos[2],iPos[3]+7,true,moveDist,1);};
		if(players[1].getDir()==2){players[1].setX(players[1].getX()-moveDist);new Trace(players[1].getX()+16,iPos[3]+7,true,moveDist,1);};
		if(players[1].getDir()==1){players[1].setY(players[1].getY()-moveDist);new Trace(iPos[2]+15,players[1].getY()+16,false,moveDist,1);};
		if(players[1].getDir()==3){players[1].setY(players[1].getY()+moveDist);new Trace(iPos[2]+15,iPos[3],false,moveDist,1);};
	}
	public void chooseColor(int delta){
		timesSinceChoice[0]+=delta;timesSinceChoice[1]+=delta;
		if(buttons.isKeyDown(Input.KEY_L))playersChosen[0]=true;
		if(!playersChosen[0]&&(timesSinceChoice[0]>=100)){
		if(buttons.isKeyDown(Input.KEY_RIGHT)){playerColors[0]++;timesSinceChoice[0]=0;};
		if(buttons.isKeyDown(Input.KEY_LEFT)){playerColors[0]--;timesSinceChoice[0]=0;}}
		if(buttons.isKeyDown(Input.KEY_F))playersChosen[1]=true;
		if(!playersChosen[1]&&(timesSinceChoice[1]>=100)){
		if(buttons.isKeyDown(Input.KEY_D)){playerColors[1]++;timesSinceChoice[1]=0;}
		if(buttons.isKeyDown(Input.KEY_A)){playerColors[1]--;timesSinceChoice[1]=0;}}
		if(playersChosen[1] && (playerColors[0]%6 == playerColors[1]%6))playerColors[0]++;
		else if(playerColors[0]%6 == playerColors[1]%6)playerColors[1]++;
		playerColors[0]+=6;playerColors[1]+=6;playerColors[0]%=6;playerColors[1]%=6;
	}
	public void timeToStart() throws SlickException{
		if(playersChosen[0]&&playersChosen[1]){
			colorSelect = false;
			players[0]=(new Cycle(""+playerColors[0], 100, 100, 0));
			players[1]=(new Cycle(""+playerColors[1], 540, 380, 2));
		}
	}
	public void drawColorSelect(Graphics g){
		g.setColor(Color.white);
		// Instructions will change with controls
		g.drawString("Use left/right or a/d to choose, f to select p2 and l to select p1",15,50);
		g.setColor(bikeColors[playerColors[0]]);
		g.fillRect(175,215,50,50);
		g.setColor(bikeColors[playerColors[1]]);
		g.fillRect(415,215,50,50);
	}
	
	public void render(GameContainer gc, Graphics g)throws SlickException{
		if(colorSelect)drawColorSelect(g);else{
		for(Cycle p : players)p.getImage().draw(p.getX(),p.getY());
		for(Trace t : Trace.getTraces()){
			g.setColor(bikeColors[playerColors[t.getPlayer()]]); //ALSO TEMPORARY UNTIL WE FINISH SPRITES
			if(t.getH())g.fillRect(t.getX(),t.getY(),t.getSpeed(),2);else g.fillRect(t.getX(),t.getY(),2,t.getSpeed());
		}}
	}
}