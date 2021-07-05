package magnet;

import javafx.scene.shape.Circle;

public class Magnet extends Circle {
    public double strength;

    public Magnet(double strength){
        setRadius(30);
        this.strength = strength;
    }
}

