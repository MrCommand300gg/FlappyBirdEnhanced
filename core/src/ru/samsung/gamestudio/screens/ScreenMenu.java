package ru.samsung.gamestudio.screens;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;
import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.MovingBackground;
import ru.samsung.gamestudio.components.TextButton;

public class ScreenMenu implements Screen {

    MyGdxGame myGdxGame;
    MovingBackground background;
    TextButton buttonPlay;
    TextButton buttonSettings;
    TextButton buttonAchievements;
    TextButton buttonLeaderboard;
    TextButton buttonProfile;
    TextButton buttonExit;

    public ScreenMenu(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        background = new MovingBackground("backgrounds/menu_bg.jpg");
        
        int btnWidth = (int)(SCR_WIDTH * 0.25f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        int startX = (SCR_WIDTH - btnWidth) / 2;
        int startY = (int)(SCR_HEIGHT * 0.75f);
        int gap = (int)(SCR_HEIGHT * 0.11f);
        
        buttonPlay = new TextButton(startX, startY, "Play");
        buttonSettings = new TextButton(startX, startY - gap, "Settings");
        buttonAchievements = new TextButton(startX, startY - gap * 2, "Achievements");
        buttonLeaderboard = new TextButton(startX, startY - gap * 3, "Leaderboard");
        buttonProfile = new TextButton(startX, startY - gap * 4, "Profile");
        buttonExit = new TextButton(startX, startY - gap * 5, "Exit");
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

            if (buttonPlay.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            } else if (buttonSettings.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenSettings);
            } else if (buttonAchievements.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenAchievements);
            } else if (buttonLeaderboard.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenLeaderboard);
            } else if (buttonProfile.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenProfile);
            } else if (buttonExit.isHit((int) touch.x, (int) touch.y)) {
                Gdx.app.exit();
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        buttonPlay.draw(myGdxGame.batch);
        buttonSettings.draw(myGdxGame.batch);
        buttonAchievements.draw(myGdxGame.batch);
        buttonLeaderboard.draw(myGdxGame.batch);
        buttonProfile.draw(myGdxGame.batch);
        buttonExit.draw(myGdxGame.batch);

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
        buttonPlay.dispose();
        buttonSettings.dispose();
        buttonAchievements.dispose();
        buttonLeaderboard.dispose();
        buttonProfile.dispose();
        buttonExit.dispose();
    }
}
