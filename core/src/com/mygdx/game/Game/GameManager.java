package com.mygdx.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Template.EasyLevel;
import com.mygdx.game.Template.LevelTemplate;
import com.mygdx.game.Template.HardLevel;

public class GameManager {
    private final PingBall ball;
    private final Paddle paddle;
    private LevelTemplate currentLevel;
    private int nivel;
    private int vidas;
    private int puntaje;
    private boolean gameOver;

    public GameManager(PingBall ball, Paddle paddle, BlockManager blockManager) {
        this.ball = ball;
        this.paddle = paddle;
        this.currentLevel = new EasyLevel(blockManager); // Comienza con el nivel fácil
        initGame();
    }

    public void initGame() {
        nivel = 1;
        vidas = 3;
        puntaje = 0;
        gameOver = false;
        resetBall();
        currentLevel.playLevel();
    }

    public void resetBall() {
        ball.setInitPos(paddle);
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
            ball.setInitPos(paddle);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                ball.setEstaQuieto(false);
            }
        }

        if (currentLevel.isOver()) {
            nivel++;
            switchLevel();
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

    private void switchLevel() {
        if (nivel % 2 == 0) {
            currentLevel = new HardLevel(blockManager);
        } else {
            currentLevel = new EasyLevel(blockManager);
        }
        currentLevel.playLevel();
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
