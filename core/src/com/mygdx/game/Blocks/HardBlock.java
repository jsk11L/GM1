package com.mygdx.game.Blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HardBlock extends Block {
    private int hitsLeft = 2;
    public HardBlock(int x, int y, int width, int height) {
        super(x, y, width, height, Color.PURPLE);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!getDestroyed()) {
            shape.setColor(getColor());
            shape.rect(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        if (hitsLeft > 0) {
            color = Color.PINK;
            hitsLeft--;
            if (hitsLeft == 0) {
                super.setDestroyed(true);
            }
        }
    }
}
