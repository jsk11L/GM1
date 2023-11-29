package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HardBlock extends Block {
    public HardBlock(int x, int y, int width, int height) {
        super(x, y, width, height, Color.RED);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!isDestroyed()) {
            shape.setColor(getColor());
            shape.rect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
