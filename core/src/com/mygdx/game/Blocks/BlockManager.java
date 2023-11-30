package com.mygdx.game.Blocks;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game.GameManager;
import com.mygdx.game.Game.PingBall;
import com.mygdx.game.Singleton.ResourceManager;

@SuppressWarnings("ALL")
public class BlockManager {
    private ArrayList<Block> blocks;
    private ResourceManager resourceManager;

    public BlockManager() {
        blocks = new ArrayList<>();
    }

    public enum BlockType {
        NORMAL,
        HARD,
        MIXED
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void drawBlocks(ShapeRenderer shape) {
        for (Block block : blocks) {
            if (!block.getDestroyed()) {
                block.draw(shape);
            }
        }
    }

    public void checkCollision(PingBall ball, GameManager game) {
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (!block.getDestroyed() && ball.collidesWith(block)) {
                ResourceManager.getInstance().playBlockHitSound();
                ball.reflect();
                block.setDestroyed(true);
                if (block.getDestroyed()) {
                    game.incrementScore();
                    blocks.remove(i);
                    i--;
                }
            }
        }
    }

    public void crearBloques(int filas, BlockType type) {
        clearBlocks();
        int blockWidth = 70;
        int blockHeight = 16;
        int y = Gdx.graphics.getHeight() - 30;

        for (int fila = 0; fila < filas; fila++) {
            y -= blockHeight + 10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                switch (type) {
                    case NORMAL:
                        addBlock(new RegularBlock(x, y, blockWidth, blockHeight));
                        break;
                    case HARD:
                        addBlock(new HardBlock(x, y, blockWidth, blockHeight));
                        break;
                    case MIXED:
                        // Para MIXED, alterna entre RegularBlock y HardBlock
                        Block block = (fila % 2 == 0) ? new RegularBlock(x, y, blockWidth, blockHeight)
                                : new HardBlock(x, y, blockWidth, blockHeight);
                        addBlock(block);
                        break;
                }
            }
        }
    }

    public void clearBlocks() {
        blocks.clear();
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }
}
