
import java.awt.*;

public class Boid extends Creature {
	int scareDistance = 80;

	public Boid() {
		width = 5;
		height = 5;
	}

	public Boid(int x, int y, double xSpeed, double ySpeed, Color color) {
		this.setX(x);
		this.setY(y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.color = color;
	}

	void draw(Graphics g) {
		if (Boids.colorOn)
			g.setColor(color);
		else
			g.setColor(Color.black);
		for (int i = 0; i < 5; i++) {
			g
					.fillOval((int) oldPositions[0][i],
							(int) oldPositions[1][i], i, i);
		}
		g.fillOval((int) getX(), (int) getY(), getWidth(), getHeight());
	}

	public void updateSpeedVelocity(PointDouble averageSpeed) {
		xSpeed += (averageSpeed.getX() - xSpeed)
				/ (10 * (100 - Boids.speedChange) + 1);
		ySpeed += (averageSpeed.getY() - ySpeed)
				/ (10 * (100 - Boids.speedChange) + 1);
		
	}

	public void updateSpeedPosition(Point averagePosition) {
		xSpeed += (averagePosition.getX() - getX())
				/ (10 * (100 - Boids.positionChange) + 1);
		ySpeed += (averagePosition.getY() - getY())
				/ (10 * (100 - Boids.positionChange) + 1);

	}

	public void updateSpeedRepel(PointDouble repel) {
		xSpeed += repel.getX() / (10 * (100 - Boids.boundRepeller) + 1);
		ySpeed += repel.getY() / (10 * (100 - Boids.boundRepeller) + 1);
	}

	public void boundSpeed() {

		double ratio = Math.abs(xSpeed) / (Math.abs(ySpeed)+.01);

		if (Math.abs(xSpeed) > Math.abs(ySpeed)) {
			xSpeed = xSpeed /(Math.abs(xSpeed)+.01) * getMaxSpeed();
			ySpeed = ySpeed / (Math.abs(ySpeed)+.01) * getMaxSpeed() / (ratio+.001);
		} else {
			ySpeed = ySpeed / (Math.abs(ySpeed)+.01)* getMaxSpeed();
			xSpeed = xSpeed / (Math.abs(xSpeed)+.01) * getMaxSpeed() * ratio;
		}

	}

	/**
	 * @param p
	 */
	public void updatePredatorFlee(Point p) {
		if (Math.sqrt(Math.pow(Math.abs(p.getX() - getX()), 2)
				+ Math.pow(Math.abs(p.getY() - getY()), 2)) < scareDistance) {
			if (Math.abs(p.getX() - getX()) < scareDistance) {
				xSpeed -= (p.getX() - getX()) / 20;
			}
			if (Math.abs(p.getY() - getY()) < scareDistance) {
				ySpeed -= (p.getY() - getY()) / 20;
			}
		}

	}
	
	protected double getMaxSpeed() {
		return Boids.maxBSpeed;
	}

}
