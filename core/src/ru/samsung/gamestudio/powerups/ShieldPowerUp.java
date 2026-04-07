package ru.samsung.gamestudio.powerups;

import com.badlogic.gdx.graphics.Texture;
import ru.samsung.gamestudio.characters.Bird;

public class ShieldPowerUp extends PowerUp {
    public ShieldPowerUp() {
        this.duration = 5f;
        this.active = false;
        // icon = new Texture("items/shield_icon.png");
    }

    @Override
    public void activate(Bird bird) {
        bird.shielded = true;
        active = true;
        remainingTime = duration;
    }

    @Override
    public void deactivate(Bird bird) {
        bird.shielded = false;
    }
}
