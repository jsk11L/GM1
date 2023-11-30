package com.mygdx.game.Template;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Game.PingBall;

public class EasyLevel extends LevelTemplate {
    public EasyLevel(BlockManager blockManager, PingBall ball) {
        super(blockManager, ball);
        this.dificultad = "F";
    }

    @Override
    public void initializeLevel() {
        ball.setXSpeed(4);
        ball.setYSpeed(6);
    }

    @Override
    protected int getLevelDifficulty() {
        return 3; // Menos bloques para un nivel fácil
    }

    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty(), BlockManager.BlockType.NORMAL); // Solo bloques normales
    }
}

