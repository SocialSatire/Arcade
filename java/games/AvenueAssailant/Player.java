package AvenueAssailant;



public abstract class Player {
	public static int g = 1500; //go away laura
	protected int v, x, y;
	protected int width, height;
	protected int jumpHeight;		//shouldn't need to use these outside of
	protected int moveSpeed;		//subclasses. add an accessor if you need to
	protected int dir;
	public int jumps;
	protected boolean jumping;
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getV(){return v;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getDir(){return dir;}
	
	public void setX(int n){x=n;}
	public void setY(int n){y=n;}
	public void setV(int n){v=n;}
	public void setDir(int n){dir=n;}
	
	public abstract void update(int delta, boolean[] keys);
	
	protected abstract void moveLeft(int moveDist);
	protected abstract void moveRight(int moveDist);
	protected abstract void jump();

	protected abstract void punch();
	protected abstract void kick();
	// probably more move types
}