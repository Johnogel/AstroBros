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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.johnogel.astrobros.interfaces.GameObject;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public abstract class AstroBro implements GameObject{
protected World world;
protected Texture texture;
protected OrthographicCamera camera;
protected Sound[] sound;
protected Box2DSprite sprite;
protected Body astro_body;
    /*public AstroBro(World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;
        sound = new Sound[20];
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyType.DynamicBody;
        float x = (float) (camera.viewportWidth*Math.random());
        float y = (float) (camera.viewportHeight*Math.random());
        circle_def.position.set(x,y);
        
        texture = new Texture(Gdx.files.internal("test.png"));
        
        batch = new SpriteBatch();
        
        astro_body = world.createBody(circle_def);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(3f);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .0f;
        
        astro_body.createFixture(circle_fixture);
        
        sprite = new Box2DSprite(texture);
        
        astro_body.setUserData(sprite);
        
        astro_body.createFixture(circle_fixture);
        
        astro_body.setLinearVelocity((float)Math.random()*20-10,(float) Math.random()*20-10);
        
        circle_shape.dispose();
        
    }
    */
    @Override
    public void update(SpriteBatch batch) {
        //System.out.println("Angle: "+astro_body.getAngle());
        batch.setProjectionMatrix(camera.combined);
        //batch.enableBlending();
        
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        
        sprite.draw(batch, astro_body);
        //batch.draw(texture, astro_body.getPosition().x, astro_body.getPosition().y, 0, 0, 6f, 6f, 1f, 1f, astro_body.getAngle() * MathUtils.radiansToDegrees, 0, 0 ,0, 0, false, false);
        //batch.draw(texture, astro_body.getPosition().x - 3, astro_body.getPosition().y - 3, 6, 6);
        batch.end();
    }
    
    public Body getBody(){
        return astro_body;
    }
    
}
