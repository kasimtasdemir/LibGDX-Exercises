package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kasim.bookgametemplate.util.Constants;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class DrawableTile extends AbstractDrawableObject {
    private TextureRegion textureRegion;
    private TiledMapTile tile;

    public DrawableTile(TiledMapTileLayer layer, int tilePositionX, int tilePositionY) {
        this.tile = layer.getCell(tilePositionX, tilePositionY).getTile();
        rotation = 0;
        scale.set(1, 1);
        textureRegion = tile.getTextureRegion();

        position.set(tilePositionX * layer.getTileWidth() / Constants.TILEMAP_PPM,
                tilePositionY * layer.getTileHeight() / Constants.TILEMAP_PPM);
        dimension.set(
                textureRegion.getRegionWidth() / Constants.TILEMAP_PPM,
                textureRegion.getRegionHeight() / Constants.TILEMAP_PPM
        );
        positionOffset.set(-dimension.x / 2f,
                -dimension.y / 2f);
        origin.set(dimension.x / 2f,
                dimension.y / 2f);
    }

    @Override
    public void render(SpriteBatchWDebug batch) {
        textureRegion = tile.getTextureRegion();
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

    public TextureRegion getTextureRegion() {
        return tile.getTextureRegion();
    }
}
