/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.interfaces;

import box2dLight.Light;

/**
 *
 * @author johno-gel
 */
public interface Controller {
    
    public void update();
    public void render();
    public void resize(int width, int height);
    public void initializeWorld();
    public void initialize();
    public void initializeController();
    public void dispose();
    public boolean isPaused();
    public void stop();
   
    
    
}
