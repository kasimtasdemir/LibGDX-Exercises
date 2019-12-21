package com.camera_exercise.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CameraExercise extends ApplicationAdapter {
    ShapeRenderer shRenderer;
    SpriteBatch batch;
    Sprite imgMap;
    int imgMap_width, imgMap_height;
    OrthographicCamera overviewCam;
    OrthographicCamera closeupCam;
    float aspect_ratio;

    Viewport vp;

    @Override
    public void create () {
        shRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        imgMap = new Sprite(new Texture("sc_map.png"));
        imgMap_width = imgMap.getTexture().getWidth();
        imgMap_height = imgMap.getTexture().getHeight();
        float w = Gdx.graphics.getWidth(); //for aspect ratio
        float h = Gdx.graphics.getHeight();
        aspect_ratio = w/h;
        //overviewCam = new OrthographicCamera(imgMap_width, imgMap_width*aspect_ratio);
        //overviewCam = new OrthographicCamera(imgMap_height/aspect_ratio, imgMap_height);
        //overviewCam = new OrthographicCamera(1024*aspect_ratio,1024);
        overviewCam = new OrthographicCamera(w,h);
        //imgMap.setSize(1024*aspect_ratio,1024);

        overviewCam.position.set(overviewCam.viewportWidth/2f, overviewCam.viewportHeight/2f,0);
        //overviewCam.position.set(200, 300,0);
        overviewCam.update();
        closeupCam = new OrthographicCamera(300,300*aspect_ratio);
        closeupCam.position.set(overviewCam.viewportWidth/2f, overviewCam.viewportHeight/2f,0);
        closeupCam.update();

        vp = new ScreenViewport(closeupCam);
        //vp = new ExtendViewport(1000,1000,closeupCam);
        //vp = new FitViewport(300,100,closeupCam);
        //vp = new StretchViewport(1000,1000,closeupCam);
        //vp.setScreenBounds(0,0,30,30);
        batch.setProjectionMatrix(overviewCam.combined);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        vp.apply();
        overviewCam.update();
        closeupCam.update();


        if(Gdx.input.isKeyPressed(Input.Keys.N)){
            batch.setProjectionMatrix(closeupCam.combined);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            batch.setProjectionMatrix(overviewCam.combined);
        }
        //batch.setProjectionMatrix(overviewCam.combined);

        batch.begin();
        imgMap.draw(batch);
        batch.end();
        shRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shRenderer.setColor(Color.FIREBRICK);
        shRenderer.circle(200,300,50);
        shRenderer.end();
    }

    @Override
    public void dispose () {
        shRenderer.dispose();
        batch.dispose();
        imgMap.getTexture().dispose();
    }

    @Override
    public void resize (int width, int height) {
        vp.update(width, height,true);
        //overviewCam.update();
        //closeupCam.zoom = 0.5f;
        //vp.setScreenBounds(200,200,100,100);
        //vp.setScreenHeight(100);
        //vp.setWorldHeight(100);

    }
    public void handleInput(){
    }
}
