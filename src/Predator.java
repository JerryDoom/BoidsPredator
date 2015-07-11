import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


/**
 * @author Ando
 *
 */
public class Predator extends Boid {
	int mode = 0;
	private double seekRadius = 100;
	private Boid target ;
	private int stamina = 70;
	public Predator() {
		width = 10;
		height = 10;
		this.setCapturable(false);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param speed
	 * @param speed2
	 * @param red
	 */
	public Predator(int x, int y, double xSpeed, double ySpeed, Color red) {
		width = 10;
		height = 10;
		this.setX(x);
		this.setY(y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.color = red;		
		this.setCapturable(false);
	}



	void draw (Graphics g) {
		if (Boids.colorOn)
			g.setColor(color);
		else
			g.setColor(Color.black);
		for (int i = 0; i < 5; i++) {
			g.fillOval((int)oldPositions[0][i]-i, (int)oldPositions[1][i]-i, i*2, i*2);
		}
		g.fillOval((int)getX()-getWidth()/2, (int)getY()-getHeight()/2, getWidth(), getHeight());
	}

	


	/**
	 * 
	 */
	public void calcSpeed(Point boidsPosition) {
		xSpeed += (boidsPosition.getX() - getX()) / (10*(100-Boids.predatorPositionChange) +1);
		ySpeed += (boidsPosition.getY() - getY()) / (10*(100-Boids.predatorPositionChange) +1);

		
	}

	/**
	 * @return
	 */
	public double getSeekRadius() {
		return seekRadius;
	}

	/**
	 * @return
	 */
	public boolean isFollowing() {
		if (target != null)
			return true;
		return false;
	}

	/**
	 * @param i
	 */
	public void decStamina(int i) {
		stamina = stamina - i;
		
	}

	/**
	 * @return
	 */
	public int getStamina() {
		return stamina;
	}

    /**
     * @param object
     */
    public void setFollow(Boid boid) {
        target = boid;
    }

    /**
     * @param i
     */
    public void setStamina(int i) {
        stamina = i;
    }

    /**
     * @return
     * 
     */
    public Boid calcSpeedTarget() {
        double diffX = target.getX() - getX();
        double diffY = target.getY() - getY();
        double from = Math.sqrt(Math.pow(diffX,2) +Math.pow(diffY,2));
        
        if(target != null && target.isCapturable() && (getY() <= getTrappedHeight() - Boids.posRectHeight) || getY() > getTrappedHeight())
        	return target;
        if ((!target.isCapturable() && from < Boids.catchDistance) || (target.isCapturable() && target.isCaptured()) ) {
        	return target;
        }
        double ratio = Math.abs(diffX) / Math.abs(diffY);
        if (Math.abs(diffX) > Math.abs(diffY)) {
            xSpeed = Math.abs(diffX)/diffX * Boids.maxChaseSpeed;
            ySpeed = Math.abs(diffY)/diffY * Boids.maxChaseSpeed /ratio;
        } else { 
            ySpeed = Math.abs(diffY)/diffY * Boids.maxChaseSpeed;
            xSpeed = Math.abs(diffX)/diffX * Boids.maxChaseSpeed * ratio;
        }
        return null;
    }

    /**
     * @param i
     */
    public void incStamina(int i) {
        stamina += i; 
    }

	protected double getMaxSpeed() {
		return Boids.maxPSpeed;
	}


}
