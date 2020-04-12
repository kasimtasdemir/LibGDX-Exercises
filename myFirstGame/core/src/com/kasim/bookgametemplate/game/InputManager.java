package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.kasim.bookgametemplate.util.Constants;

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
        switch (keycode){
            case Input.Keys.R:
                Gdx.app.debug(TAG, "User request: " + "RESET_GAME");
                worldController.userRequest(Constants.UserRequest.RESET_GAME);
                break;
            case Input.Keys.SPACE:
                worldController.userRequest(Constants.UserRequest.PLAYER_JUMP);
                break;
            case Input.Keys.ENTER:
                break;
            case Input.Keys.ESCAPE:
                Gdx.app.debug(TAG, "User request: " + "BACK_TO_MENU");
                worldController.userRequest(Constants.UserRequest.BACK_TO_MENU);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Gdx.app.debug(TAG, "Touch Down (screenX, screenY, pointer, button): " +
        //        screenX + ", " + screenY + ", " + pointer + ", " + button);
        worldController.userRequest(Constants.UserRequest.PLAYER_JUMP);
        worldController.userTouchDown(screenX, screenY);
        return true;
    }

}
