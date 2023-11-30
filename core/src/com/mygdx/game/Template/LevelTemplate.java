package com.mygdx.game.Game;

import com.mygdx.game.Blocks.BlockManager;

public abstract class LevelTemplate {
    protected BlockManager blockManager;

    public LevelTemplate(BlockManager blockManager) {
        this.blockManager = blockManager;
    }

    // Template method
    public final void playLevel() {
        initializeLevel();
        setupBlocks();
    }

    public boolean isOver(){
        return blockManager.isEmpty();
    }

    protected abstract void initializeLevel();

    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty());
    }

    protected int getLevelDifficulty() {
        return 2;
    }
}
