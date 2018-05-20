package com.isn.platformer.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;
import com.isn.platformer.Sprites.Cube;
import com.isn.platformer.Sprites.Enemy;
import com.isn.platformer.TileObjects.BlueGel;
import com.isn.platformer.TileObjects.Path;
import com.isn.platformer.TileObjects.RedGel;

public class WorldCreator {
	private Array<Enemy> enemies;
	public Array<Cube> cubes;
	public Array<Path> paths;
	public Array<BlueGel> blueGels;
	
	public WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        
      //create turn bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.filter.categoryBits = Platformer.TURN_BIT;
            body.createFixture(fdef);
        }
        
      //create red bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new RedGel(screen, object);
        }
        
      //create orange bodies/fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.friction = 0.1f;
            fdef.filter.categoryBits = Platformer.ORANGE_GEL_BIT;
            body.createFixture(fdef);
        }
        
        //create blue bodies/fixtures
        blueGels = new Array<BlueGel>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
        	blueGels.add(new BlueGel(screen, object));
        }
        
      //create path bodies/fixtures
        paths = new Array<Path>();
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
        	paths.add(new Path(screen, object));
        }
        
        //create all enemies
        enemies = new Array<Enemy>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Enemy(screen, rect.getX() / Platformer.SCALE, rect.getY() / Platformer.SCALE));
        }
        
      //create all cubes
        cubes = new Array<Cube>();
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            cubes.add(new Cube(screen, rect.getX() / Platformer.SCALE, rect.getY() / Platformer.SCALE, false));
        }
        
      //create goal bodies/fixtures
        for(MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.filter.categoryBits = Platformer.GOAL_BIT;
            body.createFixture(fdef);
        }
        
      //create purple bodies/fixtures
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.filter.categoryBits = Platformer.PURPLE_GEL_BIT;
            body.createFixture(fdef);
        }
        
      //create green bodies/fixtures
        for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.friction = 1f;
            fdef.filter.categoryBits = Platformer.GREEN_GEL_BIT;
            body.createFixture(fdef);
        }
	}
	
	public Array<Enemy> getEnemies() {
        return enemies;
    }
	
	public Array<Cube> getCubes() {
        return cubes;
    }
	
	public Array<Path> getPaths() {
        return paths;
    }
	
	public Array<BlueGel> getBlueGels() {
        return blueGels;
    }
}
