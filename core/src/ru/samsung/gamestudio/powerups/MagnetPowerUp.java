package ru.samsung.gamestudio.powerups;

import com.badlogic.gdx.graphics.Texture;
import ru.samsung.gamestudio.characters.Bird;

public class MagnetPowerUp extends PowerUp {
    public MagnetPowerUp() {
        this.duration = 5f;
        this.active = false;
        // icon = new Texture("items/magnet_icon.png");
    }

    @Override
    public void activate(Bird bird) {
        bird.magnetized = true;
        active = true;
        remainingTime = duration;
    }

    @Override
    public void deactivate(Bird bird) {
        bird.magnetized = false;
    }
}
