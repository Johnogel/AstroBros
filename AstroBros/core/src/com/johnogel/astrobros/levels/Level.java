/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.levels;

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
private Array<Sun> suns;
private Array<Body> bodies;
private Array<AstroBro> bros;
private Array<AstroBro> controlled_bros;
private Array<AstroBro> free_bros;
private Array<Body> controlled_bodies;
private Array<Body> free_bodies;
private World world;
private Player player;

    public Level(GameManager mngr){
        
        bodies = new Array();
        suns = new Array();
        bros = new Array();
        controlled_bros = new Array();
        free_bros = new Array();
        controlled_bodies = new Array();
        free_bros = new Array();
        
        this.player = mngr.getPlayer();
        this.mngr = mngr;
        
        this.world = mngr.getWorld();
        
        
        
        world.getBodies(bodies);
        
       
        
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
    
    protected void initializeContactListener(){
        world.setContactListener(new ContactListener(){

            @Override
            public void beginContact(Contact contact) {
                boolean is_bro;
                
                //array used for checking if controlled bros are contacting each other
                Array<AstroBro> contacted_bros = new Array(2);
                Body other;
                //check if an aleady controlled astro bro is contacting another body
                for(AstroBro bro : controlled_bros){
                    if(!contacted_bros.contains(bro, true)){
                        
                        if(bro.getBody().equals(contact.getFixtureA().getBody())){
                            contacted_bros.add(bro);
                        }
                        
                        else if(bro.getBody().equals(contact.getFixtureB().getBody())){
                            contacted_bros.add(bro);
                        }
                        
                    }
                }
                
                if(contacted_bros.size == 1){
                    if (contacted_bros.get(0).getBody().equals(contact.getFixtureA().getBody())){
                        
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
    
    //should be overriden in child class
    public abstract void initialize();
    
    //should call gravitate helper method
    public void update(){
        
    }
    
    private void gravitate(){
        
        //sets force on each body towards each sun
        for (Sun s : suns){
            for (AstroBro b : bros){
                Body body = b.getBody();
                
                //Do math stuff here
            }
        }
        
    }
    
}
