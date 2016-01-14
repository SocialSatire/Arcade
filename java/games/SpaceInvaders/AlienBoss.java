package SpaceInvaders;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;


public class AlienBoss{
	public Image BossAlienImage;
	public int isRender;
	public static int imageNumberBoss;
	public static Image[] BossAlienImgs = new Image[10000];
	public static AlienBoss[] bosses = new AlienBoss[10000];
	public static int numBossAliens= 0;
	public int x;
	public int y;
	public AlienBoss(int x, int y, int isRender) throws SlickException{
		this.x = x;
		this.y = y;
		this.isRender = 1;
		bosses[numBossAliens] = this;
		BossAlienImage = new Image("SpaceInvaders\\Alien.png");
		BossAlienImage.setFilter(Image.FILTER_NEAREST);
		BossAlienImgs[imageNumberBoss] = this.BossAlienImage;
		numBossAliens += 1;
		imageNumberBoss += 1;
	}
}