import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import java.io.InputStream;
import org.newdawn.slick.TrueTypeFont;
import java.io.IOException;
import java.awt.Font;
import PhotonBikes.*;

// If your virgin eyes cannot handle the sight of my code (jacques) http://codebeautify.org/javaviewer

public class PhotonBikes extends BasicGame{

	private Cycle[] players = new Cycle[2];
	private Input buttons;
	private int[] iPos = new int[4];
	private int[] moveDist = new int[2];
	private boolean colorSelect,m;
	private int[] playerColors = new int[2];
	private int[] playerTypes = new int[2];
	private Color[] bikeColors = {rgb(249,0,0), rgb(249,58,0), rgb(249,199,0), 
	rgb(27,156,0), rgb(0,249,216), rgb(0,55,193), rgb(101,0,158), rgb(249,0,157)};
	private boolean[] playersChosen = new boolean[2];
	private int[] timesSinceChoice = new int[4];
	private boolean dontTrace;
	private String[] playerNames = new String[2];
	private boolean[] selectOverride = new boolean[2];
	private int winner = -1;
	private int[] score = {0,0};
	private static String notify = "";
	private int temp = 0;
	private int[] sp = new int[4];
	private boolean[] spr = new boolean[4];
	private boolean[] j = new boolean[2];
	private int[] jTime = new int[2];
	private boolean rInit = false;
	private boolean rStart = false;
	private boolean scoreScreen = false;
	private boolean[] ready = new boolean[2];
	private int creditTimer = 0;
	private String[] codes = new String[2];
	private int[] cDelay = new int[2];
	private boolean speedMode = false;
	private String[][] cBike = {{"zcvzxncvcxcz","q","xxxxxx","q","q","q"},{"134126343231","q","222222","q","q","q"}};
	private String[] cNames = {"mike","kevin","jacques","julius","markward","robert"};
	private Audio[] bgm = new Audio[3];
	private int pDisp = 0;
	private int[] turnDist = new int[2];
	private TrueTypeFont font, bigFont, massiveFont;
	private int prevWinner = -1;
	
	private String notification;
	
