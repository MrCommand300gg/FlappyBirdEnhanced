package ru.samsung.gamestudio.screens;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;
import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.characters.Bird;
import ru.samsung.gamestudio.components.MovingBackground;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.PointCounter;
import ru.samsung.gamestudio.characters.Tube;
import ru.samsung.gamestudio.characters.Star;
import ru.samsung.gamestudio.game.DifficultyManager;
import ru.samsung.gamestudio.effects.ScreenShake;
import ru.samsung.gamestudio.effects.ParticleSystem;
import ru.samsung.gamestudio.storage.ScoreManager;
import ru.samsung.gamestudio.components.TextButton;

import java.util.Random;

public class ScreenGame implements Screen {

    MyGdxGame myGdxGame;

    Bird bird;
    PointCounter pointCounter;
    MovingBackground background;

    int tubeCount = 3;
    Tube[] tubes;

    int gamePoints;
    boolean isGameOver;
    boolean isPaused;

    DifficultyManager difficultyManager;
    ScreenShake screenShake;
    ParticleSystem particleSystem;
    ScoreManager scoreManager;

    Array<Star> stars;
    Random random;

    TextButton pauseButton;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        difficultyManager = new DifficultyManager();
        screenShake = new ScreenShake();
        particleSystem = new ParticleSystem();
        scoreManager = new ScoreManager();
        random = new Random();
        stars = new Array<>();

        initTubes();
        initStars();
        background = new MovingBackground("backgrounds/game_bg.png");
        bird = new Bird(20, SCR_HEIGHT / 2, (int)(SCR_WIDTH * 0.15f), (int)(SCR_HEIGHT * 0.2f));
        pointCounter = new PointCounter((int)(SCR_WIDTH * 0.85f), (int)(SCR_HEIGHT * 0.92f));
        pauseButton = new TextButton((int)(SCR_WIDTH * 0.92f), (int)(SCR_HEIGHT * 0.92f), "||");
    }


    @Override
    public void show() {
        gamePoints = 0;
        isGameOver = false;
        isPaused = false;
        bird.setY(SCR_HEIGHT / 2);
        initTubes();
        initStars();
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !isGameOver) {
            isPaused = !isPaused;
            if (isPaused) {
                myGdxGame.setScreen(myGdxGame.screenPause);
            }
        }

        if (Gdx.input.justTouched() && !isGameOver) {
            Vector3 touch = myGdxGame.camera.unproject(
                    new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)
            );
            if (pauseButton.isHit((int) touch.x, (int) touch.y)) {
                isPaused = true;
                myGdxGame.setScreen(myGdxGame.screenPause);
            }
        }

        if (isGameOver) {
            scoreManager.addScore(gamePoints);
            screenShake.start(0.3f, 10f);
            particleSystem.emit(bird.x + bird.width / 2, bird.y + bird.height / 2, 30, com.badlogic.gdx.graphics.Color.RED, 30);
            myGdxGame.screenRestart.gamePoints = gamePoints;
            myGdxGame.setScreen(myGdxGame.screenRestart);
        }

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bird.jump();
            particleSystem.emit(bird.x + bird.width / 2, bird.y + bird.height / 2, 5, com.badlogic.gdx.graphics.Color.YELLOW, 10);
        }

        background.move();
        bird.update(delta);
        if (!bird.isInField()) {
            System.out.println("not in field");
            isGameOver = true;
        }

        int currentSpeed = difficultyManager.getTubeSpeed(gamePoints);
        int currentGapHeight = difficultyManager.getGapHeight(gamePoints);

        for (Tube tube : tubes) {
            tube.setSpeed(currentSpeed);
            tube.setGapHeight(currentGapHeight);
            tube.move();
            if (tube.isHit(bird)) {
                isGameOver = true;
                System.out.println("hit");
            } else if (tube.needAddPoint(bird)) {
                gamePoints += 1;
                tube.setPointReceived();
                System.out.println(gamePoints);
            }
        }

        for (Star star : stars) {
            star.update(delta);
            if (star.isCollected(bird)) {
                gamePoints += 5;
                star.collected = true;
                particleSystem.emit(star.x + star.width / 2, star.y + star.height / 2, 15, com.badlogic.gdx.graphics.Color.GOLD, 20);
            }
            if (star.x < -star.width) {
                star.recycle();
            }
        }

        particleSystem.update(delta);

        Vector2 shakeOffset = screenShake.update(delta);
        myGdxGame.camera.position.set(SCR_WIDTH / 2 + shakeOffset.x, SCR_HEIGHT / 2 + shakeOffset.y, 0);

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        for (Star star : stars) {
            if (!star.collected) {
                star.draw(myGdxGame.batch);
            }
        }
        bird.draw(myGdxGame.batch);
        for (Tube tube : tubes) tube.draw(myGdxGame.batch);
        pointCounter.draw(myGdxGame.batch, gamePoints);
        particleSystem.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bird.dispose();
        background.dispose();
        pointCounter.dispose();
        pauseButton.dispose();
        for (int i = 0; i < tubeCount; i++) {
            tubes[i].dispose();
        }
        for (Star star : stars) {
            star.dispose();
        }
        particleSystem.dispose();
    }

    void initTubes() {
        int speed = difficultyManager.getTubeSpeed(gamePoints);
        int gapHeight = difficultyManager.getGapHeight(gamePoints);
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i, speed, gapHeight);
        }
    }

    void initStars() {
        stars.clear();
        int starCount = 3 + random.nextInt(3);
        for (int i = 0; i < starCount; i++) {
            float starX = SCR_WIDTH + random.nextInt(SCR_WIDTH);
            float starY = 100 + random.nextInt(SCR_HEIGHT - 200);
            float speed = difficultyManager.getTubeSpeed(gamePoints);
            stars.add(new Star(starX, starY, speed));
        }
    }

}
