/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers.screens;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.johnogel.astrobros.managers.GameManager;
import com.johnogel.astrobros.managers.SuperManager;


/**
 *
 * @author johno-gel
 */
public class GameOverScreen extends GameScreen{
    private final BitmapFont font;
    private CharSequence game_over, press_space;
    private GlyphLayout layout_top, layout_bottom;
    final float top_font_x, bottom_font_x;
    final float top_font_y, bottom_font_y;

    
    public GameOverScreen(GameManager mngr){
       
        super(mngr);
        
        font = new BitmapFont(Gdx.files.internal("data/score.fnt"));
        font.getData().setScale(0.3f, 0.3f);        
  
        game_over = "GAME OVER";
        press_space = "PRESS SPACE TO RESTART";
        layout_top = new GlyphLayout(font, game_over);
        layout_bottom = new GlyphLayout(font, press_space);
        top_font_x =  -layout_top.width / 2;
        top_font_y =  20;
        
        bottom_font_x = -layout_bottom.width / 2;
        bottom_font_y = -20;
    }

    @Override
    public void update() {
        
        ray_handler.setCombinedMatrix(camera.combined);
        camera.rotate(1.3f);
        camera.update();
    }

    @Override
    public void render() {
        super.render();
        batch.setProjectionMatrix(camera.projection);
        super.render();
        batch.begin();
        font.draw(batch, layout_top, top_font_x, top_font_y);
        font.draw(batch, layout_bottom, bottom_font_x, bottom_font_y);
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            //mngr.setLevel(mngr.getCurrentLevel());
            
            mngr.getSuperManager().setSuperController(SuperManager.MENU_MANAGER);
            mngr.getSuperManager().transition();
        }

        
    }

    @Override
    public void initialize() {
        initializeWorld();
        this.updateReferences();
        mngr.getSuperManager().getSoundPlayer().initializeLevelSounds();
        Music s = mngr.getSuperManager().getSoundPlayer().getSunSound(); 
        
        s.setLooping(true);
        s.play();
        new PointLight(ray_handler, 5000, Color.RED, 500, -camera.viewportWidth/2, -300 );
        new PointLight(ray_handler, 5000, Color.RED, 500, camera.viewportWidth/2, -300 );
        new PointLight(ray_handler, 5000, Color.RED, 500, -camera.viewportWidth/2, 300 );
        new PointLight(ray_handler, 5000, Color.RED, 500, camera.viewportWidth/2, 300 );

        
    }



    
}
