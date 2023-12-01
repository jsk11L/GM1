package com.mygdx.game.Builder;

import com.mygdx.game.Blocks.BlockManager.BlockType;
import com.mygdx.game.Template.LevelTemplate;

public interface LevelBuilder {
    LevelBuilder reset();
    LevelBuilder setDifficulty(int difficulty);
    LevelBuilder setBlockType(BlockType type);
    LevelBuilder addBlocks(int filas);
    LevelTemplate build();
}

