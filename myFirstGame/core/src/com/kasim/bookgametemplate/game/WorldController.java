package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kasim.bookgametemplate.BookGameTemplate;
import com.kasim.bookgametemplate.util.CameraHelper;
import com.kasim.bookgametemplate.util.Constants;

public class WorldController implements Disposable {
    private static final String TAG =
            WorldController.class.getName();

    public BookGameTemplate game;
    public CameraHelper cameraHelper;
    public Level level;
    public World box2dWorld;
    private WorldRenderer worldRenderer;

    public WorldController(BookGameTemplate game) {
        this.game = game;
        init();
    }
    public void setWorldRenderer(WorldRenderer worldRenderer){
        this.worldRenderer = worldRenderer;
    }

    private void init() {
        box2dWorld = new World(new Vector2(0, -10),true);
        level = new Level(box2dWorld);
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(level.testPlayer1);

        cameraHelper.setBoundaries(level.tileMapProcessor.tilemapBoundaries);
    }

    public void update(float deltaTime) {
        level.update(deltaTime); // updates box2d world
        cameraHelper.update(deltaTime);
    }

    public void userRequest(Constants.UserRequest request){
        switch (request){
            case RESET_GAME:
                Gdx.app.debug(TAG, "User request: " + "Game world reset");
                game.gameScreen.dispose();
                game.gameScreen.init();
                break;
            case BACK_TO_MENU:
                // switch to menu screen
                game.setScreen(game.menuScreen);
                break;
            case PLAYER_JUMP:
                //Gdx.app.debug(TAG, "User request: " + "PLAYER_JUMP");
                level.testPlayer1.UserRequest_Jump();
                break;
        }

    }

    @Override
    public void dispose() {
        Array<Body> bodies = new Array<Body>();
        box2dWorld.getBodies(bodies);
        for(Body body:bodies){
            box2dWorld.destroyBody(body);
        }
        box2dWorld.dispose();
        level.dispose();
    }


    public void userTouchDown(int screenX, int screenY) {
        Vector3 coordinates = new Vector3(screenX, screenY, 0);
        worldRenderer.camera.unproject(coordinates);
        Gdx.app.debug(TAG, "Touch Down (screenX, screenY, gameX, gameY): " +
                screenX + ", " + screenY + ", " + coordinates.x + ", " + coordinates.y);

    }
}
