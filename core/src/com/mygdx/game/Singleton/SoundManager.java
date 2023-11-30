package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static SoundManager instance;

    private boolean isSoundEnabled;

    private SoundManager() {
        // Constructor privado
        // Por defecto, el sonido está habilitado
        isSoundEnabled = true;
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

    public boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    public void playSound(Sound sound) {
        if (isSoundEnabled) {
            sound.play();
        }
    }

    public void playMusic(Music music) {
        if (isSoundEnabled) {
            if (!music.isPlaying()) {
                music.play();
            }
        } else {
            music.stop();
        }
    }
}
