
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Drawable {
    private int xSpeed;
    private int ySpeed;
    private Color color;
    private boolean estaQuieto;

    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
        super(x, y, size, size);  // size is used for both width and height here
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = Color.WHITE;
        this.estaQuieto = iniciaQuieto;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(getX(), getY(), getWidth() / 2);
    }
    
    public void update() {
        if (!estaQuieto) {
            setX(getX() + xSpeed);
            setY(getY() + ySpeed);
            // Collision with the walls
            if (getX() < 0 || getX() > Gdx.graphics.getWidth()) {
                xSpeed = -xSpeed;
            }
            if (getY() > Gdx.graphics.getHeight()) {
                ySpeed = -ySpeed;
            }
        }
    }

    public void reset(int newX, int newY) {
        setX(newX);
        setY(newY);
        estaQuieto = true;
    }

    public boolean checkCollision(GameObject other) {
        // Collision detection logic goes here
        // This method should return true if a collision is detected
        return false;
    }

    public boolean isStationary() {
        return estaQuieto;
    }

    public void setPosition(int newX, int newY) {
        setX(newX);
        setY(newY);
    }

    public void setStationary(boolean stationary) {
        this.estaQuieto = stationary;
    }
}
