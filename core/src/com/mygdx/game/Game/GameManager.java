package com.mygdx.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Template.EasyLevel;
import com.mygdx.game.Template.MediumLevel;
import com.mygdx.game.Template.LevelTemplate;
import com.mygdx.game.Template.HardLevel;

public class GameManager {
    private final PingBall ball;
    private final Paddle paddle;
    private LevelTemplate currentLevel;
    private int totalNivelesJugados;
    private int selectorDeNivel;
    private int vidas;
    private int puntaje;
    private BlockManager blockManager;
    private boolean gameOver;

    public GameManager(PingBall ball, Paddle paddle, BlockManager blockManager) {
        this.ball = ball;
        this.paddle = paddle;
        this.blockManager = blockManager;
        this.currentLevel = new EasyLevel(blockManager, ball);
    }

    public void initGame() {
        totalNivelesJugados = 1;
        selectorDeNivel = 0;
        vidas = 3;
        puntaje = 0;
        gameOver = false;
        resetBall();
        currentLevel.playLevel();
    }

    public void resetBall() {
        if (currentLevel != null) {
            currentLevel.initializeLevel();
        }
        ball.setInitPos(paddle);
        ball.setEstaQuieto(true);
    }

    public void resetGame() {
        totalNivelesJugados = 1;
        selectorDeNivel = 0;
        vidas = 3;
        puntaje = 0;
        gameOver = false;
        resetBall();
        switchLevel();
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
        totalNivelesJugados++;
        selectorDeNivel++;

        if (selectorDeNivel == 1) {
            currentLevel = new EasyLevel(blockManager, ball);
        } else if (selectorDeNivel == 2) {
            currentLevel = new MediumLevel(blockManager, ball);
        } else {
            currentLevel = new HardLevel(blockManager, ball);
            if (selectorDeNivel == 2) selectorDeNivel = 0;
        }

        currentLevel.playLevel();
    }
    public int getNivel(){
        return totalNivelesJugados;
    }
    public String getDificultad() {
        return currentLevel != null ? currentLevel.getDificultad() : "Unknown";
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
