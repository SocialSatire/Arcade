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
		   
		   
		//ADD MINIMUM DETECTION NIMROD
		   
		   
		public int locateTrace()
		{
			int currentSmallestAbove = 641;
			int currentSmallestBelow = 641;
			int newDir = dir1;
			for(Trace t : Trace.getTraces())
			{
				if(dir1 == 0)
				{
					for(int kevVar1 = x1-20; kevVar1 < x1-15; kevVar1++)
					{
						//if(t.getH() == false)
						//{
							if(t.getX() == kevVar1)
							{
								if(t.getY() < y1)
								{
									//Above is 1
									int traceDisplacement = t.getY()-(y1-8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestAbove)
									{
										currentSmallestAbove = traceDisplacement;
									}
								}
								else
								{
									//Below is 3
									int traceDisplacement = t.getY()-(y1+8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestBelow)
									{
										currentSmallestBelow = traceDisplacement;
									}
								}
							}
						//}
					}
					if(currentSmallestBelow < currentSmallestAbove)
					{
						newDir = 1;
					}
					else
					{
						newDir = 3;
					}
				}
				else if(dir1 == 1)
				{
					for(int kevVar1 = y1+4; kevVar1 < y1+9; kevVar1++)
					{
						//if(t.getH() == true)
						//{
							if(t.getY() == kevVar1)
							{
								if(t.getX() < x1)
								{
									//Above is 2
									int traceDisplacement = t.getX()-(x1+8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestAbove)
									{
										currentSmallestAbove = traceDisplacement;
									}
								}
								else
								{
									//Below is 0
									int traceDisplacement = t.getX()-(x1+8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestBelow)
									{
										currentSmallestBelow = traceDisplacement;
									}
								}
							}
						//}
					}
					if(currentSmallestBelow < currentSmallestAbove)
					{
						newDir = 2;
					}
					else
					{
						newDir = 0;
					}
				}
				else if(dir1 == 2)
				{
					for(int kevVar1 = x1-4; kevVar1 < x1+1; kevVar1++)
					{
						//if(t.getH() == false)
						//{
							if(t.getX() == kevVar1)
							{
								if(t.getY() < y1)
								{
									//Above is 3
									int traceDisplacement = t.getY()-(y1+8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestAbove)
									{
										currentSmallestAbove = traceDisplacement;
									}
								}
								else
								{
									//Below is 1
									int traceDisplacement = t.getY()-(y1-8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestBelow)
									{
										currentSmallestBelow = traceDisplacement;
									}
								}
							}
						//}
					}
					if(currentSmallestBelow < currentSmallestAbove)
					{
						newDir = 3;
					}
					else
					{
						newDir = 1;
					}
				}
				else if(dir1 == 3)
				{
					for(int kevVar1 = y1-12; kevVar1 < y1-7; kevVar1++)
					{
						//if(t.getH() == true)
						//{
							if(t.getY() == kevVar1)
							{
								if(t.getX() < x1)
								{
									//Above is 0
									int traceDisplacement = t.getX()-(x1-8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestAbove)
									{
										currentSmallestAbove = traceDisplacement;
									}
								}
								else
								{
									//Below is 2
									int traceDisplacement = t.getX()-(x1-8);
									if(traceDisplacement < 0)
									{
										Math.abs(traceDisplacement);
									}
									if(traceDisplacement < currentSmallestBelow)
									{
										currentSmallestBelow = traceDisplacement;
									}
								}
							}
						//}
					}
					if(currentSmallestBelow < currentSmallestAbove)
					{
						newDir = 0;
					}
					else
					{
						newDir = 2;
					}
				}
				else
				{
					newDir = dir1;
				}
			}
			return newDir;
		}
		   
	public int getChoice()
		{
 
			double soupd = Math.random();
			 
			soupd*=25;
			soupd+=5;
			 
			int soup = (int) Math.floor(soupd);
 
			double turn = Math.random();
			turn*=2;
			Math.floor(turn);
 
			int newTurn = dir1;
 
			int xtrace1 = x1-4;
			int xtrace2 = x1+15;
			int ytrace1 = y1-4;
			int ytrace2 = y1+15;
 
			int currentSmallestAbove = 641;
			int currentSmallestBelow = 641;
 
			int kevVar1 = 0;
 
			ArrayList<Trace> specificTraces1 = new ArrayList<Trace>();
			ArrayList<Trace> specificTraces2 = new ArrayList<Trace>();
 
			if(dir1 == 0)
			{
				int collide = x1+soup+32;
				int minimum = x1+32;
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
									newTurn = locateTrace();
								}
							}
						}
					}
				}
			}      
 
			else if(dir1 == 1)
			{
				int collide = y1-soup;
				int minimum = y1;
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
									newTurn = locateTrace();
								}
							}
						}
					}
				}
			}      
 
			else if(dir1 == 2)
			{
				int collide = x1-soup;
				int minimum = x1;
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
									newTurn = locateTrace();
								}
							}
						}
					}
				}
			}      
 
			else if(dir1 == 3)
			{
				int collide = y1+soup+32;
				int minimum = y1+32;
				//Wall Detection
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
				//Trace Detection
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
									newTurn = locateTrace();
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