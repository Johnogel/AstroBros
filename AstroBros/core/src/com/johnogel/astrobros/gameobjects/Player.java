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
private final int max_vel = 5000, max_force = 20000;
private boolean space_pressed;
private boolean active_player;

    public Player(World world, OrthographicCamera camera){
        super(world, camera);
        
        

  
    }
    
    public Player(World world, OrthographicCamera camera, float distance) {
        super(world, camera, distance);
        
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
        
        super.update(batch);
        
        if(!this.animated_sprite.isPlaying()){
            this.animated_sprite.setTime(0);
            //this.animated_sprite.setPlaying(true);
            
        }
        
        if (state != this.SLEEP){
            state = SLEEP;
        }
        //System.out.println("Angle: "+body.getAngle());
        batch.setProjectionMatrix(camera.combined);
        if(active_player){
            //System.out.println("MAX_FORCE: "+max_force);
            //apply force left
            
            state = AWAKE;
            
            if(Gdx.input.isKeyPressed(Keys.A)){
                if(this.body.getLinearVelocity().x > -max_vel){
                    this.body.applyForceToCenter(-max_force, 0, true);
                }
                
                state = MOVE;

            }

            //apply force down
            if(Gdx.input.isKeyPressed(Keys.S)){
                if(this.body.getLinearVelocity().y > -max_vel){
                    this.body.applyForceToCenter(0, -max_force, true);
                }
                
                state = MOVE;
            }

            //apply force right
            if(Gdx.input.isKeyPressed(Keys.D)){
                if(this.body.getLinearVelocity().x < max_vel){
                    this.body.applyForceToCenter(max_force, 0, true);
                } 
                state = MOVE;
            }

            //apply force up
            if(Gdx.input.isKeyPressed(Keys.W)){
                if(this.body.getLinearVelocity().y < max_vel){
                    this.body.applyForceToCenter(0, max_force, true);
                }
                
                state = MOVE;

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
        
        if (!animated_sprite.equals(sprites.get(state))){
            animated_sprite = sprites.get(state);
        }
        
        
    }
    
    public boolean isSpacePressed(){
        return space_pressed;
    }
    
    public void setOrbit(Sun sun){
        float distance = body.getPosition().dst(sun.getPosition());
        body.setLinearVelocity(0, (float)Math.sqrt((sun.getMass())/distance));
        System.out.println("\nMASS OF SUN: "+sun.getMass()+"\nVELOCITY: "+body.getLinearVelocity().y);
    }
    
}
