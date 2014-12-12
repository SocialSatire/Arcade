package KevCycles;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.Image;
import java.util.ArrayList;
 
public class AI{
 
        private int x1, y1, dir1, x2, y2, dir2; // x1, y1, and dir1 are for the ai; x2, y2, and dir2 are for the other player.
       
        /* Some other useful things:
        Trace.getTraces(); returns an ArrayList<Trace> of all traces
        for(Trace t : Trace.getTraces) will scan through all traces
        Trace.getX() and Trace.getY() both work
        Trace.getH() returns true if the trace is horizontal, false if it is vertical
        You can assume the traces are 5x2 rectangles
        Turns are not exact because of how the traces line up.
        */
       
       
        public AI(int x1, int y1, int dir1, int x2, int y2, int dir2){
                this.x1 = x1;this.y1 = y1;this.dir1 = dir1;this.x2 = x2;this.y2 = y2;this.dir2 = dir2;
        }
        public int getChoice()
{
       
double soupd = Math.random();
 
soupd*=15;
soupd+=11;
 
int soup = (int) Math.floor(soupd);

double turn = Math.random();
turn*=2;
Math.floor(turn);

int newTurn = dir1;

int xtrace1 = x1-4;
int xtrace2 = x1+15;
int ytrace1 = y1-4;
int ytrace2 = y1+15;

/*
public void locateTraceX(currentLocationY)
{
	for(Trace t : Trace.getTraces())
	{
		Can't do more without more knowledge of turning radius.
	}
}
*/

if(dir1 == 0)
{
	int collide = x1+soup+32;
	int minimum = x1+42;
	for(int i = minimum; i < collide; i++)
	{
		if(i == 640)
		{
			if(y1 > 240)
			{
				newTurn = 1;
			}
			else
			{
				newTurn = 3;
			}
		}
	}
	for(Trace t : Trace.getTraces())
	{
		for(int i = ytrace1; i < ytrace2; i++)
		{
			if(t.getY() == i)
			{
				for(int j = minimum; j < collide; j++)
				{
					if(t.getX() == j)
					{
						if(turn == 0)
						{
							newTurn = 1;
						}
						else
						{
							newTurn = 3;
						}
					}
				}
			}
		}
	}
}       

else if(dir1 == 1)
{
	int collide = y1-soup;
	int minimum = y1-10;
	for(int i = minimum; i > collide; i--)
	{
		if(i == 0)
		{
			if(x1 < 320)
			{
					newTurn = 0;
			}
			else
			{
					newTurn = 2;
			}
		}
	}
	for(Trace t : Trace.getTraces())
	{
		for(int i = xtrace1; i < xtrace2; i++)
		{
			if(t.getX() == i)
			{
				for(int j = collide; j < minimum; j++)
				{
					if(t.getY() == j)
					{
						if(turn == 0)
						{
								newTurn = 0;
						}
						else
						{
								newTurn = 2;
						}
					}
				}
			}
		}
	}
}       

else if(dir1 == 2)
{
	int collide = x1-soup;
	int minimum = x1-10;
	for(int i = minimum; i > collide; i--)
	{
		if(i == 0)
		{
			if(y1 > 240)
			{
					newTurn = 1;
			}
			else
			{
					newTurn = 3;
			}
		}
	}
	for(Trace t : Trace.getTraces())
	{
		for(int i = ytrace1; i < ytrace2; i++)
		{
			if(t.getY() == i)
			{
				for(int j = collide; j < minimum; j++)
				{
					if(t.getX() == j)
					{
						if(turn == 0)
						{
								newTurn = 1;
						}
						else
						{
								newTurn = 3;
						}
					}
				}
			}
		}
	}
}       

else if(dir1 == 3)
{
	int collide = y1+soup+32;
	int minimum = y1+42;
	for(int i = minimum; i < collide; i++)
	{
		if(i == 480)
		{
			if(x1 < 320)
			{
					newTurn = 0;
			}
			else
			{
					newTurn = 2;
			}
		}
	}
	for(Trace t : Trace.getTraces())
	{
		for(int i = xtrace1; i < xtrace2; i++)
		{
			if(t.getX() == i)
			{
				for(int j = minimum; j < collide; j++)
				{
					if(t.getY() == j)
					{
						if(turn == 0)
						{
								newTurn = 0;
						}
						else
						{
								newTurn = 2;
						}
					}
				}
			}
		}
	}
}

else
{
	newTurn = dir1;
}
return newTurn;
}
}

