package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.kasim.bookgametemplate.game.objects.Box2dRectangleObject;
import com.kasim.bookgametemplate.game.objects.Box2dRectangleStaticObject;
import com.kasim.bookgametemplate.game.objects.DrawableObject;
import com.kasim.bookgametemplate.game.objects.TestBox2DDynamicObject;
import com.kasim.bookgametemplate.game.objects.TestObjectBox;
import com.kasim.bookgametemplate.game.objects.TestPlayer1;
import com.kasim.bookgametemplate.util.Constants;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

import java.util.ArrayList;


public class Level implements Disposable {
    public static final String TAG = Level.class.getName();

    public TestObjectBox testObjectBox;
    //public TestBox2DStaticObject testBox2DStaticObject;
    public TestBox2DDynamicObject testBox2DDynamicObject;
    public ArrayList<Box2dRectangleObject> box2dRectangleObjects;
    public ArrayList<Box2dRectangleStaticObject> box2dRectangleStaticObjects;
    public TestPlayer1 testPlayer1;
    public TileMapProcessor tileMapProcessor;
    private World box2dWorld;

    public Level(World box2dWorld) {
        this.box2dWorld = box2dWorld;
        init();
    }


    public void init() {
        testObjectBox = new TestObjectBox();
        //testBox2DStaticObject = new TestBox2DStaticObject(box2dWorld);
        //testBox2DDynamicObject = new TestBox2DDynamicObject(box2dWorld);
        testPlayer1 = new TestPlayer1(box2dWorld);

        box2dRectangleObjects = new ArrayList<Box2dRectangleObject>();
        box2dRectangleStaticObjects = new ArrayList<Box2dRectangleStaticObject>();
        //testBox2DDynamicObjects.add(testBox2DDynamicObject);

        tileMapProcessor = new TileMapProcessor(Constants.TILEMAP_TMX_PATH, 1 / Constants.TILEMAP_PPM); // 32x32 tilemap
        // cube is 1x1 m  in this world

        tileMapProcessor.createBox2dObjects();
        Gdx.app.debug(TAG, "Level->init() testObjectBox dimension: " + testObjectBox.dimension);
    }

    public void render(SpriteBatchWDebug batch, OrthographicCamera camera) {
        tileMapProcessor.setView(camera);
        tileMapProcessor.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        testObjectBox.render(batch);
        for (Box2dRectangleObject object : box2dRectangleObjects) {
            object.render(batch);
        }
        for (Box2dRectangleStaticObject object : box2dRectangleStaticObjects) {
            object.render(batch);
        }
        //testBox2DDynamicObject.render(batch);

        testPlayer1.render(batch);
        batch.end();
    }

    public void update(float deltaTime) {
        testPlayer1.update(deltaTime);
        box2dWorld.step(1 / 60f, 6, 2);
        testObjectBox.update(deltaTime);
    }

    @Override
    public void dispose() {
        tileMapProcessor.dispose();
    }

    public class TileMapProcessor implements Disposable {
        //private final String TAG = com.kasim.bookgametemplate.game.TileMapProcessor.class.getName();
        public TiledMap tiledMap;
        public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
        public Rectangle tilemapBoundaries;


        public TileMapProcessor(String tileMapPath, float unitScale) {
            loadTileMap(tileMapPath);
            initOrthogonalTiledMapRenderer(tiledMap, unitScale);
            initTilemapBoundaries();
        }

        private void initTilemapBoundaries() {
            tilemapBoundaries = new Rectangle();
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
            tilemapBoundaries.x = 0;
            tilemapBoundaries.y = 0;
            tilemapBoundaries.width = layer.getWidth() * layer.getTileWidth() / Constants.TILEMAP_PPM;
            tilemapBoundaries.height = layer.getHeight() * layer.getTileHeight() / Constants.TILEMAP_PPM;
            Gdx.app.debug(TAG, "Tilemap Boundaries: " + tilemapBoundaries);
        }

        private void initOrthogonalTiledMapRenderer(TiledMap tiledMap, float unitScale) {
            orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
        }

        private void loadTileMap(String tileMapPath) {
            tiledMap = new TmxMapLoader().load(tileMapPath);
        }

        @Override
        public void dispose() {
            orthogonalTiledMapRenderer.dispose();
            tiledMap.dispose();
            tileMapProcessor.dispose();
        }

