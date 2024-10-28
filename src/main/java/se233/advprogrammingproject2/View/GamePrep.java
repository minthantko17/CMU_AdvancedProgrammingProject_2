package se233.advprogrammingproject2.View;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import se233.advprogrammingproject2.Controllers.OtherHandlers;
import se233.advprogrammingproject2.Launcher;

public class GamePrep extends Pane {
    Image ship1, ship2;
    ImageView ship1ImgView, ship2ImgView;
    Label ship1Label, ship2Label;

    public GamePrep(){
        ship1 = new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        ship2 = new Image(Launcher.class.getResourceAsStream("assets/playerShip1_blue.png"));

        ship1ImgView = new ImageView(ship1);
        ship2ImgView = new ImageView(ship2);

        ship1Label = new Label("Ship 1");
        ship2Label = new Label("Ship 2");

        ship1ImgView.setPreserveRatio(true);
        ship1ImgView.setFitWidth(200);
        ship1ImgView.setX((Launcher.WIDTH/2)-300);
        ship1ImgView.setY(150);

        ship2ImgView.setPreserveRatio(true);
        ship2ImgView.setFitWidth(200);
        ship2ImgView.setX((Launcher.WIDTH/2)+100);
        ship2ImgView.setY(150);

        ship1Label.setLayoutX((Launcher.WIDTH/2)-300);
        ship1Label.setLayoutY(400);

        ship2Label.setLayoutX((Launcher.WIDTH/2)+100);
        ship2Label.setLayoutY(400);

        this.getChildren().addAll(ship1ImgView, ship2ImgView, ship1Label, ship2Label);

        ship1ImgView.setOnMouseClicked(e->{
            Launcher.choosenShip=1;
            OtherHandlers.changeToGameStage();
        });
        ship2ImgView.setOnMouseClicked(e->{
            Launcher.choosenShip=2;
            OtherHandlers.changeToGameStage();
        });
    }
}
