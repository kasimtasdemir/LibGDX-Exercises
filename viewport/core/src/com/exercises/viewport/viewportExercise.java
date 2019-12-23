package com.exercises.viewport;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class viewportExercise extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite img;
    Sprite logo;
    OrthographicCamera ortCamera;
    Viewport viewport;
    Viewport hudViewport;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Sprite (new Texture("isometric_map.jpg"));
        logo = new Sprite (new Texture("coollogo_com.png"));
        ortCamera = new OrthographicCamera();
        //viewport = new ScreenViewport(ortCamera);
        //viewport = new ExtendViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);
        viewport = new StretchViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);
        //viewport = new FitViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);
        //viewport = new FillViewport(img.getTexture().getWidth(), img.getTexture().getHeight(),ortCamera);

        hudViewport = new ExtendViewport(logo.getTexture().getWidth(),logo.getTexture().getHeight());
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

        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();
        logo.draw(batch);
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
        hudViewport.update(width, height, true);

        Vector3 logoHeightV3 = hudViewport.project(new Vector3(0,logo.getTexture().getHeight(),0));
        int logoHeight = (int)logoHeightV3.y;
        hudViewport.setScreenY(MathUtils.clamp(height - logoHeight,
                0, height));
    }
}
