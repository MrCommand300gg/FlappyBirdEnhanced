# Flappy Bird Enhancement Plan (AI Agent Instructions)

## Project Context
- **Framework**: libGDX 1.12.0 (Java)
- **Structure**: `core/src/ru/samsung/gamestudio/` — main source code
- **Existing classes**: `MyGdxGame`, `ScreenGame`, `ScreenRestart`, `Bird`, `Tube`, `PointCounter`, `MovingBackground`, `TextButton`
- **Resolution**: 1280x720
- **Build**: Gradle, Android + Desktop

## Key Files
```
core/src/ru/samsung/gamestudio/
├── MyGdxGame.java              # Main class, manages screens
├── screens/
│   ├── ScreenGame.java         # Game screen
│   └── ScreenRestart.java      # Restart screen
├── characters/
│   ├── Bird.java               # Bird (frames, jump, collision)
│   └── Tube.java               # Tubes (movement, collision)
└── components/
    ├── MovingBackground.java   # Scrolling background
    ├── PointCounter.java       # Score counter
    └── TextButton.java         # Text button
```

---

## Step 1: Refactor Bird Physics

### File: `core/src/ru/samsung/gamestudio/characters/Bird.java`

**Current problem**: Bird moves with constant `speed`. Jump is a teleport to `maxHeightOfJump`.

**Changes**:
1. Replace `int speed` with `float velocityY` (Y velocity)
2. Add `float gravity = -0.8f` (gravity)
3. Add `float jumpForce = 15f` (jump force)
4. Replace `fly()` method with:
```java
public void update(float delta) {
    velocityY += gravity;
    y += velocityY * delta * 60;
    // Ceiling limit
    if (y > SCR_HEIGHT - height) {
        y = SCR_HEIGHT - height;
        velocityY = 0;
    }
}
```
5. Replace `onClick()` with:
```java
public void jump() {
    velocityY = jumpForce;
}
```
6. Add `float rotation` — bird tilt angle:
   - When rising (`velocityY > 0`): `rotation = Math.min(30, rotation + 5)`
   - When falling: `rotation = Math.max(-90, rotation - 3)`
7. In `draw()`: pass `rotation` to `batch.draw()` (rotation parameter)

### File: `core/src/ru/samsung/gamestudio/screens/ScreenGame.java`

**Changes**:
1. In `render()`: replace `bird.fly()` with `bird.update(delta)`
2. Replace `bird.onClick()` with `bird.jump()`
3. Add keyboard support:
```java
if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
    bird.jump();
}
```

---

## Step 2: Difficulty System

### New file: `core/src/ru/samsung/gamestudio/game/DifficultyManager.java`

**Create class** with fields:
```java
public class DifficultyManager {
    public enum Level { EASY, MEDIUM, HARD, INSANE }

    Level currentLevel;
    int baseSpeed = 8;
    int baseGapHeight = 400;
    int minGapHeight = 200;

    public int getTubeSpeed(int score);
    public int getGapHeight(int score);
    public Level getLevel(int score);
}
```

**Logic**:
- `EASY`: score 0-10, speed 8, gap 400
- `MEDIUM`: score 11-30, speed 10, gap 350
- `HARD`: score 31-60, speed 12, gap 300
- `INSANE`: score 61+, speed 15, gap 250

### File: `core/src/ru/samsung/gamestudio/characters/Tube.java`

**Changes**:
1. Remove `final int speed = 10`
2. Add `int speed` as constructor parameter
3. In `move()`: use passed speed
4. Add `setSpeed(int newSpeed)` for dynamic changes

### File: `core/src/ru/samsung/gamestudio/screens/ScreenGame.java`

**Changes**:
1. Create `DifficultyManager difficultyManager = new DifficultyManager()`
2. When creating tubes: pass `difficultyManager.getTubeSpeed(gamePoints)`
3. In `render()` loop: update tube speed each frame

---

## Step 3: Screen Shake on Collision

### New file: `core/src/ru/samsung/gamestudio/effects/ScreenShake.java`

**Create class**:
```java
public class ScreenShake {
    float duration;
    float intensity;
    float timer;
    boolean active;

    public void start(float duration, float intensity);
    public Vector2 update(float delta); // returns camera offset
}
```

### File: `core/src/ru/samsung/gamestudio/screens/ScreenGame.java`

