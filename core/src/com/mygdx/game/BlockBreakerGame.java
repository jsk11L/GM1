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
    private GameManager gameManager;
    private float gameOverFadeTimer = 0;
    private float gameOverOpacity = 0;

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
        gameManager = new GameManager(this, ball, paddle, blockManager);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameManager.getGameOver()) {
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

            dibujaTextos();
        } else {
            gameOverFadeTimer += Gdx.graphics.getDeltaTime();
            gameOverOpacity = Math.min(gameOverFadeTimer / 4f, 1);
            drawGameOverScreen();
        }
    }

    private void dibujaTextos() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Puntos: " + gameManager.getPuntaje(), 10, 30); // Ajusta la posición Y
        font.draw(batch, "Vidas: " + gameManager.getVidas(), Gdx.graphics.getWidth() - 150, 30); // Ajusta la posición Y
        batch.end();
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
        if (gameOverFadeTimer > 4) { // Esperar 4 segundos antes de mostrar el texto
            font.setColor(1, 1, 1, gameOverOpacity); // Aplicar opacidad
            font.getData().setScale(2, 2); // Escala para GAME OVER
            String gameOverText = "GAME OVER";
            GlyphLayout layout = new GlyphLayout(font, gameOverText);
            font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, Gdx.graphics.getHeight() / 2 + layout.height / 2);

            if (gameOverFadeTimer > 8) { // Esperar 8 segundos para mostrar "Presionar una tecla"
                String continueText = "Presiona ESPACIO para reiniciar";
                layout.setText(font, continueText);
                font.getData().setScale(1, 1);
                font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, Gdx.graphics.getHeight() / 4 + layout.height / 2);
            }
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            font.getData().setScale(1, 1);
            gameManager.resetGame();
        }
    }

}

