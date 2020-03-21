package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasim.bookgametemplate.game.objects.TestObjectBox;

public class Level {
    public static final String TAG = Level.class.getName();

    public TestObjectBox testObjectBox;

    public Level() {
        init();
    }
    public void init(){
        testObjectBox = new TestObjectBox();
        Gdx.app.debug(TAG, "Level->init() testObjectBox dimension: " + testObjectBox.dimension);
    }
    public void render (SpriteBatch batch) {
        testObjectBox.render(batch);
    }
}
