package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    private boolean gameOver;
    private float gameOverTimer;
    private float fadeValue;

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
        gameOver = false;
        gameOverTimer = 0;
        fadeValue = 0;
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

        if (!gameOver) {
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
                    gameOver = true;
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

        if (gameOver) {
            if (fadeValue < 1) {
                fadeValue += Gdx.graphics.getDeltaTime() / 3; // 3 seconds fade in
            } else {
                fadeValue = 1;
            }
            drawGameOverScreen();
            if (fadeValue == 1 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                resetGame();
            }
        }
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

    private void drawGameOverScreen() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(1, 0, 0, fadeValue); // Red with fade
        shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shape.end();

        batch.begin();
        // Restablece el color a negro para "GAME OVER"
        font.setColor(0, 0, 0, fadeValue); // Black with fade
        GlyphLayout gameOverLayout = new GlyphLayout(font, "GAME OVER");
        float gameOverX = (Gdx.graphics.getWidth() - gameOverLayout.width) / 2;
        float gameOverY = (Gdx.graphics.getHeight() + gameOverLayout.height) / 2;
        font.draw(batch, gameOverLayout, gameOverX, gameOverY);
        if (fadeValue == 1) {
            gameOverTimer += Gdx.graphics.getDeltaTime();
            if (gameOverTimer > 2) { // Wait for 2 seconds
                font.getData().setScale(1.5f); // Smaller font size
                GlyphLayout continueLayout = new GlyphLayout(font, "PRESS TO CONTINUE");
                float continueX = (Gdx.graphics.getWidth() - continueLayout.width) / 2;
                float continueY = Gdx.graphics.getHeight() / 4f + continueLayout.height / 2;
                font.setColor(1, 1, 1, 1); // White
                font.draw(batch, continueLayout, continueX, continueY);
                font.getData().setScale(3, 2); // Reset to original scale
            }
        }
        batch.end();
    }


    private void resetGame() {
        // Reset all game variables and states
        vidas = 3;
        puntaje = 0;
        nivel = 1;
        gameOver = false;
        fadeValue = 0;
        gameOverTimer = 0;
        crearBloques(2 + nivel);
        ball.setEstaQuieto(true);
    }
}
