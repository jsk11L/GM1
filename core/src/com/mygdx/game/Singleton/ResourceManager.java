package com.mygdx.game.Singleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.SoundManager;

public class ResourceManager {

    private static ResourceManager instance;

    private Music backgroundMusic;
    private Sound paddleHitSound;
    private Sound blockHitSound;
    private Sound wallHitSound;
    private Texture menuBackground;

    private ResourceManager() {
        // Constructor privado
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void loadResources() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/nocopy.mp3"));
        paddleHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/paddle_hit.mp3"));
        wallHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wall_hit.mp3"));
        blockHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/block_hit.mp3"));
        menuBackground = new Texture(Gdx.files.internal("images/badlogic.jpg"));
    }

    public void playBackgroundMusic() {
        SoundManager.getInstance().playMusic(backgroundMusic);
    }

    public void playPaddleHitSound() {
        SoundManager.getInstance().playSound(paddleHitSound);
    }

    public void playBlockHitSound() {
        SoundManager.getInstance().playSound(blockHitSound);
    }

    public void playWallHitSound() {
        SoundManager.getInstance().playSound(wallHitSound);
    }

    public Texture getMenuBackground() {
        return menuBackground;
    }

    public void dispose() {
        backgroundMusic.dispose();
        paddleHitSound.dispose();
        blockHitSound.dispose();
        menuBackground.dispose();
    }
}
