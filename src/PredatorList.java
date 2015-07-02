import java.awt.Graphics;
import java.awt.Point;

/**
 * @author Ando
 *
 */
public class PredatorList extends CreatureList {
	
	
	Boid findTarget(BoidList boids, Predator pred) {
		double x = 0, y = 0;
		Boid boid;
		for (int i = 0; i< boids.size(); i++) {
			boid = (Boid)boids.get(i);
			double distance = Math.sqrt(Math.pow((pred.getX() - boid.getX()), 2) + Math.pow((pred.getY() - boid.getY()), 2));
			if (distance < pred.getSeekRadius()) {
				return boid;
			}
		}
		
		return null;
	}
	
	PointDouble repeller(Predator pred) {
		double x = 0, y = 0;
		Boid pred2;
		for (int i = 0; i< this.size(); i++) {
			pred2 = (Predator)this.get(i);
			if (pred != pred2) {
				double distance = Math.sqrt(Math.pow((pred.getX() - pred2.getX()), 2) + Math.pow((pred.getY() - pred2.getY()), 2));
				if (distance < Boids.maxDistance) {
					double div = (pred2.getX() - pred.getX());
					if (div==0) {
						div=1;
					}
					if (Math.abs(y)<Math.abs(pred.getMaxSpeed()/div)){
						x =x- pred.getMaxSpeed()*Boids.maxDistance/div;
					}
					div = (pred2.getY() - pred.getY());
					if (div==0) {
						div=1;
					}					
					if (Math.abs(y)<Math.abs(pred.getMaxSpeed()/div)){
	                	y = y-pred.getMaxSpeed()*Boids.maxDistance/div;
					}
				}
			}
		}
		return new PointDouble(x, y);
	}	
	/**
	 * @param g
	 */
	public void draw(Graphics g, BoidList boids) {
		Point p = boids.getAveragePosition();
		Predator predator;
		for (int i = 0; i< this.size(); i++) {
			predator = (Predator)this.get(i);
			if (predator.isFollowing()) {
				predator.decStamina(1);
				Boid boid = predator.calcSpeedTarget();
				if (boid!= null) {
					//boids.remove(boid);
					
			
					predator.setFollow(null);
				}
				if  (predator.getStamina() < 1) {
					predator.setFollow(null);
				}
			} else {
			    if (predator.getStamina() < 100) {
			        predator.incStamina(1);
			    }
				Boid boid = findTarget(boids, predator);
				if (boid != null && predator.getStamina() > 99) {
					predator.setFollow(boid);
					predator.setStamina(100);
				} else {
					predator.calcSpeed(p);
					predator.updateSpeedRepel(repeller(predator));					
					predator.updateBoundPosition();
					predator.boundSpeed();
				}
			}
			predator.updatePosition();
			predator.draw(g);
		}
		
	}
	
	
}
