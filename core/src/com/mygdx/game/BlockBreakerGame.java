
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
    private Paddle paddle;
    private ArrayList<Block> blocks;
    private int lives;
    private int score;
    private int level;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);
        level = 1;
        blocks = new ArrayList<>();
        createBlocks(2 + level);

        shape = new ShapeRenderer();
        ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, 5, 7, true);
        paddle = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10, 10);
        lives = 3;
        score = 0;
    }

    private void createBlocks(int rows) {
        blocks.clear();
        int blockWidth = 70;
        int blockHeight = 26;
        int yPos = Gdx.graphics.getHeight() - blockHeight - 10;
        for (int row = 0; row < rows; row++) {
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(new Block(x, yPos, blockWidth, blockHeight));
            }
            yPos -= blockHeight + 10;
        }
    }

    private void drawTexts() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Score: " + score, 10, 25);
        font.draw(batch, "Lives: " + lives, Gdx.graphics.getWidth() - 100, 25);
        batch.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Check game start
        if (ball.isStationary()) {
            ball.setPosition(paddle.getX() + paddle.getWidth() / 2 - 5, paddle.getY() + paddle.getHeight() + 11);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setStationary(false);
        } else {
            ball.update();
        }

        // Check if the ball went below the bottom edge
        if (ball.getY() < 0) {
            lives--;
            if (lives <= 0) {
                gameOver();
            } else {
                ball.reset(paddle.getX() + paddle.getWidth() / 2 - 5, paddle.getY() + paddle.getHeight() + 11);
            }
        }

        // Draw and update blocks
        for (Block block : new ArrayList<>(blocks)) {
            block.draw(shape);
            if (ball.checkCollision(block)) {
                score++;
                blocks.remove(block);
            }
        }

        // Check level completion
        if (blocks.isEmpty()) {
            level++;
            createBlocks(2 + level);
            ball.reset(paddle.getX() + paddle.getWidth() / 2 - 5, paddle.getY() + paddle.getHeight() + 11);
        }

        // En el método render de BlockBreakerGame, debes manejar la entrada del usuario:
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            paddle.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            paddle.moveRight();
        }

        paddle.draw(shape);
        ball.draw(shape);
        ball.checkCollision(paddle);

        shape.end();
        drawTexts();
    }

    private void gameOver() {
        lives = 3;
        level = 1;
        createBlocks(2 + level);
        ball.reset(paddle.getX() + paddle.getWidth() / 2 - 5, paddle.getY() + paddle.getHeight() + 11);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shape.dispose();
    }
}
