/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.johnogel.astrobros.levels.Level;

/**
 *
 * @author johno-gel
 */
public class Background {
private Level level;
private final String 
        BACKGROUND = "background.png",
        BACKGROUND_SMALL = "background-small.png",
        BACKGROUND_BIG = "background-big.png";
private Texture layer_1, layer_2, layer_3, layer_4;
private float 
        layer_1_x, 
        layer_1_y, 
        layer_2_x, 
        layer_2_y, 
        layer_3_x, 
        layer_3_y,
        layer_4_x, 
        layer_4_y;

private final float
        SCALE_1 = .5f,
        SCALE_2 = .25f,
        SCALE_3 = .05f,
        SCALE_4 = .02f,
        SIZE_1 = .6f,
        SIZE_2 = .5f,
        SIZE_3 = .3f,
        SIZE_4 = .12f;

    public Background(Level level){
        this.level = level;
        layer_1 = new Texture(Gdx.files.internal(this.BACKGROUND_BIG));
        layer_2 = new Texture(Gdx.files.internal(this.BACKGROUND));
        layer_3 = new Texture(Gdx.files.internal(this.BACKGROUND));
        layer_4 = new Texture(Gdx.files.internal(this.BACKGROUND_SMALL));
        
        this.layer_1_x += -this.SIZE_1*layer_1.getWidth()/2;
        this.layer_1_y += -500;
        this.layer_2_x += -this.SIZE_2*layer_2.getWidth()/2;
        this.layer_2_y += -500;
        this.layer_3_x += -this.SIZE_3*layer_3.getWidth()/2;
        this.layer_3_y += -500;
        this.layer_4_x += -this.SIZE_4*layer_4.getWidth()/2;
        this.layer_4_y += -100;

        
    }
    
    public void update(){
        Vector2 delta = level.getDeltaCameraPosition();
        this.layer_1_x += -this.SCALE_1*delta.x;
        this.layer_1_y += -this.SCALE_1*delta.y;
        this.layer_2_x += -this.SCALE_2*delta.x;
        this.layer_2_y += -this.SCALE_2*delta.y;
        this.layer_3_x += -this.SCALE_3*delta.x;
        this.layer_3_y += -this.SCALE_3*delta.y;
        this.layer_4_x += -this.SCALE_4*delta.x;
        this.layer_4_y += -this.SCALE_4*delta.y;
        
    }
    
    public void drawBackground(SpriteBatch batch){
        batch.setProjectionMatrix(level.getCamera().projection);
        batch.begin();
        batch.draw(
                layer_1, 
                this.layer_1_x, 
                this.layer_1_y, 
                layer_1.getWidth()*this.SIZE_1, 
                layer_1.getHeight()*this.SIZE_1);
        batch.draw(
                layer_2, 
                this.layer_2_x, 
                this.layer_2_y, 
                layer_2.getWidth()*this.SIZE_2, 
                layer_2.getHeight()*this.SIZE_2);
        batch.draw(
                layer_3, 
                this.layer_3_x, 
                this.layer_3_y, 
                layer_3.getWidth()*this.SIZE_3, 
                layer_3.getHeight()*this.SIZE_3);
        batch.draw(
                layer_4, 
                this.layer_4_x, 
                this.layer_4_y, 
                layer_4.getWidth()*this.SIZE_4, 
                layer_4.getHeight()*this.SIZE_4);

        batch.end();
    }
    
}
