/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author johno-gel
 */
public class SuperManager extends AstroBros implements Controller{
static Controller manager;
static Array<Controller> managers;
public static final int 
        GAME_MANAGER = 0,
        MENU_MANAGER = 1;

    public SuperManager(World world, OrthographicCamera camera, RayHandler handler){
        managers = new Array();
        managers.add(new GameManager(world, camera, handler));
        managers.add(new MenuManager());
        
        manager = managers.get(SuperManager.MENU_MANAGER);
        
        
        
        

    }

    @Override
    public void update() {
        
        manager.update();
    }

    @Override
    public void render() {
        manager.render();
    }
    public static void setController(int INDEX){
        manager = managers.get(INDEX);
    }
    
    @Override
    public void dispose() {
        manager.dispose();
    }
    
}
