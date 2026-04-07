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

public class ScreenProfile implements Screen {

    MyGdxGame myGdxGame;
    BitmapFont font;
    TextButton buttonBack;
    TextButton buttonChangeName;
    ScoreManager scoreManager;
    MovingBackground background;

    String playerName;
    int totalGames;
    int bestScore;
    int starsCollected;
    int achievementsUnlocked;

    public ScreenProfile(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.scoreManager = new ScoreManager();

        background = new MovingBackground("backgrounds/menu_bg.jpg");
        
        int btnWidth = (int)(SCR_WIDTH * 0.25f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        
        buttonBack = new TextButton((SCR_WIDTH - btnWidth) / 2, (int)(SCR_HEIGHT * 0.07f), "Back");
        buttonChangeName = new TextButton((SCR_WIDTH - btnWidth) / 2, (int)(SCR_HEIGHT * 0.83f), "Change Name");

        font = new BitmapFont();
        font.getData().setScale(3f);
        font.setColor(Color.WHITE);

        loadStats();
    }

    private void loadStats() {
        playerName = scoreManager.getPlayerName();
        totalGames = scoreManager.gamesPlayed;
        bestScore = scoreManager.getBestScore();
        starsCollected = scoreManager.starsCollected;
        achievementsUnlocked = 0;
    }

    @Override
    public void show() {
        loadStats();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Vector3 touch = myGdxGame.camera.unproject(
                    new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)
            );

            if (buttonBack.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenMenu);
            } else if (buttonChangeName.isHit((int) touch.x, (int) touch.y)) {
                Gdx.app.log("Profile", "Name change clicked");
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        font.setColor(Color.GOLD);
        font.draw(myGdxGame.batch, playerName, 540, 550);
        font.setColor(Color.WHITE);
        font.draw(myGdxGame.batch, "Games: " + totalGames, 540, 480);
        font.draw(myGdxGame.batch, "Best Score: " + bestScore, 540, 410);
        font.draw(myGdxGame.batch, "Stars: " + starsCollected, 540, 340);
        font.draw(myGdxGame.batch, "Achievements: " + achievementsUnlocked, 540, 270);

        buttonBack.draw(myGdxGame.batch);
        buttonChangeName.draw(myGdxGame.batch);

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
        buttonChangeName.dispose();
        font.dispose();
    }
}
