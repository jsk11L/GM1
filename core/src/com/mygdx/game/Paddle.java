package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Drawable;

public class Paddle extends GameObject implements Drawable {
    private float speed; // Agrega esta propiedad para controlar la velocidad de la barra

    public Paddle(int x, int y, int width, int height, float speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    // Agrega los getters y setters para speed si es necesario
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // Método para mover la barra hacia la izquierda
    public void moveLeft() {
        if (getX() > 0) { // Asegúrate de que la barra no se mueva fuera de la pantalla
            setX((int) (getX() - speed));
        }
    }

    // Método para mover la barra hacia la derecha
    public void moveRight() {
        if (getX() + getWidth() < Gdx.graphics.getWidth()) { // Asegúrate de que la barra no se mueva fuera de la pantalla
            setX((int) (getX() + speed));
        }
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(Color.WHITE);
        shape.rect(getX(), getY(), getWidth(), getHeight());
    }

    // Asegúrate de que los otros métodos necesarios estén implementados...
}
