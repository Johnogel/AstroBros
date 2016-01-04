/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author johno-gel
 */
public class TextureHandler implements Disposable{
private final Array<Texture> textures;
public static final int 
        ASTRO_BRO = 0,
        SUN = 1,
        BOUNDARY_RED = 2,
        BOUNDARY_BLUE = 3,
        BOUNDARY_OUTER = 4,
        BACKGROUND = 5,
        BACKGROUND_BIG = 6,
        BACKGROUND_SMALL = 7;

    public TextureHandler(){
        textures = new Array();
        
    }
    
    public void initialize(){
        textures.add(new Texture(Gdx.files.internal("test.png")));
        textures.add(new Texture(Gdx.files.internal("SunOutline.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-red.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-blue.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-outer.png")));
        textures.add(new Texture(Gdx.files.internal("background.png")));
        textures.add(new Texture(Gdx.files.internal("background-big.png")));
        textures.add(new Texture(Gdx.files.internal("background-small.png")));
    }
    
    public Texture getTexture(int texture){
        return textures.get(texture);
    }

    @Override
    public void dispose() {
        for(Texture t : textures){
            t.dispose();
        }
    }
    
    
    
}
