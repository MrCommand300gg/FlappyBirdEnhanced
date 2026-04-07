package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import ru.samsung.gamestudio.MyGdxGame;

public class MovingBackground {

    Texture[] layers;
    float[] speeds;
    float[] offsets;
    int layerCount;

    public MovingBackground(String[] layerPaths, float[] layerSpeeds) {
        layerCount = layerPaths.length;
        layers = new Texture[layerCount];
        speeds = new float[layerCount];
        offsets = new float[layerCount];

        for (int i = 0; i < layerCount; i++) {
            layers[i] = new Texture(layerPaths[i]);
            speeds[i] = layerSpeeds[i];
            offsets[i] = 0;
        }
    }

    public MovingBackground(String pathToTexture) {
        this(new String[]{pathToTexture}, new float[]{3});
    }

    public void move() {
        for (int i = 0; i < layerCount; i++) {
            offsets[i] -= speeds[i];
            if (offsets[i] <= -MyGdxGame.SCR_WIDTH) {
                offsets[i] = 0;
            }
        }
    }

    public void draw(Batch batch) {
        for (int i = 0; i < layerCount; i++) {
            batch.draw(layers[i], offsets[i], 0, MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
            batch.draw(layers[i], offsets[i] + MyGdxGame.SCR_WIDTH, 0, MyGdxGame.SCR_WIDTH + 2, MyGdxGame.SCR_HEIGHT);
        }
    }

    public void dispose() {
        for (Texture t : layers) {
            if (t != null) t.dispose();
        }
    }
}
