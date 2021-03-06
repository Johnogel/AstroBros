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
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.support.SoundPlayer;
import com.johnogel.astrobros.support.TextureHandler;


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
private final float DELTA = .05f;
private int index;
private boolean super_controller_changed;
private final TextureHandler texture_handler;
private AssetManager asset_manager;
private boolean transitioning;
private boolean closing;

protected SoundPlayer sound_player;
public static final int 
        MENU_MANAGER = 0,
        GAME_MANAGER = 1,
        OPENING_MANAGER = 2;

    public SuperManager(){
        lights = new Array();
        managers = new Array();
        closing = false;
        asset_manager = new AssetManager();
        
        transitioning = false;
        
        /*this.world = world;
        this.camera = camera;
        this.ray_handler = ray_handler;
        */
        texture_handler = new TextureHandler(this);
        
        
        
        alpha = 0f;
        //music = Gdx.audio.newMusic(Gdx.files.internal(TITLE_SONG));
        
        sound_player = new SoundPlayer();
        sound_player.initialize();
        
        managers.add(new MenuManager(this));
        managers.add(new GameManager(this));
        managers.add(new OpeningManager(this));
        
        //initialize();
        
        
        shape_renderer = new ShapeRenderer();
        
        fading_in = false;
        fading_out = false;
        
        super_controller_changed = false;

    }
    
    public void close(){
        closing = true;
    }
    
    public AssetManager getAssetManager(){
        return asset_manager;
    }
    
    public void transition(){
        fading_out = true;
        //transitioning = true;
    }
    
    @Override
    public void update() {
        asset_manager.update();
        if(!isTransitioning()){
            manager.update();
        }
    }
    
    public TextureHandler getTextureHandler(){
        return texture_handler;
    }
    
    @Override
    public void render() {
        manager.render();
        resolveTransition();
    }
    
    
    private void resolveTransition(){
        if((fading_out || fading_in) ){
            if(fading_out){
                alpha += DELTA;
                if(alpha > .999f){
                    fading_out = false;
                    fading_in = true;
                    if(this.super_controller_changed){

                        manager = managers.get(index);
                        this.super_controller_changed = false;
                        sound_player.dispose();
                        manager.initialize();
                        manager.update();
                    }
                    else {
                        
                        manager.initializeController();
                        manager.update(); 
                        
                    }         
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
        
//        else if (texture_handler.isLoading() && transitioning){
//            Gdx.gl.glEnable(GL20.GL_BLEND);
//            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//            shape_renderer.setColor(0, 0, .50f, .08f);
//            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);  
//            shape_renderer.rect(-50, 0,1200, 1200);            
//            shape_renderer.end();
//            Gdx.gl.glDisable(GL20.GL_BLEND);
//                
//        }
//        else if(transitioning){
//            fading_in = true;
//            transitioning = false;
//        }
    }
    
    public void fadeIn(){
        fading_in = true;
        alpha = 1;
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
    
    public void setSuperController(int index){
        
        this.index = index;
        this.super_controller_changed = true;
        
    }
    
    @Override
    public void dispose() {
        for(Controller m: managers){
            m.dispose();
        }
        
        sound_player.dispose();
        shape_renderer.dispose();
        
        //texture_handler.clear();
        if(closing){
            asset_manager.dispose();
    
        }
    }

    public float getLoadingProgress(){
        return asset_manager.getProgress();
    }
    
@Override
    public void initializeWorld(){
        if(ray_handler != null){
            ray_handler.dispose();
        }
        if(sound_player != null){
            //sound_player.dispose();
        }
        
        //sound_player.initialize();
        
        //texture_handler.disposeAtlases();
        
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
    
    public SoundPlayer getSoundPlayer(){
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
    
    public boolean isLoading(){
        return texture_handler.isLoading();
    }

    @Override
    public void initialize() {
        ray_handler = new RayHandler(world);
        
        //music = Gdx.audio.newMusic(Gdx.files.internal(TITLE_SONG));
        manager = managers.get(SuperManager.OPENING_MANAGER);
        manager.initialize();

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
        
    }
    
}
