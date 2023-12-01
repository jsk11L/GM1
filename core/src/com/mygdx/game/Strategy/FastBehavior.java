package com.mygdx.game.Strategy;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Game.PingBall;

public class FastBehavior implements BallBehavior {
    @Override
    public void apply(PingBall ball) {
        ball.setColor(Color.RED);
        ball.setXSpeed(ball.getXSpeed() +2);
        ball.setYSpeed(ball.getYSpeed() +2);
    }
}
