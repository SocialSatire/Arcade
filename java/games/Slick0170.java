import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class Slick0170 extends BasicGame {
	Image sm = null;
	Image bg = null;

	int bgWidth, bgHeight;
	int smX = 100, smY = 100, smWidth, smHeight;
	int xStep = 3, yStep = 3;

	int targetFPS = 60;

	int mouseX, mouseY;

	//public boolean isRunning = false;

	public Slick0170() {
		super("Slick0170");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Slick0170(), 400, 300, false);
		app.start();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		sm = new Image("Slick0170\\sm.png");
		bg = new Image("Slick0170\\background.jpg");

		bgWidth = bg.getWidth();
		bgHeight = bg.getHeight();
		smWidth = sm.getWidth();
		smHeight = sm.getHeight();

		gc.setTargetFrameRate(targetFPS);
		gc.setShowFPS(false);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		if(Math.abs(((int) smX) - mouseX) >= 3) {
			int diff = (((int) smX) - mouseX);
			if(diff > 0) {
				smX -= xStep;
			} else {
				smX += xStep;
			}
		}

		if(Math.abs(((int) smY) - mouseY) >= 3) {
			int diff = (((int) smY) - mouseY);
			if(diff > 0) {
				smY -= yStep;
			} else {
				smY += yStep;
			}
		}

		if(smX + smWidth >= bgWidth) {
			smX = (bgWidth - smWidth);
		}

		if(smX <= 0) {
			smX = 0;
		}

		if(smY + smHeight >= bgHeight) {
			smY = (bgHeight - smHeight);
		}

		if(smY <= 0) {
			smY = 0;
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setDrawMode(g.MODE_NORMAL);

		bg.draw(0, 0);
		g.drawString("_______\n|MOIST|\n|FETUS|\n-------\n/      ", (smX + 35), (smY - 110));

		sm.drawCentered(smX, smY);
	}
}
