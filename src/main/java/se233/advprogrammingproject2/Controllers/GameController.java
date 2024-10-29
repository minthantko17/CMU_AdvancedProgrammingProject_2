package se233.advprogrammingproject2.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.model.*;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.util.AnimatedSprite;

import java.util.List;

public class GameController {
    private Scene scene;
    private GameStage gameStage;
    private GameLoop gameLoop;
    private PlayerShip playerShip;
    public static final Logger logger = LogManager.getLogger();
    private static int shotsFired;
    Timeline timeline;

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
            playerShip.setIsMoving(true);
            if(e.getCode()== KeyCode.SPACE && !playerShip.isDestroyed()){
                System.out.println("keyPressed");
                Bullet bullet = playerShip.shootBullet();
                gameLoop.addPlayerBullet(bullet);
            }else {
                gameStage.getKeys().add(e.getCode());
            }
        });

        scene.setOnKeyReleased(event -> {
            playerShip.setIsMoving(false);
            gameStage.getKeys().remove(event.getCode());
        });

        scene.setOnMousePressed(e->{
            //normal attack
            if(e.getButton()== MouseButton.SECONDARY && !playerShip.isDestroyed()){
                System.out.println("mousePressed");
                Bullet bullet = playerShip.shootBullet();
                gameLoop.addPlayerBullet(bullet);
            }

            //special attack
            if(e.getButton()==MouseButton.PRIMARY && !playerShip.isDestroyed() && GameStage.specialEnergy>=30){
                GameStage.specialEnergy=0;


                if (Launcher.choosenShip==2) {          // Need to update special att for ship2
                    shotsFired = 0;

                    AnimatedSprite chargeUp=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/charge.png")),
                            100, 100, 4, 25);

                    chargeUp.setX(playerShip.getImageView().getX()-25);
                    chargeUp.setY(playerShip.getImageView().getY()-25);
                    chargeUp.setPreserveRatio(true);
                    chargeUp.setFitWidth(100);
                    gameStage.getChildren().add(chargeUp);

                    Timeline chargeUpTimeLine=new Timeline(new KeyFrame(Duration.millis(25),
                            event -> chargeUp.update(System.nanoTime())));
                    chargeUpTimeLine.setCycleCount(4);
                    chargeUpTimeLine.setOnFinished(event->{
                        gameStage.getChildren().remove(chargeUp);
                    });
                    chargeUpTimeLine.play();

                    List<EnemyShips> enemyShipsTemp=gameLoop.enemyShips;
                    List<Asteroids> asteroidsTemp=gameLoop.asteroids;
                    List<Bullet> enemyBulletTemp=gameLoop.enemyBullets;
                    List<Bullet> bossBulletTemp=gameLoop.bossBullets;
                    Boss bossTemp=gameLoop.bossShip;

                    for(EnemyShips enemyship : enemyShipsTemp){
                        if(enemyship instanceof LargeEnemyShip){
                            ((LargeEnemyShip) enemyship).setSpeed(0.2);
                        }else{
                            ((SmallEnemyShip) enemyship).setSpeed(0.2);
                        }
                    }
                    for(Asteroids asteroid : asteroidsTemp){
                        asteroid.setSpeed(0.2);
                    }
                    for(Bullet bullet: enemyBulletTemp){
                        bullet.setSpeed(0.3);
                    }
                    if (bossBulletTemp!=null) {
                        for (Bullet bullet : bossBulletTemp) {
                            bullet.setSpeed(1);
                        }
                    }
                    if (bossTemp!=null) {bossTemp.setSpeed(0.5);}

                    timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("in handle");
                        }
                    }));
                    timeline.setCycleCount(1);
                    timeline.setOnFinished(event->{
                        for(EnemyShips enemyship : enemyShipsTemp){
                            if(enemyship instanceof LargeEnemyShip){
                                ((LargeEnemyShip) enemyship).setBackInitSpeed();
                            }else{
                                ((SmallEnemyShip) enemyship).setBackInitSpeed();
                            }
                        }
                        for(Asteroids asteroid : asteroidsTemp){
                            asteroid.setBackInitSpeed();
                        }
                        for(Bullet bullet: enemyBulletTemp){
                            bullet.setBackInitSpeed();
                        }
                        if (bossBulletTemp!=null) {
                            for(Bullet bullet: bossBulletTemp){
                                bullet.setBackInitSpeed();
                            }
                        }
                        if(bossTemp!=null){
                            bossTemp.setBackInitSpeed();
                        }

                    });
                    timeline.play();
                }

                else{
                    System.out.println("here");
                    shotsFired = 0;

                    AnimatedSprite chargeUp=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/charge.png")),
                            100, 100, 4, 25);

                    chargeUp.setX(playerShip.getImageView().getX()-25);
                    chargeUp.setY(playerShip.getImageView().getY()-25);
                    chargeUp.setPreserveRatio(true);
                    chargeUp.setFitWidth(100);
                    gameStage.getChildren().add(chargeUp);

                    Timeline chargeUpTimeLine=new Timeline(new KeyFrame(Duration.millis(25),
                            event -> {
                                chargeUp.setX(playerShip.getImageView().getX()-25);
                                chargeUp.setY(playerShip.getImageView().getY()-25);
                        chargeUp.update(System.nanoTime());
                            }));
                    chargeUpTimeLine.setCycleCount(4);
                    chargeUpTimeLine.setOnFinished(event->{
                        gameStage.getChildren().remove(chargeUp);
                    });
                    chargeUpTimeLine.play();

                    //fire instantly
//                List<Bullet> bullets=playerShip.shootSpecialAttack();
//                gameLoop.addPlayerSpecialBullet(bullets);

                    //fire 2 more times with 0.5sec interval
                    timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (shotsFired < 3) {
                                List<Bullet> bullets = playerShip.shootSpecialAttack();
                                gameLoop.addPlayerSpecialBullet(bullets);
                                shotsFired++;
                            } else {
                                shotsFired = 0;
                                timeline.stop();
                            }
                        }
                    }));
                    timeline.setCycleCount(3);
                    timeline.play();
                }
            }
        });
    }
}
