/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.levels;

import com.badlogic.gdx.graphics.Color;
import com.johnogel.astrobros.gameobjects.NonPlayer;
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
        
        
        
        this.free_bros.add(new NonPlayer(world, camera, 23,200));
        this.free_bros.add(new NonPlayer(world, camera, 96,200));
        this.free_bros.add(new NonPlayer(world, camera, 10,200));
        this.free_bros.add(new NonPlayer(world, camera, 56,200));
        
        updateBodyArrays();
        initializeContactListener();
        
        ray_handler.removeAll();

        new Sun(this, suns, 8000, Color.YELLOW, 600, width/2, height/2 );
        
        
    }
    
    
}
