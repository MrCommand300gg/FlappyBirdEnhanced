package ru.samsung.gamestudio.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class GameAssetManager {
    public AssetManager manager;

    public static final AssetDescriptor<Texture> BIRD_0 = new AssetDescriptor<>("birdTiles/bird0.png", Texture.class);
    public static final AssetDescriptor<Texture> BIRD_1 = new AssetDescriptor<>("birdTiles/bird1.png", Texture.class);
    public static final AssetDescriptor<Texture> BIRD_2 = new AssetDescriptor<>("birdTiles/bird2.png", Texture.class);
    public static final AssetDescriptor<Texture> BG_GAME = new AssetDescriptor<>("backgrounds/game_bg.png", Texture.class);
    public static final AssetDescriptor<Texture> BG_MENU = new AssetDescriptor<>("backgrounds/menu_bg.jpg", Texture.class);
    public static final AssetDescriptor<Texture> BG_RESTART = new AssetDescriptor<>("backgrounds/restart_bg.png", Texture.class);
    public static final AssetDescriptor<Texture> TUBE = new AssetDescriptor<>("tubes/tube.png", Texture.class);
    public static final AssetDescriptor<Texture> TUBE_FLIPPED = new AssetDescriptor<>("tubes/tube_flipped.png", Texture.class);
    public static final AssetDescriptor<Texture> STAR = new AssetDescriptor<>("items/stars.png", Texture.class);
    public static final AssetDescriptor<Texture> BUTTON_BG = new AssetDescriptor<>("button_bg.png", Texture.class);
    public static final AssetDescriptor<Texture> PARTICLE = new AssetDescriptor<>("effects/particlepng.png", Texture.class);

    public static final AssetDescriptor<Sound> JUMP_SOUND = new AssetDescriptor<>("sounds/jump.wav", Sound.class);
    public static final AssetDescriptor<Sound> COIN_SOUND = new AssetDescriptor<>("sounds/coin.wav", Sound.class);
    public static final AssetDescriptor<Sound> HIT_SOUND = new AssetDescriptor<>("sounds/hit.wav", Sound.class);
    public static final AssetDescriptor<Sound> DEATH_SOUND = new AssetDescriptor<>("sounds/death.wav", Sound.class);
    public static final AssetDescriptor<Sound> NEW_RECORD_SOUND = new AssetDescriptor<>("sounds/new_record.wav", Sound.class);
    public static final AssetDescriptor<Sound> BUTTON_SOUND = new AssetDescriptor<>("sounds/button.wav", Sound.class);
    public static final AssetDescriptor<Sound> ACHIEVEMENT_SOUND = new AssetDescriptor<>("sounds/achievement.wav", Sound.class);
    public static final AssetDescriptor<Sound> POWERUP_SOUND = new AssetDescriptor<>("sounds/powerup.wav", Sound.class);

    public static final AssetDescriptor<Music> MENU_MUSIC = new AssetDescriptor<>("sounds/menu_music.mp3", Music.class);
    public static final AssetDescriptor<Music> GAME_MUSIC = new AssetDescriptor<>("sounds/game_music.mp3", Music.class);

    public GameAssetManager() {
        manager = new AssetManager();
    }

    public void loadAll() {
        manager.load(BIRD_0);
        manager.load(BIRD_1);
        manager.load(BIRD_2);
        manager.load(BG_GAME);
        manager.load(BG_MENU);
        manager.load(BG_RESTART);
        manager.load(TUBE);
        manager.load(TUBE_FLIPPED);
        manager.load(STAR);
        manager.load(BUTTON_BG);
        manager.load(PARTICLE);

        manager.load(JUMP_SOUND);
        manager.load(COIN_SOUND);
        manager.load(HIT_SOUND);
        manager.load(DEATH_SOUND);
        manager.load(NEW_RECORD_SOUND);
        manager.load(BUTTON_SOUND);
        manager.load(ACHIEVEMENT_SOUND);
        manager.load(POWERUP_SOUND);

        manager.load(MENU_MUSIC);
        manager.load(GAME_MUSIC);
    }

    public void finishLoading() {
        manager.finishLoading();
    }

    public void dispose() {
        manager.dispose();
    }
}
