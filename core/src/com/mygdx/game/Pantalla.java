package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game.GameManager;
import com.mygdx.game.Singleton.ResourceManager;
import com.mygdx.game.Singleton.SoundManager;

@SuppressWarnings("ALL")
public class Pantalla {
    private SpriteBatch batch;
    private BitmapFont font;
    private EstadoPantalla estadoActual;
    private float gameOverFadeTimer;
    private ShapeRenderer shape;
    private GameManager gameManager;
    private ResourceManager resourceManager;
    private Stage stage;
    private Skin skin;
    private Stage pauseStage;
    private TextButton resumeButton;
    private TextButton mainMenuButton;
    private TextButton musicToggleButton;
    private TextButton soundToggleButton;
    private TextButton pauseMusicToggleButton;
    private TextButton pauseSoundToggleButton;
    public EstadoPantalla getEstadoActual() {
        return estadoActual;
    }

    public enum EstadoPantalla {
        MENU_PRINCIPAL,
        JUEGO,
        GAME_OVER,
        PAUSA
    }

    public Pantalla(ShapeRenderer shape, GameManager gameManager, SpriteBatch batch, BitmapFont font) {
        estadoActual = EstadoPantalla.MENU_PRINCIPAL;
        this.shape = shape;
        this.gameManager = gameManager;
        resourceManager = ResourceManager.getInstance();
        resourceManager.loadResources();
        this.batch = batch;
        this.font = font;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        pauseStage = new Stage(new ScreenViewport());

        // Asume que tienes un Skin cargado aquí
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        createButtons();
    }

    public void render() {
        switch (estadoActual) {
            case MENU_PRINCIPAL:
                mostrarMenuPrincipal();
                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
                break;
            case GAME_OVER:
                mostrarGameOver();
                break;
            case PAUSA:
                mostrarMenuPausa();
                break;
            case JUEGO:
                break;
        }
    }

    private void mostrarMenuPausa() {
        pauseStage.act(Gdx.graphics.getDeltaTime());
        pauseStage.draw();
    }

    public void setMenuPausa() {
        estadoActual = EstadoPantalla.PAUSA;
        Gdx.input.setInputProcessor(pauseStage); // Establecer el procesador de entrada para el pauseStage
    }

    private void setMenuPrincipal(){
        estadoActual = EstadoPantalla.MENU_PRINCIPAL;
        Gdx.input.setInputProcessor(stage);
    }

    private void reanudarJuego() {
        estadoActual = EstadoPantalla.JUEGO;
        Gdx.input.setInputProcessor(stage);
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

        Texture menuBackground = resourceManager.getMenuBackground();
        float width = menuBackground.getWidth() * 0.45f; // Reducir a la mitad el ancho original
        float height = menuBackground.getHeight() * 0.45f; // Reducir a la mitad la altura original
        batch.draw(menuBackground, 670, 10, width, height);

        mostrarTexto(75,20,"Presiona 'Enter' para comenzar");
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameManager.initGame();
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

    private void createButtons() {
        soundToggleButton = new TextButton("S", skin);
        musicToggleButton = new TextButton("M", skin);
        pauseSoundToggleButton = new TextButton("S", skin);
        pauseMusicToggleButton = new TextButton("M", skin);
        resumeButton = new TextButton("CONTINUAR", skin);
        mainMenuButton = new TextButton("VOLVER AL MENU", skin);

        soundToggleButton.setPosition(10, 10);
        musicToggleButton.setPosition(60, 10);
        pauseSoundToggleButton.setPosition(10, 10);
        pauseMusicToggleButton.setPosition(60, 10);
        resumeButton.setPosition(220, 275);
        mainMenuButton.setPosition(220, 215);

        soundToggleButton.setSize(40, 40);
        musicToggleButton.setSize(40, 40);
        pauseSoundToggleButton.setSize(40, 40);
        pauseMusicToggleButton.setSize(40, 40);
        resumeButton.setSize(200, 50);
        mainMenuButton.setSize(200, 50);

        soundToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               SoundManager.getInstance().toggleSound();
            }
        });

        musicToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getInstance().toggleMusic();
            }
        });

        pauseSoundToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getInstance().toggleSound();
            }
        });

        pauseMusicToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getInstance().toggleMusic();
            }
        });

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                reanudarJuego();
            }
        });

        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setMenuPrincipal();
            }
        });

        stage.addActor(soundToggleButton);
        stage.addActor(musicToggleButton);
        pauseStage.addActor(pauseSoundToggleButton);
        pauseStage.addActor(pauseMusicToggleButton);
        pauseStage.addActor(resumeButton);
        pauseStage.addActor(mainMenuButton);
    }

    public void setEstado(EstadoPantalla nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void dibujaTextos() {
        batch.begin();
        font.draw(batch, "Nivel: " + gameManager.getNivel(), 10, 475); // Ajusta la posición Y
        font.draw(batch, "Dificultad: " + gameManager.getDificultad(), Gdx.graphics.getWidth() - 150, 475); // Ajusta la posición Y
        font.draw(batch, "Puntos: " + gameManager.getPuntaje(), 10, 30); // Ajusta la posición Y
        font.draw(batch, "Vidas: " + gameManager.getVidas(), Gdx.graphics.getWidth() - 150, 30); // Ajusta la posición Y
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
    }
}