        public void setView(OrthographicCamera camera) {
            orthogonalTiledMapRenderer.setView(camera);
        }

        public void render() {
            orthogonalTiledMapRenderer.render();
        }

        public void createBox2dObjects() {
            createBox2dDynamicObjects();
            createBox2dStaticObjects();
        }

        public void createBox2dDynamicObjects() {
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("dynamic_objects");


            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    //if (layer.getCell(x, y).getTile().getTextureRegion().getRegionWidth() != 0){
                    if (layer.getCell(x, y) != null) {

                        //int tileX = x;
                        //int tileY = layerHightInTiles - 1  - y; // y down system to y up system
//                        Gdx.app.debug(TAG, "createBox2dDynamicObject cell with texture x, y, w, h: " +
//                                x + ", " + y + ", " +
//                                layer.getCell(x, y).getTile().getTextureRegion().getRegionWidth() +
//                                "," + layer.getCell(x, y).getTile().getTextureRegion().getRegionHeight() +
//                                "," + layer.getCell(x, y).getTile().getTextureRegion().getTexture().getTextureData().toString()
//                        );
                        DrawableObject newDrawableObject = new DrawableObject();
                        newDrawableObject.position.set(x * layer.getTileWidth() / Constants.TILEMAP_PPM,
                                y * layer.getTileHeight() / Constants.TILEMAP_PPM);
                        newDrawableObject.textureRegion = layer.getCell(x, y).getTile().getTextureRegion();
                        newDrawableObject.dimension.set(
                                newDrawableObject.textureRegion.getRegionWidth() / Constants.TILEMAP_PPM,
                                newDrawableObject.textureRegion.getRegionHeight() / Constants.TILEMAP_PPM
                        );
                        newDrawableObject.positionOffset.set(-newDrawableObject.dimension.x / 2f,
                                -newDrawableObject.dimension.y / 2f);
                        newDrawableObject.origin.set(newDrawableObject.dimension.x / 2f,
                                newDrawableObject.dimension.y / 2f);

                        Box2dRectangleObject newDynamicObject = new Box2dRectangleObject(box2dWorld, newDrawableObject);
                        //testBox2DDynamicObjects.add(newDynamicObject);
                        box2dRectangleObjects.add(newDynamicObject);

                    }
                }
            }

        }

        public void createBox2dStaticObjects() {
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("static_objects");


            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    //if (layer.getCell(x, y).getTile().getTextureRegion().getRegionWidth() != 0){
                    if (layer.getCell(x, y) != null) {

                        //int tileX = x;
                        //int tileY = layerHightInTiles - 1  - y; // y down system to y up system
//                        Gdx.app.debug(TAG, "createBox2dStaticObject cell with texture x, y, w, h: " +
//                                x + ", " + y + ", " +
//                                layer.getCell(x, y).getTile().getTextureRegion().getRegionWidth() +
//                                "," + layer.getCell(x, y).getTile().getTextureRegion().getRegionHeight() +
//                                "," + layer.getCell(x, y).getTile().getTextureRegion().getTexture().getTextureData().toString()
//                        );
                        DrawableObject newDrawableObject = new DrawableObject();
                        newDrawableObject.position.set(x * layer.getTileWidth() / Constants.TILEMAP_PPM,
                                y * layer.getTileHeight() / Constants.TILEMAP_PPM);
                        newDrawableObject.textureRegion = layer.getCell(x, y).getTile().getTextureRegion();
                        newDrawableObject.dimension.set(
                                newDrawableObject.textureRegion.getRegionWidth() / Constants.TILEMAP_PPM,
                                newDrawableObject.textureRegion.getRegionHeight() / Constants.TILEMAP_PPM
                        );
                        newDrawableObject.positionOffset.set(-newDrawableObject.dimension.x / 2f,
                                -newDrawableObject.dimension.y / 2f);
                        newDrawableObject.origin.set(newDrawableObject.dimension.x / 2f,
                                newDrawableObject.dimension.y / 2f);

                        Box2dRectangleStaticObject newStaticObject = new Box2dRectangleStaticObject(box2dWorld, newDrawableObject);
                        //testBox2DDynamicObjects.add(newDynamicObject);
                        box2dRectangleStaticObjects.add(newStaticObject);

                    }
                }
            }

        }
    }


}
