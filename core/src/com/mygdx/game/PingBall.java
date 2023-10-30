package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Drawable {
    private int xSpeed, ySpeed;
    private int size;
    private boolean estaQuieto;

    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
        super(x, y, size, size, Color.WHITE); // size is used for both width and height as the ball is a circle
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.estaQuieto = iniciaQuieto;
    }

    public boolean isEstaQuieto() {
        return estaQuieto;
    }

    public void setEstaQuieto(boolean estaQuieto) {
        this.estaQuieto = estaQuieto;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getY() {return y;}

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(this.color);
        shape.circle(x, y, width / 2); // width / 2 as it's the radius for the circle
    }

    @Override
    public void update() {
        if (!estaQuieto) {
            x += xSpeed;
            y += ySpeed;
            if (x - width / 2 < 0 || x + width / 2 > Gdx.graphics.getWidth()) {
                xSpeed = -xSpeed;
            }
            if (y + height / 2 > Gdx.graphics.getHeight()) {
                ySpeed = -ySpeed;
            }
        }
    }

    public void checkCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            color = Color.GREEN;
            ySpeed = -ySpeed;
        }
        else{
            color = Color.WHITE;
        }
    }
    private boolean collidesWith(Paddle pp) {

        boolean intersectaX = (pp.getX() + pp.getWidth() >= x-size) && (pp.getX() <= x+size);
        boolean intersectaY = (pp.getY() + pp.getHeight() >= y-size) && (pp.getY() <= y+size);
        return intersectaX && intersectaY;
    }

    public void checkCollision(Block block) {
        if(collidesWith(block)){
            ySpeed = - ySpeed;
            block.setDestroyed(true);
        }
    }
    private boolean collidesWith(Block bb) {

        boolean intersectaX = (bb.x + bb.width >= x-size) && (bb.x <= x+size);
        boolean intersectaY = (bb.y + bb.height >= y-size) && (bb.y <= y+size);
        return intersectaX && intersectaY;
    }

    // Collision methods remain unchanged
}


