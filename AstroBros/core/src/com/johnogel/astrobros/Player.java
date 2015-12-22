/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author johno-gel
 */
public class Player extends AstroBro{
private int max_vel, max_force;
    public Player(World world, OrthographicCamera camera) {
        super(world, camera);
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyDef.BodyType.DynamicBody;
        float x = (float) (camera.viewportWidth/2);
        float y = (float) (camera.viewportHeight/2);
        circle_def.position.set(x,y);
        
        astro_body = world.createBody(circle_def);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(3f);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .0f;
        
        astro_body.createFixture(circle_fixture);

        max_vel = 100;
        max_force = 100;
        
        
    }
    
    @Override
    public void update(){
        
        //apply force left
        if(Gdx.input.isKeyPressed(Keys.A)){
            if(this.astro_body.getLinearVelocity().x > -max_vel){
                this.astro_body.applyForceToCenter(-max_force, 0, true);
            }
            
        }
        
        //apply force down
        if(Gdx.input.isKeyPressed(Keys.S)){
            if(this.astro_body.getLinearVelocity().x > -max_vel){
                this.astro_body.applyForceToCenter(0, -max_force, true);
            }
            
        }
        
        //apply force right
        if(Gdx.input.isKeyPressed(Keys.D)){
            if(this.astro_body.getLinearVelocity().x > -max_vel){
                this.astro_body.applyForceToCenter(max_force, 0, true);
            }
            
        }
        
        //apply force up
        if(Gdx.input.isKeyPressed(Keys.W)){
            if(this.astro_body.getLinearVelocity().x > -max_vel){
                this.astro_body.applyForceToCenter(0, max_force, true);
            }
            
        }
        this.camera.position.set(this.astro_body.getPosition(), 0);
        this.camera.update();
        
        
    }
    
}
