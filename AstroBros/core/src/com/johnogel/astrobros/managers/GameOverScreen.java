/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.interfaces.Controller;
import com.johnogel.astrobros.interfaces.GameObject;
import com.johnogel.astrobros.levels.Level;

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            mngr.setLevel(mngr.getCurrentLevel());
        }
        ray_handler.setCombinedMatrix(camera.combined);
        camera.rotate(1.3f);
        camera.update();
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.projection);
        
        batch.begin();
        font.draw(batch, layout_top, top_font_x, top_font_y);
        font.draw(batch, layout_bottom, bottom_font_x, bottom_font_y);
        batch.end();

        
    }

    @Override
    public void initialize() {
        initializeWorld();
        this.updateReferences();
        PointLight light = new PointLight(ray_handler, 5000, Color.RED, 700, camera.viewportWidth/2, -300 );

        
    }

    
}
