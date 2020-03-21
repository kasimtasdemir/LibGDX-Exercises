package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.kasim.bookgametemplate.util.CameraHelper;

public class WorldController extends InputAdapter {
    private static final String TAG =
            WorldController.class.getName();

    // Test
    public Sprite[] testSprites;
    public int selectedSprite;
    public CameraHelper cameraHelper;
    public Level level;

    public WorldController () {
        init();
    }
    private void init () {
        initTestObjects();
        level = new Level();
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
    }

    private void initTestObjects() {
        // Create new array for 5 sprites
        /*testSprites = new Sprite[5];
        // Create empty POT-sized Pixmap with 8 bit RGBA pixel data
        int width = 32;
        int height = 32;
        Pixmap pixmap = createProceduralPixmap(width, height);
        // Create a new texture from pixmap data
        Texture texture = new Texture(pixmap);
        // Create new sprites using the just created texture
        for (int i = 0; i < testSprites.length; i++) {
            Sprite spr = new Sprite(texture);
            // Define sprite size to be 1m x 1m in game world
            spr.setSize(1, 1);
            // Set origin to sprite's center
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
            // Calculate random position for sprite
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            spr.setPosition(randomX, randomY);
            // Put new sprite into array
            testSprites[i] = spr;
        }
        // Set first sprite as selected one
        selectedSprite = 0;

        Array<TextureRegion> regions = new Array<TextureRegion>();
        regions.add(Assets.instance.testObjectBox.box);


        Sprite spr = new Sprite(regions.random());
        spr.setSize(1, 1);
        // Set origin to sprite's center
        spr.setOrigin(spr.getWidth() / 2.0f,spr.getHeight() / 2.0f);
        // Calculate random position for sprite
        float randomX = MathUtils.random(-2.0f, 2.0f);
        float randomY = MathUtils.random(-2.0f, 2.0f);
        spr.setPosition(randomX, randomY);
        testSprites[0] = spr;*/
    }
    private Pixmap createProceduralPixmap (int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        // Fill square with red color at 50% opacity
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        // Draw a yellow-colored X shape on square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);
        // Draw a cyan-colored border around square
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }
    public void update (float deltaTime) {
        //updateTestObjects(deltaTime);
        level.update(deltaTime);
        cameraHelper.update(deltaTime);
    }
    /*private void updateTestObjects(float deltaTime) {
        // Get current rotation from selected sprite
        float rotation = testSprites[selectedSprite].getRotation();
        // Rotate sprite by 90 degrees per second
        rotation += 90 * deltaTime;
        // Wrap around at 360 degrees
        rotation %= 360;
        // Set new rotation value to selected sprite
        testSprites[selectedSprite].setRotation(rotation);
    }*/

    @Override
    public boolean keyDown(int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }
        // Select next sprite
        else if (keycode == Input.Keys.SPACE) {
            selectedSprite = (selectedSprite + 1) % testSprites.length;
            Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
            if (cameraHelper.hasTarget()) {
                cameraHelper.setTarget(testSprites[selectedSprite]);
            }

        }
        // Toggle camera follow
        else if (keycode == Input.Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        return false;
    }
}
