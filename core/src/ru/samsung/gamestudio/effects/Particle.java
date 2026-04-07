package ru.samsung.gamestudio.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Particle {
    float x, y;
    float velocityX, velocityY;
    float life;
    float maxLife;
    float size;
    Color color;
    Texture texture;

    public Particle(float x, float y, float velocityX, float velocityY, float life, float size, Color color) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.life = life;
        this.maxLife = life;
        this.size = size;
        this.color = color;
        this.texture = new Texture("effects/particlepng.png");
    }

    public void update(float delta) {
        x += velocityX * delta * 60;
        y += velocityY * delta * 60;
        life -= delta;
    }

    public void draw(Batch batch) {
        float alpha = life / maxLife;
        Color originalColor = batch.getColor().cpy();
        color.a = alpha;
        batch.setColor(color);
        batch.draw(texture, x - size / 2, y - size / 2, size, size);
        batch.setColor(originalColor);
    }

    public boolean isDead() {
        return life <= 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
