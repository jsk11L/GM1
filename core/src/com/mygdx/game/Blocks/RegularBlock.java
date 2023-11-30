package com.mygdx.game.Blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Blocks.Block;

public class RegularBlock extends Block {
    public RegularBlock(int x, int y, int width, int height) {
        super(x, y, width, height, Color.PINK);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!getDestroyed()) {
            shape.setColor(getColor());
            shape.rect(getX(), getY(), getWidth(), getHeight());
        }
    }
}