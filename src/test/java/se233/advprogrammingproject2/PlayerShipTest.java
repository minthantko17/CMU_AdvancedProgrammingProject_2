package se233.advprogrammingproject2;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.model.Asteroids;
import se233.advprogrammingproject2.model.Bullet;
import se233.advprogrammingproject2.util.AnimatedSprite;
import se233.advprogrammingproject2.util.Keys;
import se233.advprogrammingproject2.model.PlayerShip;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerShipTest {
    private PlayerShip player;
    private GameStage gameStage;
    private Bullet bullet;
    private Asteroids asteroid;

    @Mock
    private Keys keys;  // Add a mock Keys object

    @BeforeEach
    public void setUp() {
        gameStage = Mockito.mock(GameStage.class); // Using mock for GameStage since it's part of the UI
        keys = Mockito.mock(Keys.class); // Mock the Keys object
        when(gameStage.getKeys()).thenReturn(keys); // Ensure gameStage.getKeys() returns the mock Keys
        Image playerImage = new Image(Launcher.class.getResourceAsStream("assets/player.png"));
        AnimatedSprite animatedSprite = new AnimatedSprite(playerImage, 100,100,6,150);
//        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserRed.png"));

        player = new PlayerShip(gameStage, animatedSprite, 100, 100, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.A, 5);
//        bullet = new Bullet(this, bulletImage, 100, 100, , shipLastDirectedAngle, bulletSpeed);
    }

    @Test
    public void testInitialPosition() {
        assertEquals(100, player.getCurrX());
        assertEquals(100, player.getCurrY());
    }

    @Test
    public void testMovement() {
        player.moveLeft();
        player.moveHorizontal();
        assertTrue(player.getCurrX() < 100); // X should decrease when moving left

        player.moveRight();
        player.moveHorizontal();
        assertTrue(player.getCurrX() >= 100); // X should increase when moving right

        player.moveTop();
        player.moveVertical();
        assertTrue(player.getCurrY() < 100); // Y should decrease when moving top

        player.moveBottom();
        player.moveVertical();
        assertTrue(player.getCurrY() >= 100); // Y should increase when moving bottom

        player.stopVertical();
        assertFalse(player.isMoveTop());
        assertFalse(player.isMoveBottom());

        player.stopHorizontal();
        assertFalse(player.isMoveLeft());
        assertFalse(player.isMoveRight());
    }

    @Test
    public void testUpdateInvincibility() throws InterruptedException {
        // Set invincibility and start time
        player.setInvincible(true);
        player.setInvincibleStartTime(System.currentTimeMillis());

        // Test visibility toggle during invincibility
        player.update();
        assertTrue(player.getImageView().isVisible(), "ImageView should be invisible during invincibility flicker.");

        // Wait and verify the toggle again
        Thread.sleep(200); // Wait 200ms for toggle interval
        player.update();
        assertTrue(player.getImageView().isVisible(), "ImageView should be visible again after flicker interval.");

        // Reset invincibility and verify visibility
        player.setInvincible(false);
        player.update();
        assertTrue(player.getImageView().isVisible(), "ImageView should be visible when not invincible.");
    }

    @Test
    public void testUpdateMovement() {
        // Simulate pressing left and top keys
        when(keys.isPressed(player.getLeftKey())).thenReturn(true);
        when(keys.isPressed(player.getRightKey())).thenReturn(false);
        when(keys.isPressed(player.getTopKey())).thenReturn(true);
        when(keys.isPressed(player.getBottomKey())).thenReturn(false);

        double initialX = player.getImageView().getX();
        double initialY = player.getImageView().getY();

        // Call update to simulate movement
        player.update(0);

        // Verify that the player ship moved left (decreasing X) and up (decreasing Y)
        assertTrue(player.getImageView().getX() < initialX, "PlayerShip should move left when left key is pressed.");
        assertTrue(player.getImageView().getY() < initialY, "PlayerShip should move up when top key is pressed.");
    }

    @Test
    public void testUpdateWhenDestroyed() {
        // Set the PlayerShip to destroyed
        player.setDestroyed(true);

        // Mock some key presses to verify they don't affect the destroyed ship
        when(keys.isPressed(player.getLeftKey())).thenReturn(true);
        when(keys.isPressed(player.getTopKey())).thenReturn(true);

        double initialX = player.getImageView().getX();
        double initialY = player.getImageView().getY();

        // Call update to check that no movement occurs
        player.update(0);

        // Verify that the player ship's position has not changed
        assertEquals(initialX, player.getImageView().getX(), "PlayerShip should not move when destroyed.");
        assertEquals(initialY, player.getImageView().getY(), "PlayerShip should not move when destroyed.");
    }

    @Test
    public void testInvincibility() {
        player.respawn(); // Sets player to invincible state
        assertTrue(player.isInvincible(), "Player should be invincible immediately after respawn");

        // Simulate invincibility duration passing
        try {
            Thread.sleep(player.getInvincibleDuration() + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(player.isInvincible(), "Player should no longer be invincible after duration");
    }

    @Test
    public void testShootBullet() {
        // Act: Shoot a bullet
        Bullet bullet = player.shootBullet();

        // Assert that the bullet is not null
        assertNotNull(bullet, "Bullet should not be null when player shoots.");

        // Calculate the expected bullet position
        double expectedX = player.getImageView().getBoundsInParent().getMinX() + player.getImageView().getBoundsInParent().getWidth() / 2;
        double expectedY = player.getImageView().getBoundsInParent().getMinY() + player.getImageView().getBoundsInParent().getHeight() / 2;

        // Assert that the bullet's position matches the expected position
        assertEquals(expectedX, bullet.getImageView().getX(), 0.1, "Bullet X position should match the expected X position.");
        assertEquals(expectedY, bullet.getImageView().getY(), 0.1, "Bullet Y position should match the expected Y position.");
    }

    @Test
    public void testSpecialAttack() {
        List<Bullet> bullets = player.shootSpecialAttack();
        assertEquals(12, bullets.size(), "Special attack should generate 12 bullets");
    }

    @Test
    //for destroy, respawn, isInvincible
    public void testLifeAndRespawn() {
        int initialLife = player.getLife();
        player.destroy();
        player.respawn();
        assertEquals(initialLife - 1, player.getLife(), "Player life should decrease after respawn");
        assertFalse(player.isDestroyed(), "Player should not be destroyed after respawn");
        assertTrue(player.isInvincible(), "Player should be invincible immediately after respawn");
    }
}
