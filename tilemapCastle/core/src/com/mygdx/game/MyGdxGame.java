package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite sprite;
    Texture img;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    ExtendViewport vp;
    World world;
    Box2DDebugRenderer debugRenderer;
    Body player;

    float MAX_VELOCITY = 1.5f;
    private final float PPM = 100;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("Idle (1).png");
        sprite = new Sprite();
        sprite.setRegion(img);

        map = new TmxMapLoader().load("castles.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        // create an orthographic camera, shows us 30x20 units of the world
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800/PPM, 427/PPM);
        //camera.update();
        //vp = new ExtendViewport(2700/PPM,1442/PPM, camera);
        vp = new ExtendViewport(2700/PPM/2,1442/PPM/2, camera);

        world = new World(new Vector2(0, -10),true);
        debugRenderer = new Box2DDebugRenderer();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(400/PPM,300/PPM);

        player = world.createBody(bodyDef);
        PolygonShape rectShape = new PolygonShape();
        /*
        CircleShape circle = new CircleShape();
        circle.setRadius(0.1f);*/
        rectShape.setAsBox(0.1f,0.1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.1f;
        Fixture fixture = player.createFixture(fixtureDef);

        //circle.dispose();

        //TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        for (MapObject mapObject : map.getLayers().get("platforms").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set( (rect.getX() + rect.getWidth()/2)/PPM , (rect.getY() + rect.getHeight()/2)/PPM);

            Body bd = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rect.getWidth()/2/PPM , rect.getHeight()/2/PPM);

            fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixture = bd.createFixture(fixtureDef);
            Gdx.app.log("Layer Object: ", "" + rect.getX()/PPM + ", " + rect.getY()/PPM);
        }


    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();



        // set the TiledMapRenderer view based on what the
        // camera sees, and render the map
        renderer.setView(camera);
        renderer.render();
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
        world.step(1/60f,6,2);

        int tileWidth = map.getProperties().get("tilewidth",Integer.class);
        int tileHeight = map.getProperties().get("tileheight",Integer.class);
        vp.getCamera().position.set(
                MathUtils.clamp(player.getPosition().x, vp.getCamera().viewportWidth/2,
                        tileWidth*renderer.getUnitScale() - vp.getCamera().viewportWidth/2),
                MathUtils.clamp(player.getPosition().y, vp.getCamera().viewportHeight/2,
                        tileHeight*renderer.getUnitScale() - vp.getCamera().viewportHeight/2),0f);

        vp.apply();
        //sprite.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //batch.draw(img, (player.getPosition().x)*PPM, player.getPosition().y*PPM);
        //batch.draw(img, 0, 300);
        Vector2 position = vp.project(new Vector2((player.getPosition().x-0.1f), player.getPosition().y-0.1f));
        //Gdx.app.log("Player pos: ", position.x+", "+position.y);
        //batch.draw(sprite, position.x, position.y,16,32);
        //batch.draw(sprite, position.x, position.y,0,0,img.getWidth(),img.getHeight(),0.3f,0.3f,0f);
        //batch.draw(sprite, position.x, position.y);

        batch.draw(sprite, player.getPosition().x - img.getWidth()/PPM/2+0.33f, player.getPosition().y - 0.1f,0,0,img.getWidth(),img.getHeight(),1/PPM/3,1/PPM/3,0);
        batch.end();

        batch.setProjectionMatrix(vp.getCamera().combined);
        batch.begin();
        //batch.draw(img, 0, 0);

        debugRenderer.render(world,  camera.combined);
        batch.end();



    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height,true);
    }
}
