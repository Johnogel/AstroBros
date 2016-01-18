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
import com.badlogic.gdx.physics.box2d.Body;
import com.johnogel.astrobros.interfaces.GameObject;
import com.johnogel.astrobros.support.TextureHandler;

/**
 *
 * @author johno-gel
 */
public class Locator implements GameObject{
private final AstroBro player, other_bro;
private Vector2 position, center;
private Texture texture;
private Sprite sprite;
private final float RADIUS = 50;
private float angle;

    public Locator(AstroBro player, AstroBro other){
        this.player = player;
        this.other_bro = other;
        
        center = player.getPosition();
        position = new Vector2();
        
        
        
        
    }
    
    private void updateAngle(){
        float p_x = player.getPosition().x;
        float p_y = player.getPosition().y;
        float o_x = other_bro.getPosition().x;
        float o_y = other_bro.getPosition().y;

        angle = MathUtils.atan2(p_y - o_y, p_x - o_x)*MathUtils.radiansToDegrees + 180;
    }
    
    public void initialize(){
        updateAngle();
        sprite.setRotation(angle);
        float x = RADIUS*MathUtils.cosDeg(angle);
        float y = RADIUS*MathUtils.sinDeg(angle);
        position.set(x, y);
        
    }
    
    public void initializeTexture(TextureHandler h){
        this.texture = h.getTexture(TextureHandler.LOCATOR);
        sprite = new Sprite(texture);

    }
        
    public AstroBro getPlayer(){
        return player;
    }
    
    public AstroBro getOtherBro(){
        return other_bro;
    }
    
@Override
    public void render(SpriteBatch batch){
        if(!player.equals(other_bro) && (center.dst(other_bro.getPosition()) > 110)){
            batch.begin();
            //batch.draw(texture, position.x, position.y, 1, 1);
            //sprite.draw(batch);
            //batch.dr
            //batch.draw(sprite, position.x, position.y, 10, 10);
            batch.draw(sprite, position.x, position.y, 3f, 3f, 6, 6, 1, 1, angle-90);
            batch.end();
        }
    }
    
    public void reset(){
        
    }

    @Override
    public void update(SpriteBatch batch) {
        if(!center.equals(player.getPosition())){
            center = player.getPosition();
        }
        
        
        updateAngle();
        sprite.setRotation(angle);
        float x = RADIUS*MathUtils.cosDeg(angle);
        float y = RADIUS*MathUtils.sinDeg(angle);
        
        position.set(x,y);
        
    }

 
    @Override
    public void dispose() {
        
    }

    @Override
    public Body getBody() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
