//Imports

import org.lwjgl.opengl.GL11;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.DataOutput;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Font;
import java.io.InputStream;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import SpaceInvaders.*;

public class SpaceInvaders extends BasicGame{
	// Declare Variables
	
	public int[] scores = new int[11];
	boolean pause;
	boolean gameStart, flash;
	int flashDelay;
	Input buttons;
	Random rand = new Random();
	int hits;
	int p1lives;
	int p2lives;
	int kills;
	float multiplier = 1;
	int level = 1;
	double score = 0;
	int n;
	int gunColor = 3000;
	int gunColor1 = 3000;
	int color;
	int color1;
	int spot = 0;
	int i; 
	int numBosses;
	Gun a, c;
	int b;
	int time;
	boolean gameOver = false;
	int tsp;
	int tsc;
	int tsf;
	int tss, credits;
	
	int tsa, abstime;
	boolean damage1, damage2;
	int damage1inc, damage2inc, dead1, dead2;
	TrueTypeFont font;
	TrueTypeFont font2;
	TrueTypeFont bigFont;
	Audio shootEffect, killEffect, moveEffect, playerKillEffect, menuMusic, gameMusic;

	boolean coop;//    REMEMBER THIS MIKE
	
	public SpaceInvaders(){
		super("Interstellar Interlopers");
	}
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SpaceInvaders(), 1280, 1024, true);
		app.start();
	}
	//Initialize program
	public void init(GameContainer gc) throws SlickException{
		gameStart = false;
		gc.setShowFPS(false);
		buttons = gc.getInput();
		a = new Gun(512, 1023, 0);
		gc.setTargetFrameRate(60);
		new Alien(1000000000, 100000000, 1, 1);
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
		
		// load font from a .ttf file
		try {
		shootEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("SpaceInvaders/shoot.wav"));
		killEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("SpaceInvaders/invaderkilled.wav"));
		moveEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("SpaceInvaders/fastinvader1.wav"));
		playerKillEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("SpaceInvaders/explosion.wav"));
		menuMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("SpaceInvaders/menu.wav"));
		gameMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("SpaceInvaders/game1music.wav"));
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT,  new File("font.ttf"));
			awtFont2 = awtFont2.deriveFont(32f); // set font size
			font2 = new TrueTypeFont(awtFont2, false);
			Font awtFont3 = Font.createFont(Font.TRUETYPE_FONT,  new File("font.ttf"));
			awtFont3 = awtFont2.deriveFont(72f); // set font size
			bigFont = new TrueTypeFont(awtFont3, false);
			int color=0;
			int color1=0;
			readScore();
			newgame();
			tsp=0;
			tsc=0;
			tsf=0;
			tss=0;
			pause=false;
			menuMusic.playAsMusic(1.0f, 6.0f, true);
			//credits = ArcadeMenu.getCredits();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	//Start a new game
	public void newgame() throws SlickException, FileNotFoundException {
		for(i=0; i<10000; i++){
				Bullet.bullets[i] = null;
				Bullet.numBullets = 0;
				AlienBullet.AlienBullets[i] = null;
				AlienBullet.numAlienBullets = 0;
				Alien.aliens[i] = null;
				Alien.numAliens = 0;
		}
		int gunColor = 3000;
		int gunColor1 = 3000;
		checkScore();
		try{writeScore();} catch (Exception e){;}
		readScore();
		kills = 0;
		level = 1;
		gameOver = false;
		gameStart = false;
		time = 0;
		multiplier = 1;
		tsp=0;
		tsc=0;
		tsf=0;
		tss=0;
		score=0;
		flashDelay = 0;
		flash = false;
		p1lives = 25;
		p2lives = 25;
		abstime=0;
		damage1 = false;
		damage2 = false;
		damage1inc = 0;
		damage2inc = 0;
		gameOver=false;
		gameStart = false;
	}
	
	//Accelerate bullets
	public void acceleration() throws SlickException {
		for(i=0; i<Bullet.numBullets; i++){
			Bullet.bullets[i].y -= 15*multiplier;
		}
		for(i=0; i<AlienBullet.numAlienBullets; i++){
			AlienBullet.AlienBullets[i].y += 4*multiplier;
			if(AlienBoss.bosses[i] != null){
				AlienBoss.bosses[i].y += 4*multiplier;
			}
		}	
	}
	
	//Check for collision
	public void checkCollisions() throws SlickException {
		for(i=0; i<Bullet.numBullets; i++){
			for(n=0; n<Alien.numAliens; n++){
				if(Bullet.bullets[i] != null && Alien.aliens[n] != null && Bullet.bullets[i].y > Alien.aliens[n].y && Bullet.bullets[i].y < Alien.aliens[n].y +60){
					if(Bullet.bullets[i].x > Alien.aliens[n].x && Bullet.bullets[i].x < Alien.aliens[n].x +60){
						for(int z=n; z<Alien.numAliens; z++){
							Alien.aliens[z]=Alien.aliens[z+1];
						}
						for(int z=i; z<Bullet.numBullets; z++){
							Bullet.bullets[z]=Bullet.bullets[z+1];
						}
						killEffect.playAsSoundEffect(1.0f, 0.5f, false);
						Alien.numAliens--;
						Bullet.numBullets--;
						kills++;
						score += multiplier;
					}
				}
			}
		}
		for(n=0; n<Alien.numAliens; n++){
			if(n<Alien.numAliens && Alien.aliens[n].y >= 1024){
				gameOver = true;
			}
		}
		
		for(i=0; i< AlienBullet.numAlienBullets; i++){
			if(AlienBullet.AlienBullets[i].y > a.y){
				if(AlienBullet.AlienBullets[i].x > a.x && AlienBullet.AlienBullets[i].x < a.x + 50){
					p1lives-=1;
					damage1=true;
					damage1inc = 0;
					playerKillEffect.playAsSoundEffect(1.0f, 0.5f, false);
					AlienBullet.AlienBullets[i].x = 1000000;
					AlienBullet.AlienBullets[i].y = -1000000;
				}
			}
		}
		if(coop == true && c != null){
			for(i=0; i< AlienBullet.numAlienBullets; i++){
				if(AlienBullet.AlienBullets[i].y > c.y){
					if(AlienBullet.AlienBullets[i].x > c.x && AlienBullet.AlienBullets[i].x < c.x + 50){
						p2lives-=1;
						damage2=true;
						damage2inc = 0;
						playerKillEffect.playAsSoundEffect(1.0f, 0.5f, false);
						AlienBullet.AlienBullets[i].x = 1000000;
						AlienBullet.AlienBullets[i].y =  1000000;
					}
				}
			}
		}
		
		for(i=0; i< AlienBullet.numAlienBullets; i++){
			if(AlienBullet.AlienBullets[i].isBulletRender == 1 && AlienBullet.AlienBullets[i].y > 1023){
				AlienBullet.AlienBullets[i].x = 1000000;
				AlienBullet.AlienBullets[i].y = 1000000;
			}
		}
	}
	
	//Spawn aliens
	public void spawnAliens() throws SlickException {
		float gg;
		if(tss>=(2000-(200*multiplier))){
			for(gg = 100; gg < 1200; gg += 200){
				new Alien(gg, 10f, 1, (Math.random()>.5)?-1:1);
			}
			tss=0;
		}
	}
	
	//Shoot alien bullets
	public void fireAliens() throws SlickException {
		b++;
		if(b % Math.round(120 / level) == 0){
			//Create array for previously selected aliens
			int aliens[] = new int[5];
			for(i=0; i<=50; i++){
				int randomNum = (int)Math.floor(Math.random()*Alien.numAliens);
				if(randomNum > 0){
					new AlienBullet(Alien.aliens[randomNum].x, Alien.aliens[randomNum].y);
				}
			}
		}
	}
	
	//Select player color
	public void selectColor() throws SlickException {
		if(buttons.isKeyDown(Input.KEY_N)) {
			ArcadeMenu.setRunning(false);
		}
		if(buttons.isKeyDown(Input.KEY_3)&&tsc>200){
			coop = true;
		}
		if(buttons.isKeyDown(Input.KEY_H)&&tsc>200){
			credits++;
		}
		if(buttons.isKeyDown(Input.KEY_D)&&tsc>200){
			gunColor++;tsc=0;
			shootEffect.playAsSoundEffect(1.0f, 1.0f, false);
		}
		if(buttons.isKeyDown(Input.KEY_A)&&tsc>200){
			gunColor--;tsc=0;
			shootEffect.playAsSoundEffect(1.0f, 1.0f, false);
		}
		if(coop == true){
			if(buttons.isKeyDown(Input.KEY_L)&&tsc>200){
				gunColor1++;
				tsc=0;
				shootEffect.playAsSoundEffect(1.0f, 1.0f, false);
			}
			if(buttons.isKeyDown(Input.KEY_J)&&tsc>200){
				gunColor1--;
				tsc=0;
				shootEffect.playAsSoundEffect(1.0f, 1.0f, false);
			}
		}
		color=gunColor%7;
		color1=gunColor1%7;
		if(coop){
			if(buttons.isKeyDown(Input.KEY_X) && buttons.isKeyDown(Input.KEY_2)){
				gameStart = true;
				gameMusic.playAsMusic(1.0f, 6.0f, true);
				a = new Gun(4, 1023, color);
				c = new Gun(1012, 1023, color1);
				credits--;
			}
		
		}
		else{
			if(buttons.isKeyDown(Input.KEY_X)){
				gameStart = true;
				credits--;
				a = new Gun(4, 1023, color);
				gameMusic.playAsMusic(1.0f, 6.0f, true);
			}
		}
		if(coop == true){
			multiplier = multiplier*2;
		}
	}
	
	
	
	
	//Player Controls
	public void writeScore() throws FileNotFoundException, IOException{
		DataOutputStream output = new DataOutputStream(new FileOutputStream("data.dat"));
		for(i=0; i<10; i++){
			output.writeInt(scores[i]);
		}
		output.close();
	}
	public void readScore() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("data.dat"));
        i=0;
		while (scanner.hasNextInt()) {
			scores[i++] = scanner.nextInt();
        }
		scanner.close();

	}
	public void checkScore() throws FileNotFoundException {
		for(i=10; i>0; i--){
			if(score > scores[i]){
				spot = i;
			}
		}
		for(i=spot; i<10; i++){ 
			scores[i]=scores[i+1];
		}
		scores[spot]=(int)score;
	}
	public void controls(){
		float focusmult, focusmult1;
		if(buttons.isKeyDown(Input.KEY_B)){
			focusmult = 2;
		} else {
			focusmult = 10;
		}
		
		if(buttons.isKeyDown(Input.KEY_D) && a.x < 1280f){
			a.x += focusmult*multiplier;
		}
		
		if(buttons.isKeyDown(Input.KEY_A) && a.x > 0){
			a.x -= focusmult*multiplier;
		}
		
			
		if(buttons.isKeyPressed(Input.KEY_Z) || buttons.isKeyPressed(Input.KEY_V)){
			new Bullet(a);
			shootEffect.playAsSoundEffect(1.0f, 0.5f, false);
			tsf=0;
		}
		if(coop == true && c !=null){
			if(buttons.isKeyDown(Input.KEY_5)){
				focusmult1 = 2;
			} else {
				focusmult1 = 10;
			}
			if(buttons.isKeyDown(Input.KEY_L) && c.x < 1280){
				c.x += focusmult1*multiplier;
			}
			
			if(buttons.isKeyDown(Input.KEY_J) && c.x > 0){
				c.x -= focusmult1*multiplier;
			}
				
			if(buttons.isKeyPressed(Input.KEY_1) || buttons.isKeyPressed(Input.KEY_4)){
				new Bullet(c);
				shootEffect.playAsSoundEffect(1.0f, 0.5f, false);
				tsf=0;
			}
		}
	}
	public void levelUp(){
		if(flashDelay%25 == 0){
			flash = true;
		}
	}
	
	//Update screen
	public void update(GameContainer gc, int delta) throws SlickException {
		try {
			if(buttons.isKeyDown(Input.KEY_N)) {
				ArcadeMenu.setRunning(false);
				gameMusic.stop();
				menuMusic.stop();
			}
			checkScore();
			abstime++;
			time += delta;tsp += delta;tsc += delta;tsf += delta;tss += delta;
			flashDelay++;
			if(p1lives <= 0){
				a = null;
			}
			if(coop == true && p2lives <= 0){
				c = null;
			}
			if(a == null && c == null){
				gameOver = true;
			}
			if(gameStart==false){
				selectColor();
			}
			else if(gameOver == false){
				if(pause == false){
					controls();
					spawnAliens();
					checkCollisions(); 
					for(Alien a : Alien.aliens){
						if(a!=null){
							if(a.x<=32) a.xm=1;
							if(a.x>=1248) a.xm=-1;
							a.x+= (a.xm*multiplier*5);
							a.y+=(multiplier/3);
						}
					}
					fireAliens();
					acceleration();
					//Increase level
					if(kills == (level*25) && kills != 0) {
						multiplier +=.25;
						level += 1;
						flashDelay = 0;
						levelUp();
					}
				}
				
				if(buttons.isKeyDown(Input.KEY_ESCAPE)&&tsp>200){
					pause = !pause;tsp=0;
				}
			}
			if((gameOver==true || gameStart==false)&&buttons.isKeyDown(Input.KEY_X)) newgame();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//Render
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(abstime < 300 && gameStart == true){
			font2.drawString(300, 980,"Getting grazed by a bullets "+ /*deals less damage than hitting them full-on*/"increases your score instead of damaging");
		}
		//for(i=0; i<10; i++){
			//font2.drawString(200, 20*i, scores[i]+"");
		//}
		font2.drawString(100, 100,"KILLS: "+kills+"");
		font2.drawString(100, 130,"LEVEL: "+level+"");
		font2.drawString(100, 160,"SCORE: "+score+"");
		if(damage1 == true && damage1inc < 75){
			font2.drawString(100, 190,"PLAYER 1 HEALTH: "+(Math.max(p1lives,0))+"", Color.red);
			damage1inc++;
		}
		else{
			font2.drawString(100, 190,"PLAYER 1 HEALTH: "+(Math.max(p1lives,0))+"");
		}
		if(coop == true){
			if(damage2 == true && damage2inc <75){
				font2.drawString(100, 250,"PLAYER 2 HEALTH: "+(Math.max(p2lives,0))+"", Color.red);
				damage2inc++;
			}
			else{
				font2.drawString(100, 250,"PLAYER 2 HEALTH: "+(Math.max(p2lives,0))+"");
			}
		}
		if(gameStart == false){
			if(coop != true){
			Image gunImage1=new Image("SpaceInvaders\\Gun"+color+".png");
			gunImage1.setFilter(Image.FILTER_NEAREST);
			gunImage1.draw(540, 540, 5f);
			}
			else if(coop == true){
				Image gunImage2=new Image("SpaceInvaders\\Gun"+color1+".png");
				gunImage2.setFilter(Image.FILTER_NEAREST);
				gunImage2.draw(640, 540, 5f);
				Image gunImage1=new Image("SpaceInvaders\\Gun"+color+".png");
				gunImage1.setFilter(Image.FILTER_NEAREST);
				gunImage1.draw(440, 540, 5f);
			}
			font2.drawString(500,500,"MOVE THE JOYSTICK TO SELECT YOUR COLOR");
			font2.drawString(500,700, "HOLD DOWN RED TO START GAME");
			font2.drawString(500,730,"TO SHOOT, PRESS GREEN");
			font2.drawString(500,760,"TO ADD A PLAYER, PRESS YELLOW");
			font2.drawString(500,790,"TO SLOW DOWN YOUR MOVEMENT SPEED FOR PRECISION, HOLD BLACK");
			font2.drawString(500,820,"Getting grazed by a bullets deals less damage than hitting them full-on");
		}
		else {
			if(gameOver == false){
				Image gunImage1=new Image("SpaceInvaders\\Gun"+color+".png");
				gunImage1.setFilter(Image.FILTER_NEAREST);
				for(i=0; i<Bullet.numBullets; i++){
					if(Bullet.bullets[i].isBulletRender == 1){
						gunImage1.draw(Bullet.bullets[i].x, Bullet.bullets[i].y, .25f);
					}
				}
				for(n=0; n<Alien.numAliens; n++){
					if(Alien.aliens[n].isRender == 1){
						Alien.alienImgs[n].draw(Alien.aliens[n].x + 10, Alien.aliens[n].y, 3f);
					}
				}
				for(i=0; i<AlienBullet.numAlienBullets; i++){
					if(AlienBullet.AlienBullets[i].isBulletRender == 1){
						g.fillRect(AlienBullet.AlienBullets[i].x, AlienBullet.AlienBullets[i].y, 5, 10);
					}
				}
				for(i=0; i<AlienBoss.numBossAliens; i++){
					if(AlienBoss.bosses[i] != null){
						if(AlienBoss.bosses[i].isRender != 0){
							AlienBoss.BossAlienImgs[i].draw(AlienBoss.bosses[i].x, AlienBoss.bosses[i].y, 5f);	
						}
					}
				}
				if(a != null){
					a.gunImage.draw(a.x, 982, 2f);
				}
				if(coop == true && c !=null){
					c.gunImage.draw(c.x, 982, 2f);
				}
				
				if((flash == true) && (flashDelay < 100)){
					bigFont.drawString(540, 512, "LEVEL UP!");
				}
			}
			if(gameOver == true){
				font2.drawString(540, 512, "GAME OVER!");
				font2.drawString(540, 600, "PRESS RED TO START NEW GAME");	
			}
		}
	}
}