package com.mygdx.game.Template;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Game.PingBall;
import com.mygdx.game.Strategy.BallBehavior;
import com.mygdx.game.Strategy.NormalBehavior;

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
    public BallBehavior getNormalBallBehavior() {
        return new NormalBehavior(4,4);
    }

    @Override
    public int getLevelDifficulty() {
        return 3; // Menos bloques para un nivel fácil
    }

    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty(), BlockManager.BlockType.NORMAL); // Solo bloques normales
    }
}

