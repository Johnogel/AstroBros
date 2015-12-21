/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros;

/**
 *
 * @author johno-gel
 */
public class SuperManager implements Controller{
Controller manager;
    public SuperManager(){
        manager = new GameManager();
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render() {
        manager.render();
    }
    
}
