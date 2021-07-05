package utils;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 Add(Vector3 other){
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3 Sub(Vector3 other){
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3 Mul(double k){
        return new Vector3(this.x * k, this.y * k, this.z * k);
    }

    public Vector3 Div(double k){
        return new Vector3(this.x / k, this.y / k, this.z / k);
    }

    public double Magnitude(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    public double SquareMagnitude(){
        return x*x+y*y+z*z;
    }

    public Vector3 Normalized(){
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
