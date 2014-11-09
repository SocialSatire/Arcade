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
	private final Color[] bikeColors = {rgb(249,0,0), rgb(249,58,0), rgb(249,199,0), 
	rgb(27,156,0), rgb(0,249,216), rgb(0,55,193), rgb(101,0,158), rgb(249,0,157)};
	private boolean[] playersChosen = new boolean[2];
	private int[] timesSinceChoice = new int[2];
	private boolean dontTrace;
	private boolean gameOver;
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
		playerColors[0] = 0;playerColors[1] = 5;
	}
	public void update(GameContainer gc, int delta)throws SlickException{
		if(gameOver){juliusScoreFunction();}else if(colorSelect){chooseColor(delta);timeToStart();}else{
		moveDist = 5*delta*60/1000;
		turnPlayers();
		iPos[0] = players[0].getX();
		iPos[1] = players[0].getY();
		iPos[2] = players[1].getX();
		iPos[3] = players[1].getY();
		
		checkBoundaries();
		movePlayers();
		int xDiff = players[0].getX() - players[1].getX();
		int yDiff = players[0].getY() - players[1].getY();
		// this here is wrong but whatever
		if((Math.abs(xDiff) <= 32) && (Math.abs(yDiff) <= 16)){
			players[0].setX(iPos[0]);
			players[0].setY(iPos[1]);
			players[1].setX(iPos[2]);
			players[1].setY(iPos[3]);}
		checkTraceCollisions();}
	}
	public void turnPlayers(){
		int[] oldDir={players[0].getDir(),players[1].getDir()};
		while(true){
		if(buttons.isKeyDown(Input.KEY_RIGHT)){players[0].turn(0);
		if(oldDir[0]==1){players[0].setX(players[0].getX()+16);players[0].setY(players[0].getY()+8);}
		else if(oldDir[0]==3){players[0].setX(players[0].getX()+16);players[0].setY(players[0].getY()-8);}
		break;};
		if(buttons.isKeyDown(Input.KEY_LEFT)){players[0].turn(2);
		if(oldDir[0]==1){players[0].setX(players[0].getX()+0);players[0].setY(players[0].getY()+8);}
		else if(oldDir[0]==3){players[0].setX(players[0].getX()+0);players[0].setY(players[0].getY()-8);}
		break;};
		if(buttons.isKeyDown(Input.KEY_UP)){players[0].turn(1);
		if(oldDir[0]==0){players[0].setX(players[0].getX()-16);players[0].setY(players[0].getY()-8);}
		else if(oldDir[0]==2){players[0].setX(players[0].getX()+0);players[0].setY(players[0].getY()-8);}
		break;};
		if(buttons.isKeyDown(Input.KEY_DOWN)){players[0].turn(3);
		if(oldDir[0]==0){players[0].setX(players[0].getX()-16);players[0].setY(players[0].getY()+8);}
		else if(oldDir[0]==2){players[0].setX(players[0].getX()+0);players[0].setY(players[0].getY()+8);}
		break;};
		break;}
		while(true){
		if(buttons.isKeyDown(Input.KEY_D)){players[1].turn(0);
		if(oldDir[1]==1){players[1].setX(players[1].getX()+16);players[1].setY(players[1].getY()+8);}
		else if(oldDir[1]==3){players[1].setX(players[1].getX()+16);players[1].setY(players[1].getY()-8);}
		break;};
		if(buttons.isKeyDown(Input.KEY_A)){players[1].turn(2);
		if(oldDir[1]==1){players[1].setX(players[1].getX()+0);players[1].setY(players[1].getY()+8);}
		else if(oldDir[1]==3){players[1].setX(players[1].getX()+0);players[1].setY(players[1].getY()-8);}
		break;};
		if(buttons.isKeyDown(Input.KEY_W)){players[1].turn(1);
		if(oldDir[1]==0){players[1].setX(players[1].getX()-16);players[1].setY(players[1].getY()-8);}
		else if(oldDir[1]==2){players[1].setX(players[1].getX()+0);players[1].setY(players[1].getY()-8);}
		break;};
		if(buttons.isKeyDown(Input.KEY_S)){players[1].turn(3);
		if(oldDir[1]==0){players[1].setX(players[1].getX()-16);players[1].setY(players[1].getY()+8);}
		else if(oldDir[1]==2){players[1].setX(players[1].getX()+0);players[1].setY(players[1].getY()+8);}
		break;};
		break;}
		
	}
	public void checkBoundaries(){

		// I'm not sure why this doesn't work help
		
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
		if(buttons.isKeyDown(Input.KEY_K))
		if(!(playersChosen[1] && playerColors[0] == playerColors[1]))playersChosen[0]=true;
		if(!playersChosen[0]&&(timesSinceChoice[0]>=200)){
		if(buttons.isKeyDown(Input.KEY_RIGHT)){playerColors[0]++;timesSinceChoice[0]=0;};
		if(buttons.isKeyDown(Input.KEY_LEFT)){playerColors[0]--;timesSinceChoice[0]=0;}}
		if(buttons.isKeyDown(Input.KEY_F))
		if(!(playersChosen[0] && playerColors[0] == playerColors[1]))playersChosen[1]=true;
		if(!playersChosen[1]&&(timesSinceChoice[1]>=200)){
		if(buttons.isKeyDown(Input.KEY_D)){playerColors[1]++;timesSinceChoice[1]=0;}
		if(buttons.isKeyDown(Input.KEY_A)){playerColors[1]--;timesSinceChoice[1]=0;}}
		playerColors[0]+=8;playerColors[1]+=8;playerColors[0]%=8;playerColors[1]%=8;
	}
	public void timeToStart() throws SlickException{
		if(playersChosen[0]&&playersChosen[1]){
			colorSelect = false;
			players[0]=(new Cycle(playerColors[0], 100, 100, 0));
			players[1]=(new Cycle(playerColors[1], 540, 380, 2));
		}
	}
	public void checkTraceCollisions(){
	
	// THIS DOESN'T WORK HELP
	
	/*	for(Trace t:Trace.getTraces()){
			int xDiff1 = players[0].getX() - t.getX();
			int yDiff1 = players[0].getY() - t.getY();
			int xDiff2 = players[1].getX() - t.getX();
			int yDiff2 = players[1].getY() - t.getY();
			if(players[0].getDir()%2==0?(yDiff1<8)&&(xDiff1==0):(xDiff1<8)&&(yDiff1==0)) playerLose(0);
			if(players[1].getDir()%2==0?(yDiff2<8)&&(xDiff2==0):(xDiff2<8)&&(yDiff2==0)) playerLose(1);
		}
	*/
	}
	public void playerLose(int player){
		gameOver = true;
		// Julius is doing scores and stuff that will go here
	}
	public void juliusScoreFunction(){
		Trace.clearTraces();
		// This also falls to Julius
	}
	//convenience
	public Color rgb(int a, int b, int c){return new Color(a/255.0f, b/255.0f, c/255.0f);}
	public void drawColorSelect(Graphics g) throws SlickException{
		g.setColor(Color.white);
		// Instructions will change with controls
		g.drawString("Use left/right or a/d to choose, f to select p2 and k to select p1",15,50);
		g.setColor(bikeColors[playerColors[0]]);
		g.fillRect(175,215,50,50);
		g.fillRect(120,282,67,2);
		if(!playersChosen[0]){
		g.setColor(bikeColors[(playerColors[0]+1)%8]);
		g.fillRect(225,230,35,35);
		g.setColor(bikeColors[(playerColors[0]+2)%8]);
		g.fillRect(260,245,20,20);
		g.setColor(bikeColors[(playerColors[0]+7)%8]);
		g.fillRect(140,230,35,35);
		g.setColor(bikeColors[(playerColors[0]+6)%8]);
		g.fillRect(120,245,20,20);}
		new Cycle(playerColors[0],184,275,0).getImage().draw(184,275);
		g.setColor(bikeColors[playerColors[1]]);
		g.fillRect(415,215,50,50);
		g.fillRect(360,282,67,2);
		if(!playersChosen[1]){
		g.setColor(bikeColors[(playerColors[1]+1)%8]);
		g.fillRect(465,230,35,35);
		g.setColor(bikeColors[(playerColors[1]+2)%8]);
		g.fillRect(500,245,20,20);
		g.setColor(bikeColors[(playerColors[1]+7)%8]);
		g.fillRect(380,230,35,35);
		g.setColor(bikeColors[(playerColors[1]+6)%8]);
		g.fillRect(360,245,20,20);}
		new Cycle(playerColors[1],424,275,0).getImage().draw(424,275);
	}
	public void render(GameContainer gc, Graphics g)throws SlickException{
		if(colorSelect)drawColorSelect(g);else{
		for(Cycle p : players)p.getImage().draw(p.getX(),p.getY());
		for(Trace t : Trace.getTraces()){
			g.setColor(bikeColors[playerColors[t.getPlayer()]]);
			if(t.getH())g.fillRect(t.getX(),t.getY(),t.getSpeed(),2);else g.fillRect(t.getX(),t.getY(),2,t.getSpeed());
		}}
	}
}