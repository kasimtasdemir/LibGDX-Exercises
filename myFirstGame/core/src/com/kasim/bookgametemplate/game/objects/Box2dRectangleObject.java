package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class Box2dRectangleObject extends AbstractDrawableObject {
    World box2dWorld;
    Body body;
    private TextureRegion textureRegion;
    private DrawableTile drawableTile;

    public Box2dRectangleObject(World box2dWorld, DrawableTile drawableTile){
        this.drawableTile = drawableTile;
        this.box2dWorld = box2dWorld;
        // default values
        dimension = drawableTile.dimension;
        rotation = drawableTile.rotation;
        scale = drawableTile.scale;
        position = drawableTile.position;

        positionOffset = drawableTile.positionOffset;
        position.sub(positionOffset);
        origin = drawableTile.origin;

        textureRegion = drawableTile.getTextureRegion(); //drawableObject.textureRegion;

        init();
    }

    public void init() {

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(position);
        //bodyDef.position.set(position.x - dimension.x/2f, position.y - dimension.y/2f);

        // Create our body in the world using our body definition
        body = box2dWorld.createBody(bodyDef);

// Create a polygon shape
        PolygonShape box = new PolygonShape();
        // (setAsBox takes half-width and half-height as arguments)
        box.setAsBox(dimension.x/2f, dimension.y/2f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        box.dispose();

        body.setLinearVelocity(0.2f, 0.3f);
    }

    @Override
    public void render(SpriteBatchWDebug batch) {
        textureRegion = drawableTile.getTextureRegion();
        position = body.getPosition().add(positionOffset);
        rotation = body.getAngle()* MathUtils.radiansToDegrees;
        batch.draw(textureRegion.getTexture(),
                position.x, position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                textureRegion.getRegionX(), textureRegion.getRegionY(),
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                false, false);

    }
}
