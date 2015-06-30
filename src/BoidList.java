
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ando
 * Date: 13.08.2004
 * Time: 13:29:54
 * To change this template use Options | File Templates.
 */
public class BoidList extends CreatureList{

	private Point averagePosition;
	private PointDouble averageSpeed;

	PointDouble averageSpeed(Boid boid2) {
		double xSpeed = 0, ySpeed = 0;
		Boid boid;
		for (int i = 0; i< this.size(); i++) {
			boid = (Boid)this.get(i);
			if (boid != boid2) {
				xSpeed += boid.getxSpeed();
				ySpeed += boid.getySpeed();
			}
		}
		if (size() > 1) {
			xSpeed = xSpeed /(size()-1);
			ySpeed = ySpeed /(size()-1);
		}
		return new PointDouble(xSpeed, ySpeed);
	}

	void remove (Boid boid) {
		if (this.contains(boid)) {
			super.remove(boid);
			mainProg.numOfBoidsModel.setValue(new Integer(this.size()));			
		}
		
	}
	
	Point averagePosition(Boid boid2) {
		int x = 0, y = 0;
		Boid boid;
		for (int i = 0; i< this.size(); i++) {
			boid = (Boid)this.get(i);
			if (boid != boid2) {
				x += boid.getX();
				y += boid.getY();
			}
		}
		if (size() > 1) {
			x = x /(size()-1);;
			y = y /(size()-1);;
		}

		return new Point(x, y);
	}
	
	PointDouble repeller(Boid boid) {
		double x = 0, y = 0;
		Boid boid2;
		for (int i = 0; i< this.size(); i++) {
			boid2 = (Boid)this.get(i);
			if (boid != boid2) {
				double distance = Math.sqrt(Math.pow((boid.getX() - boid2.getX()), 2) + Math.pow((boid.getY() - boid2.getY()), 2));
				if (distance < Boids.maxDistance) {
					double div = (boid2.getX() - boid.getX());
					if (div==0) {
						div=1;
					}
					if (Math.abs(y)<Math.abs(boid.getMaxSpeed()/div)){
						x =x- boid.getMaxSpeed()*Boids.maxDistance/div;
					}
					div = (boid2.getY() - boid.getY());
					if (div==0) {
						div=1;
					}					
					if (Math.abs(y)<Math.abs(boid.getMaxSpeed()/div)){
	                	y = y-boid.getMaxSpeed()*Boids.maxDistance/div;
					}
				}
			}
		}
		return new PointDouble(x, y);
	}

	void draw (Graphics g, PredatorList predators) {
		Boid boid;
		Point p = predators.getAveragePosition();
		for (int i = 0; i< this.size(); i++) {
			boid = (Boid)this.get(i);
			boid.updateSpeedVelocity(averageSpeed(boid));
			boid.updateSpeedPosition(averagePosition(boid));
			boid.updateSpeedRepel(repeller(boid));
			boid.updatePredatorFlee(p);
			boid.updateBoundPosition();
			boid.boundSpeed();
			boid.updatePosition();
			boid.draw(g);
		}
	}




}
