package se233.advprogrammingproject2.Controllers;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.model.*;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.util.AnimatedSprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameLoop extends AnimationTimer {
    private GameStage gameStage;
    private PlayerShip playerShip;
    Boss bossShip;
    private List<Bullet> playerBullets;
    private List<Bullet> playerSpecialBullets;
    List<Bullet> enemyBullets;
    List<Bullet> bossBullets;
    List<Asteroids> asteroids;
    private List<Asteroids> newAsteroids;
    List<EnemyShips> enemyShips;
    private int enemyShipType;
    private int enemyRound;
    private int asteroidRound;
    private boolean bossSpawn;
    private boolean bossDead;
//    private AnimatedSprite largeEnemySprite;


    public GameLoop(GameStage gameStage, PlayerShip playerShip) {
        this.gameStage = gameStage;
        this.playerShip = playerShip;
        playerBullets = new ArrayList<>();
        playerSpecialBullets = new ArrayList<>();
        enemyBullets=new ArrayList<>();
        bossBullets=new ArrayList<>();
        asteroids = new ArrayList<>();
        newAsteroids=new ArrayList<>();
        enemyShips=new ArrayList<>();
        spawnAsteroids(120);
        enemyShipType=0;
        asteroidRound=0;
        spawnEnemyShips(enemyShipType);
        bossSpawn =false;
        bossDead=false;
    }

    //------Main game loop handle-----
    @Override
    public void handle(long now) {
        if(!Launcher.isGameOver && !Launcher.isVictory){
            gameStage.updateScoreLabel(GameStage.score);
            gameStage.updateSpecialEnergyLabel(GameStage.specialEnergy);

            updateAndCollidePlayerBullet();
            updateAndCollidePlayerSpecialAttack();
            updateAndCollidePlayerShip(now);
            for (Asteroids asteroid : asteroids) {
                asteroid.update();
            }
            for (EnemyShips e: enemyShips){
                if(e instanceof LargeEnemyShip){
                    ((LargeEnemyShip) e).update(now);
                }else if(e instanceof SmallEnemyShip){
                    ((SmallEnemyShip) e).update(now);
                }else{
                    e.update();
                }

            }

            //Spawn again
            if(asteroids.isEmpty() && asteroidRound<1){
                asteroidRound++;
                spawnAsteroids(120);
            }
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
                    if(e instanceof LargeEnemyShip){
                        if(((LargeEnemyShip) e).canShoot(now/1000000, 3000)){
                            addAndShootEnemyBullet((LargeEnemyShip) e);
                        }
                    }
                }
            }
            updateEnemyBullets();

            //Final Boss
            if(enemyShips.isEmpty() && asteroids.isEmpty() && !bossSpawn){
                bossSpawn =true;
                spawnBoss();
                GameStage.bossHpLabel.setVisible(true);
                GameStage.bossHpLabel.setText("Boss HP: "+bossShip.getBossHP());
//                Launcher.isVictory=true;
//                OtherHandlers.gameEndScreen(this);
            }
            if(bossShip!=null){
                if(bossShip.getCurY()<40){
                    bossShip.bossComing();
                }else{
                    GameStage.bossHpBarBorder.setVisible(true);
                    GameStage.bossHpBar.setVisible(true);
                    GameStage.bossHpBar.setWidth(bossShip.getBossHP()*4);
                    bossShip.update(now);
                    if(bossShip.canShoot(now/1000000, 2000)){
                        addAndShootBossBullet();
                    }
                    updateBossBullets();
                }
            }

            //Game Victory and End
            if(enemyShips.isEmpty() && asteroids.isEmpty() && bossDead){
                Launcher.isVictory=true;
                OtherHandlers.changeToGameEndScreen(this);
            }
        }
    }


    // ------ADDING TO GAME STAGE------
    public void addPlayerBullet(Bullet bullet) {
        playerBullets.add(bullet);
        System.out.println("bullet added"+ playerBullets.size());
        gameStage.getChildren().addAll(bullet.getImageView());
        System.out.println("bullet added to GS"+ gameStage.getChildren().size());
    }

    public void addPlayerSpecialBullet(List<Bullet> specialBullets) {
        playerSpecialBullets.addAll(specialBullets);
        for(Bullet bullet : specialBullets){
            gameStage.getChildren().addAll(bullet.getImageView());
        }
    }

    public void addAndShootEnemyBullet(LargeEnemyShip enemyShip) {
        Bullet bullet = enemyShip.shootBullet();
        enemyBullets.add(bullet);
        gameStage.getChildren().addAll(bullet.getImageView());
    }

    public void addAndShootBossBullet(){
        List<Bullet> newBullets = bossShip.shootBullet();
        for(Bullet bullet : newBullets){
            bossBullets.add(bullet);
            gameStage.getChildren().addAll(bullet.getImageView());
        }
    }

    public void spawnAsteroids(int size){
        for (int i = 0; i < 6; i++) {
            double startX;
            double startY;
            int rand=(int)(Math.random()*2);
            if(rand==0){
                startX=Math.random() * (Launcher.WIDTH-60);
                startY=((int)(Math.random()*2))*(Launcher.HEIGHT-60);
            }else{
                startX=((int)(Math.random()*2))*(Launcher.WIDTH-60);
                startY=Math.random()*(Launcher.HEIGHT-60);
            }
//            double startX=Math.random() * Launcher.WIDTH;
//            double startY=Math.random() * Launcher.HEIGHT;
//            while(startX+80 > playerShip.getImageView().getX() && startX-80 < playerShip.getImageView().getX()){
//                startX=Math.random() * Launcher.WIDTH;
//            }
//            while(startY+80 > playerShip.getImageView().getY() && startY-80 < playerShip.getImageView().getY()){
//                startY=Math.random() * Launcher.HEIGHT;
//            }

            Asteroids asteroid = new Asteroids(gameStage,
                    new Image(Launcher.class.getResourceAsStream("assets/asteroid1.png")),
                    startX, startY, 1.2,
                    (int) (Math.random() * 361),    // Random angle (0 to 360 degrees)
                    1 + Math.random() * 2,   // Random rotation speed
                    size
            );
            asteroids.add(asteroid);
            gameStage.getChildren().add(asteroid.getImageView());
        }
    }

    public void spawnEnemyShips(int enemyShipType){
        if (enemyShipType==0) {
            for (int i=0; i<5 ; i++){
                double startX;
                double startY;
                int rand=(int)(Math.random()*2);
                if(rand==0){
                    startX=Math.random() * (Launcher.WIDTH-40);
                    startY=((int)(Math.random()*2))*(Launcher.HEIGHT-40);
                }else{
                    startX=((int)(Math.random()*2))*(Launcher.WIDTH-40);
                    startY=Math.random()*(Launcher.HEIGHT-40);
                }

//                EnemyShips smallEnemyShips=new SmallEnemyShip(
//                        new Image(Launcher.class.getResourceAsStream("assets/enemyBlack1.png")),
//                        startX, startY, 1.5, playerShip
//                );
                Image smallEnamyImage=new Image(Launcher.class.getResourceAsStream("assets/SmallEnemy.png"));
                AnimatedSprite smallEnemySprite=new AnimatedSprite(smallEnamyImage, 520, 520, 4, 250);
                EnemyShips smallEnemyShips=new SmallEnemyShip(smallEnemySprite, startX, startY, 1.5, playerShip);

                enemyShips.add(smallEnemyShips);
                gameStage.getChildren().add(smallEnemyShips.getImageView());
            }
        }else{
            for (int i=0; i<4 ; i++){
                double startX;
                double startY;
                startX=((int)(Math.random()*2))*(Launcher.WIDTH-60) ;
                startY=Math.random()*(Launcher.HEIGHT-60);

//                EnemyShips largeEnemyShips=new LargeEnemyShip(
//                        new Image(Launcher.class.getResourceAsStream("assets/ufo.png")),
//                        startX, startY,
//                        1.5, playerShip
//                );
                Image largeEnemyImage=new Image(Launcher.class.getResourceAsStream("assets/LargeEnemy.png"));
                AnimatedSprite largeEnemySprite=new AnimatedSprite(largeEnemyImage, 520, 330, 3, 250);
                EnemyShips largeEnemyShips=new LargeEnemyShip(largeEnemySprite, startX, startY, 1.5, playerShip);

                enemyShips.add(largeEnemyShips);
                gameStage.getChildren().add(largeEnemyShips.getImageView());
            }
        }
    }

    public void spawnBoss(){
        double startX=(Launcher.WIDTH/2)-50;
        double startY=-50;
//        Image bossShipImg=new Image(Launcher.class.getResourceAsStream("assets/ufoBoss.png"));
//        bossShip = new Boss(bossShipImg, startX, startY, 3, playerShip);

        Image bossImage=new Image(Launcher.class.getResourceAsStream("assets/FinalBoss.png"));
        AnimatedSprite largeEnemySprite=new AnimatedSprite(bossImage, 512, 254, 3, 250);
        bossShip=new Boss(largeEnemySprite, startX, startY, 3, playerShip);

        gameStage.getChildren().add(bossShip.getImageView());
    }


    // -------UPDATE CHARACTERS FOR LOOP + CHECK COLLOID----------
    // PlayerBullet
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
                        GameStage.specialEnergy=GameStage.specialEnergy+2;
                        if(GameStage.specialEnergy>30){
                            GameStage.specialEnergy=30;
                        }
                    }else{
                        GameStage.score=GameStage.score+1;
                        GameStage.specialEnergy=GameStage.specialEnergy+1;
                        if(GameStage.specialEnergy>30){
                            GameStage.specialEnergy=30;
                        }
                    }

                    //if illegal state exception occurs, add boolean value to check playerBullet is already removed or not
                    if (!isBulletRemoved) {
                        gameStage.getChildren().remove(playerBullet.getImageView());
                        playerBulletIterator.remove();
                        isBulletRemoved=true;
                    }

                    AnimatedSprite animatedSprite=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/explosionTemp.png")),
                            100, 100, 4, 25
                    );
                    gameStage.getChildren().remove(asteroid.getImageView());
                    asteroidsIterator.remove();

                    if(asteroid.getSize()>60) {
                        List<Asteroids> smallerAsteroids=asteroid.spawnSmaller(asteroidX, asteroidY, 60);
                        newAsteroids.addAll(smallerAsteroids);      //to avoid ConcurrentModificationException
                    }
                    asteroid.explode(animatedSprite, gameStage);
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
                        GameStage.specialEnergy=GameStage.specialEnergy+1;
                        if(GameStage.specialEnergy>30){
                            GameStage.specialEnergy=30;
                        }
                    }else{
                        GameStage.score=GameStage.score+2;
                        GameStage.specialEnergy=GameStage.specialEnergy+2;
                        if(GameStage.specialEnergy>30){
                            GameStage.specialEnergy=30;
                        }
                    }


                    if (!isBulletRemoved) {
                        gameStage.getChildren().remove(playerBullet.getImageView());
                        playerBulletIterator.remove();
                        isBulletRemoved=true;
                    }

                    AnimatedSprite animatedSprite=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/explosionTemp.png")),
                            100, 100, 4, 50
                    );
                    enemyShip.explode(animatedSprite, gameStage);
                    gameStage.getChildren().remove(enemyShip.getImageView());
                    enemyShipsIterator.remove();
                    break;
                }
            }

            if(bossSpawn && bossShip.checkCollision(playerBullet)){
                bossShip.setBossHP(bossShip.getBossHP()-1);
                GameStage.bossHpLabel.setText("Boss HP: "+bossShip.getBossHP());
                GameStage.bossHpBar.setWidth(bossShip.getBossHP()*4);
                if(bossShip.getBossHP()<=0){
                    bossDead=true;
                    GameStage.score=GameStage.score+50;
                    gameStage.getChildren().remove(bossShip);
                }
                if (!isBulletRemoved) {
                    gameStage.getChildren().remove(playerBullet.getImageView());
                    playerBulletIterator.remove();
                    isBulletRemoved=true;
                }
            }

        }
    }

    private void updateAndCollidePlayerSpecialAttack(){
        Iterator<Bullet> playerSpecialBulletIterator = playerSpecialBullets.iterator();
        while (playerSpecialBulletIterator.hasNext()) {
            boolean isBulletRemoved=false;
            Bullet playerSpecialBullet = playerSpecialBulletIterator.next();
            playerSpecialBullet.update();

            if(playerSpecialBullet.isOffScreen()){
                gameStage.getChildren().remove(playerSpecialBullet.getImageView());
                playerSpecialBulletIterator.remove();
                isBulletRemoved=true;
            }

            Iterator<Asteroids> asteroidsIterator=asteroids.iterator();
            while(asteroidsIterator.hasNext()){
                Asteroids asteroid=asteroidsIterator.next();
                double asteroidX=asteroid.getImageView().getX();
                double asteroidY=asteroid.getImageView().getY();
                if(asteroid.checkCollision(playerSpecialBullet)){
                    asteroid.collide(playerSpecialBullet);
                    if(asteroid.getSize()>60){
                        GameStage.score=GameStage.score+2;
                    }else{
                        GameStage.score=GameStage.score+1;
                    }

                    //if illegal state exception occurs, add boolean value to check playerBullet is already removed or not
                    if (!isBulletRemoved) {
                        gameStage.getChildren().remove(playerSpecialBullet.getImageView());
                        playerSpecialBulletIterator.remove();
                        isBulletRemoved=true;
                    }

                    gameStage.getChildren().remove(asteroid.getImageView());
                    asteroidsIterator.remove();
                    if(asteroid.getSize()>60) {
                        List<Asteroids> smallerAsteroids=asteroid.spawnSmaller(asteroidX, asteroidY, 60);
                        newAsteroids.addAll(smallerAsteroids);      //to avoid ConcurrentModificationException
                    }
                    AnimatedSprite animatedSprite=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/explosionTemp.png")),
                            100, 100, 4, 50
                    );
                    asteroid.explode(animatedSprite, gameStage);
                    break;
                }
            }
            asteroids.addAll(newAsteroids);
            newAsteroids.clear();

            Iterator<EnemyShips> enemyShipsIterator=enemyShips.iterator();
            while(enemyShipsIterator.hasNext()){
                EnemyShips enemyShip=enemyShipsIterator.next();
                if(enemyShip.checkCollision(playerSpecialBullet)){
//                    enemyShip.collide(playerBullet);
                    if(enemyShipType==0){
                        GameStage.score=GameStage.score+1;
                    }else{
                        GameStage.score=GameStage.score+2;
                    }

                    if (!isBulletRemoved) {
                        gameStage.getChildren().remove(playerSpecialBullet.getImageView());
                        playerSpecialBulletIterator.remove();
                        isBulletRemoved=true;
                    }

                    AnimatedSprite animatedSprite=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/explosionTemp.png")),
                            100, 100, 4, 50
                    );
                    enemyShip.explode(animatedSprite, gameStage);

                    gameStage.getChildren().remove(enemyShip.getImageView());
                    enemyShipsIterator.remove();
                    break;
                }
            }

            if(bossSpawn && bossShip.checkCollision(playerSpecialBullet)){
                bossShip.setBossHP(bossShip.getBossHP()-1);
                GameStage.bossHpLabel.setText("Boss HP: "+bossShip.getBossHP());
                GameStage.bossHpBar.setWidth(bossShip.getBossHP()*4);
                if (!isBulletRemoved) {
                    gameStage.getChildren().remove(playerSpecialBullet.getImageView());
                    playerSpecialBulletIterator.remove();
                    isBulletRemoved=true;
                }
                if(bossShip.getBossHP()<=0){
                    bossDead=true;
                    GameStage.score=GameStage.score+50;
                    gameStage.getChildren().remove(bossShip);
                }
                break;
            }

        }
    }

    //PlayerShip
    private void updateAndCollidePlayerShip(long now){
        playerShip.update(now);
        if(!playerShip.isDestroyed() && !playerShip.isInvincible()){

            //asteroid
            Iterator<Asteroids> asteroidsIterator=asteroids.iterator();
            while (asteroidsIterator.hasNext()){
                Asteroids asteroid=asteroidsIterator.next();
                if(playerShip.checkCollision(asteroid)){
                    AnimatedSprite animatedSprite=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/explosionTemp.png")),
                            100, 100, 4, 50
                    );
                    playerShip.explode(animatedSprite, gameStage);
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

                    AnimatedSprite animatedSprite=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/explosionTemp.png")),
                            100, 100, 4, 50
                    );
                    playerShip.explode(animatedSprite, gameStage);
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

                    AnimatedSprite animatedSprite=new AnimatedSprite(
                            new Image(Launcher.class.getResourceAsStream("assets/explosionTemp.png")),
                            100, 100, 4, 50
                    );
                    playerShip.explode(animatedSprite, gameStage);
                    playerShip.destroy();
                    gameStage.getChildren().remove(playerShip.getImageView());

                    break;
                }
            }

            if(bossShip!=null){
                if(playerShip.checkCollision(bossShip)){
                    playerShip.explode();
                    playerShip.destroy();
                    gameStage.getChildren().remove(playerShip.getImageView());
                }

                Iterator<Bullet> bossBulletsIterator=bossBullets.iterator();
                while(bossBulletsIterator.hasNext()){
                    Bullet bossBullet=bossBulletsIterator.next();
                    if(playerShip.checkCollision(bossBullet)){
                        gameStage.getChildren().remove(bossBullet.getImageView());
                        bossBulletsIterator.remove();

                        playerShip.explode();
                        playerShip.destroy();
                        gameStage.getChildren().remove(playerShip.getImageView());
                        break;
                    }
                }
            }
        }

        //respawn if there are still lives
        if (playerShip.isDestroyed() && playerShip.getLife() > 0) {
            playerShip.respawn();
            gameStage.updatePlayerLifeIcon();
            gameStage.getChildren().add(playerShip.getImageView());
            System.out.println("remaining life: "+playerShip.getLife());
        }else if(playerShip.isDestroyed() && playerShip.getLife()<=0){
            //game over action
            System.out.println("Game Over");
            Launcher.isGameOver=true;
            OtherHandlers.changeToGameEndScreen(this);
        }

    }

    private void updateEnemyBullets(){
        Iterator<Bullet> enemyBulletIterator=enemyBullets.iterator();
        while (enemyBulletIterator.hasNext()){
            Bullet enemyBullet=enemyBulletIterator.next();
            enemyBullet.update();

            if(enemyBullet.isOffScreen()){
                gameStage.getChildren().remove(enemyBullet.getImageView());
                enemyBulletIterator.remove();
            }
        }
    }

    private void updateBossBullets(){
        Iterator<Bullet> bossBulletIterator=bossBullets.iterator();
        while (bossBulletIterator.hasNext()){
            Bullet bossBullet=bossBulletIterator.next();
            bossBullet.update();

            if(bossBullet.isOffScreen()){
                gameStage.getChildren().remove(bossBullet.getImageView());
                bossBulletIterator.remove();
            }
        }
    }
}
