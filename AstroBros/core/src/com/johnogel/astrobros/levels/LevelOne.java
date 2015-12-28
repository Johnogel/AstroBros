/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.levels;

import com.badlogic.gdx.graphics.Color;
import com.johnogel.astrobros.gameobjects.BoundaryCircle;
import com.johnogel.astrobros.gameobjects.Player;
import com.johnogel.astrobros.gameobjects.Sun;
import com.johnogel.astrobros.managers.GameManager;

/**
 *
 * @author johno-gel
 */
public class LevelOne extends Level{
    
    public LevelOne(GameManager mngr){
        super(mngr);
        
    }
    @Override
    public void initialize() {
        
        

        this.initializeWorld();
        
        this.ray_handler = mngr.getRayHandler();
        this.world = mngr.getWorld();
        this.camera = mngr.getCamera();
        
        width = mngr.getWidth();
        height = mngr.getHeight();
        
        //this.ray_handler.dispose();
        

        
        this.free_bros.add(new Player(world, camera, 23,200));
        this.free_bros.add(new Player(world, camera, 96,200));
        this.free_bros.add(new Player(world, camera, 10,200));
        this.free_bros.add(new Player(world, camera, 56,200));
                                
        //world.createJoint(joint_def);
        
        this.initializePlayer();
        this.initializeArrays();
        this.initializeContactListener();
        
        
        
        
        //adds sun to suns array without storing locally
        new Sun(this, suns, 8000, Color.YELLOW, 600, width/2, height/2 );
        
        inner_orbit = new BoundaryCircle(suns.get(0), BoundaryCircle.INNER_ORBIT, world, camera);
        outer_orbit = new BoundaryCircle(suns.get(0), BoundaryCircle.OUTER_ORBIT, world, camera);
        
        
    }
    
    
}
