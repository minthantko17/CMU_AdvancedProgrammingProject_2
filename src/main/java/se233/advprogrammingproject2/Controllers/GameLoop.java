package se233.advprogrammingproject2.Controllers;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.model.*;
import se233.advprogrammingproject2.View.GameStage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameLoop extends AnimationTimer {
    private GameStage gameStage;
    private PlayerShip playerShip;
    private List<Bullet> bullets;
    private List<Asteroids> asteroids;
    private List<Asteroids> newAsteroids;
    List<EnemyShips> enemyShips;

    public GameLoop(GameStage gameStage, PlayerShip playerShip) {
        this.gameStage = gameStage;
        this.playerShip = playerShip;
        bullets = new ArrayList<>();
        asteroids = new ArrayList<>();
        newAsteroids=new ArrayList<>();
        enemyShips=new ArrayList<>();
        spawnAsteroids(120);
        spawnEnemyShips();

    }

    @Override
    public void handle(long l) {
        updateAndCollideBullet();

        //update enemy, asteroids, ... grrrrrrrrrrr

        for (Asteroids asteroid : asteroids) {
            asteroid.update();
        }

        for (EnemyShips e: enemyShips){
            e.update();
        }

        //update ship movements
        updateAndCollidePlayerShip();

        if(asteroids.isEmpty()){
            spawnAsteroids(120);
        }

    }


    // ------HELPER METHODS------
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        System.out.println("bullet added"+ bullets.size());
        gameStage.getChildren().addAll(bullet.getImageView());
        System.out.println("bullet added to GS"+ gameStage.getChildren().size());
    }

    //spawn asteroid for game init
    public void spawnAsteroids(int size){
        for (int i = 0; i < 5; i++) {
            double startX=Math.random() * Launcher.WIDTH;
            double startY=Math.random() * Launcher.HEIGHT;

            while(startX+80 > playerShip.getImageView().getX() && startX-80 < playerShip.getImageView().getX()){
                startX=Math.random() * Launcher.WIDTH;
            }
            while(startY+80 > playerShip.getImageView().getY() && startY-80 < playerShip.getImageView().getY()){
                startY=Math.random() * Launcher.HEIGHT;
            }

            Asteroids asteroid = new Asteroids(gameStage,
                    new Image(Launcher.class.getResourceAsStream("assets/asteroid1.png")),
                    startX,  // Random X position
                    startY, // Random Y position
                    1.2,
                    (int) (Math.random() * 361),    // Random angle (0 to 360 degrees)
                    1 + Math.random() * 2,   // Random rotation speed
                    size
            );
            asteroids.add(asteroid);
            gameStage.getChildren().add(asteroid.getImageView());
        }
    }

    //spawn enemy
    public void spawnEnemyShips(){
        for (int i=0; i<5 ; i++){
            double startX;
            double startY;
            int rand=(int)(Math.random()*2);
            if(rand==0){
                startX=Math.random() * Launcher.WIDTH;
                startY=((int)(Math.random()*2))*Launcher.HEIGHT-20;
            }else{
                startX=((int)(Math.random()*2))*Launcher.WIDTH-20;
                startY=Math.random()*Launcher.HEIGHT;
            }
//            while(startX+80 > playerShip.getImageView().getX() && startX-80 < playerShip.getImageView().getX()){
//                startX=Math.random() * Launcher.WIDTH;
//            }
//            while(startY+80 > playerShip.getImageView().getY() && startY-80 < playerShip.getImageView().getY()){
//                startY=Math.random() * Launcher.HEIGHT;
//            }

            EnemyShips smallEnemyShips=new smallEnemyShip(
                    new Image(Launcher.class.getResourceAsStream("assets/enemyBlack1.png")),
                    startX, startY,
                    1.5,
                    playerShip
            );
            enemyShips.add(smallEnemyShips);
            gameStage.getChildren().add(smallEnemyShips.getImageView());
        }
    }


    // -------UPDATE CHARACTERS FOR LOOP + CHECK COLLOID----------

    // Bullet and related
    private void updateAndCollideBullet(){
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            boolean isBulletRemoved=false;
            Bullet bullet = bulletIterator.next();
            bullet.update();

            if(bullet.isOffScreen()){
                gameStage.getChildren().remove(bullet.getImageView());
                bulletIterator.remove();
                isBulletRemoved=true;
            }

            //for asteroid
            Iterator<Asteroids> asteroidsIterator=asteroids.iterator();
            while(asteroidsIterator.hasNext()){
                Asteroids asteroid=asteroidsIterator.next();
                double asteroidX=asteroid.getImageView().getX();
                double asteroidY=asteroid.getImageView().getY();
                if(asteroid.checkCollision(bullet)){
                    asteroid.collide(bullet);

                    //if illegal state exception occurs, add boolean value to check bullet is already removed or not
                    if (!isBulletRemoved) {
                        gameStage.getChildren().remove(bullet.getImageView());
                        bulletIterator.remove();
                        isBulletRemoved=true;
                    }

                    gameStage.getChildren().remove(asteroid.getImageView());
                    asteroidsIterator.remove();

                    asteroid.explode();
                    if(asteroid.getSize()>60) {
                        List<Asteroids> smallerAsteroids=asteroid.spawnSmaller(asteroidX, asteroidY, 60);
                        newAsteroids.addAll(smallerAsteroids);      //to avoid ConcurrentModificationException
                    }
                    break;
                }
            }
            asteroids.addAll(newAsteroids);
            newAsteroids.clear();

            //for enemyShip
            Iterator<EnemyShips> enemyShipsIterator=enemyShips.iterator();
            while(enemyShipsIterator.hasNext()){
                EnemyShips enemyShip=enemyShipsIterator.next();
                if(enemyShip.checkCollision(bullet)){
//                    enemyShip.collide(bullet);
                    gameStage.getChildren().remove(bullet.getImageView());
                    bulletIterator.remove();

                    gameStage.getChildren().remove(enemyShip.getImageView());
                    enemyShipsIterator.remove();
                }
            }

        }
    }

    //PlayerShip and others
    private void updateAndCollidePlayerShip(){
        playerShip.update();
        if(!playerShip.isDestroyed() && !playerShip.isInvincible()){

            //asteroid
            Iterator<Asteroids> asteroidsIterator=asteroids.iterator();
            while (asteroidsIterator.hasNext()){
                Asteroids asteroid=asteroidsIterator.next();
                if(playerShip.checkCollision(asteroid)){
                    playerShip.explode();
                    playerShip.destroy();
                    gameStage.getChildren().remove(playerShip.getImageView());
                    break;
                }
            }

            Iterator<EnemyShips> enemyShipsIterator=enemyShips.iterator();
            while (enemyShipsIterator.hasNext()){
                EnemyShips enemyShip=enemyShipsIterator.next();
                if(playerShip.checkCollision(enemyShip)){
                    //enemyShip.collide()
                    gameStage.getChildren().remove(enemyShip.getImageView());
                    enemyShipsIterator.remove();

                    playerShip.explode();
                    playerShip.destroy();
                    gameStage.getChildren().remove(playerShip.getImageView());

                    break;
                }
            }

        }

        //respawn if there are still lives
        if (playerShip.isDestroyed() && playerShip.getLife() > 0) {
            System.out.println("life: "+playerShip.getLife());
            playerShip.respawn();
            gameStage.getChildren().add(playerShip.getImageView());
        }else if(playerShip.getLife()<=0){
            //game over action
            System.out.println("Game Over");
        }

    }

}
