/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.gameobjects.AstroBro;
import com.johnogel.astrobros.gameobjects.BlackHole;
import com.johnogel.astrobros.gameobjects.CircleObject;
import com.johnogel.astrobros.gameobjects.Player;
import com.johnogel.astrobros.gameobjects.Sun;
import com.johnogel.astrobros.managers.GameManager;
import com.johnogel.astrobros.support.SoundPlayer;
import com.johnogel.astrobros.support.TextureHandler;
import java.util.Random;

/**
 *
 * @author johno-gel
 */
public class BonusLevel extends Level{
    public BonusLevel(GameManager mngr, int start_time){
        super(mngr, start_time);
        
    }
    @Override
    public void initialize() {
        
        

        this.initializeWorld();
        
        this.ray_handler = mngr.getRayHandler();
        this.world = mngr.getWorld();
        this.camera = mngr.getCamera();
        
        width = mngr.getWidth();
        height = mngr.getHeight();
        
        //this.ray_handler.dispose();
        

        //this.free_bros.add(new Player(world, camera, 1));
        //this.free_bros.add(new Player(world, camera, 120));
        this.free_bros.add(new Player(world, camera, 100));
        this.free_bros.add(new Player(world, camera, 130));
        this.free_bros.add(new Player(world, camera, 160));
        this.free_bros.add(new Player(world, camera, 200));
                                
        //world.createJoint(joint_def);
        
        this.initializePlayer();
        this.initializeArrays();
        this.initializeContactListener();
        
        
        
        
        //adds sun to suns array without storing locally
        new Sun(this,  suns, 8000, Color.BLACK, 1000, width/2, height/2 );
        
        suns.get(0).initializeTexture(texture_handler, TextureHandler.BLACK_HOLE);
        
        this.setOrbits();
        
        this.initializeBoundaries();
        this.initializeBackground();
        this.initializeLocators();
        
    }
    
