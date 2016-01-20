package AvenueAssailant;

public class Sommar extends Player{	//why not
	

	public Sommar(int player){
		x=100;y=100; //these will eventually changed based on P1/P2
		width = 100;
		height = 200;
		jumpHeight = 750;
		moveSpeed = 200;	// rus fast
		jumps = 3; 		// fly
	}
	
	public void update(int delta, boolean[] keys){
		if(y<(964-height)){
			v += (g*(delta/1000.0));
			y += (v*(delta/1000.0));
		}
		else if(y==(964-height)&&v<0){
			v += (g*(delta/1000.0));
			y += (v*(delta/1000.0));
		}
		else if(!jumping||v>0){
			if(y>(964-height))
				y=964-height;
			v=0;
			jumps=3;
		}
		if(!keys[0]) jumping=false;
	
		if(keys[0]) jump();
		int moveDist = (int)(moveSpeed * (delta/250.0));
		if(keys[1]) moveLeft(moveDist);
		if(keys[3]) moveRight(moveDist);
		if(x<0) x=0;
		else if((x+width)>1280) x=(1280-width);
	}
	
	protected void jump(){			//add variable jump height later
		if(jumps>0 && !jumping){
			v=-jumpHeight;
			jumping = true;
			jumps--;
		}
	}
	
	protected void moveLeft(int moveDist){
		x-=moveDist;
	}
	protected void moveRight(int moveDist){
		x+=moveDist;
	}

	protected void punch(){;}
	protected void kick(){;}
	
}