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

    @Override
    public void create() {
        // Set Libgdx log level to DEBUG
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.instance.init(new AssetManager());
        setScreen(new MenuScreen(this));
        //setScreen(new GameScreen(this));
    }


    @Override
    public void dispose() {
        Assets.instance.dispose();
    }


    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
    }
}
