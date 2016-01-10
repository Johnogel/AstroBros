/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author johno-gel
 */
public class TextureHandler implements Disposable{
private final Array<Texture> textures;
public TextureAtlas atlas;
public Array<TextureAtlas> atlases;
public static final int 
        ASTRO_BRO = 0,
        SUN = 1,
        BOUNDARY_RED = 2,
        BOUNDARY_BLUE = 3,
        BOUNDARY_OUTER = 4,
        BACKGROUND = 5,
        BACKGROUND_BIG = 6,
        BACKGROUND_SMALL = 7,
        AWAKE = 0,
        MOVE = 1,
        SLEEP = 2;

    public TextureHandler(){
        textures = new Array(8);  
        atlases = new Array(3);
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
        //atlas = new TextureAtlas(Gdx.files.internal("bro/bros.pack"));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/awake/awake.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/move/move.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/sleep/sleep.pack")));
    }
    
    public Texture getTexture(int texture){
        return textures.get(texture);
    }
    
    /*public TextureAtlas getBroPack(){
        return atlas;
    }*/
    
    public TextureAtlas getTextureAtlas(int index){
        return atlases.get(index);
    }
    
    @Override
    public void dispose() {
        for(Texture t : textures){
            t.dispose();
        }
        atlas.dispose();
    }
    
    
    
    
    
}
