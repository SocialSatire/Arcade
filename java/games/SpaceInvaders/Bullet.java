package SpaceInvaders;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;

public class Bullet{
	public int isBulletRender;
	public static Bullet[] bullets = new Bullet[10000];
	public static int numBullets = 0;
	public float x;
	public float y;
	public Bullet(Gun a){
		this.x = a.x + 18f;
		this.y = a.y - 30f;
		this.isBulletRender = 1;
		if(numBullets < 10000){
			bullets[numBullets] = this;
		}
		else if(numBullets >10000){
			bullets[numBullets%10000] = this;
		}
		numBullets += 1;
		
	}
}