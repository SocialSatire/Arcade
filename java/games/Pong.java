import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import java.awt.Font;
import java.io.InputStream;
import java.util.Random;

public class Pong extends BasicGame {
	Image ball = null, player = null, background = null, rArrow = null, lArrow = null;
	int targetFPS = 60;
	TrueTypeFont font = null, bigFont = null;
	Random random = null;
	Audio playerHit, borderHit, endPoint;


	//program control varaibles
	boolean preGame = false, midGame = false, postPoint = false, postGame = false;
	int numRounds = 7;

	//pre-game variables
	boolean p1Confirm = false, p2Confirm = false;
	int counter2 = 0;

	//mid-game variables
	boolean lastPoint = false; 		// false for p1, true for p2
	boolean firstRound = true;
	boolean hasPlayerServed = true;
	int p1Score = 0, p2Score = 0;
	float ballVelocity = 10.0f, ballHorizontalV = 10.0f, ballVerticalV = 0.0f, playerVelocity = 8.0f;
	float p1X = 40.0f, p1Y = 450.0f, p2X = 1220.0f, p2Y = 450.0f;
	float ballX = 630.0f, ballY = 510.0f;

	//post-point/post-game variables
	float counter = 0.0f;
	String countDown = "";


	public Pong() {
		super("Pong");
	}

	public void init(GameContainer gc) throws SlickException {
		ball = new Image("Pong/ball.png");
		player = new Image("Pong/player.png");
		background = new Image("Pong/background.png");
		lArrow = new Image("Pong/left_arrow.png");
		rArrow = new Image("Pong/right_arrow.png");
		random = new Random();

		preGame = true;

		gc.setTargetFrameRate(targetFPS);
		gc.setShowFPS(true);

		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("Pong/pixelated.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(24.0f);
			font = new TrueTypeFont(awtFont, true);
			awtFont = awtFont.deriveFont(48.0f);
			bigFont = new TrueTypeFont(awtFont, true);
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			borderHit = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Pong/sounds/pongblipf4.wav"));
			playerHit = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Pong/sounds/pongblipg5.wav"));
			endPoint = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Pong/sounds/pongblipa3.wav"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if(input.isKeyDown(Input.KEY_Q)) {
			ArcadeMenu.setRunning(false);
		}

		if(preGame) preGameUpdate(gc, delta);
		if(midGame) midGameUpdate(gc, delta);
		if(postPoint) postPointUpdate(gc, delta);
		if(postGame) postGameUpdate(gc, delta);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		background.draw(0.0f, 0.0f);
		if(preGame) preGameRender(gc, g);
		if(midGame) midGameRender(gc, g);
		if(postPoint) postPointRender(gc, g);
		if(postGame) postGameRender(gc, g);
	}

	/****** PRE-GAME ******/

	public void preGameUpdate(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		counter += delta;
		if(counter >= 250) {
			if(input.isKeyDown(Input.KEY_Z)) p1Confirm = true;
			if(input.isKeyDown(Input.KEY_1)) p2Confirm = true;
		}

		if(p1Confirm && p2Confirm) {
			counter2 += delta;

			if(counter2 >= 500) {
				counter2 = 0;
				counter = 0;

				p1Confirm = p2Confirm = false;
				preGame = false;
				midGame = true;
			}
		}

		if(((input.isKeyDown(Input.KEY_J) || input.isKeyDown(Input.KEY_A)) && numRounds >= 3) && counter > 100) {
			numRounds -= 2;
			counter = 0;
		}
		if(((input.isKeyDown(Input.KEY_L) || input.isKeyDown(Input.KEY_D)) && numRounds <= 9) && counter > 100) {
			numRounds += 2;
			counter = 0;
		}
		
		if((input.isKeyDown(Input.KEY_V)&&input.isKeyDown(Input.KEY_C))||(input.isKeyDown(Input.KEY_4)&&input.isKeyDown(Input.KEY_5)))
			ball = new Image("Pong/mark_ball.png");
	}

