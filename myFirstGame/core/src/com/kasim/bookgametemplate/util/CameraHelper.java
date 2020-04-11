package com.kasim.bookgametemplate.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kasim.bookgametemplate.game.Level;
import com.kasim.bookgametemplate.game.objects.AbstractDrawableObject;

import static com.badlogic.gdx.math.MathUtils.lerp;

public class CameraHelper {
    private static final String TAG = CameraHelper.class.getName();
    private final float MAX_ZOOM_IN = 0.25f;
    private final float MAX_ZOOM_OUT = 10.0f;
    private Vector2 position;
    private float zoom;
    private AbstractDrawableObject target;
    private Rectangle boundaries;

    public CameraHelper() {
        position = new Vector2();
        zoom = 0.8f;
    }

    public void update(float deltaTime) {
        if (!hasTarget()) return;
        //position.x = target.position.x + target.origin.x;
        //position.y = target.position.y + target.origin.y;
        position.x = lerp(position.x,target.position.x + target.origin.x, 0.2f);
        position.y = lerp(position.y,target.position.y + target.origin.y, 0.2f);

    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void addZoom(float amount) {
        setZoom(zoom + amount);
    }

    public void setZoom(float zoom) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom() {
        return zoom;
    }

    public void setTarget(AbstractDrawableObject target) {
        this.target = target;
    }

    public AbstractDrawableObject getTarget() {
        return target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public boolean hasTarget(Sprite target) {
        return hasTarget() && this.target.equals(target);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        restrictIntoBoundaries(camera);
        camera.update();
    }

    private void restrictIntoBoundaries(OrthographicCamera cam) {
        // The camera dimensions, halved
        float cameraHalfWidth = cam.viewportWidth * .5f * zoom;
        float cameraHalfHeight = cam.viewportHeight * .5f * zoom;

// Move camera after player as normal


        cam.position.x = MathUtils.clamp(cam.position.x, cameraHalfWidth + boundaries.x,  + boundaries.width - cameraHalfWidth);
        cam.position.y = MathUtils.clamp(cam.position.y, cameraHalfHeight + boundaries.y, boundaries.height - cameraHalfHeight);
    }


    public void setBoundaries(Rectangle tilemapBoundaries) {
        boundaries = tilemapBoundaries;
    }
}
