/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author johno-gel
 */
public class GameManager implements Controller{
World world; 
Box2DDebugRenderer renderer;
FPSLogger logger;
    public GameManager(){
        world = new World(new Vector2(0,0), false);
        renderer = new Box2DDebugRenderer();
        logger = new FPSLogger();
        
        
    }
    
    @Override
    public void render(){
        
    }

    @Override
    public void update() {
        
    }
    
}
