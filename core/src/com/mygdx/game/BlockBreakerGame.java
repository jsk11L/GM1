package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private GameManager gameManager;
    private Pantalla pantalla;

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
        gameManager = new GameManager(ball, paddle, blockManager);
        pantalla = new Pantalla(shape, gameManager, batch, font);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        pantalla.render();

        if (Pantalla.EstadoPantalla.JUEGO == pantalla.getEstadoActual()) {
            gameManager.handleInput();
            ball.update();
            gameManager.checkGameStatus();

            ball.checkCollision(paddle);
            blockManager.checkCollision(ball, gameManager);

            shape.begin(ShapeRenderer.ShapeType.Filled);
            paddle.draw(shape);
            ball.draw(shape);
            blockManager.drawBlocks(shape);
            shape.end();

            pantalla.dibujaTextos();

            if (gameManager.getGameOver()) {
                pantalla.setEstado(Pantalla.EstadoPantalla.GAME_OVER);
            }
        }
    }
    @Override
    public void dispose() {
        pantalla.dispose();
        batch.dispose();
        font.dispose();
        shape.dispose();
    }

}

