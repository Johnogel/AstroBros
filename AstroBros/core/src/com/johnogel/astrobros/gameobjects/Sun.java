/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.interfaces.GameObject;
import com.johnogel.astrobros.levels.Level;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public class Sun implements GameObject{
private float mass, radius;
private Body sun_body;
private RayHandler ray_handler;
private PointLight light;
private OrthographicCamera camera;
private Texture texture;
private Box2DSprite sprite;

    public Sun(Level level, Array<Sun> suns, int num_rays, Color color, int intensity, float x, float y){
        
        this.camera = level.getCamera();
        
        this.ray_handler = level.getRayHandler();
        
        light = new PointLight(ray_handler, num_rays, color, intensity, x, y );
        
        mass = intensity*10;
        
        radius = intensity/10;
        
        suns.add(this);
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyDef.BodyType.StaticBody;
        circle_def.position.set(x,y);
        
        texture = new Texture(Gdx.files.internal("SunOutline.png"));
        
        
        sun_body = level.getWorld().createBody(circle_def);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(radius);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .0f;
        
        sun_body.createFixture(circle_fixture);
        
        sprite = new Box2DSprite(texture);
        
        sun_body.setUserData(sprite);
        
        sun_body.createFixture(circle_fixture);
        
        sun_body.setLinearVelocity((float)Math.random()*20-10,(float) Math.random()*20-10);
        
        circle_shape.dispose();
        
    }
    
    public void turnOff(){
        light.setActive(false);
    }
    
    public void turnOn(){
        light.setActive(true);
    }
    
    @Override
    public void update(SpriteBatch batch) {
        ray_handler.setCombinedMatrix(camera);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        
        sprite.draw(batch, sun_body);
        //batch.draw(texture, astro_body.getPosition().x, astro_body.getPosition().y, 0, 0, 6f, 6f, 1f, 1f, astro_body.getAngle() * MathUtils.radiansToDegrees, 0, 0 ,0, 0, false, false);
        //batch.draw(texture, astro_body.getPosition().x - 3, astro_body.getPosition().y - 3, 6, 6);
        batch.end();
        
    }

    @Override
    public void dispose(){
        sun_body.destroyFixture(null);
        texture.dispose();
        sprite.getTexture().dispose();
    }
}
