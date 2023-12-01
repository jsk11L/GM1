package com.mygdx.game.Builder;

import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Blocks.BlockManager.BlockType;
import com.mygdx.game.Template.LevelTemplate;
import com.mygdx.game.Game.PingBall;
import com.mygdx.game.Template.EasyLevel;
import com.mygdx.game.Template.HardLevel;
import com.mygdx.game.Template.MediumLevel;

public class ConcreteLevelBuilder implements LevelBuilder {
    private int difficulty;
    private BlockType blockType;
    private BlockManager blockManager;
    private PingBall ball;

    public ConcreteLevelBuilder(BlockManager blockManager, PingBall ball) {
        this.blockManager = blockManager;
        this.ball = ball;
    }

    @Override
    public LevelBuilder reset() {
        this.difficulty = 1;
        this.blockType = BlockType.NORMAL;
        return this;
    }

    @Override
    public LevelBuilder setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    @Override
    public LevelBuilder setBlockType(BlockType type) {
        this.blockType = type;
        return this;
    }

    @Override
    public LevelBuilder addBlocks(int filas) {
        blockManager.crearBloques(filas, blockType);
        return this;
    }

    @Override
    public LevelTemplate build() {
        switch (difficulty) {
            case 1:
                return new EasyLevel(blockManager, ball);
            case 2:
                return new MediumLevel(blockManager, ball);
            case 3:
                return new HardLevel(blockManager, ball);
            default:
                return new EasyLevel(blockManager, ball);
        }
    }
}
