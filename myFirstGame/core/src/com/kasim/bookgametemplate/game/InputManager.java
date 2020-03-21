package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputManager extends InputAdapter {
    public static final String TAG = InputManager.class.getName();
    private WorldController worldController;

    public InputManager(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    public void init() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            Gdx.app.debug(TAG, "User request: " + "Game world reset");
            worldController.userRequest_resetGame();
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
