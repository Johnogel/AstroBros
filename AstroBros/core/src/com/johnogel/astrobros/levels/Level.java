/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.levels;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.johnogel.astrobros.managers.GameManager;
import com.johnogel.astrobros.gameobjects.AstroBro;
import com.johnogel.astrobros.gameobjects.Sun;
import com.johnogel.astrobros.gameobjects.Player;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author johno-gel
 */
public abstract class Level {
private GameManager mngr;    
protected Array<Sun> suns;
protected Array<Body> bodies;
protected Array<Body> bro_bodies;
protected Array<AstroBro> controlled_bros;
protected Array<AstroBro> free_bros;
protected Array<Body> controlled_bodies;
protected Array<Body> free_bodies;
protected World world;
protected Player player;
protected RayHandler ray_handler;
protected int width, height;

protected OrthographicCamera camera;

    public Level(GameManager mngr){

        bodies = new Array();
        suns = new Array();
        
        controlled_bros = new Array();
        free_bros = new Array();
        
        bro_bodies = new Array();
        controlled_bodies = new Array();
        free_bodies = new Array();
        
        this.player = mngr.getPlayer();
        this.mngr = mngr;
        
        this.world = mngr.getWorld();
        
        
        
        this.suns = new Array();
        
        this.ray_handler = mngr.getRayHandler();
        
        width = mngr.getWidth();
        height = mngr.getHeight();
        
        world.getBodies(bodies);
        camera = mngr.getCamera();
        
       
        
    }
    
    //should be called in child initialize method
    protected void updateBodyArrays(){
        for (AstroBro b : controlled_bros){
            controlled_bodies.add(b.getBody());
        }
        
        for (AstroBro b : free_bros){
            free_bodies.add(b.getBody());
        }
    }
    
    //should also be called in initialize method
    protected void initializeContactListener(){
        world.setContactListener(new ContactListener(){

            @Override
            public void beginContact(Contact contact) {
                boolean is_bro;
                
                //array used for checking if controlled bros are contacting each other
                Array<AstroBro> contacted_bros = new Array(2);
                Body other;
                
                //check if an aleady controlled astro bro is contacting another body
                if(bro_bodies.contains(contact.getFixtureA().getBody(), true) && bro_bodies.contains(contact.getFixtureB().getBody(), true)){
                    
                    //if contact A is free and B is trying to grab
                    if(free_bodies.contains(contact.getFixtureA().getBody(), true) && controlled_bodies.contains(contact.getFixtureB().getBody(), true))
                    {
                        for(AstroBro bro : controlled_bros){

                            if(bro.getBody().equals(contact.getFixtureA().getBody())){
                                bro.getBody().setActive(false);
                                
                            }
   
                        }
                
                    }
                    
                    //if contact B is free and A is trying to grab
                    else if(free_bodies.contains(contact.getFixtureB().getBody(), true) && controlled_bodies.contains(contact.getFixtureA().getBody(), true)){
                        for(AstroBro bro : controlled_bros){

                            if(bro.getBody().equals(contact.getFixtureB().getBody())){
                                bro.getBody().setActive(false);
                            }
   
                        }
                    }
                }
                
                
                
                
                
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        
        });
    }
    
    public OrthographicCamera getCamera(){
        return camera;
    }
    
    public RayHandler getRayHandler(){
        return ray_handler;
    }
    
    public World getWorld(){
        return world;
    }
    
    //should be overriden in child class
    public abstract void initialize();
    
    //should call gravitate helper method
    public void update(){
        
    }
    
    private void gravitate(){
        
        //sets force on each body towards each sun
        for (Sun s : suns){
            for (Body b : bro_bodies){
                
                
                //Do math stuff here
            }
        }
        
    }
    
    private void reset(){
        mngr.initializeWorld();
    }
    
}
