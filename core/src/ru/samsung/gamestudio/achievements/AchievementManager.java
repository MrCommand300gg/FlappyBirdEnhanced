package ru.samsung.gamestudio.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    List<Achievement> achievements;
    Preferences prefs;

    public AchievementManager() {
        achievements = new ArrayList<>();
        prefs = Gdx.app.getPreferences("flappy_achievements");
        init();
    }

    public void init() {
        achievements.add(new Achievement("first_flight", "First Flight", "Play your first game"));
        achievements.add(new Achievement("novice", "Novice", "Score 10 points"));
        achievements.add(new Achievement("experienced", "Experienced", "Score 50 points"));
        achievements.add(new Achievement("master", "Master", "Score 100 points"));
        achievements.add(new Achievement("legend", "Legend", "Score 200 points"));
        achievements.add(new Achievement("collector", "Collector", "Collect 100 stars"));
        achievements.add(new Achievement("dedicated", "Dedicated", "Play 50 games"));
        load();
    }

    public List<Achievement> checkNewAchievements(int bestScore, int gamesPlayed, int starsCollected) {
        List<Achievement> newlyUnlocked = new ArrayList<>();
        ScoreManagerWrapper sm = new ScoreManagerWrapper(bestScore, gamesPlayed, starsCollected);

        for (Achievement a : achievements) {
            if (!a.unlocked && a.checkCondition(sm)) {
                a.unlocked = true;
                newlyUnlocked.add(a);
            }
        }
        save();
        return newlyUnlocked;
    }

    public void save() {
        for (Achievement a : achievements) {
            prefs.putBoolean(a.id, a.unlocked);
        }
        prefs.flush();
    }

    public void load() {
        for (Achievement a : achievements) {
            a.unlocked = prefs.getBoolean(a.id, false);
        }
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }
}
