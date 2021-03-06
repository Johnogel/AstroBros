/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.levels.Level;

/**
 *
 * @author johno-gel
 */
public class Background{
private final Level level;
private final String 
        BACKGROUND = "background.png",
        BACKGROUND_SMALL = "background-small.png",
        BACKGROUND_BIG = "background-big.png";
private final Array<Texture> textures;
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
        SCALE_1 = .20f,
        SCALE_2 = .10f,
        SCALE_3 = .05f,
        SCALE_4 = .02f,
        SIZE_1 = .20f,
        SIZE_2 = .25f,
        SIZE_3 = .3f,
        SIZE_4 = .12f;

    public Background(Level level){
        this.level = level;
        textures = new Array(4);
    }
    
    public void initialize(){
        textures.add(level.getTextureHandler().getTexture(TextureHandler.BACKGROUND_BIG));
        textures.add(level.getTextureHandler().getTexture(TextureHandler.BACKGROUND));
        textures.add(level.getTextureHandler().getTexture(TextureHandler.BACKGROUND));
        textures.add(level.getTextureHandler().getTexture(TextureHandler.BACKGROUND_SMALL));
        
        this.layer_1_x = -this.SIZE_1*textures.get(0).getWidth()/2;
        this.layer_1_y = -350;
        this.layer_2_x = -this.SIZE_2*textures.get(1).getWidth()/2;
        this.layer_2_y = -500;
        this.layer_3_x = -this.SIZE_3*textures.get(2).getWidth()/2;
        this.layer_3_y = -500;
        this.layer_4_x = -this.SIZE_4*textures.get(3).getWidth()/2;
        this.layer_4_y = -100;
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
    

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(level.getCamera().projection);
        //batch.disableBlending();
        //batch.setColor(Color.WHITE);
        
        batch.begin();
        //textures.get(0).
        batch.draw(
                textures.get(0), 
                this.layer_1_x, 
                this.layer_1_y, 
                textures.get(0).getWidth()*this.SIZE_1, 
                textures.get(0).getHeight()*this.SIZE_1);
        batch.draw(
                textures.get(1), 
                this.layer_2_x, 
                this.layer_2_y, 
                textures.get(1).getWidth()*this.SIZE_2, 
                textures.get(1).getHeight()*this.SIZE_2);
        batch.draw(
                textures.get(2), 
                this.layer_3_x, 
                this.layer_3_y, 
                textures.get(2).getWidth()*this.SIZE_3, 
                textures.get(2).getHeight()*this.SIZE_3);
        batch.draw(
                textures.get(3), 
                this.layer_4_x, 
                this.layer_4_y, 
                textures.get(3).getWidth()*this.SIZE_4, 
                textures.get(3).getHeight()*this.SIZE_4);
        batch.draw(
                textures.get(3), 
                this.layer_4_x - 50, 
                this.layer_4_y - 50, 
                textures.get(3).getWidth()*this.SIZE_4, 
                textures.get(3).getHeight()*this.SIZE_4);
        batch.draw(
                textures.get(3), 
                this.layer_4_x + 100, 
                this.layer_4_y - 50, 
                textures.get(3).getWidth()*this.SIZE_4, 
                textures.get(3).getHeight()*this.SIZE_4);

        batch.end();
        
    }


    public void dispose() {
        /*for(Texture t : textures){
            t.dispose();
        }
        textures.clear();*/
    }
    
}
