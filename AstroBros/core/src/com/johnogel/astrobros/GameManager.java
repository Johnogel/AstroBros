/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

/**
 *
 * @author johno-gel
 */
public class GameManager implements Controller{
World world; 
private Box2DDebugRenderer renderer;
private FPSLogger logger;
private int width, height;
private OrthographicCamera camera;
private ArrayList<GameObject> game_objects;

private float fps;
    public GameManager(){
        this.fps = 1/60f;
        width = Gdx.graphics.getWidth()/5;
        height = Gdx.graphics.getHeight()/5;
        
        world = new World(new Vector2(0,0), false);
        renderer = new Box2DDebugRenderer();
        
        logger = new FPSLogger();
        camera = new OrthographicCamera(width, height);
        camera.position.set(width * .5f, height * .5f, 0);
        camera.update();
        game_objects = new ArrayList();
        for (int i = 0; i < 15; i++){
            game_objects.add(new AstroBro(world, camera));
        }
        
        
        
    }
    
    @Override
    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
    
        
        renderer.render(world, camera.combined);
        
        
    }

    @Override
    public void update() {
        world.step(this.fps, 6, 2);
        
    }
    
    private void renderGameObjects(){
        for (GameObject o : game_objects){
            o.render();
        }
    }
    
}
