package magnet;

import javafx.scene.shape.Circle;
import utils.Vector2;
import utils.Vector3;

public class Pendulum extends Circle {

    public double pullbackStrength;
    public Vector2 currentVelocity;
    public Vector2 position;
    public Simulation simulation;

    public Pendulum(double pullbackStrength, Vector2 initialPosition,
                    Vector2 initialVelocity, Simulation simulation) {
        setRadius(30);
        currentVelocity = initialVelocity;
        position = initialPosition;
        setCenterX(initialPosition.getX());
        setCenterY(initialPosition.getY());
        this.pullbackStrength = pullbackStrength;
        this.simulation = simulation;
    }

    public void updateVelocity(double timeStep){
        Vector2 selfPos = new Vector2(getCenterX(), getCenterY());
        Vector2 acceleration = new Vector2(0, 0);
        Vector2 vectorToCenter = selfPos.Sub(simulation.center);

        // Compute pullback acceleration
        acceleration = acceleration.Sub(vectorToCenter.Mul(pullbackStrength));

        // Compute magnet acceleration
        for(Magnet magnet : simulation.magnets){
            Vector3 selfPos3 = new Vector3(getCenterX(), getCenterY(),100);
            Vector3 otherPos3 = new Vector3(magnet.getCenterX(), magnet.getCenterY(), 0);
            Vector2 otherPos = new Vector2(magnet.getCenterX(), magnet.getCenterY());
            double dist = selfPos3.Sub(otherPos3).Magnitude();
            Vector2 vectorToMagnet = selfPos.Sub(otherPos);
            acceleration = acceleration.Sub(vectorToMagnet.Div(dist*dist*dist).Mul(magnet.strength));
        }

        // Compute friction acceleration
        acceleration = acceleration.Add(currentVelocity.Mul(-simulation.friction));

        // Update velocity by the new acceleration
        currentVelocity = currentVelocity.Add(acceleration.Mul(timeStep));
    }

    public void updatePosition(double timeStep){
        Vector2 nextPosition = position.Add(currentVelocity.Mul(timeStep));
        setPosition(nextPosition);
    }


    public void setPosition(Vector2 vector2){
        position = vector2;
        this.setCenterX(vector2.getX());
        this.setCenterY(vector2.getY());
    }
}
