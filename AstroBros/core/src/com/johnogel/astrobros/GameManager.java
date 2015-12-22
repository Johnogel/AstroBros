/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author johno-gel
 */
public class GameManager extends AstroBros implements Controller{
World world; 
private Box2DDebugRenderer renderer;
private FPSLogger logger;
private int width, height;
private OrthographicCamera camera;
private Array<GameObject> game_objects;
private Array<Body> bodies;
private int max_count;
private RayHandler handler;
private float fps;

//player obviously
private Player player;
    public GameManager(World world, OrthographicCamera camera, RayHandler handler){
        this.fps = 1/60f;
        max_count = 15;
        width = Gdx.graphics.getWidth()/5;
        height = Gdx.graphics.getHeight()/5;
       
        bodies = new Array();
        
        this.handler = handler;
        this.world = world;
        renderer = new Box2DDebugRenderer();
        
        this.world.getBodies(bodies);
        
        logger = new FPSLogger();
        this.camera = camera;
        this.camera.position.set(width * .5f, height * .5f, 0);
        this.camera.update();
        game_objects = new Array();
        
        player = new Player(world, camera);
        
        for (int i = 0; i < max_count; i++){
            game_objects.add(new NonPlayer(world, camera));
        }
        
        game_objects.add(player);
        
        handler.setCombinedMatrix(camera);
        
    }
    
    @Override
    public void render(){
        
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        handler.updateAndRender();
        
        renderer.render(world, camera.combined);
        
    }

    @Override
    public void update() {
        
        handler.setCombinedMatrix(camera);
        
        player.update();
        
        world.step(this.fps, 6, 2);
        
    }
    
    private void renderGameObjects(){
        for (GameObject o : game_objects){
            o.render();
        }
      
    }
    private void updateGameObjects(){
        for (GameObject o : game_objects){
            o.render();
        }
      
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    @Override
    public void updateLights() {
        AstroBros.createPointLight(800, Color.YELLOW, 350, width/2, height/2 );
        handler.setCombinedMatrix(camera);
    }
    
}
