/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author johno-gel
 */
public class AstroBro implements GameObject{
private World world;
private OrthographicCamera camera;
private Sound[] sound;
private Body astro_body;
    public AstroBro(World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;
        sound = new Sound[20];
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyType.DynamicBody;
        float x = (float) (camera.viewportWidth*Math.random());
        float y = (float) (camera.viewportHeight*Math.random());
        circle_def.position.set(x,y);
        
        astro_body = world.createBody(circle_def);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(3f);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .5f;
        
        astro_body.createFixture(circle_fixture);
        
    }
    
    @Override
    public void update() {
    }

    @Override
    public void render() {
    }
    
}
