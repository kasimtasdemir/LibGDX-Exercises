package com.exercises.camera;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class cameraExercise extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite img;
    OrthographicCamera overviewCamera;
    OrthographicCamera closeupCamera;
    float aspect_ratio;
    boolean closeupCameraIsSelected;
    public static final String TAG = cameraExercise.class.getName();
    ShapeRenderer shRenderer;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Sprite(new Texture("isometric_map.jpg"));
        aspect_ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        overviewCamera = new OrthographicCamera(10f * aspect_ratio,10);

        closeupCamera = new OrthographicCamera();
        closeupCameraIsSelected = false;

        shRenderer = new ShapeRenderer();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        overviewCamera.update();
        closeupCamera.update();
        float delta = Gdx.graphics.getDeltaTime();

        // Below, left-top, left-bottom, right-top, right-bottom coordinates of closeupCamera mapped to
        // real world positions.
        Vector3 lt = closeupCamera.unproject(new Vector3(1,1,0));
        Vector3 lb = closeupCamera.unproject(new Vector3(1, closeupCamera.viewportHeight,0));
        Vector3 rt = closeupCamera.unproject(new Vector3(closeupCamera.viewportWidth,1,0));
        Vector3 rb = closeupCamera.unproject(new Vector3(closeupCamera.viewportWidth, closeupCamera.viewportHeight,0));

        if (Gdx.input.isKeyPressed(Input.Keys.M)){
            closeupCameraIsSelected = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.N)){
            closeupCameraIsSelected = true;
        }

        if (closeupCameraIsSelected){
            batch.setProjectionMatrix(closeupCamera.combined);
        } else {
            batch.setProjectionMatrix(overviewCamera.combined);
        }

        batch.begin();
        img.draw(batch);

        batch.end();
        shRenderer.setProjectionMatrix(overviewCamera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        shRenderer.setColor(Color.RED);
        if (!closeupCameraIsSelected){
            shRenderer.rect(lb.x, lb.y, (rb.x - lb.x), (lt.y - lb.y));
        }
        shRenderer.end();
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
                img.getTexture().getHeight() * aspect_ratio ,
                img.getTexture().getHeight());

        overviewCamera.position.set(overviewCamera.viewportWidth/2f,overviewCamera.viewportHeight/2f,0);
        overviewCamera.update();

        closeupCamera.setToOrtho(false,
                Gdx.graphics.getWidth() ,
                Gdx.graphics.getHeight());
        closeupCamera.position.set(closeupCamera.viewportWidth/2f, closeupCamera.viewportHeight/2f, 0);
        closeupCamera.zoom = 0.5f;

    }
}
