/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
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
private RayHandler ray_handler;
private SuperManager mngr;
private float fps;
private Array<Light> lights;
private boolean started;
private SpriteBatch sprite;

//player obviously
private Player player;
    public GameManager(SuperManager mngr){
        this.mngr = mngr;
        this.fps = 1/60f;
        max_count = 15;
        
        sprite = new SpriteBatch();
        
        started = false;
        
        lights = new Array();
        
        width = Gdx.graphics.getWidth()/5;
        height = Gdx.graphics.getHeight()/5;
       
        bodies = new Array();
        
        this.ray_handler = mngr.getRayHandler();
        this.world = mngr.getWorld();
        renderer = new Box2DDebugRenderer();
        
        this.world.getBodies(bodies);
        
        logger = new FPSLogger();
        this.camera = mngr.getCamera();
        this.camera.position.set(width * .5f, height * .5f, 0);
        this.camera.update();
        game_objects = new Array();
        
        player = new Player(world, camera);
        
        for (int i = 0; i < max_count; i++){
            game_objects.add(new NonPlayer(world, camera));
        }
        
        game_objects.add(player);
        
        ray_handler.setCombinedMatrix(camera);
        
        ray_handler.setCulling(true);
        ray_handler.setBlur(true);
        
        //ray_handler.setLightMapRendering(false);
        ray_handler.setShadows(true);
        
        ray_handler.setAmbientLight(0, 0, 1, .15f);
        
        
        //Sun
        this.addLight(8000, Color.YELLOW, 600, width/2, height/2 );
        
    }
    
    @Override
    public void render(){
              
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        //renderer.render(world, camera.combined);
        //update();
        this.renderGameObjects();
        ray_handler.updateAndRender();
        
        
        
        
        
        
        
        
        
    }

    @Override
    public void update() {
        
        ray_handler.setCombinedMatrix(camera);
        
        this.updateGameObjects();
        
        world.step(this.fps, 6, 2);
        
    }
    
    private void renderGameObjects(){
        for (GameObject o : game_objects){
            o.render(sprite);
        }
      
    }
    
    private void updateGameObjects(){
        for (GameObject o : game_objects){
            o.update(sprite);
        }
      
    }
    
    
    public void toggleLights(){
        for (Light l : lights){
            l.setActive(!l.isActive());
        }
    }
    
    @Override
    public void dispose() {
        world.dispose();
    }

    public void addLight(int num_rays, Color color, int reach, int x, int y){
        lights.add(new PointLight(ray_handler, num_rays, color, reach, x, y));
    }
    
    @Override
    public void updateLights() {
        
        ray_handler.setCombinedMatrix(camera);
    }

    @Override
    public void addLight(Light light) {
        lights.add(light);
    }

    @Override
    public void turnOffLights() {
        for (Light l : lights){
            l.setActive(false);
        }
    }

    @Override
    public void turnOnLights() {
        for (Light l : lights){
            l.setActive(true);
        }
    }
    
}
