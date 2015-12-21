/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author johno-gel
 */
public class AstroBro implements GameObject{
private World world;
private OrthographicCamera camera;
    public AstroBro(World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;
    }
    
    @Override
    public void update() {
    }

    @Override
    public void render() {
    }
    
}
