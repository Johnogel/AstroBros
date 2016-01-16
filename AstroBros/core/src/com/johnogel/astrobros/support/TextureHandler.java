/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
public Array<FileHandle> texture_handles;
private Texture placeholder;
//private Array<FileHandle> atlas_handles;
public static final int 
        
        SUN = 0,
        BOUNDARY_RED = 1,
        BOUNDARY_BLUE = 2,
        BOUNDARY_OUTER = 3,
        BACKGROUND = 4,
        BACKGROUND_BIG = 5,
        BACKGROUND_SMALL = 6,
        LOCATOR = 7,
        PAUSED= 8,
        TEMP = 9,
        AWAKE = 0,
        MOVE = 1,
        SLEEP = 2,
        GOLD = 3,
        SILVER = 4,
        BRONZE = 5,
        PLATINUM = 6;

    public TextureHandler(){
        textures = new Array(10);  
        atlases = new Array(5);
        texture_handles = new Array(10);
       
    }
    

    
    public void initialize(){
        
        
        
        textures.add(new Texture(Gdx.files.internal("SunOutline.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-red.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-blue.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-outer.png")));
        textures.add(new Texture(Gdx.files.internal("background.png")));
        textures.add(new Texture(Gdx.files.internal("background-big.png")));
        textures.add(new Texture(Gdx.files.internal("background-small.png")));
        textures.add(new Texture(Gdx.files.internal("locator.png")));
        textures.add(new Texture(Gdx.files.internal("paused.png")));
        textures.add(new Texture(Gdx.files.internal("test.png")));
        
        texture_handles.add(Gdx.files.internal("SunOutline.png"));
        texture_handles.add(Gdx.files.internal("boundary-red.png"));
        texture_handles.add(Gdx.files.internal("boundary-blue.png"));
        texture_handles.add(Gdx.files.internal("boundary-outer.png"));
        texture_handles.add(Gdx.files.internal("background.png"));
        texture_handles.add(Gdx.files.internal("background-big.png"));
        texture_handles.add(Gdx.files.internal("background-small.png"));
        texture_handles.add(Gdx.files.internal("locator.png"));
        texture_handles.add(Gdx.files.internal("paused.png"));
        
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/awake/awake.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/move/move.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/sleep/sleep.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/gold/gold.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/silver/silver.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/bronze/bronze.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/platinum/platinum.pack")));
        
        disposeAndClear();
    }
    
    public Texture getTexture(int index){
        Texture t1 = textures.get(index);
        Texture t2 = textures.get(TEMP);
        if(t1 == t2){
            textures.set(index, new Texture(texture_handles.get(index)));
        }
        return textures.get(index);
    }
    
    /*public TextureAtlas getBroPack(){
        return atlas;
    }*/
    
    public TextureAtlas getTextureAtlas(int index){
        return atlases.get(index);
        
    }
    
    public void disposeAndClear(){
        /*for (TextureAtlas a : atlases){
            a.dispose();
        }*/
        for(int i = 0; i < textures.size - 1; i++){
            textures.get(i).dispose();
            textures.set(i, textures.get(TEMP));
        }
        //atlases.clear();
    }
    
    @Override
    public void dispose() {
        for(Texture t : textures){
            t.dispose();
        }
        
        for(TextureAtlas a : atlases){
            a.dispose();
        }
    }
    
    
    
    
    
}
