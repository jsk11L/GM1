package com.mygdx.game.Strategy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Game.Paddle;
import com.mygdx.game.Game.PingBall;

public class PowerUp {
    private PowerUpType type;
    private Rectangle hitbox; // Usa la clase Rectangle para la detección de colisiones
    private static final int WIDTH = 30; // Ajusta según tus necesidades
    private static final int HEIGHT = 30;
    private float speed = 100;

    public BallBehavior getBehavior() {
        switch (type) {
            case FAST:
                return new FastBehavior();
            case SLOW:
                return new SlowBehavior();
        }
        return null;
    }

    public enum PowerUpType {
        FAST,
        SLOW
    }

    public PowerUp(PowerUpType type, float x, float y) {
        this.type = type;
        this.hitbox = new Rectangle(x, y, WIDTH, HEIGHT); // Define WIDTH y HEIGHT
    }

    public PowerUpType getType() {
        return type;
    }

    public void update(float deltaTime) {
        hitbox.y -= speed * deltaTime;
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(Color.YELLOW); // O cualquier color que quieras
        shape.rect(hitbox.x, hitbox.y, WIDTH, HEIGHT);
    }

    public boolean collidesWithPaddle(Paddle paddle) {
        return hitbox.overlaps(paddle.getHitbox()); // Asumiendo que Paddle tiene un método getHitbox()
    }

    // Getters y otros métodos necesarios...
}

