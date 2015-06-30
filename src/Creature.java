import java.awt.Color;

public class Creature {
	Color color;
	protected int width, height;
	private double x, y;
	protected double xSpeed, ySpeed;
	double slowDown = 0.05;	
    double[][] oldPositions = new double[2][10];


	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}



	public double getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getySpeed() {
		return ySpeed;
	}

	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	public void updatePosition() {
		for (int i = 0; i < 4; i++) {
			oldPositions[0][i] = oldPositions[0][i+1];
			oldPositions[1][i] = oldPositions[1][i+1];
		}
		oldPositions[0][4] = x;
		oldPositions[1][4] = y;
		double oldX = x;
		double oldY = y;
		
		setX(getX() + xSpeed);

        if (getX() < this.width && x < oldX) {
 	
            setX(this.width);
            xSpeed = xSpeed+5;
        } else if (getX() > Boids.fieldWidth - this.width && x > oldX) {
            setX(Boids.fieldWidth - this.width);
            xSpeed = xSpeed -5;
        }
      
        
		setY(getY() + ySpeed);
        if (getY() < this.height && y < oldY) {
            setY(this.height);
            ySpeed = ySpeed +5;
        } else if (getY() > Boids.fieldHeight - this.height && y > oldY) {
            setY(Boids.fieldHeight - this.height);
            ySpeed = ySpeed - 5;
        }
        
      
        
        xSpeed -= xSpeed/Math.abs(xSpeed) * getSlowDown();
		ySpeed -= ySpeed/Math.abs(ySpeed) * getSlowDown();
	}
	
	/**
	 * @return
	 */
	private double getSlowDown() {
		return slowDown;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
    /**
	 * @return
	 */
	protected double getMaxSpeed() {
		return 0;
	}

	public void updateBoundPosition() {
		if (getX() > Boids.fieldWidth - 20*getMaxSpeed()) {
			double div = getX() - Boids.fieldWidth;
			if (div==0) 
				div=.01;
			xSpeed += -Math.abs(2*getMaxSpeed() / div);
		}
		if (getY() > Boids.fieldHeight - 20*getMaxSpeed()) {
			double div = (getY() - Boids.fieldHeight);
			if (div==0) 
				div=.01;
			ySpeed += -Math.abs(2*getMaxSpeed() / div);
		}

		if (getX() < 20*getMaxSpeed()) {
			double div = getX();
			if (div==0) 
				div=.01;
			xSpeed +=  Math.abs(2*getMaxSpeed()/ div);
		}

		if (getY() < 20*getMaxSpeed()) {
			double div = getY();
			if (div==0) 
				div=.01;			
			ySpeed += Math.abs(2*getMaxSpeed() / div);
		}

	}
	
	public String toString() {
		return ("c: "+ color+" x:" + x+" y:" + y+" xspeed:" + xSpeed+" yspeed:" + ySpeed);
	
	}
}