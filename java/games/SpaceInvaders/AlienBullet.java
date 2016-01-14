package SpaceInvaders;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;

public class AlienBullet{
	public int isBulletRender;
	public static AlienBullet[] AlienBullets = new AlienBullet[10000];
	public static int numAlienBullets = 0;
	public float x;
	public float y;
	public AlienBullet(float bx, float by){
		this.x = bx +20;
		this.y = by +18;
		this.isBulletRender = 1;
		if(numAlienBullets < 10000){
			AlienBullets[numAlienBullets] = this;
		}
		else if(numAlienBullets >10000){
			AlienBullets[numAlienBullets%10000] = this;
		}
		numAlienBullets += 1;
	}
}