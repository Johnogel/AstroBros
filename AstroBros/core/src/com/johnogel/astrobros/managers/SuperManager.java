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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.support.SoundPlayer;


/**
 *
 * @author johno-gel
 */
public class SuperManager implements Controller{
static Controller manager;
static Array<Controller> managers;
private Array<Light> lights;
private World world;
private OrthographicCamera camera;
private RayHandler ray_handler;
private int width, height;
private ShapeRenderer shape_renderer;
private float alpha;
private boolean fading_in, fading_out;
private final float DELTA = .01f;

protected SoundPlayer sound_player;
public static final int 
        MENU_MANAGER = 0,
        GAME_MANAGER = 1;

    public SuperManager(World world, OrthographicCamera camera, RayHandler ray_handler){
        lights = new Array();
        managers = new Array();
        
        this.world = world;
        this.camera = camera;
        this.ray_handler = ray_handler;
        
        alpha = 0f;
        //music = Gdx.audio.newMusic(Gdx.files.internal(TITLE_SONG));
        
        sound_player = new SoundPlayer();
        sound_player.initialize();
        
        managers.add(new MenuManager(this));
        managers.add(new GameManager(this));
        
        //initialize();
        manager = managers.get(SuperManager.MENU_MANAGER);
        manager.initialize();
        
        shape_renderer = new ShapeRenderer();
        
        fading_in = false;
        fading_out = false;


    }
    
    public void transition(){
        fading_out = true;
    }
    
    @Override
    public void update() {
        if(!isTransitioning()){
            manager.update();
        }
    }

    @Override
    public void render() {
        manager.render();
        resolveTransition();
        

    }
    
    private void resolveTransition(){
        if(fading_out || fading_in){
            if(fading_out){
                alpha += DELTA;
                if(alpha > .999f){
                    fading_out = false;
                    fading_in = true;
                    manager.initializeController();
                    
                }
            }
            else if (fading_in){
                alpha -= DELTA;
                if(alpha < .001f){
                    fading_in = false;
                }
                    
            }
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shape_renderer.setColor(0, 0, 0, alpha);
            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);  
            shape_renderer.rect(-50, 0,1200, 1200);            
            shape_renderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
    
    private boolean isTransitioning(){
        return (fading_in || fading_out);
    }
    
    public void setController(int INDEX){
        
        //manager.turnOffLights();
        //manager.stop();
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
        sound_player.dispose();
        shape_renderer.dispose();
    }


    
@Override
    public void initializeWorld(){
        if(ray_handler != null){
            ray_handler.dispose();
        }
        if(sound_player != null){
            sound_player.dispose();
        }
        
        //music.stop();
        //music.dispose();
        //if(music_player != null){
         //   music_player.dispose();

        //}
        //music_player = new MusicPlayer();
        world = new World(new Vector2(0,0), false);
        width = Gdx.graphics.getWidth()/5;
        height = Gdx.graphics.getHeight()/5;
        camera = new OrthographicCamera(width, height);
        ray_handler = new RayHandler(world);
        ray_handler.setCombinedMatrix(camera);
        
        
        
    }
    
    public SoundPlayer getMusicPlayer(){
        return sound_player;
    }
    
    /*public Music getMusicStream(){
        return music;
    }*/
    
    public void setSong(String filename){
        //music.stop();
        //music.dispose();
        
        //music = Gdx.audio.newMusic(Gdx.files.internal(filename));
    }

    @Override
    public void initialize() {
        ray_handler = new RayHandler(world);
        //music = Gdx.audio.newMusic(Gdx.files.internal(TITLE_SONG));
        sound_player = new SoundPlayer();

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

    @Override
    public void initializeController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
