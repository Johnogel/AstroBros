/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author johno-gel
 */
public class Player extends NonPlayer{
private int max_vel, max_force;
private boolean space_pressed;
private boolean active_player;

    public Player(World world, OrthographicCamera camera){
        super(world, camera);
        
        
        
        max_vel = 50;
        max_force = 3000;
  
    }
    
    public Player(World world, OrthographicCamera camera, float distance) {
        super(world, camera, distance);
        
        max_vel = 100;
        max_force = 3500;
    }
    
    
    
    public boolean isGrabbing(){
        return space_pressed;
    }
    
    public void disablePlayer(){
        active_player = false;
    }
    
    public void enablePlayer(){
        active_player = true;
    }
    
    @Override
    public void update(SpriteBatch batch){
        //System.out.println("Angle: "+body.getAngle());
        batch.setProjectionMatrix(camera.combined);
        if(active_player){
            System.out.println("MAX_FORCE: "+max_force);
            //apply force left
            if(Gdx.input.isKeyPressed(Keys.A)){
                if(this.body.getLinearVelocity().x > -max_vel){
                    this.body.applyForceToCenter(-max_force, 0, true);
                }

            }

            //apply force down
            if(Gdx.input.isKeyPressed(Keys.S)){
                if(this.body.getLinearVelocity().y > -max_vel){
                    this.body.applyForceToCenter(0, -max_force, true);
                }

            }

            //apply force right
            if(Gdx.input.isKeyPressed(Keys.D)){
                if(this.body.getLinearVelocity().x < max_vel){
                    this.body.applyForceToCenter(max_force, 0, true);
                } 
            }

            //apply force up
            if(Gdx.input.isKeyPressed(Keys.W)){
                if(this.body.getLinearVelocity().y < max_vel){
                    this.body.applyForceToCenter(0, max_force, true);
                }

            }

            //checks if player is attempting to grab
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                if(!space_pressed){
                    space_pressed = true;
                }

            }
            else{
                space_pressed = false;
            }

            this.camera.position.set(this.body.getPosition(), 0);
            this.camera.update();
        }
        
        
    }
    
    public boolean isSpacePressed(){
        return space_pressed;
    }
    
    public void setOrbit(Sun sun){
        float distance = body.getPosition().dst(sun.getPosition());
        body.setLinearVelocity(0, (float)Math.sqrt((sun.getMass()/15)/distance));
        System.out.println("\nMASS OF SUN: "+sun.getMass()+"\nVELOCITY: "+body.getLinearVelocity().y);
    }
    
}
