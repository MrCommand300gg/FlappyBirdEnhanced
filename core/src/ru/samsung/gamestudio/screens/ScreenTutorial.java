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

public class ScreenTutorial implements Screen {

    MyGdxGame myGdxGame;
    int currentStep = 0;
    String[] steps = {
            "Tap the screen to make the bird fly up",
            "Fly between pipes to score points",
            "Collect stars for bonus points",
            "Avoid collisions!",
            "Good luck!"
    };
    MovingBackground background;
    TextButton buttonNext;
    TextButton buttonSkip;
    BitmapFont stepFont;

    public ScreenTutorial(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        background = new MovingBackground("backgrounds/menu_bg.jpg");
        
        int btnWidth = (int)(SCR_WIDTH * 0.2f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        int startX = (SCR_WIDTH - btnWidth) / 2;
        
        buttonNext = new TextButton(startX + btnWidth + 30, (int)(SCR_HEIGHT * 0.28f), "Next");
        buttonSkip = new TextButton(startX - btnWidth - 30, (int)(SCR_HEIGHT * 0.28f), "Skip");

        stepFont = new BitmapFont();
        stepFont.getData().setScale(3f);
        stepFont.setColor(Color.WHITE);
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

            if (buttonNext.isHit(tx, ty)) {
                if (currentStep < steps.length - 1) {
                    currentStep++;
                } else {
                    myGdxGame.setScreen(myGdxGame.screenGame);
                }
            } else if (buttonSkip.isHit(tx, ty)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        stepFont.draw(myGdxGame.batch, "Step " + (currentStep + 1) + ":", 540, 500);
        stepFont.draw(myGdxGame.batch, steps[currentStep], 400, 420);
        stepFont.setColor(Color.YELLOW);
        stepFont.draw(myGdxGame.batch, currentStep + 1 + "/" + steps.length, 600, 350);
        stepFont.setColor(Color.WHITE);

        buttonNext.draw(myGdxGame.batch);
        buttonSkip.draw(myGdxGame.batch);

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
        buttonNext.dispose();
        buttonSkip.dispose();
        stepFont.dispose();
    }
}
