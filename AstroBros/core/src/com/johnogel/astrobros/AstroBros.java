package com.johnogel.astrobros;

import com.johnogel.astrobros.managers.SuperManager;

import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class AstroBros extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        
        World world;
        OrthographicCamera camera;
        
        int width, height;
        
        SuperManager manager;
	
        RayHandler ray_handler;
        
        private Array<Texture> splashes;
        private Texture blue_bar, container;
        
        private boolean done_loading;
        
	@Override
	public void create () {
            batch = new SpriteBatch();
            
            
            
            splashes = new Array();
            manager = new SuperManager();
            manager.initialize();
            blue_bar = new Texture(Gdx.files.internal("blue-bar.png"));
            container = new Texture(Gdx.files.internal("load-container.png"));
            
            camera = new OrthographicCamera();
            
            done_loading = false;

	}

	@Override
	public void render () {   
            Gdx.gl20.glClearColor(0, 0, 0, 1);
        
            Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            
            
            manager.render();
           
            manager.update();
            
	}
        
        @Override
        public void dispose(){
            manager.close();
            manager.dispose();
        }
        
        
        @Override
        public void resize(int width, int height){
            float aspectRatio = (float) width / (float) height;
            
            camera = new OrthographicCamera(2f * aspectRatio, 2f);
            manager.resize(width, height);
        }
        
}
