package com.isn.platformer.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class Cube extends Sprite{
	
	protected World world;
    protected PlayScreen screen;
    
    public Body body;
    
    private TextureRegion cube;
    
    private boolean on;
    private boolean turnOnOff;

    private boolean done;
    
    private float timer;
    
    public Cube(PlayScreen screen, float x, float y, boolean on){
        this.world = screen.getWorld();
        this.screen = screen;
        this.on = on;
        setPosition(x, y);
        defineCube();
        
        cube = on ? new TextureRegion(new Texture("sprites//cube_2.png")) : new TextureRegion(new Texture("sprites//cube_1.png"));
        setBounds(getX(), getY(), 16 / Platformer.SCALE, 16 / Platformer.SCALE);
        setRegion(cube);

    	turnOnOff = false;
    	done = false;
    	timer = 0;
    }

    public void update(float dt){
    	
        if(on && timer > 5 && !done)
        	turnOnOff = true;
        
    	if(turnOnOff && !done) {
    		
    		world.destroyBody(body);
    		if(!on)
    			this.screen.creator.cubes.add(new Cube(screen, body.getPosition().x, body.getPosition().y, true));
            if(on)
    			this.screen.creator.cubes.add(new Cube(screen, body.getPosition().x, body.getPosition().y, false));

    		done = true;
            
    	} else if (!done){
    		
    		timer += dt;
    		setPosition(body.getPosition().x - getWidth() / 2 + 1 / (2 * Platformer.SCALE), body.getPosition().y - getWidth() / 2 + 1 / (2 * Platformer.SCALE));
    	
    	}
    		
    }

    protected void defineCube() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        
        PolygonShape square = new PolygonShape();
        square.setAsBox(7.5f / Platformer.SCALE, 7.5f / Platformer.SCALE);

        fdef.shape = square;
        fdef.filter.categoryBits =  on ? Platformer.POWER_BIT : Platformer.OBJECT_BIT;
        fdef.filter.maskBits = Platformer.GROUND_BIT |
				               Platformer.ENEMY_BIT |
	                           Platformer.ORANGE_GEL_BIT|
	                           Platformer.RED_GEL_BIT|
	                           Platformer.BLUE_GEL_BIT|
	                           Platformer.CHELL_BIT|
	                           Platformer.LASER_BIT|
	                           Platformer.OBJECT_BIT|
	                           Platformer.POWER_BIT;
        
        body.createFixture(fdef).setUserData(this);
    }
    
    public void turnOn() {
    	turnOnOff = true;
    }
    
    public void draw (Batch batch){
        if(!turnOnOff)
            super.draw(batch);
    }
}
