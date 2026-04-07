package ru.samsung.gamestudio.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.samsung.gamestudio.characters.Bird;

public abstract class PowerUp {
    float x, y;
    float duration;
    float remainingTime;
    boolean active;
    Texture icon;
    int width = 40;
    int height = 40;

    public abstract void activate(Bird bird);
    public abstract void deactivate(Bird bird);

    public void update(float delta) {
        if (active && remainingTime > 0) {
            remainingTime -= delta;
            if (remainingTime <= 0) {
                active = false;
            }
        }
    }

    public void draw(Batch batch) {
        if (active) {
            batch.draw(icon, x, y, width, height);
        }
    }
}
