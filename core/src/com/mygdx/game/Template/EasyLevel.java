package com.mygdx.game.Template;
import com.mygdx.game.Blocks.BlockManager;

import com.mygdx.game.Game.LevelTemplate;

public class EasyLevel extends LevelTemplate {
    public EasyLevel(BlockManager blockManager) {
        super(blockManager);
    }

    @Override
    public void initializeLevel() {
        // Configuraciones específicas para un nivel fácil
    }

    @Override
    protected int getLevelDifficulty() {
        return 1; // Menos bloques para un nivel fácil
    }
}

