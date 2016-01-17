/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.gameobjects;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.levels.Level;
import com.johnogel.astrobros.support.TextureHandler;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author johno-gel
 */
public class BlackHole extends Sun{

    public BlackHole(Level level, Array<Sun> suns, int num_rays, Color color, int intensity, float x, float y){
        
        super(level, suns, num_rays, color, intensity, x, y);
        
        texture = level.getTextureHandler().getTexture(TextureHandler.BLACK_HOLE);
        
    }
    
    
    
}
