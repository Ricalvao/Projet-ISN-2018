package com.isn.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.isn.platformer.Screens.PlayScreen;

public class Platformer extends Game {
	//Taille de l'écran
	public static final int SCREEN_WIDTH = 400;
	public static final int SCREEN_HEIGHT = 208;
	
	//Echelle pour Box2D
	public static final float SCALE = 100;

	//Bits pour les collisions
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short CHELL_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short RED_GEL_BIT = 8;
	public static final short ORANGE_GEL_BIT = 16;
	public static final short BLUE_GEL_BIT = 32;
	public static final short TURN_BIT = 64;
	public static final short LASER_BIT = 128;
	public static final short OBJECT_BIT = 256;
	public static final short POWER_BIT = 512;
	public static final short GOAL_BIT = 1024;
	public static final short PURPLE_GEL_BIT = 2048;
	public static final short CHELL_HANDS_BIT = 4096;
	public static final short GREEN_GEL_BIT = 8192;

	public SpriteBatch batch;

	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this, 6));
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}

