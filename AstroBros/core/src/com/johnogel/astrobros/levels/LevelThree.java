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
import com.johnogel.astrobros.support.TextureHandler;

/**
 *
 * @author johno-gel
 */
public class LevelThree extends Level{
    public LevelThree(GameManager mngr, int start_time){
        super(mngr, start_time);
        
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
        

        //this.free_bros.add(new Player(world, camera, 1));
        //this.free_bros.add(new Player(world, camera, 120));
        //this.free_bros.add(new Player(world, camera, 100));
        this.free_bros.add(new Player(world, camera, 130));
        this.free_bros.add(new Player(world, camera, 160));
        this.free_bros.add(new Player(world, camera, 200));
        this.free_bros.add(new Player(world, camera, 240));
                                
        //world.createJoint(joint_def);
        
        this.initializePlayer();
        this.initializeArrays();
        this.initializeContactListener();
        
        
        
        
        //adds sun to suns array without storing locally
        new Sun(this, suns, 8000, Color.RED, 1000, width/2, height/2 );
        
        suns.get(0).initializeTexture(texture_handler, TextureHandler.SUN);
        
        this.setOrbits();
        
        this.initializeBoundaries();
        this.initializeBackground();
        this.initializeLocators();
        
    }
    
}
