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
    float aspect_ratio;
    public static final String TAG = cameraExercise.class.getName();

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Sprite(new Texture("isometric_map.jpg"));
        aspect_ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        overviewCamera = new OrthographicCamera(10f * aspect_ratio,10);
        overviewCamera.setToOrtho(false,
                img.getTexture().getWidth() ,
                img.getTexture().getWidth() / aspect_ratio);
        overviewCamera.position.set(overviewCamera.viewportWidth/2f,overviewCamera.viewportHeight/2f,0);

        Gdx.app.log(TAG, "Camera size: " + overviewCamera.viewportWidth + ", " + overviewCamera.viewportHeight);
        Gdx.app.log(TAG, "aspect ratio: " + aspect_ratio);
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
        aspect_ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
/*        overviewCamera.setToOrtho(false,
                img.getTexture().getWidth() ,
                img.getTexture().getWidth() / aspect_ratio);*/
        overviewCamera.setToOrtho(false,
                Gdx.graphics.getWidth() ,
                Gdx.graphics.getHeight());
        overviewCamera.update();
        Gdx.app.log(TAG, "Camera size: " + overviewCamera.viewportWidth + ", " + overviewCamera.viewportHeight);
        Gdx.app.log(TAG, "aspect ratio: " + aspect_ratio);
        Gdx.app.log(TAG, "Window size: " + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
        Gdx.app.log(TAG, "Image size: " + img.getTexture().getWidth() + ", " + img.getTexture().getHeight());
    }
}
