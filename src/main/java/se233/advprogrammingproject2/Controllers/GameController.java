package se233.advprogrammingproject2.Controllers;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.advprogrammingproject2.model.Bullet;
import se233.advprogrammingproject2.model.PlayerShip;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameStage;

public class GameController {
    private Scene scene;
    private GameStage gameStage;
    private GameLoop gameLoop;
    private PlayerShip playerShip;
    public static final Logger logger = LogManager.getLogger();

    public GameController(Scene scene, GameStage gameStage){
        this.scene = scene;
        this.gameStage=gameStage;
        this.playerShip=gameStage.getPlayerShip();
        this.gameLoop=new GameLoop(gameStage, playerShip);
        gameLoop.start();

        setUpKeyEvents();
        addEventListeners();
    }

    private void addEventListeners(){
        //change ship direction according to cursor
        gameStage.setOnMouseMoved(e->{
            System.out.println("mousemoved");
            //ImageView center
            double centerX = playerShip.getImageView().getBoundsInParent().getMinX()+playerShip.getImageView().getBoundsInParent().getWidth() /2;
            double centerY = playerShip.getImageView().getBoundsInParent().getMinY()+playerShip.getImageView().getBoundsInParent().getWidth() /2;

            //mouse position
            double mouseX=e.getX();
            double mouseY=e.getY();

            /* calculate angle according to mouse position
            Use tan(x) when you have an angle and need the tangent value. (just my note, we don't use it here)
            Use atan2(y, x) when you have coordinates and need to find the angle while considering the signs to identify the correct quadrant.
            */
             double angle=Math.toDegrees(Math.atan2(mouseY-centerY, mouseX-centerX));

            playerShip.getImageView().setRotate(angle +90);
            logger.info("angle: {}, rotatedangle: {}",angle, playerShip.getImageView().getRotate());
        });
    }

    private void setUpKeyEvents(){
        scene.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.SPACE){
                System.out.println("keyPressed");
                shootBullet();
            }else {
                gameStage.getKeys().add(e.getCode());
            }
        });

        scene.setOnKeyReleased(event -> gameStage.getKeys().remove(event.getCode()));

        scene.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.SECONDARY){
                System.out.println("mousePressed");
                shootBullet();
            }
        });


    }

    private void shootBullet(){
        double shipX=playerShip.getImageView().getBoundsInParent().getMinX()+playerShip.getImageView().getBoundsInParent().getWidth() /2;
        double shipY=playerShip.getImageView().getBoundsInParent().getMinY()+playerShip.getImageView().getBoundsInParent().getWidth() /2;
        double shipDirection=playerShip.getImageView().getRotate()-90;     // adjust like +- 90 according to the input image

        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserRed.png"));
        double bulletSpeed=7;
        Bullet bullet=new Bullet(playerShip, bulletImage, shipX, shipY, shipDirection, bulletSpeed);
        gameLoop.addBullet(bullet);
    }
}
