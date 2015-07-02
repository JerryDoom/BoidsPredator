import java.applet.Applet;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Boids extends Applet {
    Thread animator;

    DrawingArea drawingArea;

    JLabel numOfBoidsLabel;

    TextField numOfBoidsField;

    TextField positionField;

    TextField speedField;

    TextField repelField;
    
    SpinnerNumberModel numOfBoidsModel;

    static final int fieldWidth = 750;

    static final int fieldHeight = 500;

    static double speedChange = 92;

    static double positionChange = 80;

    static int numOfBoids = 20;
    
    static int numOfPredators = 3;

    static int predatorPositionChange = 80;

    static double maxDistance = 20;

    Creatures creatures = new Creatures();

    static double boundRepeller = 99;

    private TextField distField;

    static boolean colorOn = true;

	static int catchDistance = 10;

	static double maxChaseSpeed = 4;
	
	static double maxBSpeed = 4;
	static double maxPSpeed = 2.5;

	static int delay = 40;	
	
    public void init() {
        setBackground(Color.white);
        setForeground(Color.black);

        for (int i = 0; i < numOfBoids; i++) {
            Boid boid = makeBoid();
            creatures.addBoid(boid);
            creatures.setMain(this);

        }

        for (int i = 0; i < numOfPredators; i++) {
            Predator predator = makePredator();
            creatures.addPredator(predator);

        }        


        Panel upperPanel = new Panel(new FlowLayout(FlowLayout.LEADING, 7, 5));
        Panel lowerPanel = new Panel(new FlowLayout(FlowLayout.LEADING, 7, 0));
        
        numOfBoidsLabel = new JLabel("No. of boids: " );
        upperPanel.add(numOfBoidsLabel);
        numOfBoidsModel = new SpinnerNumberModel(numOfBoids,
                0, 99, 1);
        JSpinner countSpinner = new JSpinner(numOfBoidsModel);        
        countSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                    int num = Integer.parseInt(String
                            .valueOf(source.getValue()));
                    if (creatures.boidsSize() < num) {
                        creatures.addBoid(makeBoid());
                    } else if (creatures.boidsSize() > num) {
                        creatures.removeBoid(creatures.boidsSize() - 1);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        upperPanel.add(countSpinner);
        
        JLabel  maxBSpeedLabel = new JLabel("Max. Speed" );
        upperPanel.add(maxBSpeedLabel);
        JSpinner maxBSpeedSpinner = new JSpinner(new SpinnerNumberModel(Boids.maxBSpeed,
                0, 20, .1));
        maxBSpeedSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                	maxBSpeed = Double.parseDouble(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        upperPanel.add(maxBSpeedSpinner );
        
        JLabel positionLabel = new JLabel("Drive to centre");
        upperPanel.add(positionLabel);
        JSpinner posSpinner = new JSpinner(new SpinnerNumberModel(
                positionChange, 1, 99, 1));
        posSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                    positionChange = Double.parseDouble(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        upperPanel.add(posSpinner);
        
        
        
        JLabel speedLabel = new JLabel("Av. speed");
        upperPanel.add(speedLabel);
        JSpinner speedSpinner = new JSpinner(new SpinnerNumberModel(
                speedChange, 1, 100, 1));
        speedSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                    speedChange = Double.parseDouble(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        upperPanel.add(speedSpinner);
        
        JLabel repelLabel = new JLabel("Repel");
        upperPanel.add(repelLabel);

        JSpinner repelSpinner = new JSpinner(new SpinnerNumberModel(
                boundRepeller, 1, 99, 1));
        repelSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                    boundRepeller = Double.parseDouble(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        upperPanel.add(repelSpinner);

        
        JLabel minDistanceLabel = new JLabel("Min comf distance");
        upperPanel.add(minDistanceLabel);
        
        JSpinner maxDistanceSpinner = new JSpinner(new SpinnerNumberModel(
                maxDistance, 1, 99, 1));
        maxDistanceSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                    maxDistance = Double.parseDouble(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        upperPanel.add(maxDistanceSpinner);
        
        
        JLabel  predLabel = new JLabel("No. of predators " );
        lowerPanel.add(predLabel);
        JSpinner countPredSpinner = new JSpinner(new SpinnerNumberModel(numOfPredators,
                0, 99, 1));
        countPredSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                    int num = Integer.parseInt(String
                            .valueOf(source.getValue()));
                    if (creatures.predatorsSize() < num) {
                        creatures.addPredator(makePredator());
                    } else if (creatures.predatorsSize() > num) {
                        creatures.removePredator(creatures.predatorsSize() - 1);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        lowerPanel.add(countPredSpinner);

        JLabel  maxChSpeedLabel = new JLabel("Max chase speed" );
        lowerPanel.add(maxChSpeedLabel);
        JSpinner maxChaseSpeedSpinner = new JSpinner(new SpinnerNumberModel(Boids.maxChaseSpeed,
                0, 20, .1));
        maxChaseSpeedSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                	maxChaseSpeed = Double.parseDouble(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        lowerPanel.add(maxChaseSpeedSpinner );
        

        JLabel  maxPSpeedLabel = new JLabel("Max Speed" );
        lowerPanel.add(maxPSpeedLabel);
        JSpinner maxPSpeedSpinner = new JSpinner(new SpinnerNumberModel(Boids.maxPSpeed,
                0, 20, .1));
        maxPSpeedSpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                	maxPSpeed = Double.parseDouble(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        lowerPanel.add(maxPSpeedSpinner );
        
     
        JLabel  delayLabel = new JLabel("Refresh delay in ms" );
        lowerPanel.add(delayLabel);
        JSpinner delaySpinner = new JSpinner(new SpinnerNumberModel(Boids.delay,
                0, 1000, 1));
        delaySpinner.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                try {
                	delay = Integer.parseInt(String.valueOf(source
                            .getValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        lowerPanel.add(delaySpinner );
        
        
        Panel bottomP = new Panel();
		bottomP.setLayout(new GridLayout(2, 1));
		bottomP.add(upperPanel);
		bottomP.add(lowerPanel);
        
        drawingArea = new DrawingArea(creatures);
        drawingArea.setSize(fieldWidth, fieldHeight);
        setLayout(new BorderLayout());
        this.add(bottomP, BorderLayout.SOUTH);
        this.add(drawingArea, BorderLayout.CENTER);
    }

    /**
     * @return
     */
    private Predator makePredator() {
        int x = (int) (Math.random() * (getWidth() - 200) + 100);
        int y = (int) (Math.random() * (getHeight() - 200) + 100);
        Predator predator = new Predator();
        double xSpeed = (predator.getMaxSpeed() - Math.random()
                * predator.getMaxSpeed() * 2);
        double ySpeed = (predator.getMaxSpeed() - Math.random()
                * predator.getMaxSpeed() * 2);
        Color color = new Color((int) (Math.random() * 256 * 256 * 256));
        predator.setColor(color);
        predator.setX(x);
        predator.setY(y);
        predator.setxSpeed(xSpeed);
        predator.setySpeed(ySpeed);

        return predator;
    }

    private Boid makeBoid() {
        int x = (int) (Math.random() * (getWidth() - 200) + 100);
        int y = (int) (Math.random() * (getHeight() - 200) + 100);
        Boid boid = new Boid();
        double xSpeed = (boid.getMaxSpeed() - Math.random()
                * boid.getMaxSpeed() * 2);
        double ySpeed = (boid.getMaxSpeed() - Math.random()
                * boid.getMaxSpeed() * 2);
        Color color = new Color((int) (Math.random() * 256 * 256 * 256));
        boid.setColor(color);
        boid.setX(x);
        boid.setY(y);
        boid.setxSpeed(xSpeed);
        boid.setySpeed(ySpeed);
        return boid;
    }

    public void start() {

    }

    public void stop() {
        animator = null;
    }

    public void destroy() {
    }

  

	

	
}