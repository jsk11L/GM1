package com.mygdx.game.Template;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Game.PingBall;
import com.mygdx.game.Strategy.BallBehavior;
import com.mygdx.game.Strategy.NormalBehavior;

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
    public BallBehavior getNormalBallBehavior() {
        return new NormalBehavior(6,8);
    }


    @Override
    public int getLevelDifficulty() {
        return 5;
    }

    @Override
    protected void setupBlocks() {
        blockManager.crearBloques(getLevelDifficulty(), BlockManager.BlockType.HARD);
    }
}
