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
import com.johnogel.astrobros.support.TextureHandler;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public class Sun extends CircleObject{
protected RayHandler ray_handler;
protected PointLight light;
protected OrthographicCamera camera;
protected Texture texture;
protected Box2DSprite sprite;

    public Sun(Level level, Array<Sun> suns, int num_rays, Color color, int intensity, float x, float y){
        
        this.camera = level.getCamera();
        
        this.ray_handler = level.getRayHandler();
        
        light = new PointLight(ray_handler, num_rays, color, intensity, x, y );
        
        mass = intensity*130000;
        
        radius = intensity/19.4f;
        
        suns.add(this);
        
        BodyDef circle_def = new BodyDef();
        circle_def.type = BodyDef.BodyType.StaticBody;
        circle_def.position.set(x,y);
        
        //texture = new Texture(Gdx.files.internal("SunOutline.png"));
        
        
        body = level.getWorld().createBody(circle_def);
        CircleShape circle_shape = new CircleShape();
        circle_shape.setRadius(radius);
        
        FixtureDef circle_fixture = new FixtureDef();
        circle_fixture.shape = circle_shape;
        
        circle_fixture.density = .5f;
        circle_fixture.friction = .8f;
        circle_fixture.restitution = .0f;
        
        body.createFixture(circle_fixture);
        
        /*sprite = new Box2DSprite(texture);
        
        body.setUserData(sprite);*/
        
        body.createFixture(circle_fixture);
        
        body.setLinearVelocity((float)Math.random()*20-10,(float) Math.random()*20-10);
        
        circle_shape.dispose();
        
    }
    
    public void initializeTexture(TextureHandler h, int index){
        texture = h.getTexture(index);
        sprite = new Box2DSprite(texture);
        
        body.setUserData(sprite);
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
        batch.setProjectionMatrix(camera.combined);
        //batch.begin();
        
        sprite.draw(batch, body);
        //batch.draw(texture, astro_body.getPosition().x, astro_body.getPosition().y, 0, 0, 6f, 6f, 1f, 1f, astro_body.getAngle() * MathUtils.radiansToDegrees, 0, 0 ,0, 0, false, false);
        //batch.draw(texture, astro_body.getPosition().x - 3, astro_body.getPosition().y - 3, 6, 6);
        //batch.end();
        
    }

    @Override
    public void dispose(){
        //body.destroyFixture(null);
        texture.dispose();
        sprite.getTexture().dispose();
    }

    public float getMass(){
        return mass;
    }
    
    @Override
    public Body getBody() {
        return body;
    }
}
