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
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private List<Asteroids> asteroids;
    private List<Asteroids> newAsteroids;
    List<EnemyShips> enemyShips;
    private int enemyShipType;
    private int enemyRound;
    private int asteroidRound;


    public GameLoop(GameStage gameStage, PlayerShip playerShip) {
        this.gameStage = gameStage;
        this.playerShip = playerShip;
        playerBullets = new ArrayList<>();
        enemyBullets=new ArrayList<>();
        asteroids = new ArrayList<>();
        newAsteroids=new ArrayList<>();
        enemyShips=new ArrayList<>();
        spawnAsteroids(120);
        enemyShipType=0;
        asteroidRound=0;
        spawnEnemyShips(enemyShipType);

    }

    @Override
    public void handle(long now) {
        if(!Launcher.isGameOver && !Launcher.isVictory){
            gameStage.updateScoreLabel(GameStage.score);
            gameStage.updatePlayerLifeLabel(playerShip.getLife());

            updateAndCollidePlayerBullet();

            for (Asteroids asteroid : asteroids) {
                asteroid.update();
            }

            for (EnemyShips e: enemyShips){
                e.update();
            }

            updateAndCollidePlayerShip();

            //Spawn asteroids again and again
//            if(asteroids.isEmpty()){
//                spawnAsteroids(120);
//            }

            //Spawn enemy ships again and again
//            if(enemyShips.isEmpty()){
//                if(enemyShipType==0){
//                    enemyShipType=1;
//                    spawnEnemyShips(enemyShipType);
//                }else{
//                    enemyShipType=0;
//                    spawnEnemyShips(enemyShipType);
//                }
//            }

            if(asteroids.isEmpty() && asteroidRound<1){
                asteroidRound++;
                spawnAsteroids(120);
            }

//            if(enemyShips.isEmpty() && enemyShipType==0){
//                enemyShipType=1;
//                spawnEnemyShips(enemyShipType);
//            }
            if(enemyShips.isEmpty() && enemyRound<4){
                if(enemyShipType==0){
                    enemyShipType=1;
                    spawnEnemyShips(enemyShipType);
                }else{
                    enemyShipType=0;
                    spawnEnemyShips(enemyShipType);
                }
                enemyRound++;
            }



            //largeEnemy shoot with interval
            if(enemyShipType==1 && !enemyShips.isEmpty()){
                for(EnemyShips e: enemyShips){
                    if(e instanceof largeEnemyShip){
                        if(((largeEnemyShip) e).canEnemyShoot(now/1000000, 3000)){
                            addAndShootEnemyBullet((largeEnemyShip) e);
                        }
                    }
                }
            }
            updateEnemyBullets();

            if(enemyShips.isEmpty() && asteroids.isEmpty()){
                Launcher.isVictory=true;
                OtherHandlers.gameEndScreen(this);
            }
        }
    }


    // ------HELPER METHODS------
    public void addPlayerBullet(Bullet bullet) {
        playerBullets.add(bullet);
        System.out.println("bullet added"+ playerBullets.size());
        gameStage.getChildren().addAll(bullet.getImageView());
        System.out.println("bullet added to GS"+ gameStage.getChildren().size());
    }

    public void addAndShootEnemyBullet(largeEnemyShip enemyShip) {
        Bullet bullet = enemyShip.shootBullet();
        enemyBullets.add(bullet);
        gameStage.getChildren().addAll(bullet.getImageView());
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
                    startX, startY,
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
    public void spawnEnemyShips(int enemyShipType){

        if (enemyShipType==0) {
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

                EnemyShips smallEnemyShips=new smallEnemyShip(
                        new Image(Launcher.class.getResourceAsStream("assets/enemyBlack1.png")),
                        startX, startY,
                        1.5,
                        playerShip
                );
                enemyShips.add(smallEnemyShips);
                gameStage.getChildren().add(smallEnemyShips.getImageView());
            }
        }else{
            for (int i=0; i<4 ; i++){
                double startX;
                double startY;
                startX=((int)(Math.random()*2))*Launcher.WIDTH ;
//                startX=Launcher.WIDTH;
                startY=Math.random()*(Launcher.HEIGHT-40);

                EnemyShips largeEnemyShips=new largeEnemyShip(
                        new Image(Launcher.class.getResourceAsStream("assets/ufo.png")),
                        startX, startY,
                        1.5,
                        playerShip
                );
                enemyShips.add(largeEnemyShips);
                gameStage.getChildren().add(largeEnemyShips.getImageView());
            }
        }
    }


    // -------UPDATE CHARACTERS FOR LOOP + CHECK COLLOID----------

    // PlayerBullet and related
    private void updateAndCollidePlayerBullet(){
        Iterator<Bullet> playerBulletIterator = playerBullets.iterator();
        while (playerBulletIterator.hasNext()) {
            boolean isBulletRemoved=false;
            Bullet playerBullet = playerBulletIterator.next();
            playerBullet.update();

            if(playerBullet.isOffScreen()){
                gameStage.getChildren().remove(playerBullet.getImageView());
                playerBulletIterator.remove();
                isBulletRemoved=true;
            }

            //for asteroid
            Iterator<Asteroids> asteroidsIterator=asteroids.iterator();
            while(asteroidsIterator.hasNext()){
                Asteroids asteroid=asteroidsIterator.next();
                double asteroidX=asteroid.getImageView().getX();
                double asteroidY=asteroid.getImageView().getY();
                if(asteroid.checkCollision(playerBullet)){
                    asteroid.collide(playerBullet);
                    if(asteroid.getSize()>60){
                        GameStage.score=GameStage.score+2;
                    }else{
                        GameStage.score=GameStage.score+1;
                    }

                    //if illegal state exception occurs, add boolean value to check playerBullet is already removed or not
                    if (!isBulletRemoved) {
                        gameStage.getChildren().remove(playerBullet.getImageView());
                        playerBulletIterator.remove();
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
                if(enemyShip.checkCollision(playerBullet)){
//                    enemyShip.collide(playerBullet);
                    if(enemyShipType==0){
                        GameStage.score=GameStage.score+1;
                    }else{
                        GameStage.score=GameStage.score+2;
                    }


                    if (!isBulletRemoved) {
                        gameStage.getChildren().remove(playerBullet.getImageView());
                        playerBulletIterator.remove();
                        isBulletRemoved=true;
                    }

                    gameStage.getChildren().remove(enemyShip.getImageView());
                    enemyShipsIterator.remove();
                    break;
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

            Iterator<Bullet> enemyBulletsIterator=enemyBullets.iterator();
            while(enemyBulletsIterator.hasNext()){
                Bullet enemyBullet=enemyBulletsIterator.next();
                if(playerShip.checkCollision(enemyBullet)){
                    gameStage.getChildren().remove(enemyBullet.getImageView());
                    enemyBulletsIterator.remove();

                    playerShip.explode();
                    playerShip.destroy();
                    gameStage.getChildren().remove(playerShip.getImageView());

                    break;
                }
            }

        }

        //respawn if there are still lives
        if (playerShip.isDestroyed() && playerShip.getLife() > 0) {
            playerShip.respawn();
            gameStage.getChildren().add(playerShip.getImageView());
            System.out.println("remaining life: "+playerShip.getLife());
        }else if(playerShip.isDestroyed() && playerShip.getLife()<=0){
            //game over action
            System.out.println("Game Over");
            Launcher.isGameOver=true;
            OtherHandlers.gameEndScreen(this);
        }

    }

    private void updateEnemyBullets(){
        Iterator<Bullet> enemyBulletIterator=enemyBullets.iterator();
        while (enemyBulletIterator.hasNext()){
            Bullet enemyBullet=enemyBulletIterator.next();
            enemyBullet.update();
        }

    }
}
