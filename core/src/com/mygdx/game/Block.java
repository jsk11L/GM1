package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
public class Block extends GameObject implements Drawable {
    private Color color;
    private boolean destroyed;

    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
        destroyed = false;
        Random r = new Random(x + y);
        color = new Color(0.1f + r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    // Dentro de la clase Block, el método draw podría verse así:
    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(this.color); // Asegúrate de que 'color' esté definido y sea visible
        shape.rect(getX(), getY(), getWidth(), getHeight());
    }
}