import java.awt.Point;
import java.util.ArrayList;

/*
 * Created on Aug 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Ando
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreatureList extends ArrayList {

	Boids mainProg;	
    Point getAveragePosition (){
    	
		int x = 0, y = 0;
		Creature cr;
		for (int i = 0; i< this.size(); i++) {
			cr = (Creature)this.get(i);
			x += cr.getX();
			y += cr.getY();
		}
		if (size() > 0) {
			x = x /size();
			y = y /size();
		}

		return new Point(x, y);
    }
    
	/**
	 * @param main
	 */
	public void setMain(Boids main) {
		mainProg = main;
		
	}

}
