package com.isn.platformer.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class Enemy extends Sprite{
	protected World world;
    protected PlayScreen screen;
    public Body body;
    public Vector2 velocity;
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    
    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        
        velocity = new Vector2(-1, -2);
        
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 3; i++) {
            frames.add(new TextureRegion(new Texture("sprites//blob_" + i + ".png")));
        }
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / Platformer.SCALE, 16 / Platformer.SCALE);
        setToDestroy = false;
        destroyed = false;
    }

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }else if(!destroyed) {
            body.setLinearVelocity(velocity);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Platformer.SCALE);
        fdef.filter.categoryBits = Platformer.ENEMY_BIT;
        fdef.filter.maskBits = Platformer.GROUND_BIT |
				               Platformer.ENEMY_BIT |
	                           Platformer.RED_GEL_BIT|
	                           Platformer.ORANGE_GEL_BIT|
	                           Platformer.BLUE_GEL_BIT|
	                           Platformer.CHELL_BIT|
	                           Platformer.OBJECT_BIT|
        		               Platformer.POWER_BIT|
	                           Platformer.LASER_BIT|
	                           Platformer.TURN_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }
    
    public void hit() {
        setToDestroy = true;
    }
    
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
}
