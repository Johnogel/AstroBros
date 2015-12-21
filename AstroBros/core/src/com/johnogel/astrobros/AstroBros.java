package com.johnogel.astrobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class AstroBros extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        
        SuperManager manager;
	
	@Override
	public void create () {
            manager = new SuperManager();
	}

	@Override
	public void render () {   
            manager.update();
            manager.render();
	}
}
