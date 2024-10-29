package se233.advprogrammingproject2;

import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.model.Asteroids;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AsteroidsTest {

    private Asteroids asteroid;
    private GameStage gameStage;
    private Image asteroidImage;

    @BeforeAll
    public static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await(5, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        gameStage = new GameStage();  // Set up the game stage, assuming it has a default constructor
        asteroidImage = new Image(Launcher.class.getResourceAsStream("assets/asteroid1.png"));
        asteroid = new Asteroids(gameStage, asteroidImage, 100, 100, 5, 45, 1.0, 50);
    }

    @Test
    public void testUpdate() {
        double initialX = asteroid.getImageView().getX();
        double initialY = asteroid.getImageView().getY();

        asteroid.update();

        double newX = asteroid.getImageView().getX();
        double newY = asteroid.getImageView().getY();

        assertNotEquals(initialX, newX, "X coordinate should change after update.");
        assertNotEquals(initialY, newY, "Y coordinate should change after update.");
    }

    @Test
    public void testUpdateWhenOffScreen() {
        // Move asteroid off-screen to the left
        asteroid.getImageView().setX(-10);
        asteroid.updateWhenOffScreen();
        assertEquals(Launcher.WIDTH, asteroid.getImageView().getX(), "X coordinate should wrap to the screen width when off-screen to the left.");

        // Move asteroid off-screen to the right
        asteroid.getImageView().setX(Launcher.WIDTH + 10);
        asteroid.updateWhenOffScreen();
        assertEquals(0, asteroid.getImageView().getX(), "X coordinate should wrap to 0 when off-screen to the right.");

        // Move asteroid off-screen to the top
        asteroid.getImageView().setY(-10);
        asteroid.updateWhenOffScreen();
        assertEquals(Launcher.HEIGHT, asteroid.getImageView().getY(), "Y coordinate should wrap to the screen height when off-screen to the top.");

        // Move asteroid off-screen to the bottom
        asteroid.getImageView().setY(Launcher.HEIGHT + 10);
        asteroid.updateWhenOffScreen();
        assertEquals(0, asteroid.getImageView().getY(), "Y coordinate should wrap to 0 when off-screen to the bottom.");
    }

    @Test
    public void testSpawnSmaller() {
        List<Asteroids> smallerAsteroids = asteroid.spawnSmaller(100, 100, 25);

        assertEquals(5, smallerAsteroids.size(), "There should be 5 smaller asteroids spawned.");

        for (Asteroids smallerAsteroid : smallerAsteroids) {
            assertEquals(25, smallerAsteroid.getSize(), "Each spawned asteroid should have the specified size.");
            assertEquals(100, smallerAsteroid.getImageView().getX(), "Each spawned asteroid should have the specified starting X position.");
            assertEquals(100, smallerAsteroid.getImageView().getY(), "Each spawned asteroid should have the specified starting Y position.");
            assertTrue(smallerAsteroid.getRotationSpeed() >= 1 && smallerAsteroid.getRotationSpeed() <= 3, "Rotation speed of smaller asteroids should be within range [1, 3].");
        }
    }

    @Test
    public void testGetSize() {
        assertEquals(50, asteroid.getSize(), "The asteroid size should be 50 as set in the constructor.");
    }
}
