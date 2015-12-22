package com.johnogel.astrobros;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class AstroBros extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        
        World world;
        OrthographicCamera camera;
        
        int width, height;
        
        SuperManager manager;
	
        RayHandler handler;
        
	@Override
	public void create () {
            world = new World(new Vector2(0,0), false);
            width = Gdx.graphics.getWidth()/5;
            height = Gdx.graphics.getHeight()/5;
            camera = new OrthographicCamera(width, height);
            handler = new RayHandler(world);
            handler.setCombinedMatrix(camera);
            manager = new SuperManager(world, camera, handler);

            new PointLight(handler, 800, Color.YELLOW, 350, width/2, height/2);
	}

	@Override
	public void render () {   
            
            manager.update();
            manager.render();
	}
        
        @Override
        public void dispose(){
            handler.dispose();
            manager.dispose();
        }
}
