# Asteroids

A JavaFX-based Asteroids-style arcade shooter. Pilot your ship, blast asteroids and enemy ships, dodge a boss, and rack up score.

---

## Gameplay Demo


https://github.com/user-attachments/assets/f7faa331-53c0-48da-bcdb-5f2dfc0b12ce


## Screenshots
<img width="612" height="486" alt="astroid_game_ss1" src="https://github.com/user-attachments/assets/4ff83d40-319b-46b5-ac87-1317cc45db8b" />
<img width="612" height="486" alt="astroid_game_ss3" src="https://github.com/user-attachments/assets/d49cc7cc-3111-426c-b654-93daefeba950" />
<img width="612" height="486" alt="astroid_game_ss2" src="https://github.com/user-attachments/assets/9a207200-1cb3-46f3-b770-05df9c1e49d1" />


---

## Features

- Main menu, ship/loadout selection (game prep), gameplay, and game-over screens
- Player ship with movement, shooting, and a special ability powered by energy
- Multiple enemy types: asteroids, small enemy ships, large enemy ships, and a boss
- Sprite animation system and custom mouse cursor/reticle
- Score tracking

## Player ship
There are two types of playable spaceship.
1. Red ship that has the special ability of radial shoot.
2. Blue ship that has the special ability of freeze time. (stop enemy movements).

# Enemy
There are multiple enemies such as
1. the ship that follow player
2. the ship that go straight direction, but shoot lazer to player's position
3. the boss ship

(ofc, there are also astroids that split to smaller ones when got shot).


## Requirements

- Java 22 (JDK)
- Maven (or use the bundled `mvnw` / `mvnw.cmd` wrapper)

JavaFX is pulled in automatically via Maven dependencies — no separate JavaFX SDK install needed.

## Running the game

```bash
./mvnw clean javafx:run
```

On Windows:

```bat
mvnw.cmd clean javafx:run
```

## Building a jar

```bash
./mvnw clean package
```

This produces a shaded jar (via `maven-shade-plugin`) with `se233.advprogrammingproject2.JarLauncher` as the main class.

## Running tests

```bash
./mvnw test
```

## Controls

| Action        | Key / Button                                |
|---------------|----------------------------------------------|
| Move          | W / A / S / D                                |
| Aim          | Mouse (Cursor)                               |
| Shoot         | Space                                         |
| Special attack| Left mouse click (requires full special energy) |
| Confirm/Start | Enter                                         |

## Project structure

```
src/main/java/se233/advprogrammingproject2/
├── Launcher.java            # JavaFX application entry point
├── JarLauncher.java         # Entry point used by the packaged jar
├── Controllers/             # Game loop and input/event handling
├── View/                    # Menu, prep, gameplay, and end screens
├── model/                   # Player ship, enemies, asteroids, boss, bullets
└── util/                    # Sprite animation and key-state helpers
```

## Tech stack

- Java 22
- JavaFX 20 (controls, fxml, base)
- Log4j2 for logging
- JUnit 5 + Mockito for testing

---
