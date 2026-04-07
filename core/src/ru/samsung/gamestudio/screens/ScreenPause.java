package ru.samsung.gamestudio.screens;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;
import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.TextButton;

public class ScreenPause implements Screen {

    MyGdxGame myGdxGame;
    TextButton buttonResume;
    TextButton buttonRestart;
    TextButton buttonMainMenu;

    public ScreenPause(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        int btnWidth = (int)(SCR_WIDTH * 0.25f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        int startX = (SCR_WIDTH - btnWidth) / 2;
        
        buttonResume = new TextButton(startX, (int)(SCR_HEIGHT * 0.63f), "Resume");
        buttonRestart = new TextButton(startX, (int)(SCR_HEIGHT * 0.49f), "Restart");
        buttonMainMenu = new TextButton(startX, (int)(SCR_HEIGHT * 0.35f), "Main Menu");
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

            int tx = (int) touch.x;
            int ty = (int) touch.y;

            if (buttonResume.isHit(tx, ty)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            } else if (buttonRestart.isHit(tx, ty)) {
                myGdxGame.screenGame.show();
                myGdxGame.setScreen(myGdxGame.screenGame);
            } else if (buttonMainMenu.isHit(tx, ty)) {
                myGdxGame.setScreen(myGdxGame.screenMenu);
            }
        }

        ScreenUtils.clear(0, 0, 0, 0.5f);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        buttonResume.draw(myGdxGame.batch);
        buttonRestart.draw(myGdxGame.batch);
        buttonMainMenu.draw(myGdxGame.batch);

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
        buttonResume.dispose();
        buttonRestart.dispose();
        buttonMainMenu.dispose();
    }
}
