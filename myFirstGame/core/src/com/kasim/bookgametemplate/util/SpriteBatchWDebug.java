package com.kasim.bookgametemplate.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SpriteBatchWDebug extends SpriteBatch {
    @Override
    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        super.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        drawDebugBox(region.getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    @Override
    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        super.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
        drawDebugBox(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    void drawDebugBox(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        if (!Constants.RENDER_DEBUG_ENABLED)
            return;
        // Pixmap pixmap = createPixmapBox(50, (int) (50 / width * height));//(texture.getWidth()/4, texture.getHeight()/4);


        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(super.getProjectionMatrix());

        super.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1,0.2f,0.0f,1f);
        //shapeRenderer.identity();
        shapeRenderer.rect(x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        shapeRenderer.end();
        shapeRenderer.dispose();

        super.begin();

        //Texture boxTexture = new Texture(pixmap);
        //Sprite spr = new Sprite(boxTexture);
        //super.draw(spr, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        //boxTexture.dispose(); // TODO: I think this is leaking. Must dispose boxTexture
        //pixmap.dispose();
    }

    private Pixmap createPixmapBox(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

}
