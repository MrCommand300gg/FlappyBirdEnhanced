package ru.samsung.gamestudio.powerups;

import com.badlogic.gdx.graphics.Texture;
import ru.samsung.gamestudio.characters.Bird;

public class ShrinkPowerUp extends PowerUp {
    public ShrinkPowerUp() {
        this.duration = 4f;
        this.active = false;
        // icon = new Texture("items/shrink_iconpng.png");
    }

    @Override
    public void activate(Bird bird) {
        bird.sizeMultiplier = 0.5f;
        active = true;
        remainingTime = duration;
    }

    @Override
    public void deactivate(Bird bird) {
        bird.sizeMultiplier = 1f;
    }
}
