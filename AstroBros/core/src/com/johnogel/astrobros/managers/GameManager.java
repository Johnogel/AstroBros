/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers;

import com.johnogel.astrobros.managers.screens.GameOverScreen;
import com.johnogel.astrobros.managers.screens.LevelWinScreen;
import com.johnogel.astrobros.interfaces.GameObject;
import com.johnogel.astrobros.interfaces.Controller;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.levels.Level;
import com.johnogel.astrobros.levels.LevelOne;
import com.johnogel.astrobros.levels.LevelThree;
import com.johnogel.astrobros.levels.LevelTwo;
import com.johnogel.astrobros.managers.screens.AwardScreen;
import com.johnogel.astrobros.managers.screens.GameEndScreen;
import com.johnogel.astrobros.managers.screens.LevelLossScreen;
import com.johnogel.astrobros.support.TextureHandler;

/**
 *
 * @author johno-gel
 */
public class GameManager implements Controller{
private World world; 
private Box2DDebugRenderer renderer;
private FPSLogger logger;
private final int width, height;
private OrthographicCamera camera;
protected Array<GameObject> game_objects, disposables;
private final int max_count;
private RayHandler ray_handler;
private final SuperManager mngr;
private final float fps;
private final Array<Level> levels;
private final Array<Controller> controllers;
private boolean started;
private SpriteBatch batch;
private final TextureHandler texture_handler;
private int total_score, top_score, lives;
private int prev_score;
private int level, controller_index;
private Controller controller;
private ShapeRenderer shape_renderer;
private Music music;

private float alpha, delta;

public final int 
        LEVEL_ONE = 0,
        LEVEL_TWO = 1,
        LEVEL_THREE = 2,
        GAME_OVER = 3,
        LEVEL_WIN = 4,
        LEVEL_LOSS = 5,
        GAME_END = 6,
        AWARD = 7;

    public GameManager(SuperManager mngr){
        this.mngr = mngr;
        this.fps = 1/48f;
        max_count = 50;
        alpha = 1;
        delta = .001f;
        
        game_objects = new Array();
        
        levels = new Array(6);
        controllers = new Array(10);        
        
        texture_handler = mngr.getTextureHandler();
        
        batch = new SpriteBatch(100);
        
        shape_renderer = new ShapeRenderer();
        
        started = false;
        
        width = Gdx.graphics.getWidth()/5;
        height = Gdx.graphics.getHeight()/5;

        level = this.LEVEL_ONE;
        
        total_score = 0;
        
        lives = 3;
    }
    
    @Override
    public void render(){
        
        
        //Gdx.gl20.glActiveTexture(GL20.);

        //renderer.render(world, camera.combined);
        //update();
        if(controller != null){
            controller.render();
            
        }
        
        /*
        alpha += delta;
        
        if(delta < 0 && alpha <.002f){
            delta = -delta;
        }
        if(delta > 0 && alpha > .998f){
            delta = -delta;
        }
        
        Gdx.gl20.glClearColor(1, 1, 1, alpha);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        */
    }
    
    public int getTotalScore(){
        return total_score;
    }
    
@Override
    public void initializeController(){
        if(controller_index < levels.size){
            controller = controllers.get(level);
            levels.get(level).initialize();
            levels.get(level).initializeGameObjects();
        }
        else{
            controller = controllers.get(controller_index);
            controller.initialize();
        }

    }
    
    public void resolveLevelWin(int score){
        total_score += score;
        prev_score = score;
        mngr.transition();
        //controller.stop();
        //controller = controllers.get(this.LEVEL_WIN);
        controller_index = this.LEVEL_WIN;
        //controller.initialize();
        
    }
    
    public void resolveEndGame(){
        mngr.transition();
        if(this.total_score > top_score / 2){
            controller_index = this.AWARD;
        }
        else{
            controller_index = this.GAME_OVER;
        }
        
    }
    
    public SuperManager getSuperManager(){
        return this.mngr;
    }
    //Such a sexy method
    public void resolveLevelLoss(){
        mngr.transition();
        lives--;
        System.out.println("LIVES: "+lives);
        //controller.stop();
        if (lives >= 0){
            //controller = controllers.get(this.LEVEL_LOSS);
            controller_index = this.LEVEL_LOSS;
        }
        else{
            //controller = controllers.get(this.GAME_OVER);
            controller_index = this.GAME_OVER;
        }
        
        //controller.initialize();
    }

    @Override
    public void update() {
        //System.out.println("\n\nWIDTH: "+camera.viewportWidth+"\nHEIGHT: "+camera.viewportHeight);
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
        
        else {
            
            if(!isPaused()){
                ray_handler.setCombinedMatrix(camera);            
            }
            
            
            
            if(!isPaused()){
                world.step(this.fps, 8, 4);
            }
            
            controller.update();
            
        }
        
//        if(levels.get(level).getTime()<1){
//            if(levels.get(level).win()){
//                level++;
//                
//            }
//            this.setLevel(level);
//
//        }
        
            
            
    }
    
