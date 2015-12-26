/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.levels;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.johnogel.astrobros.managers.GameManager;
import com.johnogel.astrobros.gameobjects.AstroBro;
import com.johnogel.astrobros.gameobjects.Sun;
import com.johnogel.astrobros.gameobjects.Player;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.interfaces.Controller;
import com.johnogel.astrobros.interfaces.GameObject;

/**
 *
 * @author johno-gel
 */
public abstract class Level implements Controller{
protected final GameManager mngr;    

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
        

        this.mngr = mngr;
        
        this.world = mngr.getWorld();
        
        
        
        this.suns = new Array();
  
    }
    
    //should be called in child initialize method
    protected void updateBodyArrays(){
        
        bro_bodies.add(mngr.getPlayer().getBody());
        controlled_bodies.add(mngr.getPlayer().getBody());
        
        for (AstroBro b : controlled_bros){
            controlled_bodies.add(b.getBody());
            bro_bodies.add(b.getBody());
        }
        
        for (AstroBro b : free_bros){
            free_bodies.add(b.getBody());
            bro_bodies.add(b.getBody());
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
                
                //check if both contacts are bros
                if(bro_bodies.contains(contact.getFixtureA().getBody(), false) && bro_bodies.contains(contact.getFixtureB().getBody(), false)){
                    System.out.println("THEY'RE BROS!!!!!!!!!!!!!!!!!!!!!");
                    //if contact A is free and B is trying to grab
                    if(free_bodies.contains(contact.getFixtureA().getBody(), false) && controlled_bodies.contains(contact.getFixtureB().getBody(), false))
                    {
                        for(Body body : free_bodies){

                            if(body.equals(contact.getFixtureA().getBody())){
                                //body.setActive(false);
                                //body.setType(BodyDef.BodyType.StaticBody);
                                System.out.println("CONATACT!!!!");
                                
                            }
   
                        }
                
                    }
                    
                    //if contact B is free and A is trying to grab
                    else if(free_bodies.contains(contact.getFixtureB().getBody(), false) && controlled_bodies.contains(contact.getFixtureA().getBody(), false)){
                        for(Body body : free_bodies){

                            if(body.equals(contact.getFixtureB().getBody())){
                                //body.setActive(false);
                                //body.setType(BodyDef.BodyType.StaticBody);
                                System.out.println("CONATACT!!!!");
                                
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
    @Override
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
    
    @Override
    public void initializeWorld(){
        mngr.initializeWorld();
        
        this.player = mngr.getPlayer();
        
        //don't change this...?

        //world.getBodies(bodies);
        
        

    }
    
    public void initializeGameObjects(){
        for (AstroBro g : free_bros){
            GameObject o = (GameObject) g;
            mngr.addGameObject(o);
        }
    }
    
    public void clearArrays(){
        bodies.clear();
        suns.clear();
        
        controlled_bros.clear();
        free_bros.clear();
        
        bro_bodies.clear(); 
        controlled_bodies.clear(); 
        free_bodies.clear();
    }
    
    @Override
    public void render() {
    }

    @Override
    public void addLight(Light light) {
    }

    @Override
    public void turnOffLights() {
    }

    @Override
    public void turnOnLights() {
    }

    @Override
    public void updateLights() {
    }


    @Override
    public void dispose() {
        //mngr.resetGameObjectArray();
        clearArrays();
        mngr.disposeGameObjectTextures();
    }
    
}
