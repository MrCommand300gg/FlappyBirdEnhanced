package ru.samsung.gamestudio.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import ru.samsung.gamestudio.settings.GameSettings;

public class AudioManager {
    Sound jumpSound;
    Sound coinSound;
    Sound hitSound;
    Sound deathSound;
    Sound newRecordSound;
    Sound buttonSound;
    Sound achievementSound;
    Sound powerUpSound;

    Music menuMusic;
    Music gameMusic;

    GameSettings settings;

    public AudioManager() {
        settings = GameSettings.getInstance();
        loadSounds();
    }

    private void loadSounds() {
        // Sound files not available - commented out
        // try {
        //     jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        //     coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
        //     hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
        //     deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death.wav"));
        //     newRecordSound = Gdx.audio.newSound(Gdx.files.internal("sounds/new_record.wav"));
        //     buttonSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
        //     achievementSound = Gdx.audio.newSound(Gdx.files.internal("sounds/achievement.wav"));
        //     powerUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup.wav"));
        //     menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_music.mp3"));
        //     gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game_music.mp3"));
        // } catch (Exception e) {
        // }
    }

    public void playJump() {
        if (jumpSound != null) jumpSound.play(settings.sfxVolume);
    }

    public void playCoin() {
        if (coinSound != null) coinSound.play(settings.sfxVolume);
    }

    public void playHit() {
        if (hitSound != null) hitSound.play(settings.sfxVolume);
    }

    public void playDeath() {
        if (deathSound != null) deathSound.play(settings.sfxVolume);
    }

    public void playNewRecord() {
        if (newRecordSound != null) newRecordSound.play(settings.sfxVolume);
    }

    public void playButton() {
        if (buttonSound != null) buttonSound.play(settings.sfxVolume);
    }

    public void playAchievement() {
        if (achievementSound != null) achievementSound.play(settings.sfxVolume);
    }

    public void playPowerUp() {
        if (powerUpSound != null) powerUpSound.play(settings.sfxVolume);
    }

    public void playMenuMusic() {
        if (menuMusic != null) {
            menuMusic.setVolume(settings.musicVolume);
            menuMusic.setLooping(true);
            menuMusic.play();
        }
    }

    public void playGameMusic() {
        if (gameMusic != null) {
            gameMusic.setVolume(settings.musicVolume);
            gameMusic.setLooping(true);
            gameMusic.play();
        }
    }

    public void stopMusic() {
        if (menuMusic != null) menuMusic.stop();
        if (gameMusic != null) gameMusic.stop();
    }

    public void setMusicVolume(float volume) {
        settings.musicVolume = volume;
        if (menuMusic != null) menuMusic.setVolume(volume);
        if (gameMusic != null) gameMusic.setVolume(volume);
    }

    public void setSfxVolume(float volume) {
        settings.sfxVolume = volume;
    }

    public void dispose() {
        if (jumpSound != null) jumpSound.dispose();
        if (coinSound != null) coinSound.dispose();
        if (hitSound != null) hitSound.dispose();
        if (deathSound != null) deathSound.dispose();
        if (newRecordSound != null) newRecordSound.dispose();
        if (buttonSound != null) buttonSound.dispose();
        if (achievementSound != null) achievementSound.dispose();
        if (powerUpSound != null) powerUpSound.dispose();
        if (menuMusic != null) menuMusic.dispose();
        if (gameMusic != null) gameMusic.dispose();
    }
}
