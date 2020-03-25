package com.kasim.bookgametemplate.game;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

public class TileMapProcessor implements Disposable {
    public TiledMap tiledMap;
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public TileMapProcessor(String tileMapPath, float unitScale) {
        loadTileMap(tileMapPath);
        initOrthogonalTiledMapRenderer(tiledMap, unitScale);
    }
    private void initOrthogonalTiledMapRenderer(TiledMap tiledMap, float unitScale) {
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
    }
    private void loadTileMap(String tileMapPath){
        tiledMap = new TmxMapLoader().load(tileMapPath);
    }

    @Override
    public void dispose() {
        orthogonalTiledMapRenderer.dispose();
        tiledMap.dispose();
    }

    public void setView(OrthographicCamera camera) {
        orthogonalTiledMapRenderer.setView(camera);
    }

    public void render() {
        orthogonalTiledMapRenderer.render();
    }
}
