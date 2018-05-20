package com.isn.platformer.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class RedGel extends InteractiveTileObject{
	private static TiledMapTileSet tileSet;
    private final int FLOOR_OFF = 36;
    private final int FLOOR_ON = 69;
    private final int R_WALL_OFF = 37;
    private final int R_WALL_ON = 70;
    private final int L_WALL_OFF = 38;
    private final int L_WALL_ON = 71;
    private final int L_CORNER_OFF = 39;
    private final int L_CORNER_ON = 72;
    private final int R_CORNER_OFF = 40;
    private final int R_CORNER_ON = 73;

    public RedGel(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("Tileset");
        fixture.setUserData(this);
        setCategoryFilter(Platformer.RED_GEL_BIT);
    }

    public void turnOnOff(boolean b) {
    	
    	TiledMapTileLayer.Cell[] tiles = getCells();
    	
        if(b) {
        	for(int i = 0; i < tiles.length; i++) {
        		
        		if(tiles[i].getTile().getId() == FLOOR_OFF) {
        			tiles[i].setTile(tileSet.getTile(FLOOR_ON));
        		} else if(tiles[i].getTile().getId() == R_WALL_OFF){
        			tiles[i].setTile(tileSet.getTile(R_WALL_ON));
        		} else if(tiles[i].getTile().getId() == L_WALL_OFF){
        			tiles[i].setTile(tileSet.getTile(L_WALL_ON));
        		} else if(tiles[i].getTile().getId() == L_CORNER_OFF){
        			tiles[i].setTile(tileSet.getTile(L_CORNER_ON));
        		} else if(tiles[i].getTile().getId() == R_CORNER_OFF){
        			tiles[i].setTile(tileSet.getTile(R_CORNER_ON));
        		}
        	}
        	
        } else {
        	
        	for(int i = 0; i < tiles.length; i++) {
        		
        		if(tiles[i].getTile().getId() == FLOOR_ON) {
        			tiles[i].setTile(tileSet.getTile(FLOOR_OFF));
        		} else if(tiles[i].getTile().getId() == R_WALL_ON){
        			tiles[i].setTile(tileSet.getTile(R_WALL_OFF));
        		} else if(tiles[i].getTile().getId() == L_WALL_ON){
        			tiles[i].setTile(tileSet.getTile(L_WALL_OFF));
        		} else if(tiles[i].getTile().getId() == L_CORNER_ON){
        			tiles[i].setTile(tileSet.getTile(L_CORNER_OFF));
        		} else if(tiles[i].getTile().getId() == R_CORNER_ON){
        			tiles[i].setTile(tileSet.getTile(R_CORNER_OFF));
        		}
        	}
        }
    }
}
