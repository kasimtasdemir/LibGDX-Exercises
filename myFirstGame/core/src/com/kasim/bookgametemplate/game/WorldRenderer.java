package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.kasim.bookgametemplate.util.Constants;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

import static com.badlogic.gdx.Gdx.graphics;

public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private SpriteBatchWDebug batch;
    private WorldController worldController;
    public Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cameraTilemap;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatchWDebug();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_HEIGHT);
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / graphics.getHeight()) * graphics.getWidth();
        camera.position.set(0, 0, 0);
        camera.update();

        /* cameraTilemap = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
                        Constants.VIEWPORT_HEIGHT);
        //cameraTilemap = new OrthographicCamera(graphics.getWidth(),
        //        graphics.getHeight());
        //cameraTilemap.setToOrtho(false,Constants.VIEWPORT_WIDTH,
        //        Constants.VIEWPORT_HEIGHT);

        cameraTilemap.position.set(Constants.VIEWPORT_WIDTH/2,
                Constants.VIEWPORT_HEIGHT/2, 0);
        cameraTilemap.update(); */
    }

    public void render() {

        renderWorld(batch);
        debugRenderer.render(worldController.box2dWorld, camera.combined);
    }

    private void renderWorld(SpriteBatchWDebug batch) {
        worldController.cameraHelper.applyTo(camera);

        //cameraTilemap.update();
        camera.position.set(Constants.VIEWPORT_WIDTH/2,
                Constants.VIEWPORT_HEIGHT/2,0);
        camera.update();


        worldController.level.render(batch, camera);
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        //(Constants.VIEWPORT_HEIGHT / height) * width;
        //cameraTilemap.update();
        /* cameraTilemap.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        //cameraTilemap.viewportHeight = height;
        cameraTilemap.position.set(Constants.VIEWPORT_WIDTH/2,
                Constants.VIEWPORT_HEIGHT/2,0);
        cameraTilemap.update(); */
    }

    @Override
    public void dispose() {
        batch.dispose();
        debugRenderer.dispose();
    }
}
