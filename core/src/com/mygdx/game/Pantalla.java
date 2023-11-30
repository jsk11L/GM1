package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

@SuppressWarnings("ALL")
public class Pantalla {
    private SpriteBatch batch;
    private BitmapFont font;
    private EstadoPantalla estadoActual;
    private float gameOverFadeTimer;
    private ShapeRenderer shape;
    private GameManager gameManager;

    public EstadoPantalla getEstadoActual() {
        return estadoActual;
    }

    public enum EstadoPantalla {
        MENU_PRINCIPAL,
        JUEGO,
        GAME_OVER
    }

    public Pantalla(ShapeRenderer shape, GameManager gameManager, SpriteBatch batch, BitmapFont font) {
        estadoActual = EstadoPantalla.MENU_PRINCIPAL;
        this.shape = shape;
        this.gameManager = gameManager;
        this.batch = batch;
        this.font = font;
    }

    public void render() {
        switch (estadoActual) {
            case MENU_PRINCIPAL:
                mostrarMenuPrincipal();
                break;
            case GAME_OVER:
                mostrarGameOver();
                break;
            case JUEGO:
                break;
        }
    }

    private void mostrarTexto(int addX, int addY, String text){
        GlyphLayout layout = new GlyphLayout(font, text);
        float anchoTexto = layout.width;
        float x = (Gdx.graphics.getWidth() - anchoTexto) / 2;
        float y = (float) Gdx.graphics.getHeight() / 2;
        font.draw(batch, layout, x+addX, y+addY);
    }

    private void mostrarMenuPrincipal() {

        batch.begin();
        mostrarTexto(75,20,"Presiona 'Enter' para comenzar");
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            estadoActual = EstadoPantalla.JUEGO;
        }
    }

    private void mostrarGameOver() {
        gameOverFadeTimer += Gdx.graphics.getDeltaTime();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(1, 0, 0, 1);
        shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shape.end();

        batch.begin();
        if (gameOverFadeTimer > 4) {
            mostrarTexto(75,70,"GAME OVER");

            if (gameOverFadeTimer > 8) {
                mostrarTexto(75,-40,"Presiona ESPACIO para reiniciar");
            }
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && gameOverFadeTimer > 9) {
            estadoActual = EstadoPantalla.MENU_PRINCIPAL;
            gameManager.initGame();
            gameOverFadeTimer = 0;
        }
    }

    public void setEstado(EstadoPantalla nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void dibujaTextos() {
        batch.begin();
        font.draw(batch, "Puntos: " + gameManager.getPuntaje(), 10, 30); // Ajusta la posición Y
        font.draw(batch, "Vidas: " + gameManager.getVidas(), Gdx.graphics.getWidth() - 150, 30); // Ajusta la posición Y
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
