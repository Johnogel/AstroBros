/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 * @author johno-gel
 */
public interface GameObject {
    
    public void update(SpriteBatch batch);
    public void render(SpriteBatch batch);
    public Body getBody();
    public void dispose();
    
}
