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
private Music music, sun;
private final Array<Sound> sounds;
private final Array<FileHandle> sound_handles, music_handles;
public static int
        BUMP_SOUND = 0,
        STICK_SOUND = 1,
        SIZZLE_SOUND = 2,
        GAMEPLAY_SONG = 0,
        TITLE_SONG = 1,
        BURNED_OUT = 2,
        WIN_JINGLE = 3,
        AWARDED = 4;

boolean initialized;


    public SoundPlayer(){
        initialized = false;
        sounds = new Array(3);
        sound_handles = new Array(3);
        music_handles = new Array(3);

    }
    
    public void initialize(){
        sound_handles.add(Gdx.files.internal("sounds/bump.ogg"));
        sound_handles.add(Gdx.files.internal("sounds/stick.ogg"));
        sound_handles.add(Gdx.files.internal("sounds/sizzle.ogg"));
        music_handles.add(Gdx.files.internal("music/AstroPlay.ogg"));
        music_handles.add(Gdx.files.internal("music/Astro.ogg"));
        music_handles.add(Gdx.files.internal("music/BurnedOut.ogg"));
        music_handles.add(Gdx.files.internal("music/WinJingle.ogg"));
        music_handles.add(Gdx.files.internal("music/Awarded.ogg"));
        //file_handles.add(Gdx.files.internal("sounds/bump.ogg"));
    }
    
    public void initializeLevelSounds(){
        sounds.add(Gdx.audio.newSound(sound_handles.get(0)));
        sounds.add(Gdx.audio.newSound(sound_handles.get(1)));
        sounds.add(Gdx.audio.newSound(sound_handles.get(2)));
        
        sun = Gdx.audio.newMusic(Gdx.files.internal("sounds/fire.ogg"));
        
    }
    
    public void initializeBonusLevelSounds(){
        sounds.add(Gdx.audio.newSound(sound_handles.get(0)));
        sounds.add(Gdx.audio.newSound(sound_handles.get(1)));
        sounds.add(Gdx.audio.newSound(sound_handles.get(2)));
        
        sun = Gdx.audio.newMusic(Gdx.files.internal("sounds/dark.ogg"));
    }
    
    
    
    public Sound getSound(int index){
        Sound sound = sounds.get(index);
        sound = Gdx.audio.newSound(sound_handles.get(index));
        return sounds.get(index);
    }
    
    public void playSound(int index, float volume){
        long id = sounds.get(index).play(0);
        sounds.get(index).setVolume(id, volume);
        sounds.get(index).play(id);
    }
    
    public void playSound(int index, float volume, float pitch){
        long id = sounds.get(index).play(0);
        sounds.get(index).setPitch(id, pitch);
        sounds.get(index).setVolume(id, volume);
        sounds.get(index).play(id);
    }
    
    public void playSoundEffect(int index){
        
    }
    
    public Music getSunSound(){
        return sun;
    }
    
    public void setSong(int file_handle){

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
        
        music = Gdx.audio.newMusic(music_handles.get(file_handle));
        
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
        sun.pause();
       
    }
    
    public void stop(){
        music.stop();
        sun.stop();
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
        if (sun != null){
            sun.dispose();
        }
        for(Sound s : sounds){
            s.dispose();
        }
        
        sounds.clear();
        
        //sounds.clear();
        
        //music = null;
    }
    
}
