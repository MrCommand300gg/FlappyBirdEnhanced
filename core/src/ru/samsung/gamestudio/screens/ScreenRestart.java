package ru.samsung.gamestudio.screens;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;
import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import ru.samsung.gamestudio.components.MovingBackground;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.PointCounter;
import ru.samsung.gamestudio.components.TextButton;
import ru.samsung.gamestudio.storage.ScoreManager;

public class ScreenRestart implements Screen {

    MyGdxGame myGdxGame;

    MovingBackground background;
    TextButton buttonRestart;
    TextButton buttonMainMenu;
    TextButton buttonShare;
    PointCounter pointCounter;

    BitmapFont bestScoreFont;
    BitmapFont statsFont;

    int gamePoints;
    int bestScore;
    int gamesPlayed;
    int totalStars;
    ScoreManager scoreManager;

    public ScreenRestart(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.scoreManager = new ScoreManager();

        int btnWidth = (int)(SCR_WIDTH * 0.25f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        int startX = (SCR_WIDTH - btnWidth) / 2;
        
        pointCounter = new PointCounter((int)(SCR_WIDTH * 0.59f), (int)(SCR_HEIGHT * 0.74f));
        buttonRestart = new TextButton(startX, (int)(SCR_HEIGHT * 0.56f), "Restart");
        buttonMainMenu = new TextButton(startX, (int)(SCR_HEIGHT * 0.42f), "Main Menu");
        buttonShare = new TextButton(startX, (int)(SCR_HEIGHT * 0.28f), "Share");
        background = new MovingBackground("backgrounds/restart_bg.png");

        bestScoreFont = new BitmapFont();
        bestScoreFont.getData().setScale(4f);
        bestScoreFont.setColor(Color.YELLOW);

        statsFont = new BitmapFont();
        statsFont.getData().setScale(2f);
        statsFont.setColor(Color.WHITE);
    }

    @Override
    public void show() {
        bestScore = scoreManager.getBestScore();
        gamesPlayed = scoreManager.gamesPlayed;
        totalStars = scoreManager.starsCollected;
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {

            Vector3 touch = myGdxGame.camera.unproject(
                    new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)
            );

            int tx = (int) touch.x;
            int ty = (int) touch.y;

            if (buttonRestart.isHit(tx, ty)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            } else if (buttonMainMenu.isHit(tx, ty)) {
                myGdxGame.setScreen(myGdxGame.screenMenu);
            } else if (buttonShare.isHit(tx, ty)) {
                String shareText = "I scored " + gamePoints + " in Flappy Bird! Can you beat me?";
                try {
                    String encoded = URLEncoder.encode(shareText, StandardCharsets.UTF_8.toString());
                    Gdx.net.openURI("https://twitter.com/intent/tweet?text=" + encoded);
                } catch (Exception e) {
                    Gdx.net.openURI("https://twitter.com");
                }
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        buttonRestart.draw(myGdxGame.batch);
        buttonMainMenu.draw(myGdxGame.batch);
        buttonShare.draw(myGdxGame.batch);
        pointCounter.draw(myGdxGame.batch, gamePoints);

        bestScoreFont.draw(myGdxGame.batch, "Best: " + bestScore, 750, 450);
        statsFont.draw(myGdxGame.batch, "Games: " + gamesPlayed, 750, 380);
        statsFont.draw(myGdxGame.batch, "Stars: " + totalStars, 750, 330);

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
        background.dispose();
        buttonRestart.dispose();
        buttonMainMenu.dispose();
        buttonShare.dispose();
        pointCounter.dispose();
        bestScoreFont.dispose();
        statsFont.dispose();
    }
}
