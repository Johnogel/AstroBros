/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers;

import com.johnogel.astrobros.interfaces.GameObject;
import com.johnogel.astrobros.interfaces.Controller;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.levels.Level;
import com.johnogel.astrobros.levels.LevelOne;
import com.johnogel.astrobros.levels.LevelThree;
import com.johnogel.astrobros.levels.LevelTwo;

/**
 *
 * @author johno-gel
 */
public class GameManager implements Controller{
private World world; 
private Box2DDebugRenderer renderer;
private FPSLogger logger;
private int width, height;
private OrthographicCamera camera;
protected Array<GameObject> game_objects, disposables;
private int max_count;
private RayHandler ray_handler;
private final SuperManager mngr;
private float fps;
private Array<Light> lights;
private Array<Level> levels;
private Array<Controller> controllers;
private boolean started;
private SpriteBatch batch;

private int level;

public final int 
        LEVEL_ONE = 0,
        LEVEL_TWO = 1,
        LEVEL_THREE = 2;

    public GameManager(SuperManager mngr){
        this.mngr = mngr;
        this.fps = 1/60f;
        max_count = 50;
        
        game_objects = new Array();
        
        levels = new Array();
        
        
        level = this.LEVEL_ONE;
        
        levels.add(new LevelOne(this, 10));
        levels.add(new LevelTwo(this, 60));
        levels.add(new LevelThree(this, 60));
        
        batch = new SpriteBatch();
        
        started = false;
        
        lights = new Array();
        
        width = Gdx.graphics.getWidth()/5;
        height = Gdx.graphics.getHeight()/5;

        level = this.LEVEL_ONE;

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
        
        //System.out.println("Game Objects: "+this.game_objects.size);
        if(Gdx.input.isKeyJustPressed(Keys.NUM_1)){
            this.setLevel(this.LEVEL_ONE);
        }
        
        else if(Gdx.input.isKeyJustPressed(Keys.NUM_2)){
            this.setLevel(this.LEVEL_TWO);
        }
        
        else if(Gdx.input.isKeyJustPressed(Keys.NUM_3)){
            this.setLevel(this.LEVEL_THREE);
        }
        
        else /*if(Gdx.input.isKeyPressed(Keys.ENTER))*/{
        
            ray_handler.setCombinedMatrix(camera);

            this.updateGameObjects();
            
            levels.get(level).update();
            
            world.step(this.fps, 6, 2); 
        }
        
        if(levels.get(level).getTime()<1){
            if(levels.get(level).win()){
                level++;
                
            }
            this.setLevel(level);

        }
        
            
            
    }
    
    private void renderGameObjects(){
        
        
        for (GameObject o : game_objects){
            o.render(batch);
        }
        
        levels.get(level).writeBitmapFonts(batch);
    }
    
    private void updateGameObjects(){
        for (GameObject o : game_objects){
            o.update(batch);
        }
      
    }
    
    public Array<GameObject> getGameObjects(){
        return game_objects;
    }
    
    public void clearGameObjects(){
        game_objects.clear();
    }
    
    
    public void toggleLights(){
        for (Light l : lights){
            l.setActive(!l.isActive());
        }
    }
    
    @Override
    public void dispose() {
        batch.dispose();
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
    
    public SpriteBatch getSpriteBatch(){
        return batch;
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
    public void addGameObject(GameObject o){
        game_objects.add(o);
    }
    
    public OrthographicCamera getCamera(){
        return camera;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public World getWorld(){
        return world;
    }
    
    public RayHandler getRayHandler(){
        return ray_handler;
    }
    
    public Array<Light> getLightsArray(){
        return lights;
    }
    
@Override
    public void initializeWorld(){
        
        mngr.initializeWorld(); 
        this.ray_handler = mngr.getRayHandler();
        this.world = mngr.getWorld();
        renderer = new Box2DDebugRenderer();
        
        logger = new FPSLogger();
        
        this.camera = mngr.getCamera();
        this.camera.position.set(width * .5f, height * .5f, 0);
        this.camera.update();
        
        this.clearGameObjects();
        
        
        
        //game_objects.add(player);
        
        ray_handler.setCombinedMatrix(camera);
        
        //ray_handler.setCulling(true);
        ray_handler.setBlur(true);
        
        //ray_handler.setLightMapRendering(false);
        ray_handler.setShadows(true);
        
        ray_handler.setAmbientLight(0, 0, 0, .1f);
        
        
        //Sun
        //this.addLight(8000, Color.YELLOW, 600, width/2, height/2 );
        
        
    }
    
    public void disposeGameObjectTextures(){
        for (int i = 0; i < game_objects.size; i++){
            game_objects.get(i).dispose();
            
        }

    }
    
    public void setLevel(int level){
        levels.get(level).dispose();
        this.level = level;
        levels.get(level).initialize();
        levels.get(level).initializeGameObjects();
        //this.initializeWorld();
    }

    @Override
    public void initialize() {
        this.setLevel(level);
        this.ray_handler = mngr.getRayHandler();
        this.world = mngr.getWorld();
        renderer = new Box2DDebugRenderer();
        
        logger = new FPSLogger();
        
        this.camera = mngr.getCamera();
        this.camera.position.set(width * .5f, height * .5f, 0);
        this.camera.update();
        
        this.clearGameObjects();
        
        
        
        //game_objects.add(player);
        
        ray_handler.setCombinedMatrix(camera);
        
        //ray_handler.setCulling(true);
        ray_handler.setBlur(true);
        
        //ray_handler.setLightMapRendering(false);
        ray_handler.setShadows(true);
        
        ray_handler.setAmbientLight(0, 0, 0, .1f);
        
        this.setLevel(level);
        
        
    }

    @Override
    public void resize(int width, int height) {
        this.camera = mngr.getCamera();
    }
}
