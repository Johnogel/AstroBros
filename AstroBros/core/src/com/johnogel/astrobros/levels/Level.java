/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnogel.astrobros.levels;


import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.johnogel.astrobros.managers.GameManager;
import com.johnogel.astrobros.gameobjects.AstroBro;
import com.johnogel.astrobros.gameobjects.Sun;
import com.johnogel.astrobros.gameobjects.Player;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.johnogel.astrobros.gameobjects.BoundaryCircle;
import com.johnogel.astrobros.gameobjects.CircleObject;
import com.johnogel.astrobros.gameobjects.Locator;
import com.johnogel.astrobros.gameobjects.NonPlayer;
import com.johnogel.astrobros.interfaces.Controller;
import com.johnogel.astrobros.support.Background;
import com.johnogel.astrobros.support.TextureHandler;

/**
 *
 * @author johno-gel
 */
public abstract class Level implements Controller{
protected final GameManager mngr;    

protected Array<Sun> suns;
protected Array<Body> sun_bodies;
protected Array<Body> bodies;
protected Array<Player> bros;
protected Array<Body> bro_bodies;
protected Array<AstroBro> controlled_bros;
protected Array<AstroBro> free_bros;
protected Array<Body> controlled_bodies;
protected Array<Body> free_bodies;

protected Array<Body> to_be_attached;
protected Array<Body> to_be_attached_to;

protected Array<Locator> locators;
protected Array<Body> to_be_destroyed;

protected World world;
protected Player player;
protected RayHandler ray_handler;
protected int width, height, player_index;

protected BitmapFont score;
protected CharSequence score_chars, timer_chars;
protected int safe_bros, timer, ticker;

protected final int START_TIME;
private final int MAX_BROS = 12;
protected Texture red_texture, blue_texture, boundary_texture;

protected BoundaryCircle inner_orbit, outer_orbit, outer_boundary;

protected boolean joint_def_created, goldilocks, is_red;
protected float camera_last_x, camera_last_y;
protected Background background;
protected TextureHandler texture_handler;
protected float sun_sound_constant;
protected long sun_sound_id, bump_sound_id;

protected Sound sun_sound, bump_sound;

protected int total_bros;

protected OrthographicCamera camera;


    public Level(GameManager mngr, int start_time){
        
        this.START_TIME = start_time;
        score_chars = "SAFE: 0/0";
        timer_chars = ""+start_time;
        score = new BitmapFont(Gdx.files.internal("data/score.fnt"));
        score.getData().setScale(0.3f, 0.3f);
        CharSequence glyphs = "0123456789";
        score.setFixedWidthGlyphs(glyphs);
        
        bodies = new Array(MAX_BROS);
        bros = new Array(MAX_BROS);
        

        background = new Background(this);
        
        controlled_bros = new Array(MAX_BROS);
        free_bros = new Array(MAX_BROS);
        
        bro_bodies = new Array(MAX_BROS);
        controlled_bodies = new Array(MAX_BROS);
        free_bodies = new Array(MAX_BROS);
        
        to_be_attached = new Array(MAX_BROS);
        to_be_attached_to = new Array(MAX_BROS);
        
        to_be_destroyed = new Array(MAX_BROS);
        
        locators = new Array(MAX_BROS);
        
        this.mngr = mngr;
        
        this.world = mngr.getWorld();
        
        is_red = true;
        goldilocks = false;
        
        suns = new Array(2);
        sun_bodies = new Array(2);
        
        
        
        this.texture_handler = mngr.getTextureHandler();
        
        red_texture = this.texture_handler.getTexture(TextureHandler.BOUNDARY_RED);
        blue_texture = this.texture_handler.getTexture(TextureHandler.BOUNDARY_BLUE);
        boundary_texture = this.texture_handler.getTexture(TextureHandler.BOUNDARY_OUTER);
        
        sun_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.wav"));
        bump_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bump.ogg"));
        bump_sound_id = bump_sound.play(0);
        sun_sound_id = sun_sound.play(0);
        
        
    }
    
    public Player getPlayer(){
        return player;
    }
    //should be called in child initialize method
    protected void initializeArrays(){
        
        for (AstroBro b : controlled_bros){
            controlled_bodies.add(b.getBody());
            bro_bodies.add(b.getBody());
            bros.add((Player)b);
        }
        
        for (AstroBro b : free_bros){
            free_bodies.add(b.getBody());
            bro_bodies.add(b.getBody());
            bros.add((Player)b);
        }
        
        for (Sun s : suns){
            sun_bodies.add(s.getBody());
        }
        
        setBroTextures();
        
        initializeAnimations();
        
        
    }
    
