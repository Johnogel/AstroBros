/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.johnogel.astrobros.levels.Level;

/**
 *
 * @author johno-gel
 */
public class Locator {
private final Player player, other_bro;
private Vector2 position, center;
private Texture texture;

    public Locator(Player player, Player other){
        this.player = player;
        this.other_bro = other;
    }
    
    public void initialize(){
        
    }
    
    public void reset(){
        
    }
    
    
    
}
