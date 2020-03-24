package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasim.bookgametemplate.game.Assets;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class TestPlayer1 extends AbstractDrawableObject {
    World box2dWorld;
    Body body;
    public TestPlayer1(World box2dWorld){
        this.box2dWorld = box2dWorld;
        init();
    }
    public void init() {
        TextureRegion tempReg = (TextureRegion) Assets.instance.testPlayer1.runningRightAnimation.getKeyFrame(0);
        dimension.set(1f, 1f/tempReg.getRegionWidth()*tempReg.getRegionHeight());
        rotation = 0;
        scale.set(1, 1);
        position.set(1.5f, 3.5f);
        positionOffset.set(-dimension.x / 2, -dimension.y / 2);
        position.add(positionOffset);
        origin.set(dimension.x / 2, dimension.y / 2);

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(position);

        // Create our body in the world using our body definition
        body = box2dWorld.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(dimension.x/2f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.8f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        body.setLinearVelocity(0.03f, 0.3f);
    }
    @Override
    public void render(SpriteBatchWDebug batch) {
        TextureRegion region;
        float runTimeSeconds = MathUtils.nanoToSec * TimeUtils.nanoTime();
        region = (TextureRegion) Assets.instance.testPlayer1.runningRightAnimation.getKeyFrame(runTimeSeconds*3);
        position = body.getPosition().add(positionOffset);
        rotation = body.getAngle()* MathUtils.radiansToDegrees;
        batch.draw(region.getTexture(),
                position.x, position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                0,//rotation,
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight(),
                false, false);

    }
}
