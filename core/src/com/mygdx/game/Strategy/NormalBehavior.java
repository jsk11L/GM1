package com.mygdx.game.Strategy;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Game.PingBall;

public class NormalBehavior implements BallBehavior {
    private int normalXSpeed;
    private int normalYSpeed;

    public NormalBehavior(int normalXSpeed, int normalYSpeed) {
        this.normalXSpeed = normalXSpeed;
        this.normalYSpeed = normalYSpeed;
    }

    @Override
    public void apply(PingBall ball) {
        ball.setColor(Color.WHITE);
        ball.setXSpeed(normalXSpeed);
        ball.setYSpeed(normalYSpeed);
    }
}