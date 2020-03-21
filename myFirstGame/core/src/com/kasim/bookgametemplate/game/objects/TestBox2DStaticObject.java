package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class TestBox2DStaticObject extends AbstractDrawableObject {
    World box2dWorld;
    public TestBox2DStaticObject(World box2dWorld){
        this.box2dWorld = box2dWorld;
        init();
    }
    public void init(){
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, -2));

        // Create a body from the defintion and add it to the world
        Body groundBody = box2dWorld.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(3f, 0.5f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
    }

    @Override
    public void render(SpriteBatchWDebug batch) {

    }
}
