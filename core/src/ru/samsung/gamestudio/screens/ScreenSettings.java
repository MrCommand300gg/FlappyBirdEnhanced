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
import ru.samsung.gamestudio.settings.GameSettings;
import ru.samsung.gamestudio.game.DifficultyManager;

public class ScreenSettings implements Screen {

    MyGdxGame myGdxGame;
    MovingBackground background;
    TextButton buttonBack;

    TextButton buttonMusicUp;
    TextButton buttonMusicDown;
    TextButton buttonSfxUp;
    TextButton buttonSfxDown;

    TextButton buttonEasy;
    TextButton buttonMedium;
    TextButton buttonHard;
    TextButton buttonInsane;

    BitmapFont labelFont;
    BitmapFont valueFont;
    GameSettings settings;

    public ScreenSettings(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.settings = GameSettings.getInstance();

        background = new MovingBackground("backgrounds/menu_bg.jpg");
        
        int btnWidth = (int)(SCR_WIDTH * 0.12f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        int startX = (SCR_WIDTH - btnWidth) / 2;
        
        buttonBack = new TextButton(startX, (int)(SCR_HEIGHT * 0.07f), "Back");
        buttonMusicUp = new TextButton(startX + btnWidth + 20, (int)(SCR_HEIGHT * 0.69f), "+");
        buttonMusicDown = new TextButton(startX - btnWidth - 20, (int)(SCR_HEIGHT * 0.69f), "-");
        buttonSfxUp = new TextButton(startX + btnWidth + 20, (int)(SCR_HEIGHT * 0.56f), "+");
        buttonSfxDown = new TextButton(startX - btnWidth - 20, (int)(SCR_HEIGHT * 0.56f), "-");

        int diffBtnWidth = (int)(SCR_WIDTH * 0.15f);
        int diffStartX = (int)(SCR_WIDTH * 0.28f);
        
        buttonEasy = new TextButton(diffStartX, (int)(SCR_HEIGHT * 0.42f), "Easy");
        buttonMedium = new TextButton(diffStartX + diffBtnWidth + 10, (int)(SCR_HEIGHT * 0.42f), "Medium");
        buttonHard = new TextButton(diffStartX + (diffBtnWidth + 10) * 2, (int)(SCR_HEIGHT * 0.42f), "Hard");
        buttonInsane = new TextButton(diffStartX + (diffBtnWidth + 10) * 3, (int)(SCR_HEIGHT * 0.42f), "Insane");

        labelFont = new BitmapFont();
        labelFont.getData().setScale(3f);
        labelFont.setColor(Color.WHITE);

        valueFont = new BitmapFont();
        valueFont.getData().setScale(2f);
        valueFont.setColor(Color.YELLOW);
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

            if (buttonBack.isHit(tx, ty)) {
                settings.save();
                myGdxGame.setScreen(myGdxGame.screenMenu);
            } else if (buttonMusicUp.isHit(tx, ty)) {
                settings.musicVolume = Math.min(1f, settings.musicVolume + 0.1f);
            } else if (buttonMusicDown.isHit(tx, ty)) {
                settings.musicVolume = Math.max(0f, settings.musicVolume - 0.1f);
            } else if (buttonSfxUp.isHit(tx, ty)) {
                settings.sfxVolume = Math.min(1f, settings.sfxVolume + 0.1f);
            } else if (buttonSfxDown.isHit(tx, ty)) {
                settings.sfxVolume = Math.max(0f, settings.sfxVolume - 0.1f);
            } else if (buttonEasy.isHit(tx, ty)) {
                settings.difficulty = DifficultyManager.Level.EASY;
            } else if (buttonMedium.isHit(tx, ty)) {
                settings.difficulty = DifficultyManager.Level.MEDIUM;
            } else if (buttonHard.isHit(tx, ty)) {
                settings.difficulty = DifficultyManager.Level.HARD;
            } else if (buttonInsane.isHit(tx, ty)) {
                settings.difficulty = DifficultyManager.Level.INSANE;
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);

        labelFont.draw(myGdxGame.batch, "Music Volume:", 300, 510);
        valueFont.draw(myGdxGame.batch, String.format("%.0f%%", settings.musicVolume * 100), 600, 510);
        buttonMusicUp.draw(myGdxGame.batch);
        buttonMusicDown.draw(myGdxGame.batch);

        labelFont.draw(myGdxGame.batch, "SFX Volume:", 300, 410);
        valueFont.draw(myGdxGame.batch, String.format("%.0f%%", settings.sfxVolume * 100), 600, 410);
        buttonSfxUp.draw(myGdxGame.batch);
        buttonSfxDown.draw(myGdxGame.batch);

        labelFont.draw(myGdxGame.batch, "Difficulty:", 300, 310);

        buttonEasy.draw(myGdxGame.batch);
        buttonMedium.draw(myGdxGame.batch);
        buttonHard.draw(myGdxGame.batch);
        buttonInsane.draw(myGdxGame.batch);

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
        buttonMusicUp.dispose();
        buttonMusicDown.dispose();
        buttonSfxUp.dispose();
        buttonSfxDown.dispose();
        buttonEasy.dispose();
        buttonMedium.dispose();
        buttonHard.dispose();
        buttonInsane.dispose();
        labelFont.dispose();
        valueFont.dispose();
    }
}
