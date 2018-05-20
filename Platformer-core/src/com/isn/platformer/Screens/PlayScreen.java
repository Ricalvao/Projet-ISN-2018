package com.isn.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isn.platformer.Platformer;
import com.isn.platformer.Sprites.Chell;
import com.isn.platformer.Sprites.Cube;
import com.isn.platformer.Sprites.Enemy;
import com.isn.platformer.TileObjects.BlueGel;
import com.isn.platformer.TileObjects.Path;
import com.isn.platformer.Tools.WorldContactListener;
import com.isn.platformer.Tools.WorldCreator;

public class PlayScreen implements Screen{
	private Platformer game;
	public int level;
	private boolean restart;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Tiled map variables
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    public WorldCreator creator;

    //sprites
    private Chell player;

    public PlayScreen(Platformer game, int level){

    	this.game = game;
    	this.level = level;
    	restart = false;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Platformer.SCREEN_WIDTH / Platformer.SCALE, Platformer.SCREEN_HEIGHT / Platformer.SCALE, gamecam);

        //Load our map and setup our map renderer
        map = new TmxMapLoader().load("levels//test" + level + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Platformer.SCALE);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new WorldCreator(this);

        //create mario in our game world
        player = new Chell(this);

        world.setContactListener(new WorldContactListener());
    }

    public void show() {
    	//
    }

    public void handleInput(float dt){
        //control our player using immediate impulses
        if(player.currentState != Chell.State.DEAD) {
            
        	if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                player.jump();
        	
        	if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                player.fire();
        	
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            	for (BlueGel blueGel : creator.getBlueGels())
            		if(blueGel.getBounciness() != 1.2f)
                    	blueGel.setBounciness(1.2f);
            } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            	for (BlueGel blueGel : creator.getBlueGels())
                    if(blueGel.getBounciness() != 0.5f)
                    	blueGel.setBounciness(0.5f);
            } else {
            	for (BlueGel blueGel : creator.getBlueGels())
                    if(blueGel.getBounciness() != 1f)
                    	blueGel.setBounciness(1f);
            }
            
            if(player.orange) {
            	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 6) {
            		player.body.applyLinearImpulse(new Vector2(0.4f, 0), player.body.getWorldCenter(), true);
        			player.lookRight(true);
            	}
            	
            	if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -6) {
            		player.body.applyLinearImpulse(new Vector2(-0.4f, 0), player.body.getWorldCenter(), true);
            		player.lookRight(false);
            	}
            	
            } else if(player.green){
            	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().y <= .5f) {
            		player.body.applyLinearImpulse(new Vector2(0, .5f), player.body.getWorldCenter(), true);
            		player.lookRight(true);
            	}
            	
            	if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().y <= .5f) {
            		player.body.applyLinearImpulse(new Vector2(0, .5f), player.body.getWorldCenter(), true);
            		player.lookRight(false);
            	}
            } else {
            	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2) {
            		player.body.applyLinearImpulse(new Vector2(0.1f, 0), player.body.getWorldCenter(), true);
            		player.lookRight(true);
            	}
            	
            	if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -2) {
            		player.body.applyLinearImpulse(new Vector2(-0.1f, 0), player.body.getWorldCenter(), true);
            		player.lookRight(false);
            	}
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            	restart = true;
            }
        }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
        }
        for(Cube cubes : creator.getCubes()) {
        	cubes.update(dt);
        }
        for(Path paths : creator.getPaths()) {
        	paths.update(dt);
        }

        //attach our gamecam to our players.x coordinate
        if(player.currentState != Chell.State.DEAD) {
            gamecam.position.x = player.body.getPosition().x;
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }


    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Cube cube : creator.getCubes())
            cube.draw(game.batch);
        game.batch.end();

        if(restart()){
        	game.setScreen(new PlayScreen((Platformer) game, level));
            dispose();
        } else if(nextLevel()){
        	game.setScreen(new PlayScreen((Platformer) game, level + 1));
            dispose();
        }

	}

    public boolean restart(){
        if((player.currentState == Chell.State.DEAD && player.getStateTimer() > 2) || restart){
            return true;
        }
        return false;
    }
    
    public boolean nextLevel(){
        if(player.goal){
            return true;
        }
        return false;
    }

    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}