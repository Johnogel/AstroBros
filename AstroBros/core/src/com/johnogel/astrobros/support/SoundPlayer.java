/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author johno-gel
 */
public class SoundPlayer {
private Music music;
private Array<Sound> sounds;
private Array<FileHandle> file_handles;
public static int
        BUMP_SOUND = 0,
        STICK_SOUND = 1;

boolean initialized;


    public SoundPlayer(){
        initialized = false;
        sounds = new Array(3);
        file_handles = new Array(3);
    }
    
    public void initialize(){
        file_handles.add(Gdx.files.internal("sounds/bump.ogg"));
        file_handles.add(Gdx.files.internal("sounds/stick.ogg"));
        //file_handles.add(Gdx.files.internal("sounds/bump.ogg"));
    }
    
    public void loadSound(int index){
        Sound sound = sounds.get(index);
        sound = Gdx.audio.newSound(file_handles.get(index));
    }
    
    public void playSoundEffect(int index){
        
    }
    
    
    public void setSong(String path){

        if(music != null){
            music.setLooping(false);
            if(music.isPlaying()){
                music.stop();
            }
            music.dispose();
            System.out.println("DISPOSED OF SOME MUSIC");
        }
        else{
            initialized = true;
            System.out.println("INITIALIZED TRUE");
        }
        //music = null;
        
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        
        System.out.println(music.toString());
        
    }
    
    //used for playing song or long sound effect
    public void playSong(){
        music.play();
    }
    
    public void setLooping(boolean value){
        music.setLooping(value);
    }
    
    public void pause(){
        music.pause();
    }
    
    public void stop(){
        music.stop();
    }
    
    public void setVolume(float volume){
        music.setVolume(volume);
        
    }
    
    public void log(){
        System.out.println("");
        System.out.println("IS PLAYING: "+music.isPlaying());
        System.out.println("IS LOOPING: "+music.isLooping());
        System.out.println("VOLUME: "+music.getVolume());
        System.out.println("POSITION: "+music.getPosition());
        System.out.println("");
    }
            
    
    
    
    public void dispose(){
        if (music != null){
            music.stop();
            music.dispose();
        }
        //music = null;
    }
    
}
