package ru.samsung.gamestudio.powerups;

import com.badlogic.gdx.graphics.Texture;
import ru.samsung.gamestudio.characters.Bird;

public class SpeedPowerUp extends PowerUp {
    public SpeedPowerUp() {
        this.duration = 3f;
        this.active = false;
        // icon = new Texture("items/speed_icon.png");
    }

    @Override
    public void activate(Bird bird) {
        bird.jumpForce = 25f;
        active = true;
        remainingTime = duration;
    }

    @Override
    public void deactivate(Bird bird) {
        bird.jumpForce = 15f;
    }
}
