package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.samsung.gamestudio.screens.ScreenGame;
import ru.samsung.gamestudio.screens.ScreenRestart;
import ru.samsung.gamestudio.screens.ScreenMenu;
import ru.samsung.gamestudio.screens.ScreenSettings;
import ru.samsung.gamestudio.screens.ScreenPause;
import ru.samsung.gamestudio.screens.ScreenAchievements;
import ru.samsung.gamestudio.screens.ScreenLeaderboard;
import ru.samsung.gamestudio.screens.ScreenProfile;
import ru.samsung.gamestudio.screens.ScreenTutorial;
import ru.samsung.gamestudio.settings.GameSettings;

public class MyGdxGame extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;

    public static int SCR_WIDTH = 1280;
    public static int SCR_HEIGHT = 720;

    public ScreenGame screenGame;
    public ScreenRestart screenRestart;
    public ScreenMenu screenMenu;
    public ScreenSettings screenSettings;
    public ScreenPause screenPause;
    public ScreenAchievements screenAchievements;
    public ScreenLeaderboard screenLeaderboard;
    public ScreenProfile screenProfile;
    public ScreenTutorial screenTutorial;

    GameSettings settings;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);

        settings = GameSettings.getInstance();

        screenMenu = new ScreenMenu(this);
        screenGame = new ScreenGame(this);
        screenRestart = new ScreenRestart(this);
        screenSettings = new ScreenSettings(this);
        screenPause = new ScreenPause(this);
        screenAchievements = new ScreenAchievements(this);
        screenLeaderboard = new ScreenLeaderboard(this);
        screenProfile = new ScreenProfile(this);
        screenTutorial = new ScreenTutorial(this);

        if (settings.firstTimeLaunch) {
            setScreen(screenTutorial);
            settings.firstTimeLaunch = false;
            settings.save();
        } else {
            setScreen(screenMenu);
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