	public PhotonBikes(){
		super("");
	}
	public static void main(String[] args)throws SlickException{
		AppGameContainer app = new AppGameContainer(new PhotonBikes(),1280,1024,true);
		app.start();
	}
	public void init(GameContainer gc)throws SlickException{
		buttons = gc.getInput();
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		new Trace(-10,-10,true,1,0);
		colorSelect=true;m=false;
		playerColors[0] = 0;playerColors[1] = 5;
		playerTypes[0] = 0;playerTypes[1] = 0;
		for(int i=0;i<4;i++) sp[i]=200;
		jTime[0]=0;jTime[1]=0;
		playerNames[0]="";playerNames[1]="";
		selectOverride[0]=false;selectOverride[1]=false;
		cDelay[0]=0;cDelay[1]=0;codes[0]="";codes[1]="";
		playersChosen[0]=false;playersChosen[1]=false;
		try{bgm[0]=AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("PhotonBikes\\audio\\music.wav"));
		bgm[1]=AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("PhotonBikes\\audio\\music_nightcore.wav"));
		bgm[2]=AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("PhotonBikes\\audio\\music_menu.wav"));
		InputStream inputStream	= ResourceLoader.getResourceAsStream("PhotonBikes\\font.ttf");
		Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		font = new TrueTypeFont(awtFont.deriveFont(12f), false);	
		bigFont = new TrueTypeFont(awtFont.deriveFont(24f), false);	
		massiveFont = new TrueTypeFont(awtFont.deriveFont(48f), false);	
		}catch(Exception i){;};
		startMusic();
	}
	public void update(GameContainer gc, int delta)throws SlickException{
	try{if(scoreScreen)scoring(delta);
		else if(colorSelect){chooseColor(delta);timeToStart();}
		else if(!rInit) initRound(gc);
		else if(!rStart) startRound();
		else if(winner != -1)pauseDisp(gc, delta);
		else playRound(gc,delta);
		}catch(Exception i){;};
	}
	public void playRound(GameContainer gc, int delta) throws SlickException, InterruptedException{
		if((buttons.isKeyDown(Input.KEY_B)&&playerNames[0].equals("mike"))||(buttons.isKeyDown(Input.KEY_5)&&playerNames[1].equals("mike")))m=true;else m=false;
		if(buttons.isKeyDown(Input.KEY_Z))spr[0]=true;
		if(buttons.isKeyDown(Input.KEY_1))spr[1]=true;
		if(buttons.isKeyDown(Input.KEY_X))spr[2]=true;
		if(buttons.isKeyDown(Input.KEY_2))spr[3]=true;
		moveDist[0] = speedMode?(5*delta*60/500):(5*delta*60/1000); 
		moveDist[1] = speedMode?(5*delta*60/500):(5*delta*60/1000);
		if(spr[0]&&sp[0]>=10){moveDist[0]=speedMode?(5*delta*60/250):(5*delta*60/500); sp[0]-=20;}
		if(spr[1]&&sp[1]>=10){moveDist[1]=speedMode?(5*delta*60/250):(5*delta*60/500); sp[1]-=20;}
		if(spr[2]&&sp[2]>=190){j[0]=true; jTime[0]=0; sp[2]-=200;}
		if(spr[3]&&sp[3]>=190){j[1]=true; jTime[1]=0; sp[3]-=200;}
		if((jTime[0]>=300)&&j[0]){j[0]=false; jTime[0]=0;}
		if((jTime[1]>=300)&&j[1]){j[1]=false; jTime[1]=0;}
		for(int i=0;i<4;i++){
			if(sp[i]<200) sp[i]+=((delta/200)+1);
			if(sp[i]>200) sp[i]=200;}
		turnPlayers();
		iPos[0] = players[0].getX();
		iPos[1] = players[0].getY();
		iPos[2] = players[1].getX();
		iPos[3] = players[1].getY();
		checkBoundaries(gc);
		movePlayers();
		int xDiff = players[0].getX() - players[1].getX();
		int yDiff = players[0].getY() - players[1].getY();
		if((Math.abs(xDiff) <= 32) && (Math.abs(yDiff) <= 16))winner = 2;
		checkTraceCollisions(gc);
		spr[0]=false;spr[1]=false;spr[2]=false;spr[3]=false;
		if(j[0])jTime[0]+=delta;if(j[1])jTime[1]+=delta;
	}
	public void turnPlayers() throws SlickException{
		int[] oldDir={players[0].getDir(),players[1].getDir()};
		while(turnDist[0]>17){
			if(buttons.isKeyDown(Input.KEY_D)&&!(players[0].getDir()==2)){
				players[0].turn(0);turnDist[0]=0;
				if(oldDir[0]==1){players[0].setX(players[0].getX()+16);players[0].setY(players[0].getY()+16);}
				else if(oldDir[0]==3){players[0].setX(players[0].getX()+16);players[0].setY(players[0].getY()-16);}
				break;};
			if(buttons.isKeyDown(Input.KEY_A)&&!(players[0].getDir()==0)){
				players[0].turn(2);turnDist[0]=0;
				if(oldDir[0]==1){players[0].setX(players[0].getX()-16);players[0].setY(players[0].getY()+16);}
				else if(oldDir[0]==3){players[0].setX(players[0].getX()-16);players[0].setY(players[0].getY()-16);}
				break;};
			if(buttons.isKeyDown(Input.KEY_W)&&!(players[0].getDir()==3)){
				players[0].turn(1);turnDist[0]=0;
				if(oldDir[0]==0){players[0].setX(players[0].getX()-16);players[0].setY(players[0].getY()-16);}
				else if(oldDir[0]==2){players[0].setX(players[0].getX()+16);players[0].setY(players[0].getY()-16);}
				break;};
			if(buttons.isKeyDown(Input.KEY_S)&&!(players[0].getDir()==1)){
				players[0].turn(3);turnDist[0]=0;
				if(oldDir[0]==0){players[0].setX(players[0].getX()-16);players[0].setY(players[0].getY()+16);}
				else if(oldDir[0]==2){players[0].setX(players[0].getX()+16);players[0].setY(players[0].getY()+16);}
				break;};
			break;}
		while(turnDist[1]>17){
			if(buttons.isKeyDown(Input.KEY_L)&&!(players[1].getDir()==2)){
				players[1].turn(0);turnDist[1]=0;
				if(oldDir[1]==1){players[1].setX(players[1].getX()+16);players[1].setY(players[1].getY()+16);}
				else if(oldDir[1]==3){players[1].setX(players[1].getX()+16);players[1].setY(players[1].getY()-16);}
				break;};
			if(buttons.isKeyDown(Input.KEY_J)&&!(players[1].getDir()==0)){
				players[1].turn(2);turnDist[1]=0;
				if(oldDir[1]==1){players[1].setX(players[1].getX()-16);players[1].setY(players[1].getY()+16);}
				else if(oldDir[1]==3){players[1].setX(players[1].getX()-16);players[1].setY(players[1].getY()-16);}
				break;};
			if(buttons.isKeyDown(Input.KEY_I)&&!(players[1].getDir()==3)){
				players[1].turn(1);turnDist[1]=0;
				if(oldDir[1]==0){players[1].setX(players[1].getX()-16);players[1].setY(players[1].getY()-16);}
				else if(oldDir[1]==2){players[1].setX(players[1].getX()+16);players[1].setY(players[1].getY()-16);}
				break;};
			if(buttons.isKeyDown(Input.KEY_K)&&!(players[1].getDir()==1)){
				players[1].turn(3);turnDist[1]=0;
				if(oldDir[1]==0){players[1].setX(players[1].getX()-16);players[1].setY(players[1].getY()+16);}
				else if(oldDir[1]==2){players[1].setX(players[1].getX()+16);players[1].setY(players[1].getY()+16);}
				break;};
			break;}
	}
	public void checkBoundaries(GameContainer gc){		
		if(players[0].getDir()%2==0){
			if(players[0].getX() <= 0){players[0].setX(0); winner = 1;};
			if(players[0].getX()+32 >= 1280){players[0].setX(1280-32); winner = 1;};
			if(players[0].getY() <= 0){players[0].setY(0); winner = 1;};
			if(players[0].getY()+16 >= 1024){players[0].setY(1024-16); winner = 1;};}
		else{
			if(players[0].getX() <= 0){players[0].setX(0); winner = 1;};
			if(players[0].getX()+16 >= 1280){players[0].setX(1280-16); winner = 1;};
			if(players[0].getY() <= 0){players[0].setY(0); winner = 1;};
			if(players[0].getY()+32 >= 1024){players[0].setY(1024-32); winner = 1;};};
		if(players[1].getDir()%2==0){
			if(players[1].getX() <= 0){players[1].setX(0); winner = 0;};
			if(players[1].getX()+32 >= 1280){players[1].setX(1280-32); winner = 0;};
			if(players[1].getY() <= 0){players[1].setY(0); winner = 0;};
			if(players[1].getY()+16 >= 1024){players[1].setY(1024-16); winner = 0;};}
		else{
			if(players[1].getX() <= 0){players[1].setX(0); winner = 0;};
			if(players[1].getX()+16 >= 1280){players[1].setX(1280-16); winner = 0;};
			if(players[1].getY() <= 0){players[1].setY(0); winner = 0;};
			if(players[1].getY()+32 >= 1024){players[1].setY(1024-32); winner = 0;};};
	}
	public void movePlayers(){
		if(m&&playerNames[0].equals("mike"))j[0]=true;
		if(m&&playerNames[1].equals("mike"))j[1]=true;
		if(players[0].getDir()==0){
			players[0].setX(players[0].getX()+moveDist[0]);
			if(!j[0])new Trace(players[0].getX()-moveDist[0],players[0].getY()+7,true,moveDist[0],0);};
		if(players[0].getDir()==2){
			players[0].setX(players[0].getX()-moveDist[0]);
			if(!j[0])new Trace(players[0].getX()+32,players[0].getY()+7,true,moveDist[0],0);};
		if(players[0].getDir()==1){
			players[0].setY(players[0].getY()-moveDist[0]);
			if(!j[0])new Trace(players[0].getX()+7,players[0].getY()+32,false,moveDist[0],0);};
		if(players[0].getDir()==3){
			players[0].setY(players[0].getY()+moveDist[0]);
			if(!j[0])new Trace(players[0].getX()+7,players[0].getY()-moveDist[0],false,moveDist[0],0);};
		if(players[1].getDir()==0){
			players[1].setX(players[1].getX()+moveDist[1]);
			if(!j[1])new Trace(players[1].getX()-moveDist[1],players[1].getY()+7,true,moveDist[1],1);};
		if(players[1].getDir()==2){
			players[1].setX(players[1].getX()-moveDist[1]);
			if(!j[1])new Trace(players[1].getX()+32,players[1].getY()+7,true,moveDist[1],1);};
		if(players[1].getDir()==1){
			players[1].setY(players[1].getY()-moveDist[1]);
			if(!j[1])new Trace(players[1].getX()+7,players[1].getY()+32,false,moveDist[1],1);};
		if(players[1].getDir()==3){
			players[1].setY(players[1].getY()+moveDist[1]);
			if(!j[1])new Trace(players[1].getX()+7,players[1].getY()-moveDist[1],false,moveDist[1],1);};
		turnDist[0]+=moveDist[0];turnDist[1]+=moveDist[1];
		if(m&&playerNames[0].equals("mike"))j[0]=false;
		if(m&&playerNames[1].equals("mike"))j[1]=false;
	}
	public void chooseColor(int delta){
		checkCode(delta);
		timesSinceChoice[0]+=delta;timesSinceChoice[1]+=delta;
		if(buttons.isKeyDown(Input.KEY_B))
		if(!(playersChosen[1] && (playerColors[0] == playerColors[1])))playersChosen[0]=true;
		if(!playersChosen[0]&&(timesSinceChoice[0]>=200)){
			if(buttons.isKeyDown(Input.KEY_D)){playerColors[0]++;timesSinceChoice[0]=0;};
			if(buttons.isKeyDown(Input.KEY_A)){playerColors[0]--;timesSinceChoice[0]=0;}}
		if(buttons.isKeyDown(Input.KEY_5))
		if(!(playersChosen[0] && (playerColors[0] == playerColors[1])))playersChosen[1]=true;
		if(!playersChosen[1]&&(timesSinceChoice[1]>=200)){
			if(buttons.isKeyDown(Input.KEY_L)){playerColors[1]++;timesSinceChoice[1]=0;}
			if(buttons.isKeyDown(Input.KEY_J)){playerColors[1]--;timesSinceChoice[1]=0;}}
		if(!selectOverride[0]){playerColors[0]+=8;playerColors[0]%=8;}
		if(!selectOverride[1]){playerColors[1]+=8;playerColors[1]%=8;}
		timesSinceChoice[2]+=delta;timesSinceChoice[3]+=delta;
		if(!playersChosen[0]&&(timesSinceChoice[2]>=200)){
			if(buttons.isKeyDown(Input.KEY_S)){playerTypes[0]++;timesSinceChoice[2]=0;};
			if(buttons.isKeyDown(Input.KEY_W)){playerTypes[0]--;timesSinceChoice[2]=0;}}
		if(!playersChosen[1]&&(timesSinceChoice[3]>=200)){
			if(buttons.isKeyDown(Input.KEY_K)){playerTypes[1]++;timesSinceChoice[3]=0;}
			if(buttons.isKeyDown(Input.KEY_I)){playerTypes[1]--;timesSinceChoice[3]=0;}}
		if(!selectOverride[0]){playerTypes[0]+=6;playerTypes[0]%=6;}
		if(!selectOverride[1]){playerTypes[1]+=6;playerTypes[1]%=6;}
		if(speedMode){
			playerColors[0]=0;playerColors[1]=0;playerTypes[0]=1;playerTypes[1]=1;
			playersChosen[0]=true;playersChosen[1]=true;}
	}
	public void checkTraceCollisions(GameContainer gc) throws InterruptedException{
		/*if(players[0]!=null && players[1]!=null){
			int[] xDist = {(players[0].getDir()%2 == 0)?32:16,(players[1].getDir()%2 == 0)?32:16};
			int[] yDist = {(players[0].getDir()%2 == 0)?32:16,(players[1].getDir()%2 == 0)?32:16};
			int n = 0;
			for(Trace t : Trace.getTraces()){
				if(Trace.getTraces().size()-n<=10)break; n++;
				if(t!=null){
					if(players[0].getX()-t.getX() >= -(xDist[0]-1) && players[0].getX()-t.getX() < 1 
					&& players[0].getY()-t.getY() >= -(yDist[0]-1) && players[0].getY()-t.getY() < 1 && !j[0]){winner = 1;}
					else if(players[1].getX()-t.getX() >= -(xDist[1]-1) && players[1].getX()-t.getX() <= 1 
					&& players[1].getY()-t.getY() >= -(yDist[1]-1) && players[1].getY()-t.getY() < 1 && !j[1]){winner = 0;}
				}
			}
		}*/
		if(players[0]!=null&&players[1]!=null){
			int[][] boundaries = new int[2][4];
			boundaries[0]=((players[0].getDir()%2)==0)?new int[]{players[0].getX()+32,players[0].getY(),players[0].getX(),players[0].getY()+16}:new int[]{players[0].getX()+16,players[0].getY(),players[0].getX(),players[0].getY()+32};
			boundaries[1]=((players[1].getDir()%2)==0)?new int[]{players[1].getX()+32,players[1].getY(),players[1].getX(),players[1].getY()+16}:new int[]{players[1].getX()+16,players[1].getY(),players[1].getX(),players[1].getY()+32};
			//notification = ""+boundaries[0][0]+", "
			for(Trace t : Trace.getTraces()){
				if(Trace.getTraces().size()-Trace.getTraces().indexOf(t)<10)break;
				if(t!=null){
					if(t.getX()<boundaries[0][0] && t.getX()>boundaries[0][2] && t.getY()>boundaries[0][1] && t.getY()<boundaries[0][3] && !j[0])winner=1;
					else if(t.getX()<boundaries[1][0] && t.getX()>boundaries[1][2] && t.getY()>boundaries[1][1] && t.getY()<boundaries[1][3] && !j[1])winner=0;
				}
			}
		}
	}
	public void timeToStart() throws SlickException, InterruptedException{
		if(playersChosen[0]&&playersChosen[1]){
			stopMusic();
			colorSelect = false;
			startMusic();
			players[0] = null; players[1] = null;}
	}
	public void endGame(GameContainer gc) throws SlickException, InterruptedException{
		Trace.clearTraces();
		if(winner != 2)score[winner]++;
		else if(score[0]<4 && score[1]<4){score[0]++;score[1]++;}
		if(score[0] == 5 || score[1] == 5){
			scoreScreen = true;ready[0]=false;ready[1]=false;
			players[0].turn(0);players[1].turn(0);}
		else{prevWinner = winner; winner = -1; rInit = false; rStart = false;}
	}
	public void initRound(GameContainer gc) throws SlickException{
		players[0] = null; players[1] = null;
		if(playerColors[0]!=-1) players[0] = new Cycle(playerColors[0],100,100,0,playerTypes[0]);
		else players[0]=new Cycle(100,100,0,playerNames[0]);
		if(playerColors[1]!=-1) players[1] = new Cycle(playerColors[1],1180,924,2,playerTypes[1]);
		else players[1]=new Cycle(1180,924,2,playerNames[1]);
		for(int i=0;i<4;i++) sp[i]=200;
		jTime[0]=0;jTime[1]=0;
		ready[0]=false;ready[1]=false;
		gc.setTargetFrameRate(60);
		rInit=true;
	}
	public void startRound(){
		pDisp=0;
		if(buttons.isKeyDown(Input.KEY_B))ready[0]=true;
		if(buttons.isKeyDown(Input.KEY_5))ready[1]=true;
		if(ready[0]&&ready[1])rStart=true;
	}
	public void pauseDisp(GameContainer gc, int delta) throws SlickException, InterruptedException{
		if(pDisp>2000) endGame(gc);
		else pDisp+=delta;
	}
	public void scoring(int delta){
		/*creditTimer+=delta;
		if(buttons.isKeyDown(Input.KEY_SPACE)&&creditTimer>=200){
			ArcadeMenu.setCredits(ArcadeMenu.getCredits()+1);
			creditTimer=0;}*/
		if(buttons.isKeyDown(Input.KEY_X)||buttons.isKeyDown(Input.KEY_2)){
			stopMusic();
			ArcadeMenu.setRunning(false);}
		//if(ArcadeMenu.getCredits()>=1){
			if(buttons.isKeyDown(Input.KEY_B))ready[0]=true;
			if(buttons.isKeyDown(Input.KEY_5))ready[1]=true;
			if(ready[0]&&ready[1]){scoreScreen = false; score[0]=0; score[1]=0;}//}
	}
	public void checkCode(int delta){
		if(anyKey(0)&&(cDelay[0]>100)){
			codes[0] += getKeyStr(0);
			cDelay[0]=0;}
		if(anyKey(1)&&(cDelay[1]>100)){
			codes[1] += getKeyStr(1);
			cDelay[1]=0;}
		if(cDelay[0]>500)codes[0]="";
		if(cDelay[1]>500)codes[1]="";
		if(codes[0].equals("zxzxvc")||codes[1].equals("121243"))speedMode=true;
		for(int i=0;i<2;i++)
			for(int j=0;j<6;j++){
				if(codes[i].equals(cBike[i][j])){
					playerColors[i]=-1;
					playerNames[j]=cNames[j];
					playersChosen[i]=true;
					selectOverride[i]=true;}}
		cDelay[0]+=delta;cDelay[1]+=delta;
	}
	public boolean anyKey(int player){
		if(player==0)return buttons.isKeyDown(Input.KEY_Z)||buttons.isKeyDown(Input.KEY_X)||buttons.isKeyDown(Input.KEY_C)||
						buttons.isKeyDown(Input.KEY_V)||buttons.isKeyDown(Input.KEY_B)||buttons.isKeyDown(Input.KEY_N);
		if(player==1)return buttons.isKeyDown(Input.KEY_1)||buttons.isKeyDown(Input.KEY_2)||buttons.isKeyDown(Input.KEY_3)||
						buttons.isKeyDown(Input.KEY_4)||buttons.isKeyDown(Input.KEY_5)||buttons.isKeyDown(Input.KEY_6);
		return false;
	}
	public String getKeyStr(int player){
		if(player==0){
			if(buttons.isKeyDown(Input.KEY_Z))return "z";
			if(buttons.isKeyDown(Input.KEY_X))return "x";
			if(buttons.isKeyDown(Input.KEY_C))return "c";
			if(buttons.isKeyDown(Input.KEY_V))return "v";
			if(buttons.isKeyDown(Input.KEY_B))return "b";
			if(buttons.isKeyDown(Input.KEY_N))return "n";}
		if(player==1){
			if(buttons.isKeyDown(Input.KEY_1))return "1";
			if(buttons.isKeyDown(Input.KEY_2))return "2";
			if(buttons.isKeyDown(Input.KEY_3))return "3";
			if(buttons.isKeyDown(Input.KEY_4))return "4";
			if(buttons.isKeyDown(Input.KEY_5))return "5";
			if(buttons.isKeyDown(Input.KEY_6))return "6";}
		return "";
	}
	public void startMusic(){
		if(colorSelect)bgm[2].playAsMusic(1,6,true);
		else if(speedMode)bgm[1].playAsMusic(1,6,true);
		else bgm[0].playAsMusic(1,6,true);
	}
	public void stopMusic(){
		if(colorSelect)bgm[2].stop();
		else if(speedMode)bgm[1].stop();
		else bgm[0].stop();
	}
	public boolean isMusic(){return bgm[0].isPlaying()||bgm[1].isPlaying()||bgm[2].isPlaying();}
	//convenience
	public Color rgb(int a, int b, int c){return new Color(a/255.0f, b/255.0f, c/255.0f);}
	public Color makeTransparent(Color c, float a){return new Color((float)c.getRed(),(float)c.getGreen(),(float)c.getBlue(),a);}
	public void drawColorSelect(Graphics g) throws SlickException{
		new Image("PhotonBikes\\sprites\\maintitle.png").draw(40,100);
		g.setColor(Color.white);
		int pos = 640-(bigFont.getWidth("Use left/right to select color and up/down to select type.")/2);
		bigFont.drawString(pos,300,"Use left/right to select color and up/down to select type.",Color.white);
		pos = 640-(bigFont.getWidth("In-game, press green to boost and red to jump.")/2);
		bigFont.drawString(pos,340,"In-game, press green to boost and red to jump.",Color.white);
		pos = 640-(bigFont.getWidth("First to 5 points wins.")/2);
		bigFont.drawString(pos,365,"First to 5 points wins.",Color.white);
		pos = 640-(massiveFont.getWidth("Press BLACK to select.")/2);
		massiveFont.drawString(pos,800,"Press BLACK to select.",Color.white);
		if(playerColors[0] != -1)g.setColor(bikeColors[playerColors[0]]);
		else g.setColor(Cycle.getCustomColor(playerNames[0]));
		g.fillRect(402,487,50,50);
		g.fillRect(340,590,70,2);
		if(!playersChosen[0]){
			new Cycle(playerColors[0],411,547,0,(playerTypes[0]+5)%6).getImage().draw(411,547,new Color(1f,1f,1f,0.5f));
			g.fillRect(340,554,70,2);
			new Cycle(playerColors[0],411,619,0,(playerTypes[0]+1)%6).getImage().draw(411,619,new Color(1f,1f,1f,0.5f));
			g.fillRect(340,626,70,2);
			g.setColor(bikeColors[(playerColors[0]+1)%8]);
			g.fillRect(452,502,35,35);
			g.setColor(bikeColors[(playerColors[0]+2)%8]);
			g.fillRect(487,517,20,20);
			g.setColor(bikeColors[(playerColors[0]+7)%8]);
			g.fillRect(367,502,35,35);
			g.setColor(bikeColors[(playerColors[0]+6)%8]);
			g.fillRect(347,517,20,20);}
		if(playerColors[0] != -1)new Cycle(playerColors[0],411,583,0,playerTypes[0]).getImage().draw(411,583);			
		else new Cycle(411,583,0,playerNames[0]).getImage().draw(411,583);
		if(playerColors[1] != -1)g.setColor(bikeColors[playerColors[1]]);
		else g.setColor(Cycle.getCustomColor(playerNames[1]));
		g.fillRect(828,487,50,50);
		g.fillRect(766,590,67,2);
		if(!playersChosen[1]){
			new Cycle(playerColors[1],837,547,0,(playerTypes[1]+5)%6).getImage().draw(837,547,new Color(1f,1f,1f,0.5f));
			// g.fillRect(766,554,70,2);
			new Cycle(playerColors[1],837,619,0,(playerTypes[1]+1)%6).getImage().draw(837,619,new Color(1f,1f,1f,0.5f));
			g.fillRect(766,626,70,2);
			g.setColor(bikeColors[(playerColors[1]+1)%8]);
			g.fillRect(878,502,35,35);
			g.setColor(bikeColors[(playerColors[1]+2)%8]);
			g.fillRect(913,517,20,20);
			g.setColor(bikeColors[(playerColors[1]+7)%8]);
			g.fillRect(793,502,35,35);
			g.setColor(bikeColors[(playerColors[1]+6)%8]);
			g.fillRect(773,517,20,20);}
		if(playerColors[1] != -1)new Cycle(playerColors[1],837,583,0,playerTypes[1]).getImage().draw(837,583);
		else new Cycle(837,583,0,playerNames[1]).getImage().draw(837,583);
	}
	
	public void render(GameContainer gc, Graphics g)throws SlickException{
		if(!scoreScreen){
			g.setColor(Color.white);
			if(rInit&&!rStart){
				if(prevWinner==2){
					bigFont.drawString(640-(bigFont.getWidth("Tie")/2),400,"Tie",g.getColor());
				}
				else if(prevWinner!=-1){
					if(playerColors[prevWinner] != -1)g.setColor(bikeColors[playerColors[prevWinner]]);
					else g.setColor(Cycle.getCustomColor(playerNames[prevWinner]));
					bigFont.drawString(640-(bigFont.getWidth("The winner was player "+(prevWinner+1))/2),400,"The winner was player "+(prevWinner+1),g.getColor());}
				massiveFont.drawString(640-(massiveFont.getWidth("Press black to start.")/2),500,"Press black to start.",Color.white);}
			if(playerColors[0] != -1)g.setColor(bikeColors[playerColors[0]]);
			else g.setColor(Cycle.getCustomColor(playerNames[0]));
			font.drawString(140-(font.getWidth("Player 1 Score: "+score[0])/2)+13,25,"Player 1 Score: "+score[0],g.getColor());
			if(playerColors[1] != -1)g.setColor(bikeColors[playerColors[1]]);
			else g.setColor(Cycle.getCustomColor(playerNames[1]));
			font.drawString(140-(font.getWidth("Player 2 Score: "+score[0])/2)+993,25,"Player 2 Score: "+score[1],g.getColor());
			font.drawString(25,50,"Boost:",Color.white);
			font.drawString(27,72,"Jump:",Color.white);
			font.drawString(1005,50,"Boost:",Color.white);
			font.drawString(1007,72,"Jump:",Color.white);
			//font.drawString(100,100,notification,Color.white);
			g.setColor(rgb(0,255,255));
			g.fillRect(80,50,sp[0],20);
			g.fillRect(1060,50,sp[1],20);
			g.setColor(rgb(255,128,0));
			g.fillRect(80,72,sp[2],20);
			g.fillRect(1060,72,sp[3],20);
			if(colorSelect)drawColorSelect(g);
			else{
				for(Cycle p : players)
					if(p!=null&&!(m&&p.getCustom().equals("mike")))p.getImage().draw(p.getX()+((p.getDir()%2==1)?-8:0),p.getY()+((p.getDir()%2==1)?+8:0));
				for(Trace t : Trace.getTraces()){
					if(playerColors[t.getPlayer()] != -1)g.setColor(bikeColors[playerColors[t.getPlayer()]]);
					else g.setColor(Cycle.getCustomColor(playerNames[t.getPlayer()]));
					if(t.getH())g.fillRect(t.getX(),t.getY(),t.getSpeed(),2);
					else g.fillRect(t.getX(),t.getY(),2,t.getSpeed());}}}
		else{
			if(playerColors[(score[0]==5)?0:1] != -1)g.setColor(bikeColors[playerColors[(score[0]==5)?0:1]]);
			else g.setColor(Cycle.getCustomColor(playerNames[(score[0]==5)?0:1]));
			String text="The winner is player " + ((score[0]==5)?"1":"2") + "!";
			int pos = 640-(massiveFont.getWidth(text)/2);
			massiveFont.drawString(pos,300,text,g.getColor());
			/*pos = 640-(bigFont.getWidth("Insert Credits and press black to play again.")/2);
			bigFont.drawString(pos,400,"Insert Credits and press black to play again.",g.getColor());*/
			pos = 640-(bigFont.getWidth("Press red to return to the menu.")/2);
			bigFont.drawString(pos,425,"Press red to return to the menu.",g.getColor());
			Image img = players[(score[0]==5)?0:1].getImage();
			img.setFilter(Image.FILTER_NEAREST);
			img.draw(480,620,10f);
		}}
	
	}