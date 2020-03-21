package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kasim.bookgametemplate.game.objects.TestObjectBox;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class Level {
    public static final String TAG = Level.class.getName();

    public TestObjectBox testObjectBox;

    public Level() {
        init();
    }

    public void init() {
        testObjectBox = new TestObjectBox();
        Gdx.app.debug(TAG, "Level->init() testObjectBox dimension: " + testObjectBox.dimension);
    }

    public void render(SpriteBatchWDebug batch) {
        testObjectBox.render(batch);
    }

    public void update(float deltaTime) {
        testObjectBox.update(deltaTime);
    }
}
