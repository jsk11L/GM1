package com.mygdx.game.Blocks;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game.GameManager;
import com.mygdx.game.Game.PingBall;
import com.mygdx.game.Singleton.ResourceManager;
import com.mygdx.game.Strategy.PowerUp;

@SuppressWarnings("ALL")
public class BlockManager {
    private ArrayList<Block> blocks;
    private ResourceManager resourceManager;
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    public BlockManager() {
        blocks = new ArrayList<>();
    }

    public enum BlockType {
        NORMAL,
        HARD,
        MIXED
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void drawBlocks(ShapeRenderer shape) {
        for (Block block : blocks) {
            if (!block.getDestroyed()) {
                block.draw(shape);
            }
        }
    }

    public void checkCollision(PingBall ball, GameManager game) {
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (!block.getDestroyed() && ball.collidesWith(block)) {
                resourceManager.getInstance().playBlockHitSound();
                ball.reflect();  // Asumiendo que hay una lógica para cambiar la dirección de la pelota

                block.setDestroyed(true); // Marcar el bloque como destruido
                game.incrementScore(); // Incrementar puntaje, si corresponde
                blocks.remove(i);
                i--;

                // Generar PowerUp con cierta probabilidad
                if (Math.random() < 0.1) { // Probabilidad de 10% de soltar un PowerUp
                    PowerUp.PowerUpType type = Math.random() < 0.5 ? PowerUp.PowerUpType.FAST : PowerUp.PowerUpType.SLOW;
                    powerUps.add(new PowerUp(type, block.getX(), block.getY()));
                }
            }
        }
    }

    public void drawPowerUps(ShapeRenderer shape) {
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(shape);
        }
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public List<PowerUp> getPowerUps() {
        return new ArrayList<>(powerUps);
    }

    private double getPowerUpProbability(int levelDifficulty) {
        return 0.1; // Ejemplo de probabilidad base
    }

    public void crearBloques(int filas, BlockType type) {
        clearBlocks();
        int blockWidth = 70;
        int blockHeight = 16;
        int y = Gdx.graphics.getHeight() - 30;

        for (int fila = 0; fila < filas; fila++) {
            y -= blockHeight + 10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                switch (type) {
                    case NORMAL:
                        addBlock(new RegularBlock(x, y, blockWidth, blockHeight));
                        break;
                    case HARD:
                        addBlock(new HardBlock(x, y, blockWidth, blockHeight));
                        break;
                    case MIXED:
                        // Para MIXED, alterna entre RegularBlock y HardBlock
                        Block block = (fila % 2 == 0) ? new RegularBlock(x, y, blockWidth, blockHeight)
                                : new HardBlock(x, y, blockWidth, blockHeight);
                        addBlock(block);
                        break;
                }
            }
        }
    }

    public void clearBlocks() {
        blocks.clear();
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }
}
