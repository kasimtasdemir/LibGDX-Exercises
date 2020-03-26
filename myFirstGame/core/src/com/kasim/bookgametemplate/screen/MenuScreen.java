package com.kasim.bookgametemplate.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.kasim.bookgametemplate.BookGameTemplate;

public class MenuScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getName();
    private BookGameTemplate game;
    public MenuScreen (BookGameTemplate game) {
        super(game);
        this.game = game;
    }
    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 1.0f, 0.5f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isTouched())
            game.setScreen(game.gameScreen);
    }
    @Override public void resize (int width, int height) {

    }
    @Override public void show () {
    }
    @Override public void hide () {

    }
    @Override public void pause () {

    }
}
