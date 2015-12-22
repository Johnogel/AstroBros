/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author johno-gel
 */
public class MenuManager implements Controller{
    
    SpriteBatch batch;
    BitmapFont font;
    Texture press_space;
    Texture title;
    RayHandler handler;
    OrthographicCamera camera;
    
    public MenuManager(OrthographicCamera camera, RayHandler handler){
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.handler = handler;
        this.camera = camera;
        
        
        press_space = new Texture(Gdx.files.internal("PressSpace.png"));
        title = new Texture(Gdx.files.internal("AstroBros.png"));
        
        handler.setCombinedMatrix(camera);
    }
    
    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Keys.SPACE)){
            SuperManager.setController(SuperManager.GAME_MANAGER);
        }
        handler.setCombinedMatrix(camera);
    }

    @Override
    public void render() {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        
        handler.updateAndRender(); 

        batch.begin();
        batch.draw(press_space, Gdx.graphics.getWidth()/2 - press_space.getWidth()/2, Gdx.graphics.getHeight()/2 - press_space.getHeight()/2);
        batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, Gdx.graphics.getHeight()/2+100);
        
        batch.end();
        
    }

    @Override
    public void dispose() {
    }

    @Override
    public void updateLights() {
        
        AstroBros.createPointLight(800, Color.BLUE, 200, 200, 200);
        handler.setCombinedMatrix(camera);
    }
    
}
