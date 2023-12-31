package com.mygdx.game.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Drawable;
public class Paddle extends GameObject implements Drawable {

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, Color.BLUE);
    }

    public void moveLeft() {
        if (this.x > 0) {
            this.x -= 15;
        }
    }

    public void moveRight() {
        if (this.x + this.width < Gdx.graphics.getWidth()) {
            this.x += 15;
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(this.color);
        shape.rect(x, y, width, height);
    }
}


