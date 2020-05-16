package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 650, 120);

        Arc arcSupport = new Arc(80, 60, 10, 10, 140, 210);
        arcSupport.setStroke(Color.BLACK);
        arcSupport.setStrokeWidth(2);
        arcSupport.setFill(Color.WHITE);
        root.getChildren().add(arcSupport);

        Rectangle rectangleSupport = new Rectangle(50, 69, 55, 5);
        rectangleSupport.setStroke(Color.BLACK);
        rectangleSupport.setStrokeWidth(2);
        rectangleSupport.setFill(Color.WHITE);
        root.getChildren().add(rectangleSupport);
        Rectangle support3 = new Rectangle(36, 73, 83, 19);
        support3.setStroke(Color.BLACK);
        support3.setStrokeWidth(2);
        support3.setFill(Color.LIGHTBLUE);
        root.getChildren().add(support3);

        Rectangle button1 = new Rectangle(40, 78, 10, 3);
        button1.setStroke(Color.BLACK);
        button1.setStrokeWidth(1);
        button1.setFill(Color.WHITE);
        root.getChildren().add(button1);

        Rectangle button2 = new Rectangle(106, 78, 10, 3);
        button2.setStroke(Color.BLACK);
        button2.setStrokeWidth(1);
        button2.setFill(Color.WHITE);
        root.getChildren().add(button2);

        Rectangle button3 = new Rectangle(93, 78, 10, 3);
        button3.setStroke(Color.BLACK);
        button3.setStrokeWidth(1);
        button3.setFill(Color.WHITE);
        root.getChildren().add(button3);

        Path border = new Path();
        border.setStrokeWidth(2);
        MoveTo moveTo_11 = new MoveTo(40, 10);
        QuadCurveTo curve_11 = new QuadCurveTo(71, 4, 104, 2);
        QuadCurveTo curve_12 = new QuadCurveTo(106, 28, 112, 59);
        QuadCurveTo curve_13 = new QuadCurveTo(80, 62, 46, 67);
        QuadCurveTo curve_14 = new QuadCurveTo(45, 38, 40, 10);
        border.getElements().addAll(moveTo_11, curve_11, curve_12, curve_13, curve_14);
        border.setFill(Color.WHITE);
        root.getChildren().add(border);

        Path screen = new Path();
        screen.setStrokeWidth(2);
        MoveTo moveTo_21 = new MoveTo(49, 16);
        QuadCurveTo curve_21 = new QuadCurveTo(72, 10, 97, 9);
        QuadCurveTo curve_22 = new QuadCurveTo(101, 29, 102, 51);
        QuadCurveTo curve_23 = new QuadCurveTo(80, 57, 55, 58);
        QuadCurveTo curve_24 = new QuadCurveTo(49, 34, 49, 16);
        screen.getElements().addAll(moveTo_21, curve_21, curve_22, curve_23, curve_24);
        screen.setFill(Color.LIGHTBLUE);
        root.getChildren().add(screen);

        Ellipse eye1 = new Ellipse(66, 27, 2, 5);
        eye1.setStroke(Color.BLACK);
        eye1.setStrokeWidth(1);
        eye1.setFill(Color.WHITE);
        root.getChildren().add(eye1);

        Ellipse eye2 = new Ellipse(83, 25, 2, 5);
        eye2.setStroke(Color.BLACK);
        eye2.setStrokeWidth(1);
        eye2.setFill(Color.WHITE);
        root.getChildren().add(eye2);

        Ellipse eyeBall1 = new Ellipse(66, 27, 1, 1);
        eyeBall1.setFill(Color.BLACK);
        root.getChildren().add(eyeBall1);

        Ellipse eyeBall2 = new Ellipse(83, 25, 1, 1);
        eyeBall2.setFill(Color.BLACK);
        root.getChildren().add(eyeBall2);

        Path smiling = new Path();
        MoveTo moveTo_31 = new MoveTo(57, 35);
        smiling.setStrokeWidth(1);
        QuadCurveTo curve_31 = new QuadCurveTo(66, 44, 80, 41);
        QuadCurveTo curve_32 = new QuadCurveTo(77, 48, 71, 41.8);
        smiling.getElements().addAll(moveTo_31, curve_31, curve_32);
        smiling.setFill(Color.WHITE);
        root.getChildren().add(smiling);
        Path mouth = new Path();
        MoveTo moveTo_32 = new MoveTo(80, 41);
        QuadCurveTo curve_33 = new QuadCurveTo(88, 37, 93, 31);
        mouth.setStrokeWidth(1);
        mouth.getElements().addAll(moveTo_32, curve_33);
        root.getChildren().add(mouth);

        Line hand1 = new Line(45, 50, 28, 60);
        hand1.setStroke(Color.BLACK);
        hand1.setStrokeLineCap(StrokeLineCap.ROUND);
        hand1.setStrokeWidth(3);
        root.getChildren().add(hand1);
        Line hand2 = new Line(109, 43, 131, 49);
        hand2.setStroke(Color.BLACK);
        hand2.setStrokeLineCap(StrokeLineCap.ROUND);
        hand2.setStrokeWidth(3);
        root.getChildren().add(hand2);
        int cycles = 2;
        double time = 3000;

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(cycles);
        rotateTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(50);
        translateTransition.setToX(500);

        translateTransition.setCycleCount(cycles);
        translateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
        scaleTransition.setToX(0.2);
        scaleTransition.setToY(0.2);
        scaleTransition.setCycleCount(cycles);
        scaleTransition.setAutoReverse(true);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(scaleTransition, rotateTransition, translateTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
        primaryStage.setResizable(false);
        primaryStage.setTitle("PC Animation");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
