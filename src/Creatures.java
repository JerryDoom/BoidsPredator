import java.awt.Graphics;

public class Creatures {
	BoidList boids = new BoidList();
	PredatorList predators = new PredatorList();
	
	void draw(Graphics g) {
		boids.draw(g, predators);
		predators.draw(g, boids);
	}

	/**
	 * @param boid
	 */
	public void addBoid(Boid boid) {
		boids.add(boid);
		
	}

	/**
	 * @param predator2
	 */
	public void addPredator(Predator predator) {
		predators.add(predator);
		
	}

	/**
	 * @return
	 */
	public int boidsSize() {
		return boids.size();
	}

    /**
     * @return
     */
    public int predatorsSize() {
        return predators.size();
    }
    
    /**
	 * @param i
	 */
	public void removeBoid(int i) {
		boids.remove(i);
		
	}

    public void removePredator(int i) {
        predators.remove(i);
        
    }

	/**
	 * @param boids
	 */
	public void setMain(Boids main) {
		boids.setMain(main);
		
		
	}
    
}
