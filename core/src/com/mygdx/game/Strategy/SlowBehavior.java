package com.mygdx.game.Strategy;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Game.PingBall;

public class SlowBehavior implements BallBehavior {
    @Override
    public void apply(PingBall ball) {
        ball.setColor(Color.BLUE);
        ball.setXSpeed((int) (ball.getXSpeed() -2));
        ball.setYSpeed((int) (ball.getYSpeed() -2));
    }
}
