/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.johnogel.astrobros.interfaces.GameObject;

/**
 *
 * @author johno-gel
 */
public class Sun implements GameObject{
private float mass, radius;
private Body sun_body;

    public Sun(){
        
    }
    
    @Override
    public void update(SpriteBatch batch) {
    }

    @Override
    public void render(SpriteBatch batch) {
    }
    
}
