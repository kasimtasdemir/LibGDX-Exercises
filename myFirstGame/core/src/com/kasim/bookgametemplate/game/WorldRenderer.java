package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.kasim.bookgametemplate.util.Constants;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private SpriteBatchWDebug batch;
    private WorldController worldController;
    public Box2DDebugRenderer debugRenderer;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatchWDebug();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    public void render() {

        renderWorld(batch);
        debugRenderer.render(worldController.box2dWorld, camera.combined);
    }

    private void renderWorld(SpriteBatchWDebug batch) {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();

    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        debugRenderer.dispose();
    }
}
