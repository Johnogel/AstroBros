/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author johno-gel
 */
public abstract class Level {
private GameManager mngr;    
private Array<Sun> suns;
private Array<Body> bodies;
private Array<AstroBro> bros;
private Array<AstroBro> controlled_bros;
private Array<AstroBro> free_bros;
private World world;
private Player player;

    public Level(GameManager mngr){
        
        bodies = new Array();
        suns = new Array();
        bros = new Array();
        controlled_bros = new Array();
        free_bros = new Array();
        
        this.player = mngr.getPlayer();
        this.mngr = mngr;
        
        this.world = mngr.getWorld();
        
        world.getBodies(bodies);
        
    }
    
    //should be overriden in child class
    public abstract void initialize();
    
    //should call gravitate helper method
    public void update(){
        
    }
    
    private void gravitate(){
        
        //sets force on each body towards each sun
        for (Sun s : suns){
            for (Body b : bodies){
                //Do math stuff here
            }
        }
        
    }
    
}
