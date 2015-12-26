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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.interfaces.GameObject;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public abstract class CircleObject implements GameObject{
protected float radius;
protected World world;
protected Texture texture;
protected OrthographicCamera camera;
protected Box2DSprite sprite;
protected Body body;
protected Array<Sound> sounds;

    public Body getBody(){
        return body;
    }
    
    public void setTexture(String image_name){
        texture.dispose();
        texture = new Texture(Gdx.files.internal(image_name));
        sprite = new Box2DSprite(texture);
        body.setUserData(sprite);
        
    }
    
    public float getRadius(){
        return radius;
    }
    
    public Vector2 getPosition(){
        return body.getPosition();
    }
    
    
    
}
