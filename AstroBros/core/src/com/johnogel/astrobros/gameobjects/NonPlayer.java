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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public class NonPlayer extends AstroBro{
public static float PUBLIC_RADIUS = 5.5f;
    public NonPlayer(World world, OrthographicCamera camera) {
        
        super();
        
        this.radius = PUBLIC_RADIUS;
        
        this.camera = camera;
        
        this.world = world;
        
        
        joints = new Array();
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyDef.BodyType.DynamicBody;
        float x = (float) (0);
        float y = (float) (camera.viewportHeight/2);
        circle_def.position.set(x,y);

        //texture = new Texture(Gdx.files.internal("test.png"));
        
        body = world.createBody(circle_def);
        
        body.applyForceToCenter(new Vector2(0,-3), true);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(radius);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .0f;
        
        body.createFixture(circle_fixture);
        
        //sprite = new Box2DSprite(texture);
        
        body.setUserData(sprite);
        
        body.createFixture(circle_fixture);
        
        body.setLinearVelocity((float)Math.random()*20-10,(float) Math.random()*20-10);
        
        circle_shape.dispose();
        
    }
    public NonPlayer(World world, OrthographicCamera camera, float distance) {
        this.radius = PUBLIC_RADIUS;
        
        this.world = world;
        this.camera = camera;
        sounds = new Array(20);
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyType.DynamicBody;
        circle_def.position.set(-distance,camera.position.y);
        
        //texture = new Texture(Gdx.files.internal("test.png"));
        
        body = world.createBody(circle_def);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(radius);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .0f;
        
        body.createFixture(circle_fixture);
        
        //sprite = new Box2DSprite(texture);
        
        //body.setUserData(sprite);
        
        body.createFixture(circle_fixture);
        
        //body.setLinearVelocity((float)Math.random()*20-10,(float) Math.random()*20-10);
        
        circle_shape.dispose();
    }
    
    @Override
    public void update(SpriteBatch batch){
        batch.setProjectionMatrix(camera.combined);
        super.update(batch);
    }

    @Override
    public void dispose() {
    }
    
}
