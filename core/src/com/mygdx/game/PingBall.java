
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
        // Get the ball's bounding box
        int ballLeft = getX();
        int ballRight = getX() + getWidth();
        int ballTop = getY() + getHeight();
        int ballBottom = getY();

        // Get the other object's bounding box
        int otherLeft = other.getX();
        int otherRight = other.getX() + other.getWidth();
        int otherTop = other.getY() + other.getHeight();
        int otherBottom = other.getY();

        // Check if the bounding boxes overlap
        if (ballRight < otherLeft || ballLeft > otherRight) return false; // No overlap on the x-axis
        if (ballTop < otherBottom || ballBottom > otherTop) return false; // No overlap on the y-axis

        // If we get here, there is an overlap on both axes, so there is a collision
        return true;
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
