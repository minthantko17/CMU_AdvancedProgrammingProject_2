package se233.advprogrammingproject2;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.advprogrammingproject2.model.Boss;
import se233.advprogrammingproject2.model.EnemyShips;
import se233.advprogrammingproject2.model.PlayerShip;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BossTest {
    private Boss boss;
    private PlayerShip playerShip;

    @BeforeEach
    public void setUp() {
        // Initialize the player ship for testing
        Image playerImage = new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        playerShip = new PlayerShip(null, playerImage, 100, 100, null, null, null, null, 5);

        // Initialize the Boss object
        Image bossImage = new Image(Launcher.class.getResourceAsStream("assets/ufoBoss.png"));
        boss = new Boss(bossImage, 50, 50, 5, playerShip);


    }

    @Test
    public void testCanShoot() throws NoSuchFieldException {
        Field eLastShotTime = EnemyShips.class.getDeclaredField("enemyLastShotTime");
        eLastShotTime.setAccessible(true);

        long currentTime = System.currentTimeMillis();
        long interval = 1000; // 1 second

        // Check that the boss can shoot the first time
        assertTrue(boss.canShoot(currentTime, interval), "Boss should be able to shoot immediately after initialization.");

        // Simulate time passing
        boss.eLastShotTime = currentTime; // Set the last shot time to current time
        assertFalse(boss.canShoot(currentTime, interval), "Boss should not be able to shoot again within the interval.");

        // Wait for the interval to pass
        try {
            Thread.sleep(interval + 100); // Wait slightly longer than the interval
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentTime = System.currentTimeMillis(); // Update current time
        assertTrue(boss.canShoot(currentTime, interval), "Boss should be able to shoot again after the interval.");
    }

    @Test
    public void testUpdate() {
        double initialX = boss.getCurX();
        boss.update();
        double updatedX = boss.getCurX();

        // Check if the boss has moved
        assertNotEquals(initialX, updatedX, "Boss should move when update is called.");
    }


    @Test
    public void testBossComing() {
        double initialY = boss.getCurY();
        boss.bossComing();
        double updatedY = boss.getCurY();

        // Check if the boss has moved down
        assertNotEquals(initialY, updatedY, "Boss should move down when bossComing is called.");
        assertEquals(initialY + 0.5, updatedY, 0.01, "Boss should move down by 0.5 units.");
    }

    @Test
    public void testSetAndGetBossHP() {
        int initialHP = boss.getBossHP();
        int newHP = 40;

        // Update boss HP
        boss.setBossHP(newHP);

        // Check if the HP has been updated
        assertEquals(newHP, boss.getBossHP(), "Boss HP should be updated correctly.");

        // Ensure that it can still retrieve the initial HP
        assertEquals(initialHP, 50, "Initial boss HP should still be 50.");
    }
}
