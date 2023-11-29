package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RegularBlock extends Block {
    public RegularBlock(int x, int y, int width, int height) {
        super(x, y, width, height, Color.BLUE);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!isDestroyed()) {
            shape.setColor(getColor());
            shape.rect(getX(), getY(), getWidth(), getHeight());
        }
    }
}