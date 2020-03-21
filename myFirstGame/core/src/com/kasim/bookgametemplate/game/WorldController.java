package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.kasim.bookgametemplate.util.CameraHelper;

public class WorldController extends InputAdapter {
    private static final String TAG =
            WorldController.class.getName();

    public CameraHelper cameraHelper;
    public Level level;

    public WorldController() {
        init();
    }

    private void init() {
        level = new Level();
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
        cameraHelper.update(deltaTime);
    }

    @Override
    public boolean keyDown(int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }
        // Select next sprite
        else if (keycode == Input.Keys.SPACE) {

        }
        // Toggle camera follow
        else if (keycode == Input.Keys.ENTER) {
        }
        return false;
    }
}
