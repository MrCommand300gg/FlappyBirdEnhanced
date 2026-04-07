package ru.samsung.gamestudio.screens;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;
import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.MovingBackground;
import ru.samsung.gamestudio.components.TextButton;
import ru.samsung.gamestudio.storage.ScoreManager;

public class ScreenLeaderboard implements Screen {

    MyGdxGame myGdxGame;
    TextButton buttonBack;
    BitmapFont titleFont;
    BitmapFont scoreFont;
    BitmapFont numberFont;
    ScoreManager scoreManager;
    int[] topScores;
    MovingBackground background;

    public ScreenLeaderboard(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.scoreManager = new ScoreManager();

        background = new MovingBackground("backgrounds/menu_bg.jpg");
        
        int btnWidth = (int)(SCR_WIDTH * 0.2f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        buttonBack = new TextButton((SCR_WIDTH - btnWidth) / 2, (int)(SCR_HEIGHT * 0.07f), "Back");

        titleFont = new BitmapFont();
        titleFont.getData().setScale(5f);
        titleFont.setColor(Color.GOLD);

        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(3f);
        scoreFont.setColor(Color.WHITE);

        numberFont = new BitmapFont();
        numberFont.getData().setScale(3f);
        numberFont.setColor(Color.YELLOW);

        topScores = scoreManager.getTopScores(10);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Vector3 touch = myGdxGame.camera.unproject(
                    new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)
            );

            if (buttonBack.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenMenu);
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        titleFont.draw(myGdxGame.batch, "Leaderboard", 420, 680);

        int y = 580;
        for (int i = 0; i < topScores.length; i++) {
            numberFont.draw(myGdxGame.batch, "#" + (i + 1), 350, y);
            scoreFont.draw(myGdxGame.batch, String.valueOf(topScores[i]), 500, y);
            y -= 50;
        }

        buttonBack.draw(myGdxGame.batch);

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
        buttonBack.dispose();
        titleFont.dispose();
        scoreFont.dispose();
        numberFont.dispose();
    }
}
