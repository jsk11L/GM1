package com.mygdx.game.Template;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Game.PingBall;

public class HardLevel extends LevelTemplate {
    public HardLevel(BlockManager blockManager, PingBall ball) {
        super(blockManager, ball);
        this.dificultad = "D";
    }

    @Override
    public void initializeLevel() {
        ball.setXSpeed(6);
        ball.setYSpeed(8);
    }

    @Override
    protected int getLevelDifficulty() {
        return 5;
    }

    @Override
    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty(), BlockManager.BlockType.HARD);
    }
}
