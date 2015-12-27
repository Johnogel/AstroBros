/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public class Player extends AstroBro{
private int max_vel, max_force;
private boolean space_pressed;

    public Player(World world, OrthographicCamera camera) {
        //super(world, camera);
        this.camera = camera;
        this.world = world;
        
        this.radius = 3f;
        
        joints = new Array();
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyDef.BodyType.DynamicBody;
        float x = (float) (0);
        float y = (float) (camera.viewportHeight/2);
        circle_def.position.set(x,y);
        
        texture = new Texture(Gdx.files.internal("test.png"));
        
        body = world.createBody(circle_def);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(radius);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .0f;
        
        body.createFixture(circle_fixture);
        
        sprite = new Box2DSprite(texture);
        
        body.setUserData(sprite);
        
        body.createFixture(circle_fixture);
        
        body.setLinearVelocity((float)Math.random()*20-10,(float) Math.random()*20-10);
        
        circle_shape.dispose();
        
        max_vel = 50;
        max_force = 800;
        
        
    }
    
    public boolean isGrabbing(){
        return space_pressed;
    }
    
    
    @Override
    public void update(SpriteBatch batch){
        //System.out.println("Angle: "+body.getAngle());
        
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
