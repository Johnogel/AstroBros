/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author johno-gel
 */
public class SuperManager extends AstroBros implements Controller{
Controller manager;
    public SuperManager(World world, OrthographicCamera camera, RayHandler handler){
        manager = new GameManager(world, camera, handler);
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render() {
        manager.render();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
    
}
