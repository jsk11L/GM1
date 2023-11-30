package com.mygdx.game.Template;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Game.PingBall;

public class MediumLevel extends LevelTemplate {
    public MediumLevel(BlockManager blockManager, PingBall ball) {
        super(blockManager, ball);
        this.dificultad = "M";
    }

    @Override
    public void initializeLevel() {
        ball.setXSpeed(5);
        ball.setYSpeed(7);
    }

    @Override
    protected int getLevelDifficulty() {
        return 4; // 4 filas de bloques
    }

    @Override
    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty(), BlockManager.BlockType.MIXED); // Mezcla de bloques normales y duros
    }
}
