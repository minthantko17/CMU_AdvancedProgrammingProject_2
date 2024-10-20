package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.advprogrammingproject2.Controllers.GameController;
import se233.advprogrammingproject2.View.GameStage;

public class PlayerShip {
    GameStage gameStage;
    ImageView imageView;
    double x;
    double y;
    KeyCode topKey, rightKey, bottomKey, leftKey;
    boolean isMoveTop, isMoveRight, isMoveBottom, isMoveLeft;


    public PlayerShip(GameStage gameStage, Image image, double x, double y, KeyCode topKey, KeyCode rightKey, KeyCode bottomKey, KeyCode leftKey) {
        this.gameStage=gameStage;
        this.x=x;
        this.y=y;
        this.topKey=topKey;
        this.rightKey=rightKey;
        this.bottomKey=bottomKey;
        this.leftKey=leftKey;
        this.imageView=new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setX(x);
        imageView.setY(y);


    }

    public void update(){
        boolean leftPressed= gameStage.getKeys().isPressed(this.leftKey);
        boolean rightPressed= gameStage.getKeys().isPressed(this.rightKey);
        boolean topPressed= gameStage.getKeys().isPressed(this.topKey);
        boolean bottomPressed= gameStage.getKeys().isPressed(this.bottomKey);
//        System.out.println(topPressed);
//        System.out.println(rightPressed);
//        System.out.println(bottomPressed);
//        System.out.println(leftPressed);

        if(leftPressed && rightPressed){
            this.stopHorizontal();
        }else if(leftPressed){
            this.moveLeft();
            this.moveHorizontal();
        }else if(rightPressed){
            this.moveRight();
            this.moveHorizontal();
        }else{
            this.stopHorizontal();
        }

        if(topPressed && bottomPressed){
            this.stopVertical();
        }else if(topPressed){
            this.moveTop();
            this.moveVertical();
        }else if(bottomPressed){
            this.moveBottom();
            this.moveVertical();
        }else{
            this.stopVertical();
        }
    }

    public void moveLeft(){
        isMoveLeft=true;
        isMoveRight=false;
        GameController.logger.info("Moving Left");
    }
    public void moveRight(){
        isMoveRight=true;
        isMoveLeft=false;
        GameController.logger.info("Moving Right");
    }
    public void moveTop(){
        isMoveTop=true;
        isMoveBottom=false;
        GameController.logger.info("Moving Top");
    }
    public void moveBottom(){
        isMoveBottom=true;
        isMoveTop=false;
        GameController.logger.info("Moving Bottom");
    }


    public void stopHorizontal(){
        isMoveRight=false;
        isMoveLeft=false;
    }
    public void stopVertical(){
        isMoveTop=false;
        isMoveBottom=false;
    }


    public void moveHorizontal(){
        if(isMoveLeft){
            x=x-5;
        }
        if(isMoveRight){
            x=x+5;
        }
        imageView.setX(x);
    }

    public void moveVertical(){
        if(isMoveTop){
            y=y-5;
        }
        if(isMoveBottom){
            y=y+5;
        }
        imageView.setY(y);
    }



    public ImageView getImageView(){
        return this.imageView;
    }


}
