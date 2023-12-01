package com.mygdx.game.Singleton;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static SoundManager instance;

    private boolean isSoundEnabled;
    private boolean isMusicEnabled;

    private SoundManager() {
        // Constructor privado
        // Por defecto, el sonido está habilitado
        isSoundEnabled = true;
        isMusicEnabled = true;
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void toggleSound() {
        isSoundEnabled = !isSoundEnabled;
    }

    public void toggleMusic() {
        isMusicEnabled = !isMusicEnabled;
    }

    public void playSound(Sound sound) {
        if (isSoundEnabled) {
            sound.play();
        }
    }

    public void playMusic(Music music) {
        if (isMusicEnabled) {
            if (!music.isPlaying()) {
                music.play();
            }
        } else {
            music.stop();
        }
    }
}