	public void preGameRender(GameContainer gc, Graphics g) throws SlickException {
		float x = font.getWidth("Press Any Button...") + 200;
		float l = bigFont.getWidth("table tennis simulator 2015");
		bigFont.drawString(640.0f - (l / 2), 200.0f, "table tennis simulator 2015");
		font.drawString(200.0f, 400.0f, "Player 1 Ready?");
		font.drawString(200.0f, 400.0f + font.getLineHeight(), (p1Confirm ? "Ready!" : "press green..."));
		font.drawString(1280.0f - x, 400.0f, "Player 2 Ready?");
		font.drawString(1280.0f - x, 400.0f + font.getLineHeight(), (p2Confirm ? "Ready!" : "press green..."));

		int w = font.getWidth("best of");
		font.drawString(640.0f - (w / 2), 600.0f, "best of");
		bigFont.drawString(616.0f, 600.0f + font.getLineHeight(), Integer.toString(numRounds));
		w = font.getWidth("rounds");
		font.drawString(640.0f - (w / 2), 600.0f + font.getLineHeight() + bigFont.getLineHeight(), "rounds");

		if(numRounds > 1) {
			lArrow.draw(578.0f, 600.0f + font.getLineHeight());
		}
		if(numRounds < 11) {
			rArrow.draw(664.0f, 600.0f + font.getLineHeight());
		}
	}

	/****** MID-GAME ******/

	public void midGameUpdate(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if(firstRound) {
			counter += delta;
			if(counter <= 3000) {
				int a = (int) Math.ceil((3000.0f - counter) / 1000.0f);
				countDown = Integer.toString(a);
			} else {
				countDown = "";
				counter = 0;
				firstRound = false;

				int a = random.nextInt(2);
				a *= 2;
				a -= 1;
				ballHorizontalV *= a;
				playerHit.playAsSoundEffect(1.0f, 1.0f, false);
			}
		} else if(!hasPlayerServed) {
			if(lastPoint && input.isKeyDown(Input.KEY_1)) {
				ballHorizontalV *= -1;
				hasPlayerServed = true;
			}
			if(!lastPoint && input.isKeyDown(Input.KEY_Z)) {
				hasPlayerServed = true;
				playerHit.playAsSoundEffect(1.0f, 1.0f, false);
			}

		} else {
			if(input.isKeyDown(Input.KEY_W) && p1Y > 100.0f) p1Y -= playerVelocity;
			if(input.isKeyDown(Input.KEY_S) && p1Y < 800.0f) p1Y += playerVelocity;
			if(input.isKeyDown(Input.KEY_I) && p2Y > 100.0f) p2Y -= playerVelocity;
			if(input.isKeyDown(Input.KEY_K) && p2Y < 800.0f) p2Y += playerVelocity;

			if((ballY > p1Y - 20.0f && ballY < p1Y + 140.0f) && (ballX < p1X + 20.0f && ballX > p1X - 20.0f)) {
				if(input.isKeyDown(Input.KEY_X)) {
					ballVelocity += 2.0f;
					playerVelocity += 1.0f;
				}

				double a = Math.toRadians(ballY - p1Y - 60.0f);
				ballHorizontalV = ballVelocity * (float) Math.cos(a);
				ballVerticalV = ballVelocity * (float) Math.sin(a);
				playerHit.playAsSoundEffect(1.0f, 1.0f, false);
				if(ballHorizontalV < 5.0f) ballHorizontalV = 5.0f;
			}
			if((ballY > p2Y - 20.0f && ballY < p2Y + 140.0f) && (ballX > p2X - 20.0f && ballX > p2X - 20.0f)) {
				if(input.isKeyDown(Input.KEY_2)) {
					ballVelocity += 2.0f;
					playerVelocity += 1.0f;
				}

				double a = Math.toRadians(ballY - p2Y - 60.0f);
				ballHorizontalV = -1 * ballVelocity * (float) Math.cos(a);
				ballVerticalV = ballVelocity * (float) Math.sin(a);
				playerHit.playAsSoundEffect(1.0f, 1.0f, false);
				if(ballHorizontalV > -5.0f) ballHorizontalV = -5.0f;
			}
			if(ballY < 100.0f || ballY > 920.0f) {
				ballVerticalV *= -1;
				borderHit.playAsSoundEffect(1.0f, 1.0f, false);
			}

			if(ballX < 0.0f) {
				p2Score += 1;
				midGame = false;
				postPoint = true;
				lastPoint = true;
				endPoint.playAsSoundEffect(1.0f, 1.0f, false);
			}
			if(ballX > 1260.0f) {
				p1Score += 1;
				midGame = false;
				postPoint = true;
				lastPoint = false;
				endPoint.playAsSoundEffect(1.0f, 1.0f, false);
			}

			ballX += ballHorizontalV;
			ballY += ballVerticalV;
		}
	}

