package ru.samsung.gamestudio.components;

import static ru.samsung.gamestudio.MyGdxGame.SCR_HEIGHT;
import static ru.samsung.gamestudio.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextButton {

    BitmapFont font;

    String text;
    Texture texture;

    int x, y;
    int textX, textY;
    int buttonWidth, buttonHeight;
    int textWidth, textHeight;

    public TextButton(int x, int y, String text) {
        this.text = text;
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);

        GlyphLayout gl = new GlyphLayout(font, text);
        textWidth = (int) gl.width;
        textHeight = (int) gl.height;

        texture = new Texture("button_bg.png");
        buttonWidth = (int) (SCR_WIDTH * 0.25f);
        buttonHeight = (int) (SCR_HEIGHT * 0.08f);

        textX = x + (buttonWidth - textWidth) / 2;
        textY = y + (buttonHeight + textHeight) / 2;
    }

    public boolean isHit(int tx, int ty) {
        return tx >= x && tx <= x + buttonWidth
                && ty >= y && ty <= y + buttonHeight;
    }

    public void draw(Batch batch) {
        batch.draw(texture, x, y, buttonWidth, buttonHeight);
        font.draw(batch, text, textX, textY);
    }

    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}