**Changes**:
1. Add `ScreenShake screenShake = new ScreenShake()`
2. When `isGameOver = true`: `screenShake.start(0.3f, 10f)`
3. In `render()` before drawing:
```java
Vector2 shakeOffset = screenShake.update(delta);
myGdxGame.camera.position.set(SCR_WIDTH/2 + shakeOffset.x, SCR_HEIGHT/2 + shakeOffset.y, 0);
```

---

## Step 4: Star Collectibles

### New file: `core/src/ru/samsung/gamestudio/characters/Star.java`

**Create class** with fields:
```java
public class Star {
    float x, y;
    int width = 40, height = 40;
    float speed;
    boolean collected;
    Texture texture;
    float animationTimer;

    public Star(float startX, float startY, float speed);
    public void update(float delta);
    public void draw(Batch batch);
    public boolean isCollected(Bird bird); // collision with bird
    public void dispose();
}
```

### File: `core/src/ru/samsung/gamestudio/screens/ScreenGame.java`

**Changes**:
1. Add `Array<Star> stars` (libGDX Array)
2. Add `Random random = new Random()`
3. In `initStars()`: create 3-5 stars randomly on level
4. In `render()`: update star positions, check collision with bird
5. On collection: `gamePoints += 5`, `star.collected = true`
6. Recreate stars when they leave screen

---

## Step 5: Power-up System

### New file: `core/src/ru/samsung/gamestudio/powerups/PowerUp.java`

**Abstract class**:
```java
public abstract class PowerUp {
    float x, y;
    float duration; // effect duration in seconds
    float remainingTime;
    boolean active;
    Texture icon;

    public abstract void activate(Bird bird);
    public abstract void deactivate(Bird bird);
    public void update(float delta);
    public void draw(Batch batch);
}
```

### New subclass files:
- `core/src/ru/samsung/gamestudio/powerups/ShieldPowerUp.java` — shield (invulnerability 5s)
- `core/src/ru/samsung/gamestudio/powerups/SpeedPowerUp.java` — speed boost 3s
- `core/src/ru/samsung/gamestudio/powerups/ShrinkPowerUp.java` — shrink bird 4s
- `core/src/ru/samsung/gamestudio/powerups/MagnetPowerUp.java` — star magnet 5s

### File: `core/src/ru/samsung/gamestudio/characters/Bird.java`

**Changes**:
1. Add fields: `boolean shielded`, `boolean magnetized`, `float sizeMultiplier = 1f`
2. Add method `applyPowerUp(PowerUp powerUp)`
3. In `draw()`: account for `sizeMultiplier` when rendering
4. In `isInField()` — if `shielded`, don't count collision

---

## Step 6: Main Menu Screen

### New file: `core/src/ru/samsung/gamestudio/screens/ScreenMenu.java`

**Create class** implementing `Screen`:
```java
public class ScreenMenu implements Screen {
    MyGdxGame myGdxGame;
    MovingBackground background;
    TextButton buttonPlay;
    TextButton buttonSettings;
    TextButton buttonAchievements;
    TextButton buttonLeaderboard;
    TextButton buttonProfile;
    TextButton buttonExit;

    // In render(): handle button presses
    // buttonPlay -> myGdxGame.setScreen(myGdxGame.screenGame)
    // buttonSettings -> myGdxGame.setScreen(myGdxGame.screenSettings)
    // buttonAchievements -> myGdxGame.setScreen(myGdxGame.screenAchievements)
    // buttonLeaderboard -> myGdxGame.setScreen(myGdxGame.screenLeaderboard)
    // buttonProfile -> myGdxGame.setScreen(myGdxGame.screenProfile)
    // buttonExit -> Gdx.app.exit()
}
```

**Button layout** (vertically centered):
- Play: y=550
- Settings: y=450
- Achievements: y=350
- Leaderboard: y=250
- Profile: y=150
- Exit: y=50

### File: `core/src/ru/samsung/gamestudio/MyGdxGame.java`

**Changes**:
1. Add `public ScreenMenu screenMenu`
2. In `create()`: `screenMenu = new ScreenMenu(this)`
3. Change start screen: `setScreen(screenMenu)` instead of `setScreen(screenGame)`

---

## Step 7: Settings Screen

### New file: `core/src/ru/samsung/gamestudio/screens/ScreenSettings.java`

