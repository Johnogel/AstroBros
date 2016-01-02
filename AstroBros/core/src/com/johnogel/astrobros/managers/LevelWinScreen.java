/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 *
 * @author johno-gel
 */
public class LevelWinScreen extends GameScreen{

    private final BitmapFont font;
    private CharSequence game_over, timer_chars;
    private GlyphLayout layout;
    final float fontX;
    final float fontY;
    private int level;
    
    public LevelWinScreen(GameManager mngr){
    
        super(mngr);
        
        font = new BitmapFont(Gdx.files.internal("data/score.fnt"));
        font.getData().setScale(0.3f, 0.3f);  
        game_over = "LEVEL PASSED";
        layout = new GlyphLayout(font, game_over);
        fontX =  -layout.width / 2;
        fontY =  10;
    
    }

    @Override
    public void update() {
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            mngr.incrementLevel();
        }
        ray_handler.setCombinedMatrix(camera.combined);
        camera.rotate(1.3f);
        camera.update();
    
    }

    @Override
    public void render() {
        
        batch.setProjectionMatrix(camera.projection);
        
        batch.begin();
        font.draw(batch, layout, fontX, fontY);
        batch.end();
    
    }

    @Override
    public void initialize() {
        initializeWorld();
        updateReferences();
        PointLight light = new PointLight(ray_handler, 5000, Color.GREEN, 700, camera.viewportWidth/2, -300 );

        
    }

    
}
