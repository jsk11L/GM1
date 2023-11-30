package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameManager {
    private BlockBreakerGame game;
    private PingBall ball;
    private Paddle paddle;
    private BlockManager blockManager;
    private int nivel;
    private int vidas;
    private int puntaje;
    private boolean gameOver;

    public GameManager(BlockBreakerGame game, PingBall ball, Paddle paddle, BlockManager blockManager) {
        this.game = game;
        this.ball = ball;
        this.paddle = paddle;
        this.blockManager = blockManager;
        initGame();
    }

    public void initGame() {
        nivel = 1;
        vidas = 3;
        puntaje = 0;
        gameOver = false;
        resetBall();
        blockManager.crearBloques(2 + nivel);
    }

    public void resetGame() {
        vidas = 3;
        puntaje = 0;
        nivel = 1;
        gameOver = false;
        blockManager.crearBloques(2 + nivel);
        resetBall();
    }

    public void resetBall() {
        ball.setXY(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2, paddle.getY() + paddle.getHeight());
        ball.setXSpeed(5);
        ball.setYSpeed(7);
        ball.setEstaQuieto(true);
    }

    public void checkGameStatus() {
        if (ball.getY() < 0) {
            vidas--;
            if (vidas <= 0) {
                gameOver = true;
            } else {
                resetBall();
            }
        }

        if (ball.getEstaQuieto()) {
            ball.setXY(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2, paddle.getY() + paddle.getHeight());
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                ball.setEstaQuieto(false);
            }
        }

        if (blockManager.isEmpty()) {
            nivel++;
            blockManager.crearBloques(2 + nivel);
            resetBall();
        }

    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            paddle.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            paddle.moveRight();
        }
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getVidas(){
        return vidas;
    }
    public void incrementScore() {
        puntaje++;
    }
}
