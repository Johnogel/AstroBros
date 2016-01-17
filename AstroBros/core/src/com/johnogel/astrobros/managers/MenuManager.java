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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.support.SoundPlayer;

/**
 *
 * @author johno-gel
 */
public class MenuManager implements Controller{
    
    SpriteBatch batch;
    BitmapFont font;
    Texture press_space;
    Texture title;
    RayHandler ray_handler;
    OrthographicCamera camera;
    private SuperManager mngr;
    private Array<Light> lights;
    private int title_width, title_height, space_width, space_height, loading_width, loading_height;
    private final SoundPlayer music;
    private Texture blue_bar, container;
    private boolean initialized = false;
    
    public MenuManager(SuperManager mngr){
        
        this.mngr = mngr;
        
        lights = new Array();
        
        font = new BitmapFont();
        batch = new SpriteBatch();
        
        this.title_width = 160;
        this.title_height = 50;
        this.space_width = 120;
        this.space_height = 35;
        this.loading_width = 80;
        this.loading_height = 25;
        
        
        
        
        music = mngr.getSoundPlayer();
        
        
        
    }
    
    @Override
    public void update() {
        
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !mngr.isLoading() && initialized){
            mngr.transition();
            
            
        }
        
        camera.rotate(15.0f);
        camera.update();
        
        ray_handler.setCombinedMatrix(camera);
        
        if(!initialized){
            initialized = true;
        }
    }

    @Override
    public void initializeController(){
        
        mngr.setController(SuperManager.GAME_MANAGER);
    }
        
    @Override
    public void render() {
        
        batch.setProjectionMatrix(camera.projection);   
        
        ray_handler.setCombinedMatrix(camera);
        
        ray_handler.updateAndRender(); 

        batch.begin();

        
        //batch.draw(press_space, Gdx.graphics.getWidth()/2 - press_space.getWidth()/2, Gdx.graphics.getHeight()/2 - press_space.getHeight()/2);
        //batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, Gdx.graphics.getHeight()/2+100);
        batch.draw(title, 0 - this.title_width*0.5f, camera.viewportHeight/2*.1f, this.title_width, this.title_height);
        if(mngr.isLoading()){
            batch.draw(container, 0 - this.loading_width*0.5f, -camera.viewportHeight/2*.6f, this.loading_width,this.loading_height);
            batch.draw(blue_bar, - (mngr.getLoadingProgress()*loading_width)*.5f, -camera.viewportHeight/2*.52f, this.loading_width * mngr.getLoadingProgress(),this.loading_height-9.5f);
        }
        else{
            batch.draw(press_space, 0 - this.space_width*0.5f, -camera.viewportHeight/2*.6f, this.space_width,this.space_height);

        }
        
        
        batch.end();
        
    }

    @Override
    public void dispose() {
        if(press_space != null){
            press_space.dispose();
            title.dispose();
            container.dispose();
            blue_bar.dispose();
        }
        
    }


    
    @Override
    public void initializeWorld(){
        
        mngr.initializeWorld();
        this.ray_handler = mngr.getRayHandler();
        this.camera = mngr.getCamera();
        
        if(press_space != null){
            dispose();
        }
        
        press_space = new Texture(Gdx.files.internal("PressSpace.png"));
        title = new Texture(Gdx.files.internal("AstroBros.png"));
        blue_bar = new Texture(Gdx.files.internal("blue-bar.png"));
        container = new Texture(Gdx.files.internal("load-container.png"));
        ray_handler.setCombinedMatrix(camera);
        
    }

    @Override
    public void initialize() {
        mngr.initializeWorld();
        this.ray_handler = mngr.getRayHandler();
        this.camera = mngr.getCamera();
               
        if(press_space != null){
            dispose();
        }
        
        press_space = new Texture(Gdx.files.internal("PressSpace.png"));
        title = new Texture(Gdx.files.internal("AstroBros.png"));
        blue_bar = new Texture(Gdx.files.internal("blue-bar.png"));
        container = new Texture(Gdx.files.internal("load-container.png"));
        
        new PointLight(ray_handler, 200, Color.BLUE, 600, 0, 300);
        new PointLight(ray_handler, 200, Color.BLUE, 600, 0, -300);
        
        ray_handler.setCombinedMatrix(camera);

        music.setSong(SoundPlayer.TITLE_SONG);
        music.setLooping(true);
        music.playSong();
        
        initialized = false;
        
    }

    @Override
    public void resize(int width, int height) {
        this.camera = mngr.getCamera();
    }

    @Override
    public boolean isPaused() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        music.stop();
    }
    
}
