/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
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
    
    public MenuManager(){
        font = new BitmapFont();
        batch = new SpriteBatch();
        
        press_space = new Texture(Gdx.files.internal("PressSpace.png"));
        title = new Texture(Gdx.files.internal("AstroBros.png"));
    }
    
    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Keys.SPACE)){
            SuperManager.setController(SuperManager.GAME_MANAGER);
        }
    }

    @Override
    public void render() {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        

        batch.begin();
        batch.draw(press_space, Gdx.graphics.getWidth()/2 - press_space.getWidth()/2, Gdx.graphics.getHeight()/2 - press_space.getHeight()/2);
        batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, Gdx.graphics.getHeight()/2+100);
        
        batch.end();
        
    }

    @Override
    public void dispose() {
    }
    
}
