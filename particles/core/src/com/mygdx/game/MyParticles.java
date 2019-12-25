package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rockbite.tools.talos.runtime.ParticleEffectDescriptor;
import com.rockbite.tools.talos.runtime.ParticleEffectInstance;
import com.rockbite.tools.talos.runtime.ScopePayload;
import com.rockbite.tools.talos.runtime.assets.BaseAssetProvider;
import com.rockbite.tools.talos.runtime.render.ParticleRenderer;
import com.rockbite.tools.talos.runtime.render.SpriteBatchParticleRenderer;

public class MyParticles extends ApplicationAdapter {
	SpriteBatch batch;
	// ParticleEffect pe;
	ParticleEffectInstance particleEffect;
	ParticleRenderer particleRenderer;
	ParticleEffectDescriptor particleEffectDescriptor;
	//private static ScopePayload scope = new ScopePayload();
	OrthographicCamera particleCamera;



	@Override
	public void create () {
		batch = new SpriteBatch();
		/* pe = new ParticleEffect();
		pe.load(Gdx.files.internal("particles.p"), Gdx.files.internal(""));
		pe.getEmitters().first().setPosition(Gdx.graphics.getWidth()*0,Gdx.graphics.getHeight()/2);
		pe.start();*/
		particleEffectDescriptor = new ParticleEffectDescriptor();
		TextureAtlas atlas = new TextureAtlas();
		atlas.addRegion("fire", new TextureRegion(new TextureRegion(new Texture(Gdx.files.internal("fire.png")))));
		particleEffectDescriptor.setAssetProvider(new TestAssetProvider(atlas));
		particleEffectDescriptor.load(Gdx.files.internal("talos.p"));
		particleEffect = particleEffectDescriptor.createEffectInstance();
		//particleEffect.setScope(scope);
		particleEffect.loopable = true;
		particleRenderer = new SpriteBatchParticleRenderer(batch);

		float particleCameraWidth=10f, particleCameraHeight=10f;

		particleCamera = new OrthographicCamera(particleCameraWidth,particleCameraHeight);
		particleCamera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* float pex = pe.getEmitters().first().getX();
		float pey = pe.getEmitters().first().getY();
		pe.getEmitters().first().setPosition(pex+5, pey);
		pe.update(Gdx.graphics.getDeltaTime());*/
		particleEffect.update(Gdx.graphics.getDeltaTime());

		particleCamera.update();
		batch.setProjectionMatrix(particleCamera.combined);
		batch.begin();
		//pe.draw(batch);
		particleRenderer.render(particleEffect);
		batch.end();
		/*if (pe.isComplete()){
			pe.reset();
		}*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	@Override
	public  void resize(int width, int height){
		//particleEffect.restart();
		//particleEffect.resume();
		particleEffect.setPosition(width/2, height/2 - 4);
		particleCamera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);

	}


	public class TestAssetProvider extends BaseAssetProvider {

		private final TextureAtlas atlas;

		public TestAssetProvider (final TextureAtlas atlas) {
			this.atlas = atlas;

			setAssetHandler(TextureRegion.class, new AssetHandler<TextureRegion>() {
				@Override
				public TextureRegion findAsset (String assetName) {
					return atlas.findRegion(assetName);
				}
			});

			setAssetHandler(Sprite.class, new AssetHandler<Sprite>() {
				@Override
				public Sprite findAsset (String assetName) {
					return atlas.createSprite(assetName);
				}
			});
		}
	}
}
