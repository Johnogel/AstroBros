/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 *
 * @author johno-gel
 */
public class MusicPlayer {
private Music music;
boolean initialized;

    public MusicPlayer(){
        initialized = false;
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
        Music temp = Gdx.audio.newMusic(Gdx.files.internal(path));
        
        music = temp;
        
        System.out.println(music.toString());
        
        
        
        
        
    }
    
    public void play(){
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
