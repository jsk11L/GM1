package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }
    
    protected void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    
    protected void setHeight(int height) {
        this.height = height;
    }

    public abstract void draw(ShapeRenderer shape);
}