**Create class**:
```java
public class ScreenSettings implements Screen {
    MyGdxGame myGdxGame;
    MovingBackground background;
    TextButton buttonBack;

    // Volume sliders (TextButton with +/-)
    TextButton musicVolumeUp, musicVolumeDown;
    TextButton sfxVolumeUp, sfxVolumeDown;

    // Difficulty selection
    TextButton difficultyEasy, difficultyMedium, difficultyHard, difficultyInsane;

    // Labels (BitmapFont)
    BitmapFont labelFont;
    float musicVolume = 0.5f;
    float sfxVolume = 0.5f;
}
```

**Save settings**: use `Gdx.app.getPreferences("flappy_settings")`

### New file: `core/src/ru/samsung/gamestudio/settings/GameSettings.java`

**Create singleton class**:
```java
public class GameSettings {
    private static GameSettings instance;
    Preferences prefs;

    float musicVolume;
    float sfxVolume;
    DifficultyManager.Level difficulty;
    boolean firstTimeLaunch;

    public static GameSettings getInstance();
    public void save();
    public void load();
}
```

---

## Step 8: Pause Screen

### New file: `core/src/ru/samsung/gamestudio/screens/ScreenPause.java`

**Create class**:
```java
public class ScreenPause implements Screen {
    MyGdxGame myGdxGame;
    TextButton buttonResume;
    TextButton buttonRestart;
    TextButton buttonMainMenu;
    // Semi-transparent overlay background
}
```

### File: `core/src/ru/samsung/gamestudio/screens/ScreenGame.java`

**Changes**:
1. Add `boolean isPaused = false`
2. Add pause button in top-right corner (pause icon)
3. On pause press: `myGdxGame.setScreen(myGdxGame.screenPause)`
4. When `isPaused`: don't update game logic, only render

---

## Step 9: Improved Game Over Screen

### File: `core/src/ru/samsung/gamestudio/screens/ScreenRestart.java`

**Changes**:
1. Add fields:
```java
int bestScore; // loaded from Preferences
int gamesPlayed;
int totalStars;
TextButton buttonMainMenu;
TextButton buttonShare;
BitmapFont bestScoreFont;
BitmapFont statsFont;
```
2. Add display:
   - "Score: X" (current)
   - "Best: Y" (best)
   - "Games: Z" (total games)
3. Add "Main Menu" button
4. Add "Share" button (call `Gdx.net.openURI()`)

### New file: `core/src/ru/samsung/gamestudio/storage/ScoreManager.java`

**Create class**:
```java
public class ScoreManager {
    Preferences prefs;
    int bestScore;
    int totalScore;
    int gamesPlayed;
    int starsCollected;

    public void saveScore(int score);
    public int getBestScore();
    public void addStars(int count);
    public int[] getTopScores(int count);
    public int getRank(int score);
    public void addScore(int score);
}
```

---

## Step 10: Achievement System

### New file: `core/src/ru/samsung/gamestudio/achievements/Achievement.java`

**Create class**:
```java
public class Achievement {
    String id;
    String name;
    String description;
    boolean unlocked;
    Texture icon;

    public boolean checkCondition(ScoreManager scoreManager);
}
```

### New file: `core/src/ru/samsung/gamestudio/achievements/AchievementManager.java`

**Create class**:
```java
public class AchievementManager {
    List<Achievement> achievements;

    public void init();
    public List<Achievement> checkNewAchievements(ScoreManager scoreManager);
    public void save();
    public void load();
}
```

### Achievements (implement in AchievementManager constructor):
```java
new Achievement("first_flight", "First Flight", "Play your first game", sm -> sm.gamesPlayed >= 1);
new Achievement("novice", "Novice", "Score 10 points", sm -> sm.bestScore >= 10);
new Achievement("experienced", "Experienced", "Score 50 points", sm -> sm.bestScore >= 50);
new Achievement("master", "Master", "Score 100 points", sm -> sm.bestScore >= 100);
new Achievement("legend", "Legend", "Score 200 points", sm -> sm.bestScore >= 200);
new Achievement("collector", "Collector", "Collect 100 stars", sm -> sm.starsCollected >= 100);
new Achievement("dedicated", "Dedicated", "Play 50 games", sm -> sm.gamesPlayed >= 50);
```

### New file: `core/src/ru/samsung/gamestudio/screens/ScreenAchievements.java`

**Create screen** with achievement list (unlocked — colored, locked — grey with lock icon)

