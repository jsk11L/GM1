package com.mygdx.game.Singleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {

    private static ResourceManager instance;

    private Music backgroundMusic;
    private Sound paddleHitSound;
    private Sound blockHitSound;
    private Sound wallHitSound;
    private Texture menuBackground;

    private ResourceManager() {}

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void loadResources() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/nocopy.mp3"));
        paddleHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/paddle_hit.mp3"));
        blockHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/block_hit.mp3"));
        wallHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wall_hit.mp3"));
        menuBackground = new Texture(Gdx.files.internal("images/badlogic.jpg"));
    }

    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public Sound getPaddleHitSound() {
        return paddleHitSound;
    }

    public Sound getBlockHitSound() {
        return blockHitSound;
    }

    public Sound getWallHitSound() {
        return blockHitSound;
    }

    public Texture getMenuBackground() {
        return menuBackground;
    }

    public void dispose() {
        wallHitSound.dispose();
        backgroundMusic.dispose();
        paddleHitSound.dispose();
        blockHitSound.dispose();
        menuBackground.dispose();
    }
}
