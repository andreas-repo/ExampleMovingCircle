package org.example.gui;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphicUserInterface extends Application  {

    private static final int KEYBOARD_MOVEMENT_DELTA = 5;
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    @Override
    public void start(Stage stage) throws Exception {
        final Circle circle = createCircle();
        final Group group = new Group(createInstructions(), circle);
        final TranslateTransition transition = createTranslateTransition(circle);

        final Scene scene = new Scene(group, 600, 400, Color.CORNSILK);
        moveCircleOnKeyPress(scene, circle);
        moveCircleOnMousePress(scene, circle, transition);

        stage.setScene(scene);
        stage.setTitle("Moving Circle Example");
        stage.show();
    }

    //create a circle method
    private Circle createCircle() {
        final Circle circle = new Circle(200, 150, 50, Color.BLUEVIOLET);
        circle.setOpacity(0.7);
        return circle;
    }

    //create Instructions method
    private Label createInstructions() {
        Label instructions = new Label(
                "Use the arrow keys to move the circle in small increments\n" +
                   "Click the mouse to move the circle to a given location\n" +
                   "Ctrl + Click the mouse to slowly translate the circle to a given location"
        );
        instructions.setTextFill(Color.FORESTGREEN);
        return instructions;
    }

    //create TranslateTransition method
    private TranslateTransition createTranslateTransition(final Circle circle) {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
                circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
                circle.setTranslateX(0);
                circle.setTranslateY(0);
            }
        });
        return transition;
    }

    //move circle on key press method
    private void moveCircleOnKeyPress(Scene scene, final Circle circle) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:    circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA); break;
                    case RIGHT: circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA); break;
                    case DOWN:  circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA); break;
                    case LEFT:  circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA); break;
                }
            }
        });
    }

    //move circle on mouse press method
    private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isControlDown()) {
                    circle.setCenterX(mouseEvent.getSceneX());
                    circle.setCenterY(mouseEvent.getSceneY());
                } else {
                    transition.setToX(mouseEvent.getSceneX() - circle.getCenterX());
                    transition.setToY(mouseEvent.getSceneY() - circle.getCenterY());
                    transition.playFromStart();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
