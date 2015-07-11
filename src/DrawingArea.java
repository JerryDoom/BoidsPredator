
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
		
		offGraphics.setColor(Boids.predatorColor[0]);
		offGraphics.fillRect(Boids.posRectWidth, 0, Boids.rectWidth, Boids.posRectHeight);
		offGraphics.setColor(Color.BLACK);
		offGraphics.drawRect(Boids.posRectWidth, 0, Boids.rectWidth, Boids.posRectHeight);		

		offGraphics.setColor(Boids.predatorColor[1]);
		offGraphics.fillRect(Boids.posRectWidth, Boids.posRectHeight, Boids.rectWidth, Boids.rectHeight);
		offGraphics.setColor(Color.BLACK);
		offGraphics.drawRect(Boids.posRectWidth, Boids.posRectHeight, Boids.rectWidth, Boids.rectHeight);		

		offGraphics.setColor(Boids.predatorColor[2]);
		offGraphics.fillRect(Boids.posRectWidth, 2*Boids.posRectHeight , Boids.rectWidth, Boids.rectHeight);
		offGraphics.setColor(Color.BLACK);
		offGraphics.drawRect(Boids.posRectWidth, 2*Boids.posRectHeight, Boids.rectWidth, Boids.rectHeight);		

		offGraphics.setColor(Color.white);
		offGraphics.fillRect(Boids.posRectWidth, 0, Boids.widthScapeSize, Boids.heightScapeSize);
		offGraphics.fillRect(Boids.posRectWidth, Boids.posRectHeight, Boids.widthScapeSize , Boids.heightScapeSize);
		offGraphics.fillRect(Boids.posRectWidth, (2 * Boids.posRectHeight), Boids.widthScapeSize, Boids.heightScapeSize);
		
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
