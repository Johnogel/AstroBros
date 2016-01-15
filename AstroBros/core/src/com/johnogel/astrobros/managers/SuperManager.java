/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers;

import com.johnogel.astrobros.interfaces.Controller;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


/**
 *
 * @author johno-gel
 */
public final class SuperManager implements Controller{
static Controller manager;
static Array<Controller> managers;
private Array<Light> lights;
private World world;
private OrthographicCamera camera;
private RayHandler ray_handler;
private int width, height;
protected Music music;
public static final int 
        MENU_MANAGER = 0,
        GAME_MANAGER = 1;
public static final String
        GAMEPLAY_SONG = "music/DontSleep.wav",
        TITLE_SONG = "music/Sleep.wav";

    public SuperManager(World world, OrthographicCamera camera, RayHandler ray_handler){
        lights = new Array();
        managers = new Array();
        
        this.world = world;
        this.camera = camera;
        this.ray_handler = ray_handler;
        
        music = Gdx.audio.newMusic(Gdx.files.internal(TITLE_SONG));
        
        managers.add(new MenuManager(this));
        managers.add(new GameManager(this));
        
        initialize();
        manager = managers.get(SuperManager.MENU_MANAGER);
        manager.initialize();


    }
    
    
    
    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render() {
        manager.render();
    }
    
    public void setController(int INDEX){
        
        //manager.turnOffLights();
        manager.stop();
        manager = managers.get(INDEX);
        manager.initialize();
        
    }
    
    public void addLight(int num_rays, Color color, int reach, int x, int y){
        lights.add(new PointLight(ray_handler, num_rays, color, reach, x, y));
    }
    
    public World getWorld(){
        return world;
    }
    
    public OrthographicCamera getCamera(){
        return camera;
    }
    
    public RayHandler getRayHandler(){
        return ray_handler;
    }
    
    @Override
    public void dispose() {
        manager.dispose();
        music.dispose();
    }


    
@Override
    public void initializeWorld(){
        ray_handler.dispose();
        //music.stop();
        //music.dispose();
        world = new World(new Vector2(0,0), false);
        width = Gdx.graphics.getWidth()/5;
        height = Gdx.graphics.getHeight()/5;
        camera = new OrthographicCamera(width, height);
        ray_handler = new RayHandler(world);
        ray_handler.setCombinedMatrix(camera);
        
        
        
    }
    
    public Music getMusicStream(){
        return music;
    }
    
    public void setSong(String filename){
        //music.dispose();
        music = Gdx.audio.newMusic(Gdx.files.internal(filename));
    }

    @Override
    public void initialize() {
        ray_handler = new RayHandler(world);

    }

    @Override
    public void resize(int width, int height) {
        manager.resize(width, height);
    }

    @Override
    public boolean isPaused() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
    }
    
}
