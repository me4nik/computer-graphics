package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 600, 400);

        Ellipse ellipse = new Ellipse(300, 200, 40, 100);
        ellipse.setFill(Color.LIGHTGREEN);
        root.getChildren().add(ellipse);

        Polygon pol1 = new Polygon(50, 150, 150, 40, 300, 200);
        Polygon pol2 = new Polygon(550, 150, 450, 40, 300, 200);
        Polygon pol3 = new Polygon(50, 250, 150, 360, 300, 200);
        Polygon pol4 = new Polygon(550, 250, 450, 360, 300, 200);

        pol1.setFill(Color.CYAN);
        pol2.setFill(Color.CYAN);
        pol3.setFill(Color.CYAN);
        pol4.setFill(Color.CYAN);

        root.getChildren().addAll(pol1, pol2, pol3, pol4);

        Line line1 = new Line(280, 20, 290, 150);
        Line line2 = new Line(320, 20, 310, 150);

        root.getChildren().addAll(line1, line2);

        primaryStage.setTitle("Lab1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
