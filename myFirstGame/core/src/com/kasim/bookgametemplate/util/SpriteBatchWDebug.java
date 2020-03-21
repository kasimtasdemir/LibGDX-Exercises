package com.kasim.bookgametemplate.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    void drawDebugBox(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation){
        if (!Constants.RENDER_DEBUG_ENABLED)
            return;
        Pixmap pixmap = createPixmapBox(50,(int) (50/width*height));//(texture.getWidth()/4, texture.getHeight()/4);
        Texture boxTexture = new Texture(pixmap);
        Sprite spr = new Sprite(boxTexture);
        //spr.setSize(width, height);
        //spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
        super.draw(spr,x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }
    private Pixmap createPixmapBox (int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        // Fill square with red color at 50% opacity
        //pixmap.setColor(1, 0, 0, 0.5f);
        //pixmap.fill();
        // Draw a yellow-colored X shape on square
        /*pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);*/
        // Draw a cyan-colored border around square
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }
}
