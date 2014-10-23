/*Slick0100a.java
Copyright 2012, R.G.Baldwin

A simple program that shows the method definitions 
required by the Slick framework.

Tested using JDK 1.7 under WinXP
*********************************************************/
 
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
 
public class Slick0100a extends BasicGame{

  public Slick0100a(){
    //Call to superclass constructor is required.
    super("This title will be overridden later.");
  }//end constructor
  //----------------------------------------------------//

  public static void main(String[] args)
                                    throws SlickException{
    //Constructor for AppGameContainer requires parameter
    // of interface type Game. Hence, object of this class
    // must provide concrete definitions of the five
    // methods declared in the Game class. Two of those
    // methods are overridden in the BasicGame class. The
    // other three are not.
    AppGameContainer app = 
                       new AppGameContainer(new Slick0100a());
    app.start();//this statement is required
  }//end main
  //----------------------------------------------------//

  @Override
  public void init(GameContainer gc)
                                   throws SlickException {
    //Concrete override required.
    //Do any required initialization here.
  }//end init
  //----------------------------------------------------//

  @Override
  public void update(GameContainer gc, int delta)
                                    throws SlickException{
    //Concrete override required.
    //Put the game logic here.
  }//end update
  //----------------------------------------------------//

  public void render(GameContainer gc, Graphics g)
                                    throws SlickException{
    //Concrete override required.
    //Draw the current state of the game here.
  }//end render
  //----------------------------------------------------//
  public String getTitle(){
    //Concrete override is optional. Overridden in 
    // BasicGame class. When overridden here, overrides
    // the title provided in the constructor above.
    return "THIS IS MIKE'S DOING REMEMBER";
  }//end getTitle
  //----------------------------------------------------//
  
  public boolean closeRequested(){
    //Concrete override is optional. Overridden in 
    // BasicGame class.
    return false;
  }//end closeRequested
}//end class Slick0100a