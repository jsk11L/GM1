package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;
    private PingBall ball;
    private Paddle pad;
    private ArrayList<Block> blocks;
    private int vidas;
    private int puntaje;
    private int nivel;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);
        nivel = 1;
        vidas = 3;
        puntaje = 0;
        blocks = new ArrayList<>();

        shape = new ShapeRenderer();
        ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 20, 5, 7, true);
        pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
        crearBloques(2 + nivel);
    }

    private void crearBloques(int filas) {
        blocks.clear();
        int blockWidth = 70;
        int blockHeight = 26;
        int y = Gdx.graphics.getHeight() - 30;
        for (int cont = 0; cont < filas; cont++) {
            y -= blockHeight + 10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }

    private void dibujaTextos() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Puntos: " + puntaje, 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 10);
        batch.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        pad.draw(shape);
        if (ball.isEstaQuieto()) {
            ball.setXY(pad.getX() + pad.getWidth() / 2 - ball.getWidth() / 2, pad.getY() + pad.getHeight() + 1);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
        } else {
            ball.update();
        }

        if (ball.getY() < 0) {
            vidas--;
            ball.setEstaQuieto(true);
            if (vidas <= 0) {
                // Reset game
                vidas = 3;
                nivel = 1;
                puntaje = 0;
                crearBloques(2 + nivel);
            }
        }

        if (blocks.size() == 0) {
            nivel++;
            crearBloques(2 + nivel);
            ball.setEstaQuieto(true);
        }

        for (Block b : blocks) {
            b.draw(shape);
            ball.checkCollision(b);
        }

        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (b.isDestroyed()) {
                puntaje++;
                blocks.remove(b);
                i--;
            }
        }

        ball.checkCollision(pad);
        ball.draw(shape);

        shape.end();

        dibujaTextos();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pad.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pad.moveRight();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shape.dispose();
    }
}
