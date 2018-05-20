package com.isn.platformer.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class Path extends InteractiveTileObject{
	private static TiledMapTileSet tileSet;
    private int sourceOff;
    private int sourceOn;
    private int pathOff;
    private int pathOn;
    private TiledMapTileLayer.Cell[] tiles;

    public Path(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("Tileset");
        fixture.setUserData(this);
        if(w > 1)
        	setCategoryFilter(Platformer.NOTHING_BIT);
        else
        	setCategoryFilter(Platformer.GROUND_BIT);
        tiles = getCells();
        sourceOff = w > 1 ? 67 : 101;
        sourceOn = w > 1 ? 68 : 100;
        pathOff = w > 1 ? 43 : 34;
        pathOn = w > 1 ? 35 : 43;
    }
    
    public void update(float dt) {
    	if(getSource().getTile().getId() >= 69 && getSource().getTile().getId() <= 73) {
    		turnOnOff(true);
    	} else {
    		turnOnOff(false);
    	}
    }
    

    public void turnOnOff(boolean b) {
    	
    	if(b) {
        	for(int i = 0; i < tiles.length; i++) {
        		if(tiles[i].getTile().getId() == sourceOff) {
        			tiles[i].setTile(tileSet.getTile(sourceOn));
            	} else if(tiles[i].getTile().getId() == pathOff){
            		tiles[i].setTile(tileSet.getTile(pathOn));
            	}
        		if(w == 1)
                	setCategoryFilter(Platformer.NOTHING_BIT);
                else
                	setCategoryFilter(Platformer.GROUND_BIT);
        	}
        	
        } else {
        	
        	for(int i = 0; i < tiles.length; i++) {
        		if(tiles[i].getTile().getId() == sourceOn) {
        			tiles[i].setTile(tileSet.getTile(sourceOff));
            	} else if(tiles[i].getTile().getId() == pathOn){
            		tiles[i].setTile(tileSet.getTile(pathOff));
            	}
        		if(w > 1)
                	setCategoryFilter(Platformer.NOTHING_BIT);
                else
                	setCategoryFilter(Platformer.GROUND_BIT);
        	}
        }
    }
}
