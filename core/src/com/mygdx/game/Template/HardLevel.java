package com.mygdx.game.Template;
import com.mygdx.game.Blocks.BlockManager;

import com.mygdx.game.Game.LevelTemplate;

public class HardLevel extends LevelTemplate {
    public HardLevel(BlockManager blockManager) {
        super(blockManager);
    }

    @Override
    public void initializeLevel() {
        // Configuraciones específicas para un nivel difícil
    }

    @Override
    protected int getLevelDifficulty() {
        return 3; // Más bloques para un nivel difícil
    }
}