/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers.screens;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.johnogel.astrobros.managers.GameManager;
import com.johnogel.astrobros.support.SoundPlayer;

/**
 *
 * @author johno-gel
 */
public class LevelWinScreen extends GameScreen{

    private final BitmapFont font;
    private final CharSequence game_over, press_space;
    private CharSequence score;
    private GlyphLayout layout_top, layout_bottom, layout_middle;
    final float top_font_x, bottom_font_x;
    final float top_font_y, bottom_font_y;
    private float middle_font_x, middle_font_y;
    private int level;
    
    public LevelWinScreen(GameManager mngr){
    
        super(mngr);
        
        font = new BitmapFont(Gdx.files.internal("data/score.fnt"));
        font.getData().setScale(0.3f, 0.3f);  
        game_over = "LEVEL COMPLETE";
        press_space = "PRESS SPACE TO CONTINUE";
        score = "0";
        
        layout_top = new GlyphLayout(font, game_over);
        
        layout_bottom = new GlyphLayout(font, press_space);
        top_font_x =  -layout_top.width / 2;
        top_font_y =  20;
        
        middle_font_y = 0;
        
        bottom_font_x =  -layout_bottom.width / 2;
        bottom_font_y =  -20;
    
    }

    @Override
    public void update() {

        
        middle_font_x = -layout_middle.width / 2;
        
        ray_handler.setCombinedMatrix(camera.combined);
        camera.rotate(1.3f);
        camera.update();
    
    }

    @Override
    public void render() {
        
        batch.setProjectionMatrix(camera.projection);
        super.render();
        batch.begin();
        font.draw(batch, layout_top, top_font_x, top_font_y);
        font.draw(batch, layout_middle, middle_font_x, middle_font_y);
        font.draw(batch, layout_bottom, bottom_font_x, bottom_font_y);
        batch.end();
        
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            mngr.incrementLevel();
            //mngr.getSuperManager().transition();
        }
        
    
    }

    @Override
    public void initialize() {
        initializeWorld();
        updateReferences();
        String s = ""+mngr.getPreviousScore();
        score = "YOU SAVED " + s + " BROS";
        System.out.println("score: "+s);
        layout_middle = new GlyphLayout(font, score);
        SoundPlayer sp = mngr.getSuperManager().getSoundPlayer();
        sp.setSong(SoundPlayer.WIN_JINGLE);
        sp.setLooping(false);
        sp.setVolume(.9f);
        sp.playSong();
        new PointLight(ray_handler, 5000, Color.GREEN, 500, camera.viewportWidth/2, -300 );
        new PointLight(ray_handler, 5000, Color.GREEN, 500, camera.viewportWidth/2, 300 );
        new PointLight(ray_handler, 5000, Color.GREEN, 500, -camera.viewportWidth/2, -300 );
        new PointLight(ray_handler, 5000, Color.GREEN, 500, -camera.viewportWidth/2, 300 );

        
    }


    
}
