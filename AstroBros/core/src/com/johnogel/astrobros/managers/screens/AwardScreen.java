/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers.screens;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.johnogel.astrobros.managers.GameManager;
import com.johnogel.astrobros.managers.SuperManager;
import com.johnogel.astrobros.support.TextureHandler;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 *
 * @author johno-gel
 */
public class AwardScreen extends GameScreen{

    private final BitmapFont font;
    private final CharSequence game_over;
    private CharSequence middle_text, bottom_text;
    private GlyphLayout layout_top, layout_bottom, layout_middle;
    private float top_font_x, bottom_font_x;
    private float top_font_y, bottom_font_y;
    private float middle_font_x, middle_font_y;
    private int level;
    private Animation animation;
    private AnimatedSprite sprite;
    private final float 
            FPS = 1/60f;
    private float state_time;
    public AwardScreen(GameManager mngr){
    
        super(mngr);
        
        font = new BitmapFont(Gdx.files.internal("data/score.fnt"));
        font.getData().setScale(0.3f, 0.3f);  
        game_over = "AWARD!";
        bottom_text = "";
        //middle_text = "0";
        
        layout_top = new GlyphLayout(font, game_over);
        
        layout_bottom = new GlyphLayout(font, bottom_text);
        top_font_x =  -layout_top.width / 2;
        top_font_y =  20;
        
        middle_font_y = 0;
        
        bottom_font_x =  -layout_bottom.width / 2;
        bottom_font_y =  -20;
        
        //ticker = 0;
        state_time = 0;
    }

    @Override
    public void update() {
        
        state_time += FPS*.5f;
        middle_font_x = -layout_middle.width / 2;
        
        ray_handler.setCombinedMatrix(camera.combined);
        
        bottom_font_x =  -layout_bottom.width / 2;
        bottom_font_y =  -20;
        
        if(sprite.isAnimationFinished()){
            sprite.setTime(0);
            sprite.play();
        }

    
    }

    @Override
    public void render() {
        camera.rotate(1.3f);
        camera.update();
        super.render();
        batch.setProjectionMatrix(camera.projection);
        super.render();
        batch.begin();
        //font.draw(batch, layout_top, top_font_x, top_font_y);
        font.draw(batch, layout_middle, middle_font_x, middle_font_y);
        font.draw(batch, layout_bottom, bottom_font_x, bottom_font_y);       
        batch.draw(animation.getKeyFrame(state_time), -19, 0, 38, 38);
        batch.end();
        //batch.setProjectionMatrix(camera.projection);
        
                
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            //mngr.setLevel(mngr.getCurrentLevel());
            int total_score = mngr.getTotalScore();
            int top_score =  mngr.getTopScore();
            
            if(total_score == top_score){
                mngr.initializeBonus();
            }
            else{
                mngr.getSuperManager().setSuperController(SuperManager.MENU_MANAGER);
                mngr.getSuperManager().transition();
        
            }
        }
    }

    @Override
    public void initialize() {
        initializeWorld();
        updateReferences();
        String s = ""+mngr.getPreviousScore();
        //middle_text = "YOU SAVED " + s + " BROS";
        System.out.println("score: "+s);
        
        
        
        int total_score = mngr.getTotalScore();
        int top_score =  mngr.getTopScore();
        //AnimatedSprite sp;
        
        //adding moving animation
        Color color;
        
        if(total_score == top_score){
            animation = new Animation(FPS, mngr.getTextureHandler().getTextureAtlas(TextureHandler.PLATINUM).getRegions());
            middle_text = "";
            bottom_text = "PERFECT!";
            color = Color.WHITE;
        }
        else if(mngr.getTotalScore() > mngr.getTopScore()-2){
            animation = new Animation(FPS, mngr.getTextureHandler().getTextureAtlas(TextureHandler.GOLD).getRegions());
            middle_text = "AWESOME!";
            bottom_text = "TRY FOR PLATINUM!";
            color = Color.GOLD;
        }
        else if(mngr.getTotalScore() > mngr.getTopScore()-3){
            animation = new Animation(FPS, mngr.getTextureHandler().getTextureAtlas(TextureHandler.SILVER).getRegions());
            middle_text = "GREAT!";
            bottom_text = "TRY FOR GOLD!";
            color = Color.SLATE;
        }
        else if(mngr.getTotalScore() > mngr.getTopScore() - 5){
            animation = new Animation(FPS, mngr.getTextureHandler().getTextureAtlas(TextureHandler.BRONZE).getRegions());
            middle_text = "GOOD!";
            bottom_text = "TRY FOR SILVER!";
            color = Color.TAN;
        }
        else{
            animation = new Animation(FPS, mngr.getTextureHandler().getTextureAtlas(TextureHandler.BRONZE).getRegions());
            middle_text = "";
            bottom_text = "WHAT!";
            color = Color.BLACK;
        }
        
        
        new PointLight(ray_handler, 5000, color, 500, camera.viewportWidth/2, -300 );
        new PointLight(ray_handler, 5000, Color.BLACK, 500, camera.viewportWidth/2, 300 );
        new PointLight(ray_handler, 5000, Color.BLACK, 500, -camera.viewportWidth/2, -300 );
        new PointLight(ray_handler, 5000, color, 500, -camera.viewportWidth/2, 300 );
        layout_middle = new GlyphLayout(font, middle_text);
        layout_bottom = new GlyphLayout(font, bottom_text);
        sprite = new AnimatedSprite(animation);
        
        animation.setPlayMode(Animation.PlayMode.LOOP);
        sprite.setTime(0);
        sprite.play();
        

        
    }
}
