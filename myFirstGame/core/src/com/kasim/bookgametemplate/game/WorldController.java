package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.kasim.bookgametemplate.util.CameraHelper;

public class WorldController {
    private static final String TAG =
            WorldController.class.getName();

    public CameraHelper cameraHelper;
    public Level level;

    public WorldController() {
        init();
    }

    private void init() {
        level = new Level();
        cameraHelper = new CameraHelper();
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
        cameraHelper.update(deltaTime);
    }

    public void userRequest_resetGame(){
        Gdx.app.debug(TAG, "User request: " + "Game world reset");
    }


}
