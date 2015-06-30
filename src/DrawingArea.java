
import java.awt.*;

public class DrawingArea extends Panel implements Runnable {
    Thread animator;
	Graphics offGraphics;
	Image offImage;
	Creatures creatures;

	public DrawingArea(Creatures creatures) {
		this.creatures = creatures;
		animator = new Thread(this);
		animator.setPriority(Thread.MAX_PRIORITY);
		animator.start();
	}

	public void update(Graphics g) {
		if (offGraphics == null) {
			offImage = createImage(Boids.fieldWidth+1, Boids.fieldHeight+1);
			offGraphics = offImage.getGraphics();

		}
		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, Boids.fieldWidth, Boids.fieldHeight);
		offGraphics.setColor(Color.BLACK);
		offGraphics.drawRect(0, 0, Boids.fieldWidth, Boids.fieldHeight);		
		creatures.draw(offGraphics);
		g.drawImage(offImage, 0, 0, null);


	}

	public void run() {
		while (Thread.currentThread() == animator) {
			repaint();
			try {
				Thread.sleep(Boids.delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void paint(Graphics g) {
		//Draw a Rectangle around the applet's display area.
		if (offImage != null) {
			g.drawImage(offImage, 0, 0, null);
		}
	}


}