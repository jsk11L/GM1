package com.mygdx.game.Template;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Game.PingBall;
import com.mygdx.game.Strategy.BallBehavior;
import com.mygdx.game.Strategy.NormalBehavior;

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
    public BallBehavior getNormalBallBehavior() {
        return new NormalBehavior(5,7);
    }

    @Override
    public int getLevelDifficulty() {
        return 4; // 4 filas de bloques
    }

    @Override
    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty(), BlockManager.BlockType.MIXED); // Mezcla de bloques normales y duros
    }
}
