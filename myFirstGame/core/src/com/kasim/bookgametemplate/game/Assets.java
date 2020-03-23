package com.kasim.bookgametemplate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kasim.bookgametemplate.util.Constants;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    // objects
    public AssetTestObjectBox testObjectBox;
    public AssetTestPlayer1 testPlayer1;
    public AssetUISkin uiSkin;

    private static final Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    // singleton: prevent instantiation from other classes
    private Assets() {
    }


    public void init (AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        // Load skins
        //assetManager.load(Constants.TEXTURE_ATLAS_SKIN_LIBGDX_UI, TextureAtlas.class);
        assetManager.load(Constants.SKIN_LIBGDX_UI, Skin.class, new SkinLoader.SkinParameter(Constants.TEXTURE_ATLAS_SKIN_LIBGDX_UI));

        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            Gdx.app.debug(TAG, "Atlas Texture: " + t);
        }

        Skin skin = assetManager.get(Constants.SKIN_LIBGDX_UI);

        testObjectBox = new AssetTestObjectBox(atlas);
        testPlayer1 = new AssetTestPlayer1(atlas);
        uiSkin = new AssetUISkin(skin);


    }
    @Override
    public void dispose () {
        assetManager.dispose(); }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }

    // Objects
    public class AssetTestObjectBox {
        public final TextureAtlas.AtlasRegion box;

        public AssetTestObjectBox(TextureAtlas atlas) {
            // box = atlas.findRegion("crate");
            box = atlas.findRegion("wall_mid");

            Gdx.app.debug(TAG, "test box width: " + box.getRegionWidth());
        }
    }
    public class AssetTestPlayer1{
        public Animation runningRightAnimation;
        public AssetTestPlayer1(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> runningRightFrames = new Array<TextureAtlas.AtlasRegion>();
            runningRightFrames.add(atlas.findRegion("big_zombie_run_anim_f0"));
            runningRightFrames.add(atlas.findRegion("big_zombie_run_anim_f1"));
            runningRightFrames.add(atlas.findRegion("big_zombie_run_anim_f2"));
            runningRightFrames.add(atlas.findRegion("big_zombie_run_anim_f3"));

            runningRightAnimation = new Animation(
                    0.25f, runningRightFrames, Animation.PlayMode.LOOP);

            Gdx.app.debug(TAG, "Player 1 run animation loaded." );
        }

    }
    public class AssetUISkin {
        //public final TextureAtlas.AtlasRegion box;
        public Skin skin;

        public AssetUISkin(Skin skin) {
            this.skin = skin;
            //Gdx.app.debug(TAG, "test box width: " + box.getRegionWidth());
        }
    }

}
