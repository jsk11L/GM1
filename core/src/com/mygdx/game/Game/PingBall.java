package com.mygdx.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Blocks.Block;
import com.mygdx.game.Drawable;
import com.mygdx.game.Singleton.ResourceManager;
import com.mygdx.game.Strategy.BallBehavior;

public class PingBall extends GameObject implements Drawable {
    private int xSpeed, ySpeed;
    private final int size;
    private boolean estaQuieto;
    private BallBehavior defaultBehavior;
    private BallBehavior currentBehavior;
    private float powerUpTimeLeft = 0;

    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
        super(x, y, size, size, Color.WHITE);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.estaQuieto = iniciaQuieto;
    }

    public void setNormalBehavior(BallBehavior normalBehavior) {
        this.defaultBehavior = normalBehavior;
    }

    public void setBehavior(BallBehavior behavior) {
        this.currentBehavior = behavior;
        behavior.apply(this);
    }

    public void setPowerUp(BallBehavior behavior, float duration, Color color) {
        setBehavior(behavior);
        setColor(color);
        powerUpTimeLeft = duration;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setInitPos(Paddle paddle){
        setXY(paddle.getX() + paddle.getWidth() / 2 - getWidth() / 2, paddle.getY() + paddle.getHeight());
    }

    public void setEstaQuieto(boolean estaQuieto){
        this.estaQuieto = estaQuieto;
    }

    public boolean getEstaQuieto(){
        return estaQuieto;
    }

    public void setXSpeed(int xSpeed){
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed){
        this.ySpeed = ySpeed;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(this.color);
        shape.circle(x, y, (float) size / 2);
    }

    public void update() {
        if (!estaQuieto) {
            x += xSpeed;
            y += ySpeed;

            if (x - size / 2 < 0 || x + size / 2 > Gdx.graphics.getWidth()) {
                ResourceManager.getInstance().playWallHitSound();
                xSpeed = -xSpeed;
            }
            if (y + size / 2 > Gdx.graphics.getHeight()) {
                ResourceManager.getInstance().playWallHitSound();
                ySpeed = -ySpeed;
            }
        }

        if (powerUpTimeLeft > 0) {
            powerUpTimeLeft -= Gdx.graphics.getDeltaTime();
            if (powerUpTimeLeft <= 0) {
                setBehavior(defaultBehavior);
            }
        }
    }

    public void checkCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            if(!getEstaQuieto()) ResourceManager.getInstance().playPaddleHitSound();
            ySpeed = -ySpeed;
            y = paddle.getY() + paddle.getHeight() + size / 2;
        }
    }

    public boolean collidesWith(Paddle paddle) {
        boolean intersectaX = (paddle.getX() + paddle.getWidth() >= x - size / 2) && (paddle.getX() <= x + size / 2);
        boolean intersectaY = paddle.getY() < y + size / 2 && paddle.getY() + paddle.getHeight() > y - size / 2;
        return intersectaX && intersectaY;
    }

    public boolean collidesWith(Block block) {
        boolean intersectaX = (block.getX() + block.getWidth() >= x - size / 2) && (block.getX() <= x + size / 2);
        boolean intersectaY = (block.getY() + block.getHeight() >= y - size / 2) && (block.getY() <= y + size / 2);
        return intersectaX && intersectaY;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed(){
        return ySpeed;
    }

    public void reflect() {
        ySpeed = -ySpeed;
    }

    public void resetToNormalBehavior() {
        setBehavior(defaultBehavior);
        setColor(Color.WHITE);
    }
}



