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
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public abstract class AstroBro extends CircleObject{

protected Array<JointDef> joints;

    @Override
    public void update(SpriteBatch batch) {
        //System.out.println("Angle: "+body.getAngle());
        batch.setProjectionMatrix(camera.combined);
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
        
        batch.begin();       
        sprite.draw(batch, body);
        batch.end();
        
    }
    

    
    @Override
    public void dispose(){
     
        //body.destroyFixture(null);
        sprite.getTexture().dispose();
        texture.dispose();
    }
    
}
