package com.isn.platformer.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class Laser extends Sprite{

    PlayScreen screen;
    World world;
    TextureRegion laser;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    Body body;
    
    public Laser(PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        laser = new TextureRegion(new Texture ("sprites//laser.png"));
        setBounds(x, y, 6  / Platformer.SCALE, 2 / Platformer.SCALE);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 6 / Platformer.SCALE : getX() - 6 / Platformer.SCALE, getY() +  2 / Platformer.SCALE);
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / Platformer.SCALE);
        fdef.filter.categoryBits = Platformer.LASER_BIT;
        fdef.filter.maskBits = Platformer.GROUND_BIT |
        					   Platformer.RED_GEL_BIT |
        					   Platformer.ORANGE_GEL_BIT |
        					   Platformer.BLUE_GEL_BIT |
		        			   Platformer.ENEMY_BIT |
		        			   Platformer.OBJECT_BIT|
	                           Platformer.POWER_BIT;
        fdef.density = 10;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        body.setLinearVelocity(new Vector2(fireRight ? 4 : -4, 0f));
        body.setGravityScale(0);
    }

    public void update(float dt){
        stateTime += dt;
        setRegion(laser);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}
