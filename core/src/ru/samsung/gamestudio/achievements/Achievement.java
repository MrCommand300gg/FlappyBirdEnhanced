package ru.samsung.gamestudio.achievements;

import com.badlogic.gdx.graphics.Texture;

public class Achievement {
    public String id;
    public String name;
    public String description;
    public boolean unlocked;
    Texture icon;

    public Achievement(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unlocked = false;
    }

    public boolean checkCondition(ScoreManagerWrapper scoreManager) {
        switch (id) {
            case "first_flight":
                return scoreManager.gamesPlayed >= 1;
            case "novice":
                return scoreManager.bestScore >= 10;
            case "experienced":
                return scoreManager.bestScore >= 50;
            case "master":
                return scoreManager.bestScore >= 100;
            case "legend":
                return scoreManager.bestScore >= 200;
            case "collector":
                return scoreManager.starsCollected >= 100;
            case "dedicated":
                return scoreManager.gamesPlayed >= 50;
            default:
                return false;
        }
    }
}

class ScoreManagerWrapper {
    int bestScore;
    int gamesPlayed;
    int starsCollected;

    ScoreManagerWrapper(int bestScore, int gamesPlayed, int starsCollected) {
        this.bestScore = bestScore;
        this.gamesPlayed = gamesPlayed;
        this.starsCollected = starsCollected;
    }
}
