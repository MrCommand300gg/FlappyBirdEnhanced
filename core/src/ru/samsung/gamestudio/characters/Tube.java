package ru.samsung.gamestudio.characters;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;
import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

public class Tube {

    Texture textureUpperTube;
    Texture textureDownTube;

    Random random;

    int x, gapY;
    int distanceBetweenTubes;

    boolean isPointReceived;

    int speed;
    final int width = 120;
    final int height = 450;
    int gapHeight = 350;
    int padding = 50;

    public Tube(int tubeCount, int tubeIdx, int speed, int gapHeight) {
        random = new Random();
        this.speed = speed;
        this.gapHeight = gapHeight;

        gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        distanceBetweenTubes = (SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        textureUpperTube = new Texture("tubes/tube_flipped.png");
        textureDownTube = new Texture("tubes/tube.png");

        isPointReceived = false;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    public void setGapHeight(int newGapHeight) {
        this.gapHeight = newGapHeight;
    }

    public void draw(Batch batch) {
        batch.draw(textureUpperTube, x, gapY + gapHeight / 2, width, height);
        batch.draw(textureDownTube, x, gapY - gapHeight / 2 - height, width, height);
    }

    public void move() {
        x -= speed;
        if (x < -width) {
            isPointReceived = false;
            x = SCR_WIDTH + distanceBetweenTubes;
            gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        }
    }

    public boolean isHit(Bird bird) {
        if (bird.shielded) return false;

        // коллизия нижней трубы
        if (bird.y <= gapY - gapHeight / 2 && bird.x + bird.width >= x && bird.x <= x + width)
            return true;
        // коллизия верхней трубы
        if (bird.y + bird.height >= gapY + gapHeight / 2 && bird.x + bird.width >= x && bird.x <= x + width)
            return true;

        return false;
    }

    public boolean needAddPoint(Bird bird) {
        return !isPointReceived && bird.x > x + width;
    }

    public void setPointReceived() {
        isPointReceived = true;
    }

    public void dispose() {
        textureDownTube.dispose();
        textureUpperTube.dispose();
    }
}