package com.exercises.camera;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    OrthographicCamera closeupCamera;
    float aspect_ratio;
    boolean closeupCameraIsSelected;
    public static final String TAG = cameraExercise.class.getName();

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Sprite(new Texture("isometric_map.jpg"));
        aspect_ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        overviewCamera = new OrthographicCamera(10f * aspect_ratio,10);

        closeupCamera = new OrthographicCamera();
        closeupCameraIsSelected = false;
        Gdx.app.log(TAG, "Camera size: " + overviewCamera.viewportWidth + ", " + overviewCamera.viewportHeight);
        Gdx.app.log(TAG, "aspect ratio: " + aspect_ratio);
        Gdx.app.log(TAG, "Window size: " + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
        Gdx.app.log(TAG, "Image size: " + img.getTexture().getWidth() + ", " + img.getTexture().getHeight());
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.M)){
            closeupCameraIsSelected = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.N)){
            closeupCameraIsSelected = true;
        }
        overviewCamera.update();
        closeupCamera.update();
        if (closeupCameraIsSelected){
            batch.setProjectionMatrix(closeupCamera.combined);
        } else {
            batch.setProjectionMatrix(overviewCamera.combined);
        }

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
        aspect_ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

        overviewCamera.setToOrtho(false,
                Gdx.graphics.getWidth() ,
                Gdx.graphics.getHeight());
        overviewCamera.position.set(overviewCamera.viewportWidth/2f,overviewCamera.viewportHeight/2f,0);
        overviewCamera.update();

        closeupCamera.setToOrtho(false,
                Gdx.graphics.getWidth() ,
                Gdx.graphics.getHeight());
        closeupCamera.position.set(closeupCamera.viewportWidth/2f, closeupCamera.viewportHeight/2f, 0);
        closeupCamera.zoom = 0.5f;

        Gdx.app.log(TAG, "Overview Camera size: " + overviewCamera.viewportWidth + ", " + overviewCamera.viewportHeight);
        Gdx.app.log(TAG, "Closeup Camera size: " + closeupCamera.viewportWidth + ", " + closeupCamera.viewportHeight);
        Gdx.app.log(TAG, "aspect ratio: " + aspect_ratio);
        Gdx.app.log(TAG, "Window size: " + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
        Gdx.app.log(TAG, "Image size: " + img.getTexture().getWidth() + ", " + img.getTexture().getHeight());
    }
}
