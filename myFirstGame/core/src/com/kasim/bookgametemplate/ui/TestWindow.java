package com.kasim.bookgametemplate.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kasim.bookgametemplate.game.Assets;
import com.kasim.bookgametemplate.screen.GameScreen;

public class TestWindow {
    private Skin skin;
    private GameScreen gameScreen;
    public TestWindow(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }
    public void show(){
        skin = Assets.instance.uiSkin.skin;
        // Table window = new Table();
        final Window window =  new Window("Test Title",skin);
        //window.setFillParent(true);

        gameScreen.stage.addActor(window);
        final TextButton button = new TextButton("Click me!", skin);
        window.add(button);
        window.add(new Image(skin.newDrawable("white", Color.RED))).size(64);

        //window.debugTable();
        window.debugAll();

        Gdx.input.setInputProcessor(gameScreen.stage);

        button.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + button.isChecked());
                button.setText("Good job!");
                //window.setRotation(45);
                window.setVisible(false);
                Gdx.input.setInputProcessor(gameScreen.inputManager);
            }
        });

    }
    public void hide(){

    }
}
