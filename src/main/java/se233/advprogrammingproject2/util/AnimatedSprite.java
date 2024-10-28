package se233.advprogrammingproject2.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

public class AnimatedSprite extends ImageView{
    private Image[] frames;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private long frameDuration; // time for each frame in milliseconds

    public AnimatedSprite(Image spriteSheet, int frameWidth, int frameHeight, int frameCount, long frameDuration) {
        this.frames = new Image[frameCount];
        this.frameDuration = frameDuration;

        // Split the sprite sheet into individual frames
        for (int i = 0; i < frameCount; i++) {
            int x = (i * frameWidth) % (int) spriteSheet.getWidth();
            int y = (i * frameWidth) / (int) spriteSheet.getWidth() * frameHeight;
            frames[i] = new WritableImage(spriteSheet.getPixelReader(), x, y, frameWidth, frameHeight);
        }

        this.setImage(frames[currentFrame]);
    }

    public void update(long now) {
        // If enough time has passed, move to the next frame
        if (now - lastFrameTime >= frameDuration * 1_000_000) {
            currentFrame = (currentFrame + 1) % frames.length;
            this.setImage(frames[currentFrame]);
            lastFrameTime = now;
        }
    }

    public void drawFirst(){
        currentFrame=0;
        this.setImage(frames[currentFrame]);
    }
}

