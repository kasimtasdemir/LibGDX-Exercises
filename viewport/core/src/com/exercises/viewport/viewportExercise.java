package com.exercises.viewport;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class viewportExercise extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite img;
    OrthographicCamera ortCamera;
    Viewport viewport;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Sprite (new Texture("isometric_map.jpg"));
        ortCamera = new OrthographicCamera();
        viewport = new ScreenViewport(ortCamera);
        //viewport = new ExtendViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);
        //viewport = new StretchViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);
        //viewport = new FitViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);
        //viewport = new FillViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);
        viewport.setScreenBounds(0,0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(ortCamera.combined);
        batch.begin();
        img.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.getTexture().dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true ); // the 3rd parameter centers the view
                                                            // However, if it is centered, you cannot move the camera
    }
}
