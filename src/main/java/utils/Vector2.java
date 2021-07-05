package utils;

public class Vector2 {
    private double x;
    private double y;

    public Vector2(){
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2 Add(Vector2 other){
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 Sub(Vector2 other){
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 Mul(double k){
        return new Vector2(this.x * k, this.y * k);
    }

    public Vector2 Div(double k){
        return new Vector2(this.x / k, this.y / k);
    }

    public double Magnitude(){
        return Math.sqrt(x*x+y*y);
    }

    public double SquareMagnitude(){
        return x*x+y*y;
    }

    public Vector2 Normalized(){
        return this.Div(this.Magnitude());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
