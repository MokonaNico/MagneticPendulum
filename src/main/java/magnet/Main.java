package magnet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import utils.Vector2;
import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String [] args){
        launch(args);
    }

    /**
     * Get a list of magnets with the color and the offset of arrays
     * @param center the center of the simulation
     * @param magnetStrength the strength of the magnet
     * @param colors the array of color for the magnet
     * @param offsetX the array of offset in x
     * @param offsetY the array of offset in y
     * @return list of magnets with the correct color and offset
     */
    public List<Magnet> getListMagnet(Vector2 center, double magnetStrength,
                                      String[] colors, double[] offsetX, double[] offsetY){
        ArrayList<Magnet> magnets = new ArrayList<>();
        for(int i = 0; i < colors.length; i++){
            Magnet magnet = new Magnet(magnetStrength);
            magnet.setCenterX(center.getX() + offsetX[i]);
            magnet.setCenterY(center.getY() + offsetY[i]);
            magnet.setFill(Color.web(colors[i]));
            magnets.add(magnet);
        }
        return magnets;
    }

    /**
     * Create a triangle of magnet
     */
    public List<Magnet> getTriangleMagnet(double dist, Vector2 center, double magnetStrength) {
        String[] colors = new String[]{"0xe2d810","0xd9138a","0x12a4d9"};
        double[] offsetX = new double[]{0, dist * Math.cos(Math.PI/6), -dist * Math.cos(Math.PI/6)};
        double[] offsetY = new double[]{-dist, dist * Math.sin(Math.PI/6), dist * Math.sin(Math.PI/6)};
        return getListMagnet(center, magnetStrength, colors, offsetX, offsetY);
    }

    /**
     * Create an hexagon of magnet
     */
    public List<Magnet> getHexagonalMagnet(double dist, Vector2 center, double magnetStrength) {
        String[] colors = new String[]{"0x6600ff","0xd9138a","0x12a4d9","0xe68a00","0x339933","0xe2d810"};
        double[] offsetX = new double[]{0, dist * Math.cos(Math.PI/6), dist * Math.cos(Math.PI/6),
                                        0, - dist * Math.cos(Math.PI/6), -dist * Math.cos(Math.PI/6)};
        double[] offsetY = new double[]{-dist, -dist * Math.sin(Math.PI/6), dist * Math.sin(Math.PI/6),
                                        dist, dist * Math.sin(Math.PI/6), -dist * Math.sin(Math.PI/6)};
        return getListMagnet(center, magnetStrength, colors, offsetX, offsetY);
    }

    /**
     * Create a line of magnet
     */
    public List<Magnet> getLineMagnet(double dist, Vector2 center, double magnetStrength){
        String[] colors = new String[]{"0xe2d810","0xd9138a","0x12a4d9"};
        double[] offsetX = new double[]{0,0,0};
        double[] offsetY = new double[]{-dist,0,dist};
        return getListMagnet(center, magnetStrength, colors, offsetX, offsetY);
    }

    /**
     * Create a square of magnet
     */
    public List<Magnet> getSquareMagnet(double dist, Vector2 center, double magnetStrength){
        String[] colors = new String[]{"0xe2d810","0xd9138a","0x12a4d9","0x339933"};
        double[] offsetX = new double[]{0,dist,0,-dist};
        double[] offsetY = new double[]{-dist,0,dist,0};
        return getListMagnet(center, magnetStrength, colors, offsetX, offsetY);
    }

    /**
     * Create a pentagon of magnet
     */
    public List<Magnet> getPentagonMagnet(double dist, Vector2 center, double magnetStrength){
        String[] colors = new String[]{"0xe2d810","0xd9138a","0x12a4d9","0xe68a00","0x339933"};
        double[] offsetX = new double[]{0,dist*Math.cos(Math.PI/10),dist*Math.cos((3*Math.PI)/10),
                                        -dist*Math.cos((3*Math.PI)/10),-dist*Math.cos(Math.PI/10)};
        double[] offsetY = new double[]{-dist,-dist*Math.sin(Math.PI/10),dist*Math.sin((3*Math.PI)/10),
                                        dist*Math.sin((3*Math.PI)/10),-dist*Math.sin(Math.PI/10)};
        return getListMagnet(center, magnetStrength, colors, offsetX, offsetY);
    }

    /**
     * Generate the fractals with the color of magnet by calculating all the final position of magnet on every position
     * @param size the size of the square that represente one start position, 1 is for all pixel (can take multiple minutes to compute)
     */
    public void generateFractals(int size, Simulation simulation, Pendulum pendulum){
        for(int x = 0; x <= 1000; x+=size){
            for(int y = 0; y <= 1000; y+=size){

                int centerx = x;
                int centery = y;
                if (size != 1) {
                    centerx = x-size/2;
                    centery = y-size/2;
                }

                pendulum.setCenterX(centerx);
                pendulum.setCenterY(centery);
                pendulum.position = new Vector2(centerx, centery);
                pendulum.currentVelocity = new Vector2(0,0);

                double i = 0;
                while(true){
                    if(Math.abs(pendulum.currentVelocity.getX()) < 0.1 &&
                            Math.abs(pendulum.currentVelocity.getY()) < 0.1 &&
                            i > 0.1)
                        break;
                    pendulum.updateVelocity(i);
                    pendulum.updatePosition(i);
                    i += 0.0001;
                }

                Rectangle tile = new Rectangle(size,size);
                tile.setX(centerx);
                tile.setY(centery);
                for(Magnet magnet : simulation.magnets){
                    if(pendulum.getBoundsInParent().intersects(magnet.getBoundsInParent())){
                        tile.setFill(magnet.getFill());
                    }
                }
                simulation.getChildren().add(tile);
            }
        }
    }

    public void renderImage(Scene scene){
        File file = new File("image.png");
        try {

            WritableImage writableImage = scene.snapshot(null);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) { ex.printStackTrace(); }
    }

    @Override
    public void start(Stage primaryStage) {

        Vector2 center = new Vector2(500,500);
        double magnetStrength = 5000000;
        double dist = 250;

        Simulation simulation = new Simulation(0.1, center);

        simulation.addMagnets(getTriangleMagnet(dist, center, magnetStrength));

        Pendulum pendulum = new Pendulum(0.1, new Vector2(0,0),
                new Vector2(0,0),simulation);
        simulation.setPendulum(pendulum);

        Polyline polyline = new Polyline();
        simulation.getChildren().add(polyline);

        Scene scene = new Scene(simulation, 1000,1000);

        primaryStage.setScene(scene);
        primaryStage.show();

        generateFractals(10, simulation, pendulum);
        //renderImage(scene);

        simulation.setOnMouseClicked(event -> {
            polyline.getPoints().clear();
            pendulum.setCenterX(event.getX());
            pendulum.setCenterY(event.getY());
            pendulum.position = new Vector2(event.getX(), event.getY());
            pendulum.currentVelocity = new Vector2(0,0);
            for(double i = 0; i < 0.5; i+=0.0001){
                polyline.getPoints().addAll(pendulum.getCenterX(), pendulum.getCenterY());
                pendulum.updateVelocity(i);
                pendulum.updatePosition(i);
            }
            polyline.getPoints().addAll(pendulum.getCenterX(), pendulum.getCenterY());
        });
    }
}
