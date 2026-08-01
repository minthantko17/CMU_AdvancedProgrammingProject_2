package se233.advprogrammingproject2.View;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import se233.advprogrammingproject2.Controllers.GameController;
import se233.advprogrammingproject2.Controllers.OtherHandlers;
import se233.advprogrammingproject2.Launcher;

public class GamePrep extends Pane {
    Image ship1, ship2, ship1Text, ship2Text;
    ImageView ship1ImgView, ship2ImgView, ship1TextImgView, ship2TextImgView;
    Label title;
    Rectangle rec1, rec2;

    public GamePrep(){

        Image backgroundImg = new Image(Launcher.class.getResourceAsStream("assets/backgroundImg.png"));

        BackgroundImage background = new BackgroundImage(
                backgroundImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(background));

        Image cursorImage=new Image(Launcher.class.getResourceAsStream("assets/redTarget.png"), 48, 48, true, true);
        Cursor cursor = new ImageCursor(cursorImage, cursorImage.getWidth()/2, cursorImage.getHeight()/2);
        this.setCursor(cursor);

        rec1 = new Rectangle(145, 200, 310, 500);
        rec1.setFill(Color.rgb(0,0,0,0.3));
        rec1.setStroke(Color.WHITE);
        rec1.setStrokeWidth(5);
        rec1.setArcHeight(40);
        rec1.setArcWidth(40);

        rec2 = new Rectangle(545, 200, 310, 500);
        rec2.setFill(Color.rgb(0,0,0,0.3));
        rec2.setStroke(Color.WHITE);
        rec2.setStrokeWidth(5);
        rec2.setArcHeight(40);
        rec2.setArcWidth(40);


        title = new Label("Choose Your Spaceship");
        title.setStyle("-fx-text-fill: white;");
        title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 50));
        title.setAlignment(Pos.CENTER);
        title.setLayoutX(225);
        title.setLayoutY(100);


        ship1 = new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        ship2 = new Image(Launcher.class.getResourceAsStream("assets/playerShip1_blue.png"));
        ship1Text = new Image(Launcher.class.getResourceAsStream("assets/RedSpaceShip_Text.png"));
        ship2Text = new Image(Launcher.class.getResourceAsStream("assets/BlueSpaceShip_Text.png"));

        ship1ImgView = new ImageView(ship1);
        ship2ImgView = new ImageView(ship2);
        ship1TextImgView = new ImageView(ship1Text);
        ship2TextImgView = new ImageView(ship2Text);

        ship1ImgView.setPreserveRatio(true);
        ship1ImgView.setFitWidth(100);
        ship1ImgView.setX((Launcher.WIDTH/2) - 250);
        ship1ImgView.setY(300);

        ship2ImgView.setPreserveRatio(true);
        ship2ImgView.setFitWidth(100);
        ship2ImgView.setX((Launcher.WIDTH/2) + 155);
        ship2ImgView.setY(300);

        ship1TextImgView.setPreserveRatio(true);
        ship1TextImgView.setFitWidth(300);
        ship1TextImgView.setX((Launcher.WIDTH/2) - 350);
        ship1TextImgView.setY(350);

        ship2TextImgView.setPreserveRatio(true);
        ship2TextImgView.setFitWidth(300);
        ship2TextImgView.setX((Launcher.WIDTH/2) + 50);
        ship2TextImgView.setY(350);


        this.getChildren().addAll(rec1, rec2, ship1ImgView, ship2ImgView, ship1TextImgView, ship2TextImgView, title);

        ship1ImgView.setOnMouseClicked(e->{
            Launcher.choosenShip=1;
            GameController.logger.info("Choosen ship {}", Launcher.choosenShip);
            OtherHandlers.changeToGameStage();
        });
        rec1.setOnMouseClicked(e->{
            Launcher.choosenShip=1;
            GameController.logger.info("Choosen ship {}", Launcher.choosenShip);
            OtherHandlers.changeToGameStage();
        });
        ship1TextImgView.setOnMouseClicked(e->{
            Launcher.choosenShip=1;
            GameController.logger.info("Choosen ship {}", Launcher.choosenShip);
            OtherHandlers.changeToGameStage();
        });


        ship2ImgView.setOnMouseClicked(e->{
            Launcher.choosenShip=2;
            GameController.logger.info("Choosen ship {}", Launcher.choosenShip);
            OtherHandlers.changeToGameStage();
        });
        rec2.setOnMouseClicked(e->{
            Launcher.choosenShip=2;
            GameController.logger.info("Choosen ship {}", Launcher.choosenShip);
            OtherHandlers.changeToGameStage();
        });
        ship2TextImgView.setOnMouseClicked(e->{
            Launcher.choosenShip=2;
            GameController.logger.info("Choosen ship {}", Launcher.choosenShip);
            OtherHandlers.changeToGameStage();
        });
    }
}

