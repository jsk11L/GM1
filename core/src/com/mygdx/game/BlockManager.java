package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BlockManager {
    private ArrayList<Block> blocks;

    public BlockManager() {
        blocks = new ArrayList<>();
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void drawBlocks(ShapeRenderer shape) {
        for (Block block : blocks) {
            block.draw(shape);
        }
    }

    public void checkCollision(PingBall ball) {
        for (Block block : blocks) {
            if (block.getDestroyed() && ball.collidesWith(block)) {
                ball.reflect();
                block.setDestroyed(true);
            }
        }
    }

    public void clearBlocks() {
        blocks.clear();
    }



    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    // Agregar más métodos según sea necesario...
}
