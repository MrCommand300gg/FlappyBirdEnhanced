package ru.samsung.gamestudio.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import ru.samsung.gamestudio.effects.Particle;

import java.util.Random;

public class ParticleSystem {
    Array<Particle> particles;
    Random random;

    public ParticleSystem() {
        particles = new Array<>();
        random = new Random();
    }

    public void emit(float x, float y, int count, Color color, float spread) {
        for (int i = 0; i < count; i++) {
            float angle = (float) (random.nextDouble() * Math.PI * 2);
            float speed = (float) (random.nextDouble() * spread);
            float vx = (float) Math.cos(angle) * speed;
            float vy = (float) Math.sin(angle) * speed;
            float life = 0.5f + random.nextFloat() * 0.5f;
            float size = 10 + random.nextFloat() * 10;
            particles.add(new Particle(x, y, vx, vy, life, size, color));
        }
    }

    public void update(float delta) {
        for (int i = particles.size - 1; i >= 0; i--) {
            particles.get(i).update(delta);
            if (particles.get(i).isDead()) {
                particles.get(i).dispose();
                particles.removeIndex(i);
            }
        }
    }

    public void draw(Batch batch) {
        for (Particle p : particles) {
            p.draw(batch);
        }
    }

    public void dispose() {
        for (Particle p : particles) {
            p.dispose();
        }
        particles.clear();
    }
}