	public void midGameRender(GameContainer gc, Graphics g) throws SlickException {
		ball.rotate(1.0f);
		ball.draw(ballX, ballY);
		player.draw(p1X, p1Y);
		player.draw(p2X, p2Y);

		bigFont.drawString(400.0f, 20.0f, Integer.toString(p1Score));
		bigFont.drawString(880.0f, 20.0f, Integer.toString(p2Score));

		if(firstRound) {
			float x = font.getWidth("game starts in X seconds") / 2;
			font.drawString(640.0f - x, 400.0f, "Game starts in " + countDown + " seconds...");
		}

		if(!hasPlayerServed) {
			float x = font.getWidth("player X serves") / 2;
			font.drawString(640.0f - x, 400.0f, "Player " + (lastPoint ? "2" : "1") + " serves.");

			x = font.getWidth("press green...") / 2;
			font.drawString(640.0f - x, 400.0f + font.getHeight(), "Press green...");
		}
	}

	/****** POST-POINT ******/

	public void postPointUpdate(GameContainer gc, int delta) throws SlickException {
		counter += delta;

		if(p1Score < ((numRounds + 1) / 2) && p2Score < ((numRounds + 1) / 2)) {
			counter = 0.0f;

			resetValues();

			postPoint = false;
			midGame = true;
			hasPlayerServed = false;
		} else {
			postPoint = false;
			postGame = true;
			counter = 0.0f;
		}
	}

	public void postPointRender(GameContainer gc, Graphics g) throws SlickException {
		bigFont.drawString(400.0f, 20.0f, Integer.toString(p1Score));
		bigFont.drawString(880.0f, 20.0f, Integer.toString(p2Score));
	}

	/****** POST-GAME ******/

	public void postGameUpdate(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		counter += delta;

		//if(input.isKeyDown(Input.KEY_SPACE)) ArcadeMenu.setCredits(ArcadeMenu.getCredits()+1);

		if(/*ArcadeMenu.getCredits() >= 1 && */(input.isKeyDown(Input.KEY_Z) || input.isKeyDown(Input.KEY_1))) {
			p1Score = 0;
			p2Score = 0;
			resetValues();

			postGame = false;
			preGame = true;
			//ArcadeMenu.setCredits(ArcadeMenu.getCredits()-1);
		}
		if(input.isKeyDown(Input.KEY_X) || input.isKeyDown(Input.KEY_2)) 
			ArcadeMenu.setRunning(false);

		if(counter < 10000.0f) {
			double a = Math.ceil((10000.0f - counter) / 1000.0f);
			countDown = Integer.toString((int) a);
		} else {
			ArcadeMenu.setRunning(false);
		}
	}

	public void postGameRender(GameContainer gc, Graphics g) throws SlickException {
		float x = bigFont.getWidth("game over!") / 2;
		bigFont.drawString(640.0f - x, 200.0f, "GAME OVER!");

		x = bigFont.getWidth("player x wins!") / 2;
		bigFont.drawString(640.0f - x, 200.0f + bigFont.getLineHeight(), ((p1Score > p2Score) ? "Player 1" : "Player 2") + " wins!");

		x = bigFont.getWidth("Play again?      "/* + ArcadeMenu.getCredits() + "/1 credit"*/) / 2;
		bigFont.drawString(640.0f - x, 400.0f, "Play Again?      " /*+ Integer.toString(ArcadeMenu.getCredits()) + "/1 credit"*/);
		//if(ArcadeMenu.getCredits() >= 1) {
			x = bigFont.getWidth("press green to restart, press red to exit...") / 2;
			bigFont.drawString(640.0f - x, 400.0f + bigFont.getLineHeight(), "Press green to restart, press red to exit...");
		//}

		x = font.getWidth("returning to main menu in " + countDown + " seconds") / 2;
		font.drawString(640.0f - x, 900.0f, "Returning to main menu in " + countDown + " seconds");
	}

	public void resetValues() {
		//pre-game variables
		p1Confirm = false;
		p2Confirm = false;
		counter2 = 0;

		//mid-game variables
		ballVelocity = 10.0f;
		ballHorizontalV = 10.0f;
		ballVerticalV = 0.0f;
		playerVelocity = 8.0f;
		p1X = 40.0f;
		p1Y = 450.0f;
		p2X = 1220.0f;
		p2Y = 450.0f;
		ballX = 630.0f;
		ballY = 510.0f;

		//post-point/post-game variables
		counter = 0.0f;
		countDown = "";
	}
	
	
	//come on jacques
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Pong(), 1280, 1024, false);
		app.start();
	}
}