---

## Step 11: Parallax Background

### File: `core/src/ru/samsung/gamestudio/components/MovingBackground.java`

**Changes**:
1. Support multiple layers:
```java
public class MovingBackground {
    Texture[] layers;      // background layers
    float[] speeds;        // speed of each layer
    float[] offsets;       // offset of each layer
    int layerCount;

    public MovingBackground(String[] layerPaths, float[] layerSpeeds);
}
```
2. In `draw()`: render each layer at different speed (far — slower, near — faster)
3. In `move()`: update offset of each layer

### New textures in `assets/backgrounds/`:
- `bg_layer0.png` — sky (farthest, speed 0.5)
- `bg_layer1.png` — mountains (speed 1)
- `bg_layer2.png` — trees (speed 2)
- `bg_layer3.png` — ground with grass (speed 3, current)

---

## Step 12: Particle Effects

### New file: `core/src/ru/samsung/gamestudio/effects/Particle.java`

**Create class**:
```java
public class Particle {
    float x, y;
    float velocityX, velocityY;
    float life; // lifetime
    float maxLife;
    float size;
    Color color;

    public void update(float delta);
    public void draw(Batch batch); // uses circular texture
}
```

### New file: `core/src/ru/samsung/gamestudio/effects/ParticleSystem.java`

**Create class**:
```java
public class ParticleSystem {
    Array<Particle> particles;
    Random random;

    public void emit(float x, float y, int count, Color color, float spread);
    public void update(float delta);
    public void draw(Batch batch);
    public void dispose();
}
```

### File: `core/src/ru/samsung/gamestudio/screens/ScreenGame.java`

**Changes**:
1. Create `ParticleSystem particleSystem = new ParticleSystem()`
2. On jump: `particleSystem.emit(bird.x, bird.y, 5, Color.YELLOW, 10)`
3. On star collect: `particleSystem.emit(star.x, star.y, 15, Color.GOLD, 20)`
4. On death: `particleSystem.emit(bird.x, bird.y, 30, Color.RED, 30)`
5. In `render()`: `particleSystem.update(delta)` and `particleSystem.draw(batch)`

---

## Step 13: Sound System

### New file: `core/src/ru/samsung/gamestudio/audio/AudioManager.java`

**Create class**:
```java
public class AudioManager {
    Sound jumpSound;
    Sound coinSound;
    Sound hitSound;
    Sound deathSound;
    Sound newRecordSound;
    Sound buttonSound;
    Sound achievementSound;
    Sound powerUpSound;

    Music menuMusic;
    Music gameMusic;

    GameSettings settings;

    public void playJump();
    public void playCoin();
    public void playHit();
    public void playDeath();
    public void playNewRecord();
    public void playButton();
    public void playAchievement();
    public void playPowerUp();

    public void playMenuMusic();
    public void playGameMusic();
    public void stopMusic();

    public void setMusicVolume(float volume);
    public void setSfxVolume(float volume);

    public void dispose();
}
```

**Sound files** (place in `assets/sounds/`):
- `jump.wav`, `coin.wav`, `hit.wav`, `death.wav`, `new_record.wav`, `button.wav`, `achievement.wav`, `powerup.wav`
- `menu_music.mp3`, `game_music.mp3`

### Changes in existing files:
- `ScreenGame.java`: call `audioManager.playJump()` on jump, `playHit()` on collision
- `ScreenMenu.java`: `audioManager.playMenuMusic()` in `show()`
- `ScreenRestart.java`: `audioManager.playDeath()` in `show()`
- `TextButton.java`: `audioManager.playButton()` on press

---

## Step 14: Leaderboard Screen

### New file: `core/src/ru/samsung/gamestudio/screens/ScreenLeaderboard.java`

**Create class**:
```java
public class ScreenLeaderboard implements Screen {
    MyGdxGame myGdxGame;
    TextButton buttonBack;
    BitmapFont titleFont;
    BitmapFont scoreFont;
    BitmapFont numberFont;
    int[] topScores; // from ScoreManager

    // Render table: rank, score, separator
}
```

### File: `core/src/ru/samsung/gamestudio/storage/ScoreManager.java`

**Add methods**:
```java
public void addScore(int score); // inserts into sorted list
public int[] getTopScores(int count); // returns top-N
public int getRank(int score); // returns position in ranking
public String getPlayerName();
public void setPlayerName(String name);
public Map<String, Integer> getAllStats();
```