    @Override

    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            resolvePause();
        }
        
        
        
        if(!paused){
            
            if(sizzle){
                Random r = new Random();
                
                sound_player.playSound(SoundPlayer.SIZZLE_SOUND, .9f, r.nextFloat()*5+1.0f);
                sizzle = false;
            }
        
            this.camera_last_x = camera.position.x;
            this.camera_last_y = camera.position.y;

            mngr.updateGameObjects();

            if(dead){
                notifyLoss();
            }
            
            /*this was the part where you could switch, but I'm nixing it
            //switches player if connected
            if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){

                player_index++;
                if(player_index > controlled_bros.size - 1){
                    player_index = 0;
                }
                player.disablePlayer();
                player = (Player)controlled_bros.get(player_index);
                player.enablePlayer();
            }

            //switches player if connected
            if(Gdx.input.isKeyJustPressed(Keys.LEFT)){

                player_index--;
                if(player_index == -1 || player_index > controlled_bros.size - 1){
                    player_index = controlled_bros.size - 1;
                }
                player.disablePlayer();
                player = (Player)controlled_bros.get(player_index);
                player.enablePlayer();
            }
            */
            gravitate();
            if(!player.isSpacePressed()){
                Array<Joint> joints = new Array(); 
                world.getJoints(joints);
                for(Joint j : joints){
                    if(j.getBodyA().equals(player.getBody()) || j.getBodyB().equals(player.getBody())){
                        world.destroyJoint(j);
                    }
                }
                int size = controlled_bros.size;
                for(int i = 0; i < size; i++){

                    //System.out.println("---------------------------------------ADD DEM FREE BODIES");
                    free_bodies.add(controlled_bodies.pop());
                    free_bros.add(controlled_bros.pop());


                }
                controlled_bros.clear();
                controlled_bodies.clear();
                free_bros.removeValue(player, false);
                free_bodies.removeValue(player.getBody(), false);
                controlled_bodies.add(player.getBody());
                controlled_bros.add(player);
            }

            //need to figure out way to remove bodies from arrays outside of contact listener
            attachBodies();
            if (joint_def_created){

                joint_def_created = false;
            }



            //checks if any bro is in goldilocks zone. I'm not sure if I'm spelling that correctly
            int i = 0;
            goldilocks = false;
            for(AstroBro b : bros){
                if(CircleObject.distance(b, inner_orbit) > inner_orbit.getRadius() && CircleObject.distance(b, outer_orbit) < outer_orbit.getRadius()){
                    //System.out.println("GOLDILOCKS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    i++;
                    goldilocks = true;

                    if(is_red){
    //                    inner_orbit.setTexture(BoundaryCircle.BLUE);
    //                    outer_orbit.setTexture(BoundaryCircle.BLUE);
                        inner_orbit.setTexture(blue_texture);
                        outer_orbit.setTexture(blue_texture);

                        is_red = false;

                    }
                }
                if(CircleObject.distance(b, inner_orbit) > outer_boundary.getRadius() ){
                    this.enforceOutOfBounds(b);

                }
            }

            safe_bros = i;
            score_chars = i+" / "+this.total_bros;


            if(!goldilocks && !is_red){
                inner_orbit.setTexture(red_texture);
                outer_orbit.setTexture(red_texture);
                is_red = true;
            }

            ticker++;
            if(ticker%60 == 0){
                timer--;
                timer_chars = ""+timer;
                if(safe_bros == bros.size){
                    win_timer--;
                    
                }
                else{
                    win_timer = this.WIN_TIMER_MAX;
                    
                }
                win_timer_chars = ""+win_timer;
            }
            if(win_timer < 1 && safe_bros == bros.size){
                notifyWin();
            }
            
            if(bros.size == 1){
                notifyLoss();
            }

            //check if level is over
            if(timer < 1){
                if(this.safe_bros > this.total_bros/2){
                    notifyWin();
                }

                else{
                    notifyLoss();
                }

            }

            while (bumps > 0){
                float pitch = MathUtils.random(.4f) + .42f;
                System.out.println("PITCH: "+pitch);
                sound_player.playSound(SoundPlayer.BUMP_SOUND, .01f, pitch);
                bumps--;
            }

            cleanUp();

            if(!dead){
                updateSunSound();
                background.update();
                updateLocators(mngr.getSpriteBatch());
            }
            
            
        }
    }
    
    @Override
    public void initializeGameObjects(){
//        for (AstroBro g : free_bros){
//            mngr.addGameObject(g);
//        }
        mngr.addGameObject(inner_orbit);
        mngr.addGameObject(outer_orbit);
        mngr.addGameObject(outer_boundary);
        
        for(Player p : bros){
            mngr.addGameObject(p);
        }
        
        for(Sun s : suns){
            mngr.addGameObject(s);
        }
        
        sound_player.initializeBonusLevelSounds();
        
        //bump_sound = sound_player.getSound(SoundPlayer.BUMP_SOUND);
        //bump_sound_id = bump_sound.play(0);
        
        sun_sound = sound_player.getSunSound();
        
        //sun_sound_id = sun_sound.play(0);
        sun_sound.setLooping(true);
        sun_sound.play();
        
        total_bros = bros.size;
        
        dead = false;
        
        /*music.stop();
        music.dispose();*/
        sound_player.setSong(SoundPlayer.GAMEPLAY_SONG);
        
        sound_player.log();
        //music = mngr.getSuperManager().getMusicStream();
        /*music.setLooping(true);
        music.play();*/
        sound_player.setLooping(true);
        sound_player.setVolume(0.80f);
        
        //music.log();
        
        sound_player.playSong();

    }
    
    @Override
    protected void notifyWin(){
        sound_player.stop();
        mngr.resolveBonusWin();
    }
    
    @Override
    protected void notifyLoss(){
        sound_player.stop();
        mngr.resolveBonusLoss();
    }
}
