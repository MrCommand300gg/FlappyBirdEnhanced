package ru.samsung.gamestudio.game;

public class DifficultyManager {
    public enum Level { EASY, MEDIUM, HARD, INSANE }

    Level currentLevel;
    int baseSpeed = 8;
    int baseGapHeight = 350;
    int minGapHeight = 200;

    public int getTubeSpeed(int score) {
        if (score <= 10) {
            currentLevel = Level.EASY;
            return 8;
        } else if (score <= 30) {
            currentLevel = Level.MEDIUM;
            return 10;
        } else if (score <= 60) {
            currentLevel = Level.HARD;
            return 12;
        } else {
            currentLevel = Level.INSANE;
            return 15;
        }
    }

    public int getGapHeight(int score) {
        if (score <= 10) {
            return 350;
        } else if (score <= 30) {
            return 300;
        } else if (score <= 60) {
            return 260;
        } else {
            return 220;
        }
    }

    public Level getLevel(int score) {
        if (score <= 10) return Level.EASY;
        else if (score <= 30) return Level.MEDIUM;
        else if (score <= 60) return Level.HARD;
        else return Level.INSANE;
    }
}
