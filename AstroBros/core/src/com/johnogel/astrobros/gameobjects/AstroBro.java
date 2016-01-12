/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.support.TextureHandler;
import java.util.Random;
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
protected Array<Animation> animations;
protected AnimatedBox2DSprite animated_sprite;
protected Array<AnimatedBox2DSprite> sprites;
protected int state;
protected Sound stick_sound;
protected boolean alive;
protected final float 
        FPS = 1/24f;
protected final int  
        AWAKE = 2,
        SLEEP = 1,
        MOVE = 0;

    public AstroBro(){
        //frames = new Array(120);
        Random gen = new Random();
        
        frame = 0;
        ticker = gen.nextInt(80);
        
        sprites = new Array(3);
        animations = new Array(3);
        
        stick_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/stick.ogg"));
        
        alive = true;
       
    }
    
    public void playStickSound(){
        
        long id = stick_sound.play();
        stick_sound.setPitch(id, 1.6f);
        stick_sound.setVolume(id, 1);
    }
    
    @Override
    public void update(SpriteBatch batch) {
        //System.out.println("Angle: "+body.getAngle());
        batch.setProjectionMatrix(camera.combined);
        if(animated_sprite.isAnimationFinished()){
            animated_sprite.setPlaying(true);
        }
        
        ticker++;
        if(ticker % 60 == 0){
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
    
    public void setAlive(boolean state){
        this.alive = state;
        body.setAwake(state);
        //world.destroyBody(body);
    }
    @Override
    

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        //batch.begin();       
        //sprite.draw(batch, body);
        if(alive){
            sprites.get(state).draw(batch, body);
        }
        //animated_sprite.draw(batch, body);
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
    public void initializeAnimations(TextureHandler handler){
        float time = MathUtils.random.nextFloat()*6;
        
        //Animation a;
        AnimatedSprite s;
        
        //adding moving animation
        animations.add(new Animation(FPS, handler.getTextureAtlas(TextureHandler.MOVE).getRegions()));
        AnimatedSprite s1 = new AnimatedSprite(animations.get(MOVE));
        sprites.add(new AnimatedBox2DSprite(s1));
        sprites.get(MOVE).setTime(time);


        //adding sleep animation

        animations.add(new Animation(FPS, handler.getTextureAtlas(TextureHandler.SLEEP).getRegions()));
        s = new AnimatedSprite(animations.get(SLEEP));
        sprites.add(new AnimatedBox2DSprite(s));
        sprites.get(SLEEP).setTime(time);

        //adding awake animation

        animations.add(new Animation(FPS, handler.getTextureAtlas(TextureHandler.AWAKE).getRegions()));
        s = new AnimatedSprite(animations.get(AWAKE));
        sprites.add(new AnimatedBox2DSprite(s));
        sprites.get(AWAKE).setTime(time);
        
        state = SLEEP;
        animated_sprite = sprites.get(state);

    }
    
    @Override
    public void dispose(){
     
        //body.destroyFixture(null);
        //sprite.getTexture().dispose();
        //texture.dispose();
        stick_sound.dispose();
    }
    
}
