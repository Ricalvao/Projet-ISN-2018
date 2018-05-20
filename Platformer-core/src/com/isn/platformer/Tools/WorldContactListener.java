package com.isn.platformer.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.isn.platformer.Platformer;
import com.isn.platformer.Sprites.Chell;
import com.isn.platformer.Sprites.Cube;
import com.isn.platformer.Sprites.Enemy;
import com.isn.platformer.Sprites.Laser;
import com.isn.platformer.TileObjects.Path;
import com.isn.platformer.TileObjects.RedGel;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Platformer.ENEMY_BIT | Platformer.OBJECT_BIT:
            case Platformer.ENEMY_BIT | Platformer.POWER_BIT:
            case Platformer.ENEMY_BIT | Platformer.TURN_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Platformer.CHELL_BIT | Platformer.PURPLE_GEL_BIT:
            case Platformer.CHELL_BIT | Platformer.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.CHELL_BIT)
                    ((Chell) fixA.getUserData()).die();
                else
                    ((Chell) fixB.getUserData()).die();
                break;
            case Platformer.ENEMY_BIT | Platformer.ENEMY_BIT:
            	((Enemy)fixA.getUserData()).reverseVelocity(true, false);
            	((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Platformer.CHELL_BIT | Platformer.ORANGE_GEL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.CHELL_BIT)
                    ((Chell)fixA.getUserData()).overOrange(true);
                else
                	((Chell)fixB.getUserData()).overOrange(true);
                break;
            case Platformer.POWER_BIT | Platformer.RED_GEL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.POWER_BIT) {
                    ((RedGel)fixB.getUserData()).turnOnOff(true);
                } else {
                	((RedGel)fixA.getUserData()).turnOnOff(true);
                }
                break;
            case Platformer.LASER_BIT | Platformer.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.ENEMY_BIT) {
                    ((Enemy)fixA.getUserData()).hit();
                    ((Laser)fixB.getUserData()).setToDestroy();
                } else {
                	((Enemy)fixB.getUserData()).hit();
                	((Laser)fixA.getUserData()).setToDestroy();
                }
                break;
            case Platformer.LASER_BIT | Platformer.GROUND_BIT:
            case Platformer.LASER_BIT | Platformer.POWER_BIT:
            	if(fixA.getFilterData().categoryBits == Platformer.LASER_BIT) {
                    ((Laser)fixA.getUserData()).setToDestroy();
                } else {
                	((Laser)fixB.getUserData()).setToDestroy();
                }
                break;
            case Platformer.LASER_BIT | Platformer.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.LASER_BIT) {
                    ((Laser)fixA.getUserData()).setToDestroy();
                    ((Cube)fixB.getUserData()).turnOn();
                } else {
                	((Laser)fixB.getUserData()).setToDestroy();
                    ((Cube)fixA.getUserData()).turnOn();
                }
                break;
            case Platformer.CHELL_BIT | Platformer.GOAL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.CHELL_BIT)
                    ((Chell) fixA.getUserData()).reachedGoal();
                else
                    ((Chell) fixB.getUserData()).reachedGoal();
                break;
            case Platformer.CHELL_HANDS_BIT | Platformer.GREEN_GEL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.CHELL_BIT)
                    ((Chell) fixA.getUserData()).touchGreen(true);
                else
                    ((Chell) fixB.getUserData()).touchGreen(true);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    	Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Platformer.CHELL_BIT | Platformer.ORANGE_GEL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.CHELL_BIT)
                    ((Chell)fixA.getUserData()).overOrange(false);
                else
                	((Chell)fixB.getUserData()).overOrange(false);
                break;
            case Platformer.POWER_BIT | Platformer.RED_GEL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.POWER_BIT) {
                    ((RedGel)fixB.getUserData()).turnOnOff(false);
                } else {
                	((RedGel)fixA.getUserData()).turnOnOff(false);
                }
                break;
            case Platformer.CHELL_HANDS_BIT | Platformer.GREEN_GEL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.CHELL_BIT)
                    ((Chell) fixA.getUserData()).touchGreen(false);
                else
                    ((Chell) fixB.getUserData()).touchGreen(false);
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
