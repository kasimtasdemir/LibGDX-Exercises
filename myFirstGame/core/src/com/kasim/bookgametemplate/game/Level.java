package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.kasim.bookgametemplate.game.objects.TestBox2DDynamicObject;
import com.kasim.bookgametemplate.game.objects.TestBox2DStaticObject;
import com.kasim.bookgametemplate.game.objects.TestObjectBox;
import com.kasim.bookgametemplate.game.objects.TestPlayer1;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class Level {
    public static final String TAG = Level.class.getName();

    public TestObjectBox testObjectBox;
    public TestBox2DStaticObject testBox2DStaticObject;
    public TestBox2DDynamicObject testBox2DDynamicObject;
    public TestPlayer1 testPlayer1;
    private World box2dWorld;

    public Level(World box2dWorld) {
        this.box2dWorld = box2dWorld;
        init();
    }

    public void init() {
        testObjectBox = new TestObjectBox();
        testBox2DStaticObject = new TestBox2DStaticObject(box2dWorld);
        testBox2DDynamicObject = new TestBox2DDynamicObject(box2dWorld);
        testPlayer1 = new TestPlayer1(box2dWorld);
        Gdx.app.debug(TAG, "Level->init() testObjectBox dimension: " + testObjectBox.dimension);
    }

    public void render(SpriteBatchWDebug batch) {

        testObjectBox.render(batch);
        testBox2DDynamicObject.render(batch);
        testPlayer1.render(batch);
    }

    public void update(float deltaTime) {
        box2dWorld.step(1/60f,6,2);
        testObjectBox.update(deltaTime);
    }
}
