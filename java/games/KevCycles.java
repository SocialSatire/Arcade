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
	private int[] playerTypes = new int[2];
	private Color[] bikeColors = {rgb(249,0,0), rgb(249,58,0), rgb(249,199,0), 
	rgb(27,156,0), rgb(0,249,216), rgb(0,55,193), rgb(101,0,158), rgb(249,0,157)};
	private boolean[] playersChosen = new boolean[2];
	private int[] timesSinceChoice = new int[4];
	private boolean dontTrace;
	private boolean gameOver;
	private int countDown;
	private String[] playerNames = new String[2];
	private boolean[] selectOverride = new boolean[2];
	public KevCycles(String playerOne, String playerTwo){
		super("Cool Game.exe");
		playerNames[0] = playerOne; playerNames[1] = playerTwo;
	}
	public static void main(String[] args)throws SlickException{
		AppGameContainer app = new AppGameContainer(new KevCycles(args[0], args[1]),640,480,false);
		app.start();
	}
	public void init(GameContainer gc)throws SlickException {
		buttons = gc.getInput();
		gc.setTargetFrameRate(60);
		new Trace(-10,-10,true,moveDist,0);//shut up
		colorSelect=true;countDown=4;
		playerColors[0] = 0;playerColors[1] = 5;
		playerTypes[0] = 0;playerTypes[1] = 0;
		int temp = 0;
		for(String s : playerNames){
			if(s.equals("mike") || s.equals("kevin") || s.equals("jacques") || s.equals("julius") || s.equals("robert") || s.equals("markward")){
				selectOverride[temp] = true; playersChosen[temp] = true; playerColors[temp] = -1;}
			temp++;}
	}
	public void update(GameContainer gc, int delta)throws SlickException{
		try{if(gameOver){juliusScoreFunction();}else if(colorSelect){chooseColor(delta);timeToStart(countDown);}else{
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
		checkTraceCollisions();
		//for(Cycle p : players) if(p.getCustom().equals("mike")) p.getAnimated().update(delta);
		}}catch(InterruptedException i){;};
	}
	public void turnPlayers() throws SlickException{
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
		if(!selectOverride[0]){playerColors[0]+=8;playerColors[0]%=8;}
		if(!selectOverride[1]){playerColors[1]+=8;playerColors[1]%=8;}
		timesSinceChoice[2]+=delta;timesSinceChoice[3]+=delta;
		if(!playersChosen[0]&&(timesSinceChoice[2]>=200)){
		if(buttons.isKeyDown(Input.KEY_DOWN)){playerTypes[0]++;timesSinceChoice[2]=0;};
		if(buttons.isKeyDown(Input.KEY_UP)){playerTypes[0]--;timesSinceChoice[2]=0;}}
		if(!playersChosen[1]&&(timesSinceChoice[3]>=200)){
		if(buttons.isKeyDown(Input.KEY_S)){playerTypes[1]++;timesSinceChoice[3]=0;}
		if(buttons.isKeyDown(Input.KEY_W)){playerTypes[1]--;timesSinceChoice[3]=0;}}
		if(!selectOverride[0]){playerTypes[0]+=6;playerTypes[0]%=6;}
		if(!selectOverride[1]){playerTypes[1]+=6;playerTypes[1]%=6;}
		
	}
	public void timeToStart(int c) throws SlickException, InterruptedException{
		if(playersChosen[0]&&playersChosen[1])if(countDown == 0){
			colorSelect = false;Thread.sleep(1000);
			if(playerColors[0]!=-1) players[0] = new Cycle(playerColors[0],100,100,0,playerTypes[0]);
			else if(playerNames[0].equals("mike"))players[0]=new Cycle(100,100,0,"mike");//shut up this works
			else players[0]=new Cycle(100,100,0,playerNames[0]);
			if(playerColors[1]!=-1) players[1] = new Cycle(playerColors[1],540,380,2,playerTypes[1]);
			else if(playerNames[1].equals("mike"))players[1]=new Cycle(540,380,2,"mike");
			else players[1]=new Cycle(540,380,2,playerNames[1]);
		}
		else{countDown--; Thread.sleep(1000);}
	}
	public void checkTraceCollisions(){
	/*

		for(Trace t : Trace.getTraces()){
			if(t!=null && players[0]!=null && players[1]!=null){
				if(players[0].getX()-t.getX() <= 6 && players[0].getY()-t.getY() <= 6){
					winner = 1;
				}
				else if(players[1].getX()-t.getX() <= 6 && players[1].getY()-t.getY() <= 6){
					winner = 0;
				}
				else{
					break;
				}
			}
			
		}*/
	
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
		int[] temp = {(playerNames[0].equals("mike")?-8:0),(playerNames[1].equals("mike")?-8:0)};
		g.setColor(Color.white);
		// Instructions will change with controls
		g.drawString("Use left/right or a/d to choose, f to select p2 and k to select p1",15,50);
		if(playerColors[0] != -1)g.setColor(bikeColors[playerColors[0]]);
		else g.setColor(Cycle.getCustomColor(playerNames[0]));
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
		if(playerColors[0] != -1)new Cycle(playerColors[0],184,275,0,playerTypes[0]).getImage().draw(184,275);
		else new Cycle(184,275,0,playerNames[0]).getImage().draw(184+temp[0],275+temp[0]);
		
		if(playerColors[1] != -1)g.setColor(bikeColors[playerColors[1]]);
		else g.setColor(Cycle.getCustomColor(playerNames[1]));
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
		if(playerColors[1] != -1)new Cycle(playerColors[1],424,275,0,playerTypes[1]).getImage().draw(424,275);
		else new Cycle(424,275,0,playerNames[1]).getImage().draw(424+temp[1],275+temp[1]);
		
	}
	public void render(GameContainer gc, Graphics g)throws SlickException{
		g.drawString(""+playerNames[0],50,50);
		if((countDown > 0)&&playersChosen[0]&&playersChosen[1]){g.setColor(Color.white); 
		g.drawString(""+countDown,320,240);}
		if(colorSelect)drawColorSelect(g);else{
		for(Cycle p : players)
			if(!p.getCustom().equals("mike"))p.getImage().draw(p.getX(),p.getY());
			else {if(p.getDir()%2 == 1)p.getAnimated().draw(p.getX(),p.getY()-8);
			else p.getAnimated().draw(p.getX()-8,p.getY()-8);}
		for(Trace t : Trace.getTraces()){
			if(playerColors[t.getPlayer()] != -1)g.setColor(bikeColors[playerColors[t.getPlayer()]]);
			else g.setColor(Cycle.getCustomColor(playerNames[t.getPlayer()]));
			if(t.getH())g.fillRect(t.getX(),t.getY(),t.getSpeed(),2);else g.fillRect(t.getX(),t.getY(),2,t.getSpeed());
		}}
	}
}