---

## Step 15: Screen Adaptation

### File: `core/src/ru/samsung/gamestudio/MyGdxGame.java`

**Changes**:
1. Remove fixed `SCR_WIDTH = 1280` and `SCR_HEIGHT = 720`
2. Add `public static int SCR_WIDTH, SCR_HEIGHT` — compute dynamically
3. In `create()`:
```java
SCR_WIDTH = Gdx.graphics.getWidth();
SCR_HEIGHT = Gdx.graphics.getHeight();
```
4. Use `Viewport` (e.g. `FitViewport`) instead of manual camera calculation

### All classes:
- Replace hardcoded coordinates with relative: `SCR_WIDTH * 0.5f` instead of `640`
- Use proportions: `buttonWidth = SCR_WIDTH * 0.3f`

---

## Step 16: AssetManager

### New file: `core/src/ru/samsung/gamestudio/assets/GameAssetManager.java`

**Create class**:
```java
public class GameAssetManager {
    AssetManager manager;

    // Textures
    public static final AssetDescriptor<Texture> BIRD_0 = new AssetDescriptor<>("birdTiles/bird0.png", Texture.class);
    public static final AssetDescriptor<Texture> BG_GAME = new AssetDescriptor<>("backgrounds/game_bg.png", Texture.class);
    // ... all textures

    // Sounds
    public static final AssetDescriptor<Sound> JUMP_SOUND = new AssetDescriptor<>("sounds/jump.wav", Sound.class);
    // ... all sounds

    public void loadAll();
    public void finishLoading();
    public void dispose();
}
```

### Changes in `MyGdxGame.java`:
1. Create `GameAssetManager assetManager = new GameAssetManager()`
2. In `create()`: `assetManager.loadAll(); assetManager.finishLoading();`
3. Pass `assetManager` to screen and character constructors
4. Everywhere replace `new Texture(...)` with `assetManager.manager.get(...)`

---

## Step 17: Player Profile Screen

### New file: `core/src/ru/samsung/gamestudio/screens/ScreenProfile.java`

**Create class**:
```java
public class ScreenProfile implements Screen {
    MyGdxGame myGdxGame;
    BitmapFont font;
    TextButton buttonBack;
    TextButton buttonChangeName;

    String playerName; // from settings
    int totalGames;
    int bestScore;
    int starsCollected;
    int achievementsUnlocked;

    // Display statistics
    // Name change functionality
}
```

---

## Step 18: Tutorial Screen

### New file: `core/src/ru/samsung/gamestudio/screens/ScreenTutorial.java`

**Create class**:
```java
public class ScreenTutorial implements Screen {
    int currentStep = 0;
    String[] steps = {
        "Tap the screen to make the bird fly up",
        "Fly between pipes to score points",
        "Collect stars for bonus points",
        "Avoid collisions!",
        "Good luck!"
    };
    Texture[] stepImages; // illustrations for each step

    // "Next" and "Skip" buttons
}
```

### File: `core/src/ru/samsung/gamestudio/screens/ScreenGame.java`

**Changes**:
1. On first launch: `if (firstTime) myGdxGame.setScreen(myGdxGame.screenTutorial)`

---

## Step 19: Required Textures and Resources

### Required textures (add to `assets/`):

