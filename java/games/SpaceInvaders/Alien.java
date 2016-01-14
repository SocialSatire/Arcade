package SpaceInvaders;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;


public class Alien{
	public Image AlienImage;
	public int isRender;
	public static int imageNumber;
	public static Image[] alienImgs = new Image[10000];
	public static Alien[] aliens = new Alien[10000];
	public static int numAliens= 0;
	public float x;
	public float y;
	public int xm;
	public Alien(float x, float y, int isRender, int xm) throws SlickException {
		this.x = x;
		this.y = y;
		this.isRender = 1;
		this.xm = xm;
		aliens[numAliens] = this;
		AlienImage = new Image("SpaceInvaders\\Alien.png");
		AlienImage.setFilter(Image.FILTER_NEAREST);
		alienImgs[imageNumber] = this.AlienImage;
		if(imageNumber < 10000){
			alienImgs[imageNumber] = this.AlienImage;
		}
		else if(imageNumber >10000){
			alienImgs[imageNumber%10000] = this.AlienImage;
		}
		if(numAliens < 10000){
			aliens[numAliens] = this;
		}
		else if(numAliens >10000){
			aliens[numAliens%10000] = this;
		}
		numAliens += 1;
		imageNumber += 1;
	}
}