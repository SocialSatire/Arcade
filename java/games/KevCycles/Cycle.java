package KevCycles;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Color;
 
public class Cycle{
	
	private Image cycleImage;
	private SpriteSheet animatedImage;
	private Animation animatedCycle;
	private int x, y;
	private int dir;
	private String custom;
	public Cycle(int color, int x, int y, int dir, int type)throws SlickException{
		this.cycleImage = new Image("KevCycles/sprites/"+type+"cycle"+color+".png");
		this.x = x;this.y = y;this.dir = dir;this.custom = "";
		cycleImage.rotate(90*dir);
	}
	public Cycle(int x, int y, int dir, String custom)throws SlickException{
		if(custom.equals("mike")){animatedImage = new SpriteSheet("KevCycles/sprites/"+custom+"/"+dir+".png",48,32);
		animatedCycle = new Animation(animatedImage,50); 
		this.cycleImage = new Image("KevCycles/sprites/"+custom+"_still.png");
		animatedCycle.setAutoUpdate(true);animatedCycle.setLooping(true);}
		else this.cycleImage = new Image("KevCycles/sprites/"+custom+".png");
		this.x = x;this.y = y;this.dir = dir; cycleImage.rotate(90*dir);
		this.custom = custom;
	}
	public Image getImage(){return cycleImage;}
	public int getX(){return x;}
	public int getY(){return y;}
	public void setX(int n){x=n;}
	public void setY(int n){y=n;}
	public void turn(int newDir) throws SlickException{
		if(custom != "mike") cycleImage.rotate(((newDir-dir)%4)*-90);
		else{
			int t = animatedCycle.getFrame(); int i = (newDir%2==0?1:-1); 
			this.animatedCycle = new Animation(new SpriteSheet("KevCycles/sprites/"+custom+"/"+newDir+".png",40+(i*8),40-(i*8)),50);
			animatedCycle.setCurrentFrame(t);}
		dir = newDir;
	}
	public int getDir(){return dir;}
	public String getCustom(){return custom;}
	public Animation getAnimated(){return animatedCycle;}
	public static Color getCustomColor(String name){
		if(name.equals("mike")) return new Color(255,255,255);
		else if(name.equals("kevin")) return new Color(52,12,107);
		else if(name.equals("jacques")) return new Color(42,255,41);
		else if(name.equals("julius")) return new Color(250,72,0);
		else if(name.equals("markward")) return new Color(71,2,7);
		else if(name.equals("robert")) return new Color(119,102,14);
		else return new Color(128,128,128); //this color should never appear
	}
}