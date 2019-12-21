package com.exercises.camera;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class cameraExercise extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite img;
    OrthographicCamera overviewCamera;
    public static final String TAG = cameraExercise.class.getName();

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Sprite(new Texture("isometric_map.jpg"));
        overviewCamera = new OrthographicCamera();
        Gdx.app.log(TAG, "Camera size: " + overviewCamera.viewportWidth + ", " + overviewCamera.viewportHeight);

        Gdx.app.log(TAG, "Window size: " + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
        Gdx.app.log(TAG, "Image size: " + img.getTexture().getWidth() + ", " + img.getTexture().getHeight());
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        overviewCamera.update();
        batch.setProjectionMatrix(overviewCamera.combined);
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
    public void resize (int width, int height){

    }
}
