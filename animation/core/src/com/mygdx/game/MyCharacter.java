package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MyCharacter {
    public MyCharacter(){

    }
    public void update(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moveRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveLeft();
        } else {

        }

    }
    public void render(SpriteBatch batch){
        TextureRegion region = Assets.instance.myCharacterAssets.standingLeft;
        float walkTimeSeconds = MathUtils.nanoToSec * TimeUtils.nanoTime();
        region = (TextureRegion) Assets.instance.myCharacterAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
                batch.begin();
        batch.draw(
                region.getTexture(),
                100,
                100,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                5,
                5,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
        batch.end();

    }
    public void moveRight(){

    }
    public void moveLeft(){

    }
}