    public void updateArrays(){
        for (AstroBro b : controlled_bros){
            controlled_bodies.add(b.getBody());
            bro_bodies.add(b.getBody());
            //bros.add(b);
        }
        
        for (AstroBro b : free_bros){
            free_bodies.add(b.getBody());
            bro_bodies.add(b.getBody());
            //bros.add(b);
        }
        
        for (Sun s : suns){
            sun_bodies.add(s.getBody());
        }
        
    }

    //should also be called in initialize method
    protected void initializeContactListener(){
        world.setContactListener(new ContactListener(){

            @Override
            public void beginContact(Contact contact) {
                boolean is_bro;
                
                //check if a bro is touching the sun
                if (contact.getFixtureA().getBody().equals(suns.get(0).getBody())
                        || contact.getFixtureB().getBody().equals(suns.get(0).getBody())){
                    
                
                    if (bro_bodies.contains(contact.getFixtureA().getBody(), true)){
                        if(contact.getFixtureA().getBody().equals(player.getBody())){
                            notifyLoss();
                        }
                        
                        //contact.getFixtureA().getBody().setActive(false);
                        for(AstroBro b : bros){
                            if(b.getBody().equals(contact.getFixtureA().getBody())){
                                b.setAlive(false);
                                
                                to_be_destroyed.add(b.getBody());
                        
                            }
                        }
                        /*free_bodies.removeValue(contact.getFixtureA().getBody(), true);
                        controlled_bodies.removeValue(contact.getFixtureA().getBody(), true);
                        bro_bodies.removeValue(contact.getFixtureA().getBody(), true);
                        mngr.removeGameObject(contact.getFixtureA().getBody());*/
                        //System.out.println("SO FAR SO GOOD!---------------");
                    }
                    
                    if (bro_bodies.contains(contact.getFixtureB().getBody(), true)){
                        if(contact.getFixtureB().getBody().equals(player.getBody())){
                            notifyLoss();
                        }
                        //contact.getFixtureB().getBody().setActive(false);
                        for(AstroBro b : bros){
                            if(b.getBody().equals(contact.getFixtureB().getBody())){
                                b.setAlive(false);
                                
                        
                            }
                        }
                        /*free_bodies.removeValue(contact.getFixtureB().getBody(), true);
                        controlled_bodies.removeValue(contact.getFixtureB().getBody(), true);
                        bro_bodies.removeValue(contact.getFixtureB().getBody(), true);
                        mngr.removeGameObject(contact.getFixtureB().getBody());*/
                        
                        //System.out.println("SO FAR SO GOOD!---------------");
                    }
            
                    
               
                }
                //check if both contacts are bros
                if(bro_bodies.contains(contact.getFixtureA().getBody(), false) && bro_bodies.contains(contact.getFixtureB().getBody(), false)){
                    
                    
                    bump_sound_id = bump_sound.play(.5f);
                    bump_sound.setPitch(bump_sound_id, .5f);
                    //System.out.println("THEY'RE BROS!!!!!!!!!!!!!!!!!!!!!");
                    //if contact A is free and B is trying to grab
                    if(free_bodies.contains(contact.getFixtureA().getBody(), false) && controlled_bodies.contains(contact.getFixtureB().getBody(), false))
                    {
                        to_be_attached_to.add(contact.getFixtureB().getBody());
                        //finds which free bro exactly is being contacted
                        for(int i = 0; i < free_bros.size; i++){

                            if(free_bros.get(i).getBody().equals(contact.getFixtureA().getBody())){
                                //bro.getBody().setActive(false);
                                //bro.getBody().setType(BodyDef.BodyType.StaticBody);
                                
                                to_be_attached.add(free_bros.get(i).getBody());
                                
                                //System.out.println("RADIUS: "+free_bros.get(i).getRadius()*2);

                                joint_def_created = true;

                                //world.createJoint(joint_def);

                                //joint_def.initialize(joint_def.bodyA, joint_def.bodyB, new Vector2(contact.getFixtureB().getBody().getPosition().x+3, contact.getFixtureB().getBody().getPosition().y+3));
                                //free_bros.get(i).setTexture("badlogic.jpg");
                                /*controlled_bodies.add(free_bodies.removeIndex(free_bodies.indexOf(free_bros.get(i).getBody(), false)));
                                controlled_bros.add(free_bros.removeIndex(i));*/

                                //System.out.println("CONATACT!!!! BRO SHOULD HAVE BEEN ADDED TO OTHER ARRAY 1111");

                            }

                        }

                    }

                    //if contact B is free and A is trying to grab
                    else if(free_bodies.contains(contact.getFixtureB().getBody(), false) && controlled_bodies.contains(contact.getFixtureA().getBody(), false)){
                        
                        to_be_attached_to.add(contact.getFixtureA().getBody());
                        
                        for(int i = 0; i < free_bros.size; i++){

                            if(free_bros.get(i).getBody().equals(contact.getFixtureB().getBody())){
                                //bro.getBody().setActive(false);
                                //bro.getBody().setType(BodyDef.BodyType.StaticBody);
                                //free_bros.get(i).setTexture("badlogic.jpg");
                                to_be_attached.add(free_bros.get(i).getBody());
                                
                                joint_def_created = true;

                                //world.createJoint(joint_def);

                                //joint_def.initialize(joint_def.bodyA, joint_def.bodyB, new Vector2(contact.getFixtureA().getBody().getPosition().x+3, contact.getFixtureA().getBody().getPosition().y+3));
                                
                                /*controlled_bodies.add(free_bodies.removeIndex(free_bodies.indexOf(free_bros.get(i).getBody(), false)));
                                controlled_bros.add(free_bros.removeIndex(i));*/
                                //System.out.println("CONATACT!!!! BRO SHOULD HAVE BEEN ADDED TO OTHER ARRAY 2222");
                                
                            }
   
                        }
                    }
                    
                    if(contact.getFixtureA().getBody().equals(outer_orbit.getBody()) || contact.getFixtureB().getBody().equals(outer_orbit.getBody())){
                        Body outer;
                        if(contact.getFixtureA().getBody().equals(outer_orbit.getBody())){
                            outer = contact.getFixtureA().getBody();
                        }
                        else if (contact.getFixtureB().getBody().equals(outer_orbit.getBody())){
                            outer = contact.getFixtureB().getBody();
                        }
                        
                        if(Vector2.dst(inner_orbit.getPosition().x, inner_orbit.getPosition().y, player.getPosition().x, player.getPosition().y) > inner_orbit.getRadius()){
                            //System.out.println("GOLDILOCKS!!");
                        }
                  
                    }
                
                }
     
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        
        });
    }
    
    public OrthographicCamera getCamera(){
        return camera;
    }
    
    public RayHandler getRayHandler(){
        return ray_handler;
    }
    
    public World getWorld(){
        return world;
    }
    
    private void attachBodies(){
        /*System.out.println("CONTROLLED BROS SIZE: "+controlled_bros.size+"\nCONTROLLED BODIES SIZE: "+controlled_bodies.size);
        System.out.println("FREE BROS SIZE: "+free_bros.size+"\nFREE BODIES SIZE: "+free_bodies.size);
        System.out.println("BROS SIZE: "+bros.size);*/
        if(to_be_attached.size == to_be_attached_to.size && to_be_attached.size > 0){
            
            for(int i = 0; i < to_be_attached.size; i++){
                if(Gdx.input.isKeyPressed(Keys.SPACE)){
                    RevoluteJointDef joint_def = new RevoluteJointDef();
                    joint_def.bodyA = to_be_attached_to.get(i);
                    joint_def.bodyB = to_be_attached.get(i);
                    joint_def.collideConnected = false;
                    //joint_def.localAnchorA.set(0, free_bros.get(i).getRadius()*2);
                    float distance = NonPlayer.PUBLIC_RADIUS*2;

                    float a_x = joint_def.bodyA.getPosition().x;
                    float a_y = joint_def.bodyA.getPosition().y;
                    float b_x = joint_def.bodyB.getPosition().x;
                    float b_y = joint_def.bodyB.getPosition().y;

                    float angle = MathUtils.atan2(a_y - b_y, a_x - b_x)*MathUtils.radiansToDegrees + 180;

                    float x = distance*MathUtils.cosDeg(angle);
                    float y = distance*MathUtils.sinDeg(angle);

                    joint_def.localAnchorA.set(x, y);
                    
                    //angle = MathUtils.atan2(b_y - a_y, b_x - a_x)*MathUtils.radiansToDegrees + 180;

                    
                    
                    
                    joint_def.localAnchorB.set(0,0);
                    
                    
                    
                    
                    world.createJoint(joint_def);
                    //System.out.println("RADIUS: "+free_bros.get(i).getRadius()*2);
                    //controlled_bodies.add(free_bodies.removeIndex(free_bodies.indexOf(to_be_attached.get(i), false)));
                    //controlled_bros.add(free_bros.removeIndex(i));

                    if(!controlled_bodies.contains(to_be_attached.get(i), false)){
                        controlled_bodies.add(free_bodies.removeIndex(free_bodies.indexOf(to_be_attached.get(i), false)));
                        for(AstroBro b : free_bros){
                            if(b.getBody().equals(joint_def.bodyB)){
                                controlled_bros.add(free_bros.removeIndex(free_bros.indexOf(b, false)));
                            }
                        }
                    }
                }
            }
            player.playStickSound();
            to_be_attached.clear();
            to_be_attached_to.clear();
        }
     
        

    }
    
    //should call gravitate helper method
    @Override
    public void update(){
        //player.update(mngr.getSpriteBatch());
        this.camera_last_x = camera.position.x;
        this.camera_last_y = camera.position.y;
        
        mngr.updateGameObjects();
        
        //switches player if connected
        if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
            
            player_index++;
            if(player_index > controlled_bros.size - 1){
                player_index = 0;
            }
            player.disablePlayer();
            player = (Player)controlled_bros.get(player_index);
            player.enablePlayer();
        }
        
        //switches player if connected
        if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
            
            player_index--;
            if(player_index == -1 || player_index > controlled_bros.size - 1){
                player_index = controlled_bros.size - 1;
            }
            player.disablePlayer();
            player = (Player)controlled_bros.get(player_index);
            player.enablePlayer();
        }
        
