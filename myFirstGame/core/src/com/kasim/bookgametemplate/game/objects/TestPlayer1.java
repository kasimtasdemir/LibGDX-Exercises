package com.kasim.bookgametemplate.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.utils.TimeUtils;
import com.kasim.bookgametemplate.game.Assets;
import com.kasim.bookgametemplate.util.Constants;
import com.kasim.bookgametemplate.util.SpriteBatchWDebug;

public class TestPlayer1 extends AbstractDrawableObject {
    public static final String TAG = TestPlayer1.class.getName();
    World box2dWorld;
    Body body;
//    Body bodyFeet;
//    private Joint prismaticJoint;
    private Fixture footSensorFixture;
    float timeSinceLastJump;
    float timeSinceLastFlipDirectionRequest;
    int totalFootContacts = 0;

    public enum State {
        JUMPING,
        RUNNING,
        FALLING
    }
    public boolean onTheGround;
    public enum Direction {
        RIGHT,
        LEFT
    }
    public State state;
    public Direction direction;
    public TestPlayer1(World box2dWorld){
        this.box2dWorld = box2dWorld;
        init();
    }
    public void init() {
        state = State.RUNNING;
        direction = Direction.RIGHT;
        onTheGround = true;
        timeSinceLastJump = 0f;
        timeSinceLastFlipDirectionRequest = 0f;
        totalFootContacts = 0;
        TextureRegion tempReg = (TextureRegion) Assets.instance.testPlayer1.runningRightAnimation.getKeyFrame(0);
        dimension.set(1f, 1f/tempReg.getRegionWidth()*tempReg.getRegionHeight());
        rotation = 0;
        scale.set(1, 1);
        position.set(1.5f, 5.5f);
        positionOffset.set(-dimension.x / 2, -dimension.y / 2);
        position.add(positionOffset);
        origin.set(dimension.x / 2, dimension.y / 2);

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;

        // Create our body in the world using our body definition
        body = box2dWorld.createBody(bodyDef);


//        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(dimension.x/2f);

        // Create a polygon shape
        PolygonShape box = new PolygonShape();
        // (setAsBox takes half-width and half-height as arguments)
        box.setAsBox(dimension.x/2f, dimension.y/2f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.3f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // foot sensor
        box.setAsBox(dimension.x/2.5f, dimension.y/10f, new Vector2(0f,-dimension.y/2f),0f);
        fixtureDef.shape = box;
        fixtureDef.isSensor = true;
        footSensorFixture = body.createFixture(fixtureDef);
        footSensorFixture.setUserData(3);

        box2dWorld.setContactListener(new ContactListener() {


            @Override
            public void beginContact(Contact contact) {

                if (contact.getFixtureA().getUserData()!=null && ((int)contact.getFixtureA().getUserData() == 3)) {
                    if (onTheGround==false)
                        Gdx.app.log("onTheGround"," = true");
                    onTheGround = true;
                    totalFootContacts++;
                }
                if (contact.getFixtureB().getUserData()!=null && ((int)contact.getFixtureB().getUserData() == 3)){
                    if (onTheGround==false)
                        Gdx.app.log("onTheGround"," = true");
                    onTheGround = true;
                    totalFootContacts++;

                }
                if (totalFootContacts==0){
                    if (onTheGround==true)
                        Gdx.app.log("onTheGround"," = false");
                    onTheGround = false;
                }

                //Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void endContact(Contact contact) {
                if (contact.getFixtureA().getUserData()!=null && ((int)contact.getFixtureA().getUserData() == 3)) {
                    totalFootContacts--;
                    if (timeSinceLastJump<0.1){
                        contact.getFixtureB().getBody().applyLinearImpulse(new Vector2(0f,-0.2f),
                                contact.getFixtureA().getBody().getWorldCenter(),false);
                    }
                }
                if (contact.getFixtureB().getUserData()!=null && ((int)contact.getFixtureB().getUserData() == 3)){
                    totalFootContacts--;
                    if (timeSinceLastJump<0.1){
                        contact.getFixtureA().getBody().applyLinearImpulse(new Vector2(0f,-0.2f),
                                contact.getFixtureB().getBody().getWorldCenter(),false);
                    }
                }
                if (totalFootContacts==0){
                    if (onTheGround==true)
                        Gdx.app.log("onTheGround"," = false");
                    onTheGround = false;
                }
                //Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
//        // feet
//        bodyFeet = box2dWorld.createBody(bodyDef);
//        box.setAsBox(dimension.x/2.5f, dimension.y/10f);
//        fixtureDef.shape = box;
//        bodyFeet.createFixture(fixtureDef);
//
//        PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
//        prismaticJointDef.bodyA = body;
//        prismaticJointDef.bodyB = bodyFeet;
//        prismaticJointDef.collideConnected = false;
//        prismaticJointDef.localAxisA.set(0f,1f);
//        prismaticJointDef.localAxisA.nor();
//        prismaticJointDef.localAnchorA.set(0, -dimension.y/2f);
//        prismaticJointDef.localAnchorB.set(0,-dimension.y/10f);
//        prismaticJointDef.enableLimit = true;
//        prismaticJointDef.lowerTranslation = -dimension.y/10f;
//        prismaticJointDef.upperTranslation = dimension.y/10f;
//        prismaticJointDef.enableMotor = true;
//        prismaticJointDef.motorSpeed = 3f;
//        prismaticJointDef.maxMotorForce = 3f;
//        prismaticJoint = box2dWorld.createJoint(prismaticJointDef);


        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        box.dispose();
        circle.dispose();

        body.setLinearVelocity(0.03f, 0.3f);
    }
    @Override
    public void render(SpriteBatchWDebug batch) {
        boolean flipX = false;
        if (direction == Direction.LEFT)
            flipX = true;
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
                flipX, false);

    }
    public void update(float deltaTime){
        Vector2 bodyVelocity = body.getLinearVelocity();
        timeSinceLastJump = timeSinceLastJump + deltaTime;
        timeSinceLastFlipDirectionRequest = timeSinceLastFlipDirectionRequest + deltaTime;
        switch (state){
            case RUNNING:
                //Gdx.app.debug(TAG, "Player state: " + "RUNNING");
                break;
            case JUMPING:

                break;
        }

        switch (direction){
            case RIGHT:
                if (bodyVelocity.x < Constants.MAX_PLAYER_HORIZONTAL_VELOCITY )
                    body.applyForceToCenter(Constants.PLAYER_RUNNING_FORCE_X,0f,true);
                break;
            case LEFT:
                if (bodyVelocity.x > -Constants.MAX_PLAYER_HORIZONTAL_VELOCITY )
                    body.applyForceToCenter(-Constants.PLAYER_RUNNING_FORCE_X,0f,true);
                break;
        }
        /*body.setLinearVelocity(MathUtils.clamp(body.getLinearVelocity().x,
                -Constants.MAX_PLAYER_HORIZONTAL_VELOCITY,  Constants.MAX_PLAYER_HORIZONTAL_VELOCITY),
                MathUtils.clamp(body.getLinearVelocity().y,
                        -Constants.MAX_PLAYER_VELOCITY,  Constants.MAX_PLAYER_VELOCITY));*/


    }

    public void UserRequest_Jump() {
        if (timeSinceLastFlipDirectionRequest <= 0.25f) {
            if (direction == Direction.RIGHT)
                direction = Direction.LEFT;
            else
                direction = Direction.RIGHT;
            body.setLinearVelocity(body.getLinearVelocity().x, Math.min(0f,body.getLinearVelocity().y));
            Gdx.app.debug(TAG, "double click time: " + timeSinceLastFlipDirectionRequest );
        }
        else if (onTheGround  && timeSinceLastJump > 0.25f) {
            body.setLinearVelocity(body.getLinearVelocity().x, Constants.PLAYER_JUMPING_VELOCITY);
            onTheGround = false;
            timeSinceLastJump = 0;
            Gdx.app.debug(TAG, "Just Jumped");
        }
        timeSinceLastFlipDirectionRequest = 0;

        //bodyFeet.setLinearVelocity(bodyFeet.getLinearVelocity().x, -15f);
        //bodyFeet.applyLinearImpulse(0,-1f,bodyFeet.getWorldCenter().x, bodyFeet.getWorldCenter().y,true);
        //body.applyLinearImpulse(0,1f,body.getWorldCenter().x, body.getWorldCenter().y,true);


    }
}
