package com.exercises.camera;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class cameraExercise extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite img;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Sprite(new Texture("isometric_map.jpg"));
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        img.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.getTexture().dispose();
    }
}
