package com.isn.platformer.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class BlueGel extends InteractiveTileObject{
	private float bounciness;

    public BlueGel(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Platformer.BLUE_GEL_BIT);
        bounciness = 1f;
        body.getFixtureList().get(0).setRestitution(bounciness);
    }
    
    public void setBounciness(float f) {
    	body.getFixtureList().get(0).setRestitution(f);
    	bounciness = f;
    }
    
    public float getBounciness() {
    	return bounciness;
    }
}