        gravitate();
        if(Gdx.input.isKeyJustPressed(Keys.R)){
            Array<Joint> joints = new Array(); 
            world.getJoints(joints);
            for(Joint j : joints){
                if(j.getBodyA().equals(player.getBody()) || j.getBodyB().equals(player.getBody())){
                    world.destroyJoint(j);
                }
            }
            int size = controlled_bros.size;
            for(int i = 0; i < size; i++){

                //System.out.println("---------------------------------------ADD DEM FREE BODIES");
                free_bodies.add(controlled_bodies.pop());
                free_bros.add(controlled_bros.pop());
                    

            }
            controlled_bros.clear();
            controlled_bodies.clear();
            free_bros.removeValue(player, false);
            free_bodies.removeValue(player.getBody(), false);
            controlled_bodies.add(player.getBody());
            controlled_bros.add(player);
        }
        
        //need to figure out way to remove bodies from arrays outside of contact listener
        attachBodies();
        if (joint_def_created){
            
            joint_def_created = false;
        }
        
        
        
        //checks if any bro is in goldilocks zone. I'm not sure if I'm spelling that correctly
        int i = 0;
        goldilocks = false;
        for(AstroBro b : bros){
            if(CircleObject.distance(b, inner_orbit) > inner_orbit.getRadius() && CircleObject.distance(b, outer_orbit) < outer_orbit.getRadius()){
                //System.out.println("GOLDILOCKS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                i++;
                goldilocks = true;
                
                if(is_red){
//                    inner_orbit.setTexture(BoundaryCircle.BLUE);
//                    outer_orbit.setTexture(BoundaryCircle.BLUE);
                    inner_orbit.setTexture(blue_texture);
                    outer_orbit.setTexture(blue_texture);
                    
                    is_red = false;
                    
                }
            }
            if(CircleObject.distance(b, inner_orbit) > outer_boundary.getRadius() ){
                this.enforceOutOfBounds(b);

            }
        }

