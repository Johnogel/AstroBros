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
public class GameOverScreen implements Controller{
    private World world; 
    private Box2DDebugRenderer renderer;
    private FPSLogger logger;
    private int width, height;
    private OrthographicCamera camera;
    protected Array<GameObject> game_objects, disposables;
    private int max_count;
    private RayHandler ray_handler;
    private final GameManager mngr;
    private float fps;
    private Array<Light> lights;
    private Array<Level> levels;
    private boolean started;
    private SpriteBatch batch;

    private int level;
    
    public GameOverScreen(GameManager mngr){
        this.mngr = mngr;
        this.world = mngr.getWorld();
        
        batch = new SpriteBatch();
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render() {
        
    }

    @Override
    public void resize(int width, int height) {
    }

 

    @Override
    public void initializeWorld() {
        //mngr.clearGameObjects();
        mngr.initializeWorld();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void dispose() {
    }
    
}
