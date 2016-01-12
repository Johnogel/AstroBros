/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.johnogel.astrobros.interfaces.GameObject;
import com.johnogel.astrobros.support.TextureHandler;

/**
 *
 * @author johno-gel
 */
public class Locator implements GameObject{
private final Player player, other_bro;
private Vector2 position, center;
private Texture texture;
private Sprite sprite;
private final float RADIUS = 5;
private float angle;

    public Locator(Player player, Player other){
        this.player = player;
        this.other_bro = other;
        
        center = player.getPosition();
        position = new Vector2();
        
        
        
        
    }
    
    public void initialize(){
        angle = center.angle(this.other_bro.getPosition());
        sprite.setRotation(angle);
        float x = 5*MathUtils.cosDeg(angle);
        float y = 5*MathUtils.sinDeg(angle);
        position.set(x, y);
        
    }
    
    public void initializeTexture(TextureHandler h){
        this.texture = h.getTexture(0);
        sprite = new Sprite(texture);

    }
        
    
@Override
    public void render(SpriteBatch batch){
        batch.begin();
        //batch.draw(texture, position.x, position.y, 1, 1);
        sprite.draw(batch);
        batch.end();
    }
    
    public void reset(){
        
    }

    @Override
    public void update(SpriteBatch batch) {
        angle = center.angle(this.other_bro.getPosition());
        sprite.setRotation(angle);
        float x = 5*MathUtils.cosDeg(angle);
        float y = 5*MathUtils.sinDeg(angle);
        
        position.set(x,y);
        
    }

 
    @Override
    public void dispose() {
        
    }
    
    
    
}
