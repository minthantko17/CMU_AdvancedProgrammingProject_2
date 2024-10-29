package se233.advprogrammingproject2;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.advprogrammingproject2.model.PlayerShip;
import se233.advprogrammingproject2.model.SmallEnemyShip;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class SmallEnemyShipTest {
    private PlayerShip playerShip;
    private SmallEnemyShip smallEnemyShip;

    @BeforeEach
    public void setUp() {
        // Setup a player ship for the small enemy ship to follow
        Image playerImage = new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        playerShip = new PlayerShip(null, playerImage, 100, 100, null, null, null, null, 5);

        // Setup a small enemy ship with arbitrary values
        Image enemyImage = new Image(Launcher.class.getResourceAsStream("assets/enemyBlack1.png"));
        smallEnemyShip = new SmallEnemyShip(enemyImage, 50, 50, 2, playerShip);
    }

    @Test
    public void testCanShoot() {

        long interval = 1000; // 1 second
        long currentTime = System.currentTimeMillis();

        // Initially, the enemy ship should be able to shoot
        assertTrue(smallEnemyShip.canShoot(currentTime, interval));

        // Simulate waiting for less than the interval
        smallEnemyShip.setEnemyLastShotTime(currentTime); // Set the last shot time
        assertFalse(smallEnemyShip.canShoot(currentTime + 500, interval), "Should not be able to shoot before the interval");

        // Simulate waiting for the interval to pass
        assertTrue(smallEnemyShip.canShoot(currentTime + 1000, interval), "Should be able to shoot after the interval");
    }

    @Test
    public void testIsFollowing() throws NoSuchFieldException {

        Field isFollowing = SmallEnemyShip.class.getDeclaredField("isFollowing");
        isFollowing.setAccessible(true);

        // Initially, isFollowing should be false
        assertFalse(smallEnemyShip.isFollowing, "Enemy ship should not be following initially");

        // Simulate a time period where the enemy ship can follow
        smallEnemyShip.initTime = System.currentTimeMillis() - 2500; // Simulate 2.5 seconds have passed
        smallEnemyShip.isFollowing(); // Call isFollowing method

        // It should be following since 2500 ms has passed
        assertTrue(smallEnemyShip.isFollowing, "Enemy ship should be following after the time condition is met");
    }

    @Test
    public void testUpdateWhenOffScreen() {
        // Move the enemy ship off-screen
        smallEnemyShip.getImageView().setX(-10);
        smallEnemyShip.updateWhenOffScreen();

        // It should wrap around to the right side of the screen
        assertEquals(Launcher.WIDTH, smallEnemyShip.getImageView().getX(), "Enemy ship should wrap around to the right side when off screen left");

        // Move the enemy ship off-screen to the right
        smallEnemyShip.getImageView().setX(Launcher.WIDTH + 10);
        smallEnemyShip.updateWhenOffScreen();

        // It should wrap around to the left side of the screen
        assertEquals(0, smallEnemyShip.getImageView().getX(), "Enemy ship should wrap around to the left side when off screen right");

        // Move the enemy ship off-screen at the top
        smallEnemyShip.getImageView().setY(-10);
        smallEnemyShip.updateWhenOffScreen();

        // It should wrap around to the bottom of the screen
        assertEquals(Launcher.HEIGHT, smallEnemyShip.getImageView().getY(), "Enemy ship should wrap around to the bottom when off screen top");

        // Move the enemy ship off-screen at the bottom
        smallEnemyShip.getImageView().setY(Launcher.HEIGHT + 10);
        smallEnemyShip.updateWhenOffScreen();

        // It should wrap around to the top of the screen
        assertEquals(0, smallEnemyShip.getImageView().getY(), "Enemy ship should wrap around to the top when off screen bottom");
    }

    @Test
    public void testUpdate() {
        // Set the player ship's position
        playerShip.setCurrX(200);
        playerShip.setCurrY(200);

        // Call update method to test movement towards the player
        smallEnemyShip.update();

        // The enemy ship should now be closer to the player ship
        assertNotEquals(50, smallEnemyShip.getImageView().getX(), "Enemy ship should update its X position towards the player");
        assertNotEquals(50, smallEnemyShip.getImageView().getY(), "Enemy ship should update its Y position towards the player");
    }
}