```
assets/
├── backgrounds/
│   ├── bg_layer0.png          # Sky (1280x720)
│   ├── bg_layer1.png          # Mountains (1280x720)
│   ├── bg_layer2.png          # Trees (1280x720)
│   ├── bg_layer3.png          # Ground with grass (1280x720)
│   ├── menu_bg.png            # Menu background (1280x720)
│   ├── night_bg.png           # Night background (1280x720)
│   └── restart_bg.png         # Game over background (current)
├── birdTiles/
│   ├── bird0.png              # Current frames
│   ├── bird1.png
│   ├── bird2.png
│   ├── golden_bird0.png       # Golden skin
│   ├── golden_bird1.png
│   └── golden_bird2.png
├── tubes/
│   ├── tube.png               # Current tubes
│   ├── tube_flipped.png
│   ├── ice_tube.png           # Ice tubes
│   └── ice_tube_flipped.png
├── items/
│   ├── star.png               # Star (40x40)
│   ├── shield_icon.png        # Shield icon
│   ├── speed_icon.png         # Speed icon
│   ├── shrink_icon.png        # Shrink icon
│   └── magnet_icon.png        # Magnet icon
├── ui/
│   ├── button_bg.png          # Button (current)
│   ├── button_pressed.png     # Pressed button
│   ├── pause_icon.png         # Pause icon
│   ├── play_icon.png          # Play icon
│   ├── settings_icon.png      # Settings icon
│   ├── trophy_icon.png        # Achievement icon
│   ├── leaderboard_icon.png   # Leaderboard icon
│   ├── profile_icon.png       # Profile icon
│   └── lock_icon.png          # Lock (locked elements)
├── effects/
│   ├── particle.png           # Particle (white circle)
│   ├── star_particle.png      # Gold particle
│   └── fire_particle.png      # Fire particle
└── sounds/
    ├── jump.wav
    ├── coin.wav
    ├── hit.wav
    ├── death.wav
    ├── new_record.wav
    ├── button.wav
    ├── achievement.wav
    ├── powerup.wav
    ├── menu_music.mp3
    └── game_music.mp3
```

---

## Step 20: Create README.md

### New file: `README.md` (project root)

**Create file** with the following sections:

```markdown
# Flappy Bird

## Description
Enhanced Flappy Bird game built with libGDX framework.
Features improved physics, power-ups, achievements, and more.

## Features
- Realistic bird physics with gravity and rotation
- Dynamic difficulty system (Easy → Insane)
- Collectible stars for bonus points
- Power-ups: Shield, Speed Boost, Shrink, Magnet
- Parallax multi-layer background
- Particle effects on jump, collection, and death
- Sound effects and background music
- Main menu with navigation
- Settings (volume, difficulty selection)
- Pause screen
- Achievement system (7 achievements)
- Local leaderboard (top-10 scores)
- Player profile with statistics
- Tutorial for new players
- Screen shake on collision
- Multi-screen support

## Project Structure
```
core/src/ru/samsung/gamestudio/
├── MyGdxGame.java              # Main game class
├── screens/                    # All game screens
├── characters/                 # Bird, Tube, Star
├── components/                 # UI components
├── effects/                    # Particles, screen shake
├── audio/                      # Sound management
├── achievements/               # Achievement system
├── game/                       # Difficulty manager
├── powerups/                   # Power-up classes
├── settings/                   # Game settings
├── storage/                    # Score persistence
└── assets/                     # Asset manager
```

## How to Run

### Desktop
```bash
./gradlew desktop:run
```

### Android
```bash
./gradlew android:installDebug
```

### Build APK
```bash
./gradlew android:assembleRelease
```

## Controls
- **Mouse click / Touch**: Make the bird jump
- **Spacebar**: Make the bird jump (desktop)
- **ESC**: Pause the game

## Requirements
- Java 11+
- Android SDK (for Android build)
- Gradle 7.x

## Dependencies
- libGDX 1.12.0
- libGDX Freetype
```

---

## Execution Order

```
Step 1  → Bird physics (foundation for everything)
Step 2  → Difficulty system
Step 3  → Screen shake
Step 4  → Stars
Step 5  → Power-ups
Step 19 → Textures (create/add)
Step 16 → AssetManager (resource loading)
Step 6  → Main menu
Step 7  → Settings
Step 8  → Pause
Step 9  → Game Over improvements
Step 10 → Achievements
Step 11 → Parallax
Step 12 → Particles
Step 13 → Sound
Step 14 → Leaderboard
Step 15 → Screen adaptation
Step 17 → Profile
Step 18 → Tutorial
Step 20 → README.md
```

## New Packages (create directories)
```
core/src/ru/samsung/gamestudio/
├── assets/
│   └── GameAssetManager.java
├── audio/
│   └── AudioManager.java
├── achievements/
│   ├── Achievement.java
│   └── AchievementManager.java
├── effects/
│   ├── ScreenShake.java
│   ├── Particle.java
│   └── ParticleSystem.java
├── game/
│   └── DifficultyManager.java
├── powerups/
│   ├── PowerUp.java
│   ├── ShieldPowerUp.java
│   ├── SpeedPowerUp.java
│   ├── ShrinkPowerUp.java
│   └── MagnetPowerUp.java
├── settings/
│   └── GameSettings.java
└── storage/
    └── ScoreManager.java
```