    public int getPreviousScore(){
        return prev_score;
    }
    
    public void removeGameObject(GameObject o){
        game_objects.removeIndex(game_objects.indexOf(o, true));
    }
    
    public void removeGameObject(Body b){
        int index = -1;
        //b.destroyFixture(b.getFixtureList().pop());
        //b.setActive(false);
        //b.setAwake(false);
        for (GameObject o : game_objects){
            if(b.equals(o.getBody())){
                index = game_objects.indexOf(o, false);
                break;
            }
        }
        if(index > -1){
            game_objects.removeIndex(index);
        }
    }
    
    public void renderGameObjects(){

        levels.get(level).drawBackground(batch);
        batch.enableBlending();
        batch.begin();
        for (GameObject o : game_objects){
            o.render(batch);
        }

        batch.end();
        ray_handler.updateAndRender();
        levels.get(level).drawHUD(batch);
        
    }
    
    public void updateGameObjects(){
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
    
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
    
    public SpriteBatch getSpriteBatch(){
        return batch;
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
    
    public TextureHandler getTextureHandler(){
        return mngr.getTextureHandler();
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
        
        //ray_handler.setCombinedMatrix(camera);
        
        //ray_handler.setCulling(true);
        ray_handler.setBlur(true);
        
        //ray_handler.setLightMapRendering(false);
        ray_handler.setShadows(true);
        
        ray_handler.setAmbientLight(1, 1, 1, .10f);

        //Sun
        //this.addLight(8000, Color.YELLOW, 600, width/2, height/2 );
        
        
    }
    
    public void disposeGameObjectTextures(){
        for (int i = 0; i < game_objects.size; i++){
            game_objects.get(i).dispose();
            
        }

    }
    
    public void setLevel(int level){
        levels.get(this.level).dispose();
        this.level = level;
        controller_index = level;
//        controller = controllers.get(level);
//        levels.get(level).initialize();
//        levels.get(level).initializeGameObjects();
        //this.initializeWorld();
    }

    public ShapeRenderer getShapeRenderer(){
        return shape_renderer;
    }
    
    @Override
    public void initialize() {
        shape_renderer.dispose();
        shape_renderer = new ShapeRenderer();
        mngr.initializeWorld();
        this.camera = mngr.getCamera();
        this.camera.position.set(width * .5f, height * .5f, 0);
        this.camera.update();
        this.ray_handler = mngr.getRayHandler();
        this.world = mngr.getWorld();
        
        levels.clear();
        controllers.clear();
        
        levels.add(new LevelOne(this, 60));
        levels.add(new LevelTwo(this, 60));
        levels.add(new LevelThree(this, 60));
        
        top_score = 0;
        total_score = 0;
        
        controllers.add(levels.get(0));
        controllers.add(levels.get(1));
        controllers.add(levels.get(2));
        controllers.add(new GameOverScreen(this));
        controllers.add(new LevelWinScreen(this));
        controllers.add(new LevelLossScreen(this));
        controllers.add(new GameEndScreen(this));
        controllers.add(new AwardScreen(this));
        //this.setLevel(level);

        renderer = new Box2DDebugRenderer();
        
        logger = new FPSLogger();
        

        
        this.clearGameObjects();
        
        
        
        //game_objects.add(player);
        
        ray_handler.setCombinedMatrix(camera);
        
        //ray_handler.setCulling(true);
        ray_handler.setBlur(true);
        
        //ray_handler.setLightMapRendering(false);
        ray_handler.setShadows(true);
        
        ray_handler.setAmbientLight(1, 1, 1, .6f);
        
        level = LEVEL_ONE;
        //controller_index = level;
        controller = controllers.get(level);
        controller.initialize();
        levels.get(level).initializeGameObjects();
        levels.get(level).update();
        //this.setLevel(level);
        
        
        
        lives = 3;
        
        
        
        
        
        
    }
    
    public void updateTopScore(int score){
        top_score += score;
    }
    
    public int getTopScore(){
        return top_score;
    }
    
    @Override
    public void resize(int width, int height) {
        this.camera = mngr.getCamera();
        ray_handler.setCombinedMatrix(camera);
        for(Level l : levels){
            l.resize(width, height);
        }
        batch.setProjectionMatrix(camera.combined);
        
    }
    
    public int getCurrentLevel(){
        return level;
    }
    
    public int getNumberOfLevels(){
        return levels.size;
    }
    
    public void incrementLevel(){
        mngr.transition();
        if (level < levels.size - 1/*change back to one*/){
            this.setLevel(level + 1);
        }
        else{
            controller_index = this.GAME_END;
            
            //controller.initialize();
        }
        
 
    }

    @Override
    public boolean isPaused() {
        return controller.isPaused();
    }

    @Override
    public void stop() {
        music.stop();
    }
}
