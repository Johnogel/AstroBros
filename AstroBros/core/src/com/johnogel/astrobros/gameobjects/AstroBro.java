/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.utils.Array;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;


/**
 *
 * @author johno-gel
 */
public abstract class AstroBro extends CircleObject{
protected float health;
protected Array<JointDef> joints;
protected Array<Texture> frames;
protected int frame, ticker;
protected Animation animation;
protected AnimatedBox2DSprite animated_sprite;
    
    public AstroBro(){
        //frames = new Array(120);
        frame = 0;
        ticker = 0;
        
       
    }
    
    @Override
    public void update(SpriteBatch batch) {
        //System.out.println("Angle: "+body.getAngle());
        batch.setProjectionMatrix(camera.combined);
        if(animated_sprite.isAnimationFinished()){
            animated_sprite.setPlaying(true);
        }
        
        ticker++;
        if(ticker % 35 == 0){
            frame++;
        }
        if(frame % 2 == 0){
            this.animated_sprite.setTime(0);
        }
        
        if(ticker > 5999){
            ticker = 0;
        }
        
        if(frame > 2999){
            frame = 0;
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
        //sprite.draw(batch, body);
        animated_sprite.draw(batch, body);
        //batch.end();
        
    }
    
    public void initializeAnimation(TextureAtlas atlas){
        float time = MathUtils.random.nextFloat()*6;
        animation = new Animation(1/48f, atlas.getRegions());
        AnimatedSprite a = new AnimatedSprite(animation);
        animated_sprite = new AnimatedBox2DSprite(a);
        animated_sprite.setTime(time);

        animated_sprite.setPlaying(true);
        //animated_sprite.setAutoUpdate(true);
        
        
        
        //body.setUserData(animated_sprite);
    }
    
    @Override
    public void dispose(){
     
        //body.destroyFixture(null);
        sprite.getTexture().dispose();
        texture.dispose();
    }
    
}
