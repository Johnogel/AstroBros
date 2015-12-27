/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public class BoundaryCircle extends CircleObject{
public final static float 
        INNER_ORBIT = 3.14f,
        OUTER_ORBIT = 3.95f,
        OUTER_BOUND = 10f;

private Body bounding_body;
    public BoundaryCircle(Sun sun, float type, World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;
        
        radius = sun.getRadius()*type;
        
        sounds = new Array(2);
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyDef.BodyType.StaticBody;
        circle_def.active = false;
        
        
        circle_def.position.set(sun.getPosition());
        
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
    }
    
    @Override
    public Body getBody() {
        return bounding_body;
    }

    @Override
    public void update(SpriteBatch batch) {
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
    }
    
}
