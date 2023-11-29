package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Drawable {
    private int xSpeed, ySpeed;
    private int size;
    private boolean estaQuieto;

    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
        super(x, y, size, size, Color.WHITE);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.estaQuieto = iniciaQuieto;
    }

    // Getters, setters y otros métodos...

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(this.color);
        shape.circle(x, y, size / 2);
    }

    @Override
    public void update() {
        if (!estaQuieto) {
            x += xSpeed;
            y += ySpeed;

            // Rebotar en los bordes de la pantalla
            if (x - size / 2 < 0 || x + size / 2 > Gdx.graphics.getWidth()) {
                xSpeed = -xSpeed;
            }
            if (y + size / 2 > Gdx.graphics.getHeight()) {
                ySpeed = -ySpeed;
            }
        }
    }

    public void checkCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            ySpeed = -ySpeed;
            // Ajusta la posición Y para evitar que la pelota se "pegue" al paddle
            y = paddle.getY() + paddle.getHeight() + size / 2;
        }
    }

    public void checkCollision(Block block) {
        if(collidesWith(block)){
            ySpeed = -ySpeed;
            block.setDestroyed(true);
        }
    }

    private boolean collidesWith(Paddle paddle) {
        boolean intersectaX = (paddle.getX() + paddle.getWidth() >= x - size / 2) && (paddle.getX() <= x + size / 2);
        boolean intersectaY = paddle.getY() < y + size / 2 && paddle.getY() + paddle.getHeight() > y - size / 2;
        return intersectaX && intersectaY;
    }

    private boolean collidesWith(Block block) {
        boolean intersectaX = (block.getX() + block.getWidth() >= x - size / 2) && (block.getX() <= x + size / 2);
        boolean intersectaY = (block.getY() + block.getHeight() >= y - size / 2) && (block.getY() <= y + size / 2);
        return intersectaX && intersectaY;
    }
}



