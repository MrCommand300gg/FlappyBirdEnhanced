package ru.samsung.gamestudio.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    Preferences prefs;
    public int bestScore;
    public int totalScore;
    public int gamesPlayed;
    public int starsCollected;

    public ScoreManager() {
        prefs = Gdx.app.getPreferences("flappy_scores");
        load();
    }

    public void saveScore(int score) {
        if (score > bestScore) {
            bestScore = score;
        }
        totalScore += score;
        gamesPlayed++;
        save();
    }

    public int getBestScore() {
        return bestScore;
    }

    public void addStars(int count) {
        starsCollected += count;
        save();
    }

    public int[] getTopScores(int count) {
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int s = prefs.getInteger("top" + i, 0);
            if (s > 0) scores.add(s);
        }
        int[] result = new int[scores.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = scores.get(i);
        }
        return result;
    }

    public int getRank(int score) {
        int[] tops = getTopScores(100);
        int rank = 1;
        for (int s : tops) {
            if (score < s) rank++;
        }
        return rank;
    }

    public void addScore(int score) {
        List<Integer> scores = new ArrayList<>();
        for (int s : getTopScores(100)) {
            scores.add(s);
        }
        scores.add(score);
        scores.sort((a, b) -> b - a);

        for (int i = 0; i < Math.min(10, scores.size()); i++) {
            prefs.putInteger("top" + i, scores.get(i));
        }
        prefs.flush();
        saveScore(score);
    }

    public String getPlayerName() {
        return prefs.getString("playerName", "Player");
    }

    public void setPlayerName(String name) {
        prefs.putString("playerName", name);
        prefs.flush();
    }

    public java.util.Map<String, Integer> getAllStats() {
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        stats.put("bestScore", bestScore);
        stats.put("totalScore", totalScore);
        stats.put("gamesPlayed", gamesPlayed);
        stats.put("starsCollected", starsCollected);
        return stats;
    }

    private void save() {
        prefs.putInteger("bestScore", bestScore);
        prefs.putInteger("totalScore", totalScore);
        prefs.putInteger("gamesPlayed", gamesPlayed);
        prefs.putInteger("starsCollected", starsCollected);
        prefs.flush();
    }

    private void load() {
        bestScore = prefs.getInteger("bestScore", 0);
        totalScore = prefs.getInteger("totalScore", 0);
        gamesPlayed = prefs.getInteger("gamesPlayed", 0);
        starsCollected = prefs.getInteger("starsCollected", 0);
    }
}
