package ru.samsung.gamestudio.characters;

import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;
import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Star {
    public float x, y;
    public int width = 40, height = 40;
    float speed;
    public boolean collected;
    Texture texture;
    float animationTimer;

    public Star(float startX, float startY, float speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.collected = false;
        this.animationTimer = 0;
        texture = new Texture("items/stars.png");
    }

    public void update(float delta) {
        x -= speed * delta * 60;
        animationTimer += delta;
    }

    public void draw(Batch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public boolean isCollected(Bird bird) {
        if (collected) return false;
        
        float birdCenterX = bird.x + bird.width / 2;
        float birdCenterY = bird.y + bird.height / 2;
        float starCenterX = x + width / 2;
        float starCenterY = y + height / 2;
        
        float distance = (float) Math.sqrt(
            Math.pow(birdCenterX - starCenterX, 2) + 
            Math.pow(birdCenterY - starCenterY, 2)
        );
        
        return distance < (bird.width / 2 + width / 2);
    }

    public void recycle() {
        x = SCR_WIDTH + (float) (Math.random() * SCR_WIDTH);
        y = 100 + (float) (Math.random() * (SCR_HEIGHT - 200));
        collected = false;
    }

    public void dispose() {
        texture.dispose();
    }
}
