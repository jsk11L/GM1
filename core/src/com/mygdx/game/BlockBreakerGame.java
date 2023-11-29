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

public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;
    private PingBall ball;
    private Paddle paddle;
    private BlockManager blockManager;
    private int vidas;
    private int puntaje;
    private int nivel;
    private boolean gameOver;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);
        shape = new ShapeRenderer();

        ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 20, 5, 7, true);
        paddle = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
        blockManager = new BlockManager();
        initGame();
    }

    private void initGame() {
        nivel = 1;
        vidas = 3;
        puntaje = 0;
        gameOver = false;
        crearBloques(2 + nivel);
    }

    private void crearBloques(int filas) {
        blockManager.clearBlocks();
        int blockWidth = 70;
        int blockHeight = 26;
        int y = Gdx.graphics.getHeight() - 30;
        for (int fila = 0; fila < filas; fila++) {
            y -= blockHeight + 10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blockManager.addBlock(new RegularBlock(x, y, blockWidth, blockHeight));
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameOver) {
            handleInput();
            ball.update();
            blockManager.checkCollision(ball);

            if (ball.getEstaQuieto()) {
                ball.setXY(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2, paddle.getY() + paddle.getHeight());
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    ball.setEstaQuieto(false);
                }
            }

            ball.checkCollision(paddle);
            blockManager.checkCollision(ball);

            shape.begin(ShapeRenderer.ShapeType.Filled);
            paddle.draw(shape);
            ball.draw(shape);
            blockManager.drawBlocks(shape);
            shape.end();

            dibujaTextos();
            checkGameStatus();
        } else {
            drawGameOverScreen();
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            paddle.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            paddle.moveRight();
        }
    }

    private void dibujaTextos() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Puntos: " + puntaje, 10, 30); // Ajusta la posición Y
        font.draw(batch, "Vidas: " + vidas, Gdx.graphics.getWidth() - 150, 30); // Ajusta la posición Y
        batch.end();
    }

    private void checkGameStatus() {
        if (ball.getY() < 0) {
            vidas--;
            if (vidas <= 0) {
                gameOver = true;
            } else {
                resetBall();
            }
        }

        if (blockManager.isEmpty()) {
            nivel++;
            crearBloques(2 + nivel);
            resetBall();
        }
    }

    private void resetGame() {
        vidas = 3;
        puntaje = 0;
        nivel = 1;
        gameOver = false;
        crearBloques(2 + nivel);
        resetBall();
    }

    private void resetBall() {
        ball.setXY(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2, paddle.getY() + paddle.getHeight());
        ball.setXSpeed(5);
        ball.setYSpeed(7);
        ball.setEstaQuieto(true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shape.dispose();
    }

    private void drawGameOverScreen() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(1, 0, 0, 1); // Rojo
        shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shape.end();

        batch.begin();
        font.setColor(1, 1, 1, 1); // Blanco
        font.getData().setScale(2, 2);
        String gameOverText = "GAME OVER";
        GlyphLayout layout = new GlyphLayout(font, gameOverText);
        font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, Gdx.graphics.getHeight() / 2 + layout.height / 2);

        font.getData().setScale(1, 1);
        String restartText = "Presiona ESPACIO para reiniciar";
        layout.setText(font, restartText);
        font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, (float) Gdx.graphics.getHeight() / 4);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            font.getData().setScale(1, 1);
            resetGame();
        }
    }

}

