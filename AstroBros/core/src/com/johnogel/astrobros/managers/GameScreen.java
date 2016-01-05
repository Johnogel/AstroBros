/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
public abstract class GameScreen implements Controller{
    protected World world; 
    protected Box2DDebugRenderer renderer;
    protected FPSLogger logger;
    protected int width, height;
    protected OrthographicCamera camera;
    protected Array<GameObject> game_objects, disposables;
    protected int max_count;
    protected RayHandler ray_handler;
    protected final GameManager mngr;
    protected float fps;
    protected Array<Light> lights;
    protected Array<Level> levels;
    protected boolean started;
    protected SpriteBatch batch;

    public GameScreen(GameManager mngr){
        this.mngr = mngr;
        this.world = mngr.getWorld();
        this.camera = mngr.getCamera();        
        this.batch = mngr.getSpriteBatch();
        this.ray_handler = mngr.getRayHandler();
        
    }
    
        @Override
    public void resize(int width, int height) {
        
    }

    //call in child initialize method
    protected void updateReferences(){
        this.world = mngr.getWorld();
        this.camera = mngr.getCamera();        
        this.batch = mngr.getSpriteBatch();
        this.ray_handler = mngr.getRayHandler();
        this.ray_handler.setCulling(false);
    }
    

    @Override
    public void render(){
        ray_handler.updateAndRender();
    }
    
    @Override
    public void initializeWorld() {
        //mngr.clearGameObjects();
        mngr.initializeWorld();
    }
    
    
    @Override
    public void dispose() {
    }
    
    
}
