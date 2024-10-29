package se233.advprogrammingproject2;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.advprogrammingproject2.model.Bullet;
import se233.advprogrammingproject2.model.LargeEnemyShip;
import se233.advprogrammingproject2.model.PlayerShip;

import static org.junit.jupiter.api.Assertions.*;

public class LargeEnemyShipTest {
    private LargeEnemyShip largeEnemyShip;
    private PlayerShip playerShip;
    private long lastShotTime;

    @BeforeEach
    public void setUp() {
        // Set up the player ship
        Image playerImage = new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        playerShip = new PlayerShip(null, playerImage, 100, 100, null, null, null, null, 5);

        // Set up the large enemy ship
        Image enemyImage = new Image(Launcher.class.getResourceAsStream("assets/ufo.png"));
        largeEnemyShip = new LargeEnemyShip(enemyImage, 50, 50, 3, playerShip);
        lastShotTime = System.currentTimeMillis();
    }

    @Test
    public void testCanShoot_True() {
        long currentTime = lastShotTime + 2000; // Simulate time passing
        long shootInterval = 1000; // 1 second interval

        assertTrue(largeEnemyShip.canShoot(currentTime, shootInterval), "Enemy should be able to shoot after interval.");
    }

    @Test
    public void testUpdate_PositionChange() {
        double initialX = 50;
        double speed = 3; // Speed set in constructor
        long currentTime = System.currentTimeMillis();
        largeEnemyShip.update();
        double expectedX = initialX + speed * Math.cos(Math.toRadians(largeEnemyShip.getMovementDirectionAngle())); // Adjust based on angle

        assertEquals(expectedX, largeEnemyShip.getImageView().getX(), 0.1, "Enemy ship's X position should update based on speed.");
    }

    @Test
    public void testShootBullet() {
        long currentTime = System.currentTimeMillis();
        long shootInterval = 1000; // 1 second interval
        if (largeEnemyShip.canShoot(currentTime, shootInterval)) {
            Bullet bullet = largeEnemyShip.shootBullet();

            assertNotNull(bullet, "Bullet should not be null when the enemy shoots.");

            assertEquals(50 + largeEnemyShip.getImageView().getBoundsInParent().getWidth() / 2, bullet.getImageView().getX(), 0.1, "Bullet X position should match the enemy ship's position.");
            assertEquals(50 + largeEnemyShip.getImageView().getBoundsInParent().getHeight() / 2, bullet.getImageView().getY(), 0.1, "Bullet Y position should match the enemy ship's position.");
        }
    }
}
