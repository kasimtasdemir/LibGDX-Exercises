package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final Assets instance = new Assets();
    public MyCharacterAssets myCharacterAssets;
    private AssetManager assetManager;
    private Assets(){

    }
    public void init(AssetManager am){
        this.assetManager = am;
        assetManager.setErrorListener(this);
        assetManager.load("udacity.txt", TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas atlas = assetManager.get("udacity.txt");
        myCharacterAssets = new MyCharacterAssets(atlas);

    }
    @Override
    public void dispose() {

    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.log("Assets", "Asset could not be load." + asset.fileName, throwable);
    }

    public class MyCharacterAssets{
        public final AtlasRegion standingLeft;
        public final AtlasRegion standingRight;
        public final Animation walkingLeftAnimation;

        public MyCharacterAssets(TextureAtlas atlas){
            standingLeft = atlas.findRegion("standing-left");
            standingRight = atlas.findRegion("standing-right");

            Array<AtlasRegion> walkingLeftFrames = new Array<AtlasRegion>();
            walkingLeftFrames.add(atlas.findRegion("walk-1-left"));
            walkingLeftFrames.add(atlas.findRegion("walk-2-left"));
            walkingLeftFrames.add(atlas.findRegion("walk-3-left"));
            walkingLeftFrames.add(atlas.findRegion("walk-2-left"));

            walkingLeftAnimation = new Animation(
                    0.25f,walkingLeftFrames, Animation.PlayMode.LOOP_PINGPONG);
        }
    }
}

