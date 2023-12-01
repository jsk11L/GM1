package com.mygdx.game.Template;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Game.PingBall;
import com.mygdx.game.Strategy.BallBehavior;

public abstract class LevelTemplate {
    protected BlockManager blockManager;
    protected PingBall ball;
    protected String dificultad;

    public LevelTemplate(BlockManager blockManager, PingBall ball) {
        this.blockManager = blockManager;
        this.ball = ball;
    }

    // Template method
    public final void playLevel() {
        initializeLevel();
        setupBlocks();
    }

    public String getDificultad() {
        return dificultad;
    }

    public boolean isOver(){
        return blockManager.isEmpty();
    }

    public abstract void initializeLevel();

    public abstract BallBehavior getNormalBallBehavior();

    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty(), BlockManager.BlockType.NORMAL); // Asumiendo un tipo por defecto
    }

    public int getLevelDifficulty() {
        return 3;
    }
}
