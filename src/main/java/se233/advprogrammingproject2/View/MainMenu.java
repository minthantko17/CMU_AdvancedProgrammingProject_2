package se233.advprogrammingproject2.View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import se233.advprogrammingproject2.Controllers.OtherHandlers;
import se233.advprogrammingproject2.Launcher;

public class MainMenu extends Pane {
    private Label gameName;
    private Label startText;

    public MainMenu(){
        Image backgroundImg = new Image(Launcher.class.getResourceAsStream("assets/backgroundImg.png"));
        BackgroundImage background=new BackgroundImage(
                backgroundImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT );
        this.setBackground(new Background(background));

        Image cursorImage=new Image(Launcher.class.getResourceAsStream("assets/redTarget.png"), 48, 48, true, true);
        Cursor cursor=new ImageCursor(cursorImage, cursorImage.getWidth()/2, cursorImage.getHeight()/2);
        this.setCursor(cursor);

        gameName = new Label("ASTEROIDS");
        gameName.setStyle("-fx-text-fill: white;");
        gameName.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 80));
        gameName.setAlignment(Pos.CENTER);
        gameName.setLayoutX(270);
        gameName.setLayoutY(250);

        startText=new Label("Press ENTER to start the game");
        startText.setStyle("-fx-text-fill: white;");
        startText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 20));
        startText.setAlignment(Pos.CENTER);
        startText.setLayoutX(350);
        startText.setLayoutY(360);

        Timeline timeline=new Timeline(
                new KeyFrame(Duration.seconds(0.5), e->{startText.setVisible(false);}),
                new KeyFrame(Duration.seconds(1), e->{startText.setVisible(true);})
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        this.getChildren().addAll(gameName, startText);
    }

}
