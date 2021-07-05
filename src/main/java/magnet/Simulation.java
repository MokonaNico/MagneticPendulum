package magnet;

import javafx.scene.layout.Pane;
import utils.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Simulation extends Pane {

    public List<Magnet> magnets = new ArrayList<>();
    public Pendulum pendulum;

    public double friction;
    public Vector2 center;

    public Simulation(double friction, Vector2 center){
        this.friction = friction;
        this.center = center;
    }

    public void addMagnet(Magnet magnet){
        magnets.add(magnet);
        getChildren().add(magnet);
    }

    public void addMagnets(List<Magnet> magnets){
        for(Magnet magnet : magnets){
            addMagnet(magnet);
        }
    }

    public void setPendulum(Pendulum pendulum) {
        this.pendulum = pendulum;
        getChildren().add(pendulum);
    }

}
