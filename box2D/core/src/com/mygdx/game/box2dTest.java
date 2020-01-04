package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class box2dTest extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camera;
	Viewport vp;
	Body player;
	float MAX_VELOCITY = 1.5f;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(20,20);//Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//camera.position.set( 10f,10f,0); //Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2f,0);
		vp = new ExtendViewport(5,5,camera);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		world = new World(new Vector2(0, -10),true);
		debugRenderer = new Box2DDebugRenderer();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(-2,2);

		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(0.1f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.6f;
		Fixture fixture = body.createFixture(fixtureDef);

		circle.dispose();

		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
// Set its world position
		groundBodyDef.position.set(new Vector2(0, 0));

// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(camera.viewportWidth, 0.2f);
// Create a fixture from our polygon shape and add it to our ground body
		groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
		groundBox.dispose();


		Vector2 pos = body.getPosition();
		// Apply a force of 1 meter per second on the X-axis at pos.x/pos.y of the body slowly moving it right
		//body.applyForce(0.2f, 0.0f, pos.x, pos.y, true);

// If we always want to apply force at the center of the body, use the following
		//body.applyForceToCenter(0.2f, 0.0f, true);
		// Immediately set the X-velocity to 1 meter per second causing the body to move right quickly
		body.applyLinearImpulse(0.002f, 0.01f, pos.x, pos.y, false);

		//Defining a player
		//bodyDef = new BodyDef();
		bodyDef.type =  BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(-1f,1f);

		player = world.createBody(bodyDef);
		circle = new  CircleShape();
		circle.setRadius(0.2f);
		//fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.6f;
		fixture = player.createFixture(fixtureDef);

		circle.dispose();

		// Joint
		// bodyJointA
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0f,2f);
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(0.1f,0.1f);
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.3f;
		Body bodyJointA = world.createBody(bodyDef);
		bodyJointA.createFixture(fixtureDef);

		//bodyJointB
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(1f,3f);
		polygonShape.setAsBox(0.1f,0.1f);
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.3f;
		Body bodyJointB = world.createBody(bodyDef);
		bodyJointB.createFixture(fixtureDef);

		//Joint
		DistanceJointDef defJoint = new DistanceJointDef ();
		defJoint.length = 0;
		defJoint.initialize(bodyJointA, bodyJointB, bodyJointA.getPosition(),
				new Vector2(bodyJointB.getPosition().x,bodyJointB.getPosition().y +0.1f) );

		DistanceJoint joint = (DistanceJoint) world.createJoint(defJoint);

		// Friction joint
		// bodyJointA
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(2f,2f);
		polygonShape = new PolygonShape();
		polygonShape.setAsBox(0.1f,0.1f);
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.3f;
		Body bodyFrictionA = world.createBody(bodyDef);
		bodyFrictionA.createFixture(fixtureDef);

		//bodyJointB
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(3f,2f);
		polygonShape.setAsBox(0.1f,0.1f);
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.3f;
		Body bodyFrictionB = world.createBody(bodyDef);
		bodyFrictionB.createFixture(fixtureDef);

		//Friction Joint
		FrictionJointDef frictionJointDef = new FrictionJointDef ();
		frictionJointDef.maxForce = 0.5f;
		frictionJointDef.maxTorque = 0.5f;
		frictionJointDef.initialize(bodyFrictionA,bodyFrictionB,new Vector2(2.5f,3f));

		FrictionJoint frictionJoint = (FrictionJoint) world.createJoint(frictionJointDef);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Vector2 vel = player.getLinearVelocity();
		Vector2 pos = player.getPosition();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && vel.x > -MAX_VELOCITY){
			player.applyLinearImpulse(-0.01f, 0, pos.x, pos.y, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && vel.x < MAX_VELOCITY){
			player.applyLinearImpulse(0.007f, 0, pos.x, pos.y, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && vel.y < MAX_VELOCITY){
			player.applyLinearImpulse(0f, 0.01f, pos.x, pos.y, true);
		}

		vp.apply();
		world.step(1/60f,6,2);

		batch.begin();
		//batch.draw(img, 0, 0);

		debugRenderer.render(world,  camera.combined);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		world.dispose();
		debugRenderer.dispose();
	}
	@Override
	public void resize(int width, int height){
		vp.update(width,height,false);
	}
}
