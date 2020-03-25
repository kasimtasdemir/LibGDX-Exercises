package com.kasim.bookgametemplate.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kasim.bookgametemplate.BookGameTemplate;
import com.kasim.bookgametemplate.game.InputManager;
import com.kasim.bookgametemplate.game.TileMapProcessor;
import com.kasim.bookgametemplate.game.WorldController;
import com.kasim.bookgametemplate.game.WorldRenderer;
import com.kasim.bookgametemplate.ui.TestWindow;
import com.kasim.bookgametemplate.util.Constants;

public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    public InputManager inputManager;
    private BookGameTemplate game;
    private boolean paused;



    public TileMapProcessor tileMapProcessor;


    public Stage stage; // For UI
    private TestWindow testWindow;

    public GameScreen(BookGameTemplate game) {
        super(game);
        this.game = game;
    }

    @Override
    public void render(float deltaTime) {
        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passe // since last rendered frame.
            worldController.update(deltaTime);
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        }
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,
                0xed / 255.0f, 0xff / 255.0f); // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Render game world to screen
        worldRenderer.render();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        inputManager = new InputManager(worldController);
        //Gdx.input.setCatchBackKey(true);

        stage = new Stage(new ScreenViewport());
        //testWindow = new TestWindow(this);
        //testWindow.show();

        tileMapProcessor = new TileMapProcessor(Constants.TILEMAP_TMX_PATH, 1/ Constants.TILEMAP_PPM); // 32x32 tilemap
        // cube is 1x1 m  in this world

    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        stage.dispose();
        tileMapProcessor.dispose();
        //Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }
}
