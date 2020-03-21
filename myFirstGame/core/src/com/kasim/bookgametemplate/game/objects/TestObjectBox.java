package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kasim.bookgametemplate.game.Assets;

public class TestObjectBox extends AbstractDrawableObject {
    private TextureRegion testBox;
    public static final String TAG = Assets.class.getName();

    public TestObjectBox(){
        init();
    }

    private void init () {
        Gdx.app.debug(TAG, "TestObjectBox->init() entered ");
        dimension.set(1f, 1f);
        testBox = Assets.instance.testObjectBox.box;
        Gdx.app.debug(TAG, "TestObjectBox->init() testBox width: " + testBox.getRegionWidth());
    }
    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;

        reg = testBox;
        batch.draw(reg.getTexture(),
                position.x, position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
    }
}
