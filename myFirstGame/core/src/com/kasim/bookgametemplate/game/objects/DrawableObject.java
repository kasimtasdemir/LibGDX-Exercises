package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class DrawableObject extends AbstractDrawableObject {
    public TextureRegion textureRegion;
    public DrawableObject(){
        dimension.set(1f, 1f);
        rotation = 0;
        scale.set(1, 1);
        position.set(2.5f, 2.5f);
        //positionOffset.set(-dimension.x / 2f, -dimension.y / 2f);
        positionOffset.set(0, 0);
        origin.set(dimension.x / 2f, dimension.y / 2f);
    }
    @Override
    public void render(SpriteBatchWDebug batch) {
        batch.draw(textureRegion.getTexture(),
                position.x, position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                textureRegion.getRegionX(), textureRegion.getRegionY(),
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                false, false);
    }
}