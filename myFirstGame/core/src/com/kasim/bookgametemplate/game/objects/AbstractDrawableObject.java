package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public abstract class AbstractDrawableObject {
    public Vector2 position;
    public Vector2 positionOffset;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    public AbstractDrawableObject() {
        position = new Vector2(0, 0);
        positionOffset = new Vector2( 0, 0);
        dimension = new Vector2(1, 1);
        origin = new Vector2(0, 0);
        scale = new Vector2(1, 1);
        rotation = 0;
    }

    public void update(float deltaTime) {
    }

    public abstract void render(SpriteBatchWDebug batch);

}
