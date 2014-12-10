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
 
soupd*=30;
soupd+=10;
 
int soup = (int) Math.floor(soupd);

if(dir1 == 0)
{
        int collide = x1+soup+32;
        int minimum = x1+42;
		int trace1 = y1-5;
		int trace2 = y1+5;
        for(int i = minimum; i < collide; i++)
        {
                if(i == 640)
                        {
                                double turn = Math.random();
                                turn*=2;
                                Math.floor(turn);
                                if(turn == 0)
                                {
                                        return 1;
                                }
                                else
                                {
                                        return 3;
                                }
                        }
        }
        return dir1;

        for(Trace t : Trace.getTraces())
        {
			for(int i = trace1; i < trace2; i++)
			{
                if(t.getY() == i)
                {
                        for(int j = minimum; j < collide; j++)
                        {
                                if(t.getX() == j)
                                {
                                        double turn = Math.random();
                                        turn*=2;
                                        Math.floor(turn);
                                        if(turn == 0)
                                        {
                                                return 1;
                                        }
                                        else
                                        {
                                                return 3;
                                        }
                                }
                        }
                        return dir1;
                }
                else
				{
                        return dir1;
                }
			}
        }
}       

else if(dir1 == 1)
{
        int collide = y1-soup;
        int minimum = y1-10;
		int trace1 = x1-5;
		int trace2 = x1+5;
        for(int i = minimum; i < collide; i++)
        {
                if(i == 0)
                        {
                                double turn = Math.random();
                                turn*=2;
                                Math.floor(turn);
                                if(turn == 0)
                                {
                                        return 0;
                                }
                                else
                                {
                                        return 2;
                                }
                        }
        }
        return dir1;

        for(Trace t : Trace.getTraces())
        {
			for(int i = trace1; i < trace2; i++)
			{
                if(t.getX() == i)
                {
                        for(int j = minimum; j < collide; j++)
                        {
                                if(t.getY() == j)
                                {
                                        double turn = Math.random();
                                        turn*=2;
                                        Math.floor(turn);
                                        if(turn == 0)
                                        {
                                                return 0;
                                        }
                                        else
                                        {
                                                return 2;
                                        }
                                }
                        }
                        return dir1;
                }
                else
				{
                        return dir1;
                }
			}
        }
}       

else if(dir1 == 2)
{
        int collide = x1-soup;
        int minimum = x1-10;
		int trace1 = y1-5;
		int trace2 = y1+5;
        for(int i = minimum; i < collide; i++)
        {
                if(i == 0)
                        {
                                double turn = Math.random();
                                turn*=2;
                                Math.floor(turn);
                                if(turn == 0)
                                {
                                        return 1;
                                }
                                else
                                {
                                        return 3;
                                }
                        }
        }
        return dir1;

        for(Trace t : Trace.getTraces())
        {
			for(int i = trace1; i < trace2; i++)
			{
                if(t.getY() == i)
                {
                        for(int j = minimum; j < collide; j++)
                        {
                                if(t.getX() == j)
                                {
                                        double turn = Math.random();
                                        turn*=2;
                                        Math.floor(turn);
                                        if(turn == 0)
                                        {
                                                return 1;
                                        }
                                        else
                                        {
                                                return 3;
                                        }
                                }
                        }
                        return dir1;
                }
                else
				{
                        return dir1;
                }
			}
        }
}       

else
{
        int collide = y1+soup+32;
        int minimum = y1+32;
		int trace1 = x1-5;
		int trace2 = x1+5;
        for(int i = minimum; i < collide; i++)
        {
                if(i == 480)
                        {
                                double turn = Math.random();
                                turn*=2;
                                Math.floor(turn);
                                if(turn == 0)
                                {
                                        return 0;
                                }
                                else
                                {
                                        return 2;
                                }
                        }
        }
        return dir1;

        for(Trace t : Trace.getTraces())
        {
			for(int i = trace1; i < trace2; i++)
			{
                if(t.getX() == i)
                {
                        for(int j = minimum; j < collide; j++)
                        {
                                if(t.getY() == j)
                                {
                                        double turn = Math.random();
                                        turn*=2;
                                        Math.floor(turn);
                                        if(turn == 0)
                                        {
                                                return 0;
                                        }
                                        else
                                        {
                                                return 2;
                                        }
                                }
                        }
                        return dir1;
                }
                else
				{
                        return dir1;
                }
			}
        }
}       
return dir1;
}
}