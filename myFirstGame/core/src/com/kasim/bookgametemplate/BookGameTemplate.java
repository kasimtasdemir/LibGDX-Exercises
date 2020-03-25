package com.kasim.bookgametemplate;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.kasim.bookgametemplate.game.Assets;
import com.kasim.bookgametemplate.screen.GameScreen;
import com.kasim.bookgametemplate.screen.MenuScreen;

public class BookGameTemplate extends Game {
    private static final String TAG = BookGameTemplate.class.getName();
    public MenuScreen menuScreen;
    public GameScreen gameScreen;

    @Override
    public void create() {
        // Set Libgdx log level to DEBUG
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.instance.init(new AssetManager());
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        //setScreen(menuScreen);
        setScreen(gameScreen);
    }


    @Override
    public void dispose() {
        Assets.instance.dispose();
        gameScreen.dispose();
        menuScreen.dispose();
    }


    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
    }
}
