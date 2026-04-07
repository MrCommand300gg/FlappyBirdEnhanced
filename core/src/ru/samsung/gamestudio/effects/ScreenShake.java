package ru.samsung.gamestudio.effects;

import com.badlogic.gdx.math.Vector2;

public class ScreenShake {
    float duration;
    float intensity;
    float timer;
    boolean active;

    public void start(float duration, float intensity) {
        this.duration = duration;
        this.intensity = intensity;
        this.timer = duration;
        this.active = true;
    }

    public Vector2 update(float delta) {
        if (!active) {
            return new Vector2(0, 0);
        }

        timer -= delta;
        if (timer <= 0) {
            active = false;
            return new Vector2(0, 0);
        }

        float progress = 1 - (timer / duration);
        float currentIntensity = intensity * (1 - progress);

        float offsetX = (float) (Math.random() * 2 - 1) * currentIntensity;
        float offsetY = (float) (Math.random() * 2 - 1) * currentIntensity;

        return new Vector2(offsetX, offsetY);
    }

    public boolean isActive() {
        return active;
    }
}
