/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.johnogel.astrobros.managers.SuperManager;

/**
 *
 * @author johno-gel
 */
public class TextureHandler implements Disposable{
private final Array<Texture> textures;
public TextureAtlas atlas;
public Array<TextureAtlas> atlases;
private Array<String> filenames, unloads;
private AssetManager mngr;
//private Array<FileHandle> atlas_handles;
public static final int 
        ASTRO_BRO = 0,
        SUN = 1,
        BOUNDARY_RED = 2,
        BOUNDARY_BLUE = 3,
        BOUNDARY_OUTER = 4,
        BACKGROUND = 5,
        BACKGROUND_BIG = 6,
        BACKGROUND_SMALL = 7,
        LOCATOR = 8,
        PAUSED= 9,
        LIFE = 10,
        BLACK_HOLE = 11,
        AWAKE = 12,
        MOVE = 13,
        SLEEP = 14,
        GOLD = 15,
        SILVER = 16,
        BRONZE = 17,
        PLATINUM = 18,
        BLACK = 19;

    public TextureHandler(SuperManager sm){
        
        textures = new Array(10);  
        atlases = new Array(7);
        filenames = new Array(20);
        unloads = new Array(20);
        
        mngr = sm.getAssetManager();
       
    }
    
    public void initialize(){
       /* textures.add(new Texture(Gdx.files.internal("test.png")));
        textures.add(new Texture(Gdx.files.internal("SunOutline.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-red.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-blue.png")));
        textures.add(new Texture(Gdx.files.internal("boundary-outer.png")));
        textures.add(new Texture(Gdx.files.internal("background.png")));
        textures.add(new Texture(Gdx.files.internal("background-big.png")));
        textures.add(new Texture(Gdx.files.internal("background-small.png")));
        textures.add(new Texture(Gdx.files.internal("locator.png")));
        textures.add(new Texture(Gdx.files.internal("paused.png")));
        //atlas = new TextureAtlas(Gdx.files.internal("bro/bros.pack"));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/awake/awake.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/move/move.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/sleep/sleep.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/gold/gold.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/silver/silver.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/bronze/bronze.pack")));
        atlases.add(new TextureAtlas(Gdx.files.internal("animations/platinum/platinum.pack")));*/
        
        filenames.add("test.png");
        filenames.add("SunOutline.png");
        filenames.add("boundary-red.png");
        filenames.add("boundary-blue.png");
        filenames.add("boundary-outer.png");
        filenames.add("background.png");
        filenames.add("background-big.png");
        filenames.add("background-small.png");
        filenames.add("locator.png");
        filenames.add("paused.png");
        filenames.add("life.png");
        filenames.add("BlackHole.png");
        filenames.add("animations/awake/awake.pack");
        filenames.add("animations/move/move.pack");
        filenames.add("animations/sleep/sleep.pack");
        filenames.add("animations/gold/gold.pack");
        filenames.add("animations/silver/silver.pack");
        filenames.add("animations/bronze/bronze.pack");
        filenames.add("animations/platinum/platinum.pack");
        filenames.add("animations/black/black.pack");
        
        for (int i = 0; i < 12; i++){
            mngr.load(filenames.get(i), Texture.class);
        }
        
        for (int i = 12; i < 20; i++){
            mngr.load(filenames.get(i), TextureAtlas.class);
        }
        
        //mngr.finishLoading();

    }
    
    
    
    public Texture getTexture(int texture){
        
        return mngr.get(filenames.get(texture), Texture.class);
    }
    
    /*public TextureAtlas getBroPack(){
        return atlas;
    }*/
    
    public TextureAtlas getTextureAtlas(int index){
        return mngr.get(filenames.get(index), TextureAtlas.class);
        
    }
    
    public boolean isLoading(){
        
        for(String s : filenames){
            if (!mngr.isLoaded(s)){
                return true;
            }
        }
        return false;
    }
    
    public void clear(){
        mngr.clear();
    }
    
    public void disposeAtlases(){
        for (TextureAtlas a : atlases){
            a.dispose();
        }
        atlases.clear();
    }
    
    @Override
    public void dispose() {
        for(Texture t : textures){
            t.dispose();
        }
        for(TextureAtlas a : atlases){
            a.dispose();
        }
        
        
        //atlas.dispose();
    }
    
    
    
    
    
}