        safe_bros = i;
        score_chars = "SAFE: "+i+"/"+this.total_bros;
        
        
        if(!goldilocks && !is_red){
            inner_orbit.setTexture(red_texture);
            outer_orbit.setTexture(red_texture);
            is_red = true;
        }
        
        ticker++;
        if(ticker%60 == 0){
            timer--;
            timer_chars = ""+timer;
        }
        
        //check if level is over
        if(timer < 1){
            if(this.safe_bros >= this.total_bros/2){
                notifyWin();
            }
            
            else{
                notifyLoss();
            }
            
        }
        
        
        
        cleanUp();
        
        updateSunSound();
        background.update();
        updateLocators(mngr.getSpriteBatch());
        
        
        

    }
    
    private void cleanUp(){
        for(Body b : to_be_destroyed){
            
     
            world.destroyBody(b);
            b.setUserData(null);
            b = null;
            
            for(int i = 0; i < locators.size; i++){
                if(locators.get(i).getPlayer().getBody().equals(b) || locators.get(i).getOtherBro().getBody().equals(b)){
                    locators.removeIndex(i);
                }               
            }
            
            for(int i = 0; i < bros.size; i++){
                if(bros.get(i).getBody().equals(b)){
                    if(b.equals(player.getBody())){
                        player.disablePlayer();
                        player = null;
                    }
                    bodies.removeValue(b, true);
                    free_bodies.removeValue(b, true);
                    controlled_bodies.removeValue(b, true);
                    bro_bodies.removeValue(b, true);
                    mngr.removeGameObject(b);
                    free_bros.removeValue(bros.get(i), true);
                    controlled_bros.removeValue(bros.get(i), true);
                    bros.removeIndex(i);
                }
        
            }
        
        }
        
        to_be_destroyed.clear();
    }
    
    private void updateLocators(SpriteBatch batch){
        for (Locator l : locators){
            l.update(batch);
        }
    }
    //should call mngr method to handle screen changing
    private void notifyWin(){
        mngr.resolveLevelWin();
    }
    
    //should call mngr method to handle screen changing
    private void notifyLoss(){
        mngr.resolveLevelLoss();
    }
    
    private void updateSunSound(){
        float dst = player.getBody().getPosition().dst(suns.get(0).getPosition());
        if(dst < outer_orbit.getRadius()){
            sun_sound.setVolume(sun_sound_id, (outer_orbit.getRadius()-dst)*this.sun_sound_constant);
        }
    }
    
    private void enforceOutOfBounds(AstroBro b){
        Sun s = suns.get(0);
        
        float distance_squared = CircleObject.distance(s, b)*CircleObject.distance(s, b);
        float mass = s.getMass();
        float force = 200 * (mass/distance_squared);

        float bro_x = b.getPosition().x;
        float bro_y = b.getPosition().y;
        float sun_x = s.getPosition().x;
        float sun_y = s.getPosition().y;

        float angle = MathUtils.atan2(bro_y - sun_y, bro_x - sun_x)*MathUtils.radiansToDegrees + 180;

        if(b.getBody().equals(player.getBody())){
            //System.out.println("ANGLE BEING USED: "+angle);
        }

        float force_x = force * MathUtils.cosDeg(angle);
        float force_y = force * MathUtils.sinDeg(angle);
                
                
                
        b.getBody().applyForceToCenter(force_x, force_y, true);
        
    }
    
    //should be called in child initialize method after players are initialized
    public void initializeLocators(){
        
        for(AstroBro b : bros){
            locators.add(new Locator(player, b));
        }
        
        for(Locator l : locators){
            l.initializeTexture(texture_handler);
            l.initialize();
        }
        
    }
    
    private void gravitate(){
        
        //sets force on each body towards each sun
        for (Sun s : suns){
            for (AstroBro b : bros){
                float distance_squared = CircleObject.distance(s, b)*CircleObject.distance(s, b);
                float mass = s.getMass();
                float force = mass/distance_squared;

                float bro_x = b.getPosition().x;
                float bro_y = b.getPosition().y;
                float sun_x = s.getPosition().x;
                float sun_y = s.getPosition().y;
                
                float angle = MathUtils.atan2(bro_y - sun_y, bro_x - sun_x)*MathUtils.radiansToDegrees + 180;
                
                if(b.getBody().equals(player.getBody())){
                    //System.out.println("ANGLE BEING USED: "+angle);
                }
                
                float force_x = force * MathUtils.cosDeg(angle);
                float force_y = force * MathUtils.sinDeg(angle);
                
                
                
                b.getBody().applyForceToCenter(force_x, force_y, true);
               
            }
        }
        
    }
    
    public void drawBackground(SpriteBatch batch){
        background.render(batch);
        
    }
    
    public void drawHUD(SpriteBatch batch){
        camera.update();
        batch.setProjectionMatrix(camera.projection);

        batch.begin();
        //score.setColor(Color.WHITE);
        
      
        //score.draw(batch, score_chars, player.getPosition().x, player.getPosition().y+Gdx.graphics.getHeight()/2);
        //score.draw(batch, score_chars, 0-camera.viewportWidth*0.4f,camera.viewportHeight*0.4f);
        score.draw(batch, score_chars, -camera.viewportWidth*0.11f,camera.viewportHeight*0.47f, 2, 0, false);
        score.draw(batch, timer_chars, camera.viewportWidth*0.45f,camera.viewportHeight*0.47f, 2, 0, false);
        //score.draw(batch, score_chars, 0,0);
        
        //score.draw(batch, score_chars, 0, 0, 20, 10, true);
        batch.end();
        for(Locator l : locators){
            l.render(batch);
        }
    }
    
    //must call after suns are created in initialize method
    protected void setOrbits(){
        for(Player p : bros){
            p.setOrbit(suns.get(0));
        }
    }
    
    protected void initializeBoundaries(){
        inner_orbit = new BoundaryCircle(suns.get(0), BoundaryCircle.INNER_ORBIT, world, camera);
        outer_orbit = new BoundaryCircle(suns.get(0), BoundaryCircle.OUTER_ORBIT, world, camera);
        outer_boundary = new BoundaryCircle(suns.get(0), BoundaryCircle.OUTER_BOUND, world, camera);
        is_red = true;
        inner_orbit.setTexture(red_texture);
        outer_orbit.setTexture(red_texture);
        outer_boundary.setTexture(boundary_texture);
        
        this.sun_sound_constant = 1 / outer_orbit.getRadius();
    }
    
    //call in initialize method
    protected void initializePlayer(){
        player_index = 0;
        controlled_bros.add(new Player(world, camera, 40));
        player = (Player)controlled_bros.get(player_index);
        player.enablePlayer();
        
    }
    
    //returns change in camera positions to be used by background
    public Vector2 getDeltaCameraPosition(){
        Vector2 delta = 
                new Vector2(camera.position.x - this.camera_last_x, 
                            camera.position.y - this.camera_last_y);
        return delta;
    }
    
    //must call in child initialize method
    protected void initializeBackground(){
        //background.dispose();
        background.initialize();
    }
    
    @Override
    public void initializeWorld(){
        clearArrays();
        mngr.initializeWorld();
        safe_bros = 0;
        timer = this.START_TIME;
        ticker = 0;
        score_chars = "SAFE: 0/"+this.total_bros;
        timer_chars = ""+timer;
        
        
        
        
        
        
        //bros.add(player);
        //this.world = mngr.getWorld();
        
        //don't change this...?

        //world.getBodies(bodies);
        
        

    }
    
    public void initializeGameObjects(){
//        for (AstroBro g : free_bros){
//            mngr.addGameObject(g);
//        }
        mngr.addGameObject(inner_orbit);
        mngr.addGameObject(outer_orbit);
        mngr.addGameObject(outer_boundary);
        for(Player p : bros){
            mngr.addGameObject(p);
        }
        
        for(Sun s : suns){
            mngr.addGameObject(s);
        }
        
        sun_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.wav"));
        
        sun_sound_id = sun_sound.play(0);
        sun_sound.setLooping(sun_sound_id, true);
        
        
        total_bros = bros.size;
        

    }
    
    public TextureHandler getTextureHandler(){
        return this.texture_handler;
    }
    
    private void setBroTextures(){
        for(Player p : bros){
            p.setTexture(mngr.getTextureHandler().getTexture(TextureHandler.ASTRO_BRO));
        }
    }
    
    public void clearArrays(){
        
        bodies.clear();
        suns.clear();
        
        controlled_bros.clear();
        free_bros.clear();
        
        bro_bodies.clear(); 
        controlled_bodies.clear(); 
        free_bodies.clear();
        
        locators.clear();
        
        to_be_destroyed.clear();
        
        for(AstroBro b : bros){
            b.dispose();
        }
        bros.clear();
        sun_sound.stop();
        sun_sound.dispose();
        bump_sound.stop();
        bump_sound.dispose();
        //sun_sound.dispose();
        
    }
    
    @Override
    public void render() {
        mngr.renderGameObjects();
    }


    @Override
    public void dispose() {
        //mngr.resetGameObjectArray();
        clearArrays();
        mngr.disposeGameObjectTextures();
        background.dispose();
        sun_sound.stop();
        sun_sound.dispose();
        

    }
    
@Override
    public void resize(int width, int height){
        this.camera = mngr.getCamera();
        this.ray_handler = mngr.getRayHandler();
        
    }
    
    public int getTime(){
        return timer;
    }
    
    public boolean win(){
        return safe_bros >= this.total_bros/2;
    }
    
    public void initializeAnimations(){
        for (Player p : bros){
            p.initializeAnimations(mngr.getTextureHandler());
        }
    }
    
}
