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
import ru.samsung.gamestudio.achievements.Achievement;
import ru.samsung.gamestudio.achievements.AchievementManager;

import java.util.List;

public class ScreenAchievements implements Screen {

    MyGdxGame myGdxGame;
    MovingBackground background;
    TextButton buttonBack;
    BitmapFont titleFont;
    BitmapFont nameFont;
    BitmapFont descFont;
    AchievementManager achievementManager;
    List<Achievement> achievements;

    public ScreenAchievements(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        background = new MovingBackground("backgrounds/menu_bg.jpg");
        
        int btnWidth = (int)(SCR_WIDTH * 0.2f);
        int btnHeight = (int)(SCR_HEIGHT * 0.08f);
        buttonBack = new TextButton((SCR_WIDTH - btnWidth) / 2, (int)(SCR_HEIGHT * 0.07f), "Back");

        titleFont = new BitmapFont();
        titleFont.getData().setScale(5f);
        titleFont.setColor(Color.GOLD);

        nameFont = new BitmapFont();
        nameFont.getData().setScale(2f);
        nameFont.setColor(Color.WHITE);

        descFont = new BitmapFont();
        descFont.getData().setScale(1.5f);
        descFont.setColor(Color.LIGHT_GRAY);

        achievementManager = new AchievementManager();
        achievements = achievementManager.getAchievements();
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
        titleFont.draw(myGdxGame.batch, "Achievements", 440, 680);

        int y = 600;
        for (Achievement a : achievements) {
            if (a.unlocked) {
                nameFont.setColor(Color.GREEN);
                descFont.setColor(Color.LIGHT_GRAY);
            } else {
                nameFont.setColor(Color.GRAY);
                descFont.setColor(Color.DARK_GRAY);
            }
            nameFont.draw(myGdxGame.batch, a.name, 300, y);
            descFont.draw(myGdxGame.batch, a.description, 300, y - 30);
            y -= 70;
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
        nameFont.dispose();
        descFont.dispose();
    }
}
