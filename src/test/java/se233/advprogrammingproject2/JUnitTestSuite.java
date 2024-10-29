package se233.advprogrammingproject2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({AsteroidsTest.class, BossTest.class, LargeEnemyShipTest.class, PlayerShipTest.class, SmallEnemyShipTest.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JUnitTestSuite {
    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }
}
