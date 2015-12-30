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
    private int title_width, title_height, space_width, space_height;
    
    public MenuManager(SuperManager mngr){
        
        this.mngr = mngr;
        
        lights = new Array();
        
        font = new BitmapFont();
        batch = new SpriteBatch();
        
        this.title_width = 160;
        this.title_height = 50;
        this.space_width = 120;
        this.space_height = 35;
        
        
        
        
    }
    
    @Override
    public void update() {
        
        if (Gdx.input.isKeyPressed(Keys.SPACE)){
            
            mngr.setController(SuperManager.GAME_MANAGER);
            
        }
        
        ray_handler.setCombinedMatrix(camera);
    }

    @Override
    public void render() {
        
        batch.setProjectionMatrix(camera.projection);   
        
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        ray_handler.setCombinedMatrix(camera);
        
        ray_handler.updateAndRender(); 

        batch.begin();

        
        //batch.draw(press_space, Gdx.graphics.getWidth()/2 - press_space.getWidth()/2, Gdx.graphics.getHeight()/2 - press_space.getHeight()/2);
        //batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, Gdx.graphics.getHeight()/2+100);
        batch.draw(title, 0 - this.title_width*0.5f, 20, this.title_width, this.title_height);
        batch.draw(press_space, 0 - this.space_width*0.5f, -40, this.space_width,this.space_height);
        
        
        batch.end();
        
    }

    @Override
    public void dispose() {
    }


    
    @Override
    public void initializeWorld(){
        
        mngr.initializeWorld();
        this.ray_handler = mngr.getRayHandler();
        this.camera = mngr.getCamera();
               
        press_space = new Texture(Gdx.files.internal("PressSpace.png"));
        title = new Texture(Gdx.files.internal("AstroBros.png"));
        
        ray_handler.setCombinedMatrix(camera);
        
        
        
        
    }

    @Override
    public void initialize() {
        mngr.initializeWorld();
        this.ray_handler = mngr.getRayHandler();
        this.camera = mngr.getCamera();
               
        press_space = new Texture(Gdx.files.internal("PressSpace.png"));
        title = new Texture(Gdx.files.internal("AstroBros.png"));
        
        
        
        
        
        PointLight pointLight = new PointLight(ray_handler, 800, Color.BLUE, 200, 0, 0);
        ray_handler.setCombinedMatrix(camera);
        
    }

    @Override
    public void resize(int width, int height) {
        this.camera = mngr.getCamera();
    }
    
}
