package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
public class Block extends GameObject {
    private boolean destroyed;

    public Block(int x, int y, int width, int height) {
        super(x, y, width, height, new Color(0.1f + new Random(x + y).nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 1));
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(this.color);
            shape.rect(x, y, width, height);
        }
    }
}
