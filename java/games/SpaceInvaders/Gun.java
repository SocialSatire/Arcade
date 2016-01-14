package SpaceInvaders;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;

public class Gun {
	public Image gunImage;
	public float x;
	public float y;
	public int color;
	public Gun(float x, float y, int color) throws SlickException {
		gunImage = new Image("SpaceInvaders\\Gun"+color+".png");
		gunImage.setFilter(Image.FILTER_NEAREST);
		this.x = x;
		this.y = y;
	}
}