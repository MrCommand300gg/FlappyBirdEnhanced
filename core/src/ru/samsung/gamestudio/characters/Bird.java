package ru.samsung.gamestudio.characters;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Bird {

    public float x, y;
    public int width, height;

    float velocityY;
    float gravity = -0.8f;
    public float jumpForce = 15f;

    float rotation;

    int frameCounter;
    Texture[] framesArray;

    public boolean shielded;
    public boolean magnetized;
    public float sizeMultiplier = 1f;

    public Bird(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        frameCounter = 0;

        framesArray = new Texture[]{
                new Texture("birdTiles/bird0.png"),
                new Texture("birdTiles/bird1.png"),
                new Texture("birdTiles/bird2.png"),
                new Texture("birdTiles/bird1.png"),
        };
    }

    public void setY(float y) {
        this.y = y;
        this.velocityY = 0;
    }

    public void jump() {
        velocityY = jumpForce;
    }

    public void update(float delta) {
        velocityY += gravity;
        y += velocityY * delta * 60;

        if (y > SCR_HEIGHT - height) {
            y = SCR_HEIGHT - height;
            velocityY = 0;
        }

        if (velocityY > 0) {
            rotation = Math.min(30, rotation + 5);
        } else {
            rotation = Math.max(-90, rotation - 3);
        }
    }

    public boolean isInField() {
        if (y + height < 0) return false;
        if (y > SCR_HEIGHT) return false;
        return true;
    }

    public void draw(Batch batch) {
        int frameMultiplier = 10;
        float currentWidth = width * sizeMultiplier;
        float currentHeight = height * sizeMultiplier;
        float offsetX = (width - currentWidth) / 2;
        float offsetY = (height - currentHeight) / 2;
        batch.draw(framesArray[frameCounter / frameMultiplier], x + offsetX, y + offsetY, currentWidth, currentHeight);
        if (frameCounter++ == framesArray.length * frameMultiplier - 1) frameCounter = 0;
    }

    public void dispose() {
        for (Texture texture : framesArray) {
            texture.dispose();
        }
    }

}
