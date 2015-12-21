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
    public GameManager(){
        width = Gdx.graphics.getWidth()/3;
        height = Gdx.graphics.getHeight()/3;
        world = new World(new Vector2(0,0), false);
        renderer = new Box2DDebugRenderer();
        logger = new FPSLogger();
        camera = new OrthographicCamera(width, height);
        
        game_objects = new ArrayList();
        for (int i = 0; i < 15; i++){
            game_objects.add(new AstroBro(world, camera));
        }
        
        
        
    }
    
    @Override
    public void render(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
    
        
        renderer.render(world, camera.combined);
        
        
    }

    @Override
    public void update() {
        
    }
    
    private void renderGameObjects(){
        for (GameObject o : game_objects){
            o.render();
        }
    }
    
}
