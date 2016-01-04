/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.utils.Array;


/**
 *
 * @author johno-gel
 */
public abstract class AstroBro extends CircleObject{
protected float health;
protected Array<JointDef> joints;
protected Array<Texture> frames;
protected int frame, ticker;
    
    public AstroBro(){
        frames = new Array(120);
        frame = 0;
        ticker = 0;
    }
    
    @Override
    public void update(SpriteBatch batch) {
        //System.out.println("Angle: "+body.getAngle());
        batch.setProjectionMatrix(camera.combined);
        
        
        ticker++;
        if(ticker % 30 == 0){
            frame++;
        
        }
        
        if(frame > frames.size-1){
            frame = 0;
        }
        
        if (ticker > 2999){
            ticker = 0;
        }
        //batch.enableBlending();
    }

    public Array<JointDef> getJoints(){
        return joints;
    }
    
    public void addJoint(JointDef joint){
        joints.add(joint);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        //batch.begin();       
        sprite.draw(batch, body);
        //batch.end();
        
    }
    

    
    @Override
    public void dispose(){
     
        //body.destroyFixture(null);
        sprite.getTexture().dispose();
        texture.dispose();
    }
    
}
