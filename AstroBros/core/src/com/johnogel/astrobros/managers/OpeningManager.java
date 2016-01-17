/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.managers;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.interfaces.Controller;
import static com.johnogel.astrobros.managers.SuperManager.manager;
import com.johnogel.astrobros.support.SoundPlayer;

/**
 *
 * @author johno-gel
 */
public class OpeningManager implements Controller{
    SpriteBatch batch;
    BitmapFont font;
    //Texture press_space;
    Texture instructions;
    RayHandler ray_handler;
    OrthographicCamera camera;
    private SuperManager mngr;
    private Array<Light> lights;
    private int title_width, title_height, space_width, space_height, loading_width, loading_height;
    //private final SoundPlayer music;
    //private Texture blue_bar, container;
    private Array<Texture> textures;
    private int texture_index;
    private ShapeRenderer shape_renderer;
    private boolean fading_in, fading_out;
    private float alpha;
    private final float
            DELTA = .05f;
    
    public OpeningManager(SuperManager mngr){
        
        this.mngr = mngr;
        
        alpha = 0;
        
        texture_index = 0;
        
        lights = new Array();
        
        font = new BitmapFont();
        batch = new SpriteBatch();
        
        this.title_width = 160;
        this.title_height = 50;
        this.space_width = 120;
        this.space_height = 35;
        this.loading_width = 80;
        this.loading_height = 25;
        
        textures = new Array();
        
        
        shape_renderer = new ShapeRenderer();
        
        //music = mngr.getSoundPlayer();

    }
    
    private void transition(){
        fading_out = true;
    }
    
    private void resolveTransition(){
        if((fading_out || fading_in) ){
            if(fading_out){
                alpha += DELTA;
                if(alpha > .999f){
                    fading_out = false;
                    fading_in = true;
                    texture_index++;
                        
                }
            }
            
            
            else if (fading_in){
                alpha -= DELTA;
                if(alpha < .001f){
                    fading_in = false;
                }
                    
            }
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shape_renderer.setColor(0, 0, 0, alpha);
            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);  
            shape_renderer.rect(-50, 0,1200, 1200);            
            shape_renderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        
//        else if (texture_handler.isLoading() && transitioning){
//            Gdx.gl.glEnable(GL20.GL_BLEND);
//            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//            shape_renderer.setColor(0, 0, .50f, .08f);
//            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);  
//            shape_renderer.rect(-50, 0,1200, 1200);            
//            shape_renderer.end();
//            Gdx.gl.glDisable(GL20.GL_BLEND);
//                
//        }
//        else if(transitioning){
//            fading_in = true;
//            transitioning = false;
//        }
    }
    
    @Override
    public void update() {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !mngr.isLoading()){
            
            transition();
            //texture_index++;

            
        }
        if(texture_index > textures.size -1){
            mngr.getTextureHandler().initialize();
            mngr.getAssetManager().update();
            mngr.fadeIn();

            mngr.setController(SuperManager.MENU_MANAGER);
        }        
        /*camera.rotate(15.0f);
        camera.update();
        
        ray_handler.setCombinedMatrix(camera);*/
    }

    @Override
    public void initializeController(){
        
        mngr.setController(SuperManager.GAME_MANAGER);
    }
        
    @Override
    public void render() {
        
        batch.setProjectionMatrix(camera.view);   
        
        /*ray_handler.setCombinedMatrix(camera);
        
        ray_handler.updateAndRender(); */

        batch.begin();

        batch.draw(textures.get(texture_index), -1f, -1f, 2,2);
        
        //batch.draw(press_space, Gdx.graphics.getWidth()/2 - press_space.getWidth()/2, Gdx.graphics.getHeight()/2 - press_space.getHeight()/2);
        //batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, Gdx.graphics.getHeight()/2+100);
        /*batch.draw(instructions, 0 - this.title_width*0.5f, camera.viewportHeight/2*.1f, this.title_width, this.title_height);
        if(mngr.isLoading()){
            batch.draw(container, 0 - this.loading_width*0.5f, -camera.viewportHeight/2*.6f, this.loading_width,this.loading_height);
            batch.draw(blue_bar, - (mngr.getLoadingProgress()*loading_width)*.5f, -camera.viewportHeight/2*.52f, this.loading_width * mngr.getLoadingProgress(),this.loading_height-9.5f);
        }
        else{
            batch.draw(press_space, 0 - this.space_width*0.5f, -camera.viewportHeight/2*.6f, this.space_width,this.space_height);

        }*/
        
        
        batch.end();
        
        resolveTransition();
        
    }

    @Override
    public void dispose() {
        /*press_space.dispose();
        instructions.dispose();
        container.dispose();
        blue_bar.dispose();*/
        
        for(Texture t : textures){
            t.dispose();
        }
        textures.clear();
        
    }


    
    @Override
    public void initializeWorld(){
        
        mngr.initializeWorld();
        this.ray_handler = mngr.getRayHandler();
        this.camera = mngr.getCamera();
        
        
        ray_handler.setCombinedMatrix(camera);
        
    }

    @Override
    public void initialize() {
        mngr.initializeWorld();
        this.ray_handler = mngr.getRayHandler();
        this.camera = mngr.getCamera();
               
        
        /*press_space = new Texture(Gdx.files.internal("PressSpace.png"));
        instructions = new Texture(Gdx.files.internal("AstroBros.png"));
        blue_bar = new Texture(Gdx.files.internal("blue-bar.png"));
        container = new Texture(Gdx.files.internal("load-container.png"));*/
        
        textures.add(new Texture(Gdx.files.internal("credit.png")));
        textures.add(new Texture(Gdx.files.internal("libgdx.png")));
        textures.add(new Texture(Gdx.files.internal("instructions.png")));
        
        new PointLight(ray_handler, 200, Color.BLUE, 600, 0, 300);
        new PointLight(ray_handler, 200, Color.BLUE, 600, 0, -300);
        
        ray_handler.setCombinedMatrix(camera);


        
    }

    @Override
    public void resize(int width, int height) {
        this.camera = mngr.getCamera();
    }

    @Override
    public boolean isPaused() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    @Override
    public void stop() {
        //music.stop();
    }
    
}
