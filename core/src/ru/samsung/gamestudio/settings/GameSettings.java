package ru.samsung.gamestudio.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import ru.samsung.gamestudio.game.DifficultyManager;

public class GameSettings {
    private static GameSettings instance;
    Preferences prefs;

    public float musicVolume = 0.5f;
    public float sfxVolume = 0.5f;
    public DifficultyManager.Level difficulty = DifficultyManager.Level.MEDIUM;
    public boolean firstTimeLaunch = true;

    private GameSettings() {
        prefs = Gdx.app.getPreferences("flappy_settings");
        load();
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public void save() {
        prefs.putFloat("musicVolume", musicVolume);
        prefs.putFloat("sfxVolume", sfxVolume);
        prefs.putString("difficulty", difficulty.name());
        prefs.putBoolean("firstTimeLaunch", firstTimeLaunch);
        prefs.flush();
    }

    public void load() {
        musicVolume = prefs.getFloat("musicVolume", 0.5f);
        sfxVolume = prefs.getFloat("sfxVolume", 0.5f);
        String diff = prefs.getString("difficulty", "MEDIUM");
        difficulty = DifficultyManager.Level.valueOf(diff);
        firstTimeLaunch = prefs.getBoolean("firstTimeLaunch", true);
    }
}
