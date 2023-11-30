package com.mygdx.game.Blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Drawable;
import com.mygdx.game.Game.GameObject;

public abstract class Block extends GameObject implements Drawable {
    private boolean destroyed;

    public Block(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        this.destroyed = false;
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void draw(ShapeRenderer shape){}
}

