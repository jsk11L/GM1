package com.mygdx.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Blocks.BlockManager;
import com.mygdx.game.Builder.ConcreteLevelBuilder;
import com.mygdx.game.Builder.LevelBuilder;
import com.mygdx.game.Strategy.BallBehavior;
import com.mygdx.game.Strategy.PowerUp;
import com.mygdx.game.Template.EasyLevel;
import com.mygdx.game.Template.MediumLevel;
import com.mygdx.game.Template.LevelTemplate;
import com.mygdx.game.Template.HardLevel;

import java.util.Iterator;

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
        ball.setNormalBehavior(currentLevel.getNormalBallBehavior());
        currentLevel.playLevel();
    }

    public void resetBall() {
        if (currentLevel != null) {
            ball.setNormalBehavior(currentLevel.getNormalBallBehavior());
            currentLevel.initializeLevel();
        }
        ball.setInitPos(paddle);
        ball.setEstaQuieto(true);
        ball.resetToNormalBehavior();
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

    public void actualizarPowerUps(float deltaTime) {
        for (Iterator<PowerUp> it = blockManager.getPowerUps().iterator(); it.hasNext();) {
            PowerUp powerUp = it.next();
            powerUp.update(deltaTime);
            if (powerUp.collidesWithPaddle(paddle)) {
                Color color = powerUp.getType() == PowerUp.PowerUpType.FAST ? Color.RED : Color.BLUE;
                ball.setPowerUp(powerUp.getBehavior(), 5.0f, color);
                it.remove();
            }
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
        LevelBuilder builder = new ConcreteLevelBuilder(blockManager, ball);

        // Ejemplo: Construir un nivel medio con bloques mixtos y 4 filas
        currentLevel = builder.reset()
                .setDifficulty(2)
                .setBlockType(BlockManager.BlockType.MIXED)
                .addBlocks(4)
                .build();

        currentLevel.playLevel();
        BallBehavior normalBehavior = currentLevel.getNormalBallBehavior();
        ball.setNormalBehavior(normalBehavior);
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
