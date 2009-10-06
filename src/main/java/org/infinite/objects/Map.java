package org.infinite.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.infinite.db.dto.Area;
import org.infinite.db.dto.AreaItem;
import org.infinite.engines.map.MapBlock;
import org.infinite.engines.map.MapEngine;
import org.infinite.engines.map.StatusAreaItem;

public class Map {

	private Area dao;
	private List<AreaItem> items;
	private List<Integer> itemsStatus = new ArrayList<Integer>();

	private LinkedList<  LinkedList<MapBlock> > mapBackground = new LinkedList<LinkedList<MapBlock>>();

	public static final int MAP_WIDTH = 4 * 3 * 72;
	public static final int MAP_HEIGHT = 4 * 3 * 42;


	public Map(Area a,Character c,MapEngine mapEngine) {

		setDao(a);
		setItems( mapEngine.getAreaItemsFromArea( getDao()) );

		for (int i = 0; i < getItems().size(); i++) {
			int status = mapEngine.getAreaStatus(c, getItems().get(i) );
			getItemsStatus().add(i, status); 
		}

		for(int y = 0; y < getDao().getNy(); y++) 
		{
			LinkedList<MapBlock> row = new LinkedList<MapBlock>();			
			for (int x = 0; x < getDao().getNx(); x++) 
			{
				MapBlock block = new MapBlock();
				block.setBackground(getAreaName()+"/"+getAreaName()+"_"+y+x);
				block.setItems( getAreaItem(x, y));
				row.add( block );
			}
			getMapBackground().add(row);
		}		
	}

	public LinkedList<  LinkedList<MapBlock> > getMapBackground() {
		return mapBackground;
	}

	private void setDao(Area dao) {
		this.dao = dao;
	}

	public Area getDao() {
		return dao;
	}

	private void setItems(List<AreaItem> items) {
		this.items = items;
	}

	public List<AreaItem> getItems() {
		return items;
	}

	public int getNx(){
		return getDao().getNx();
	}

	public int getNy(){
		return getDao().getNy();
	}

	public String getAreaName(){
		return getDao().getName();
	}

	public String getAreaDesc(){
		return getDao().getDescription();
	}

	private ArrayList<StatusAreaItem> getAreaItem(int x, int y){

		ArrayList<StatusAreaItem> l = new ArrayList<StatusAreaItem>();
		for (Iterator<AreaItem> iterator = getItems().iterator(); iterator.hasNext();) 
		{
			AreaItem areaItem = iterator.next();
			StatusAreaItem sai = new StatusAreaItem(areaItem);
			
			if(areaItem.getAreax()==x && areaItem.getAreay()==y){
				int index = getItems().indexOf(areaItem);
				sai.setStatus( getItemsStatus().get( index ) );
				l.add(sai);	
			}
		}		
		return l;
	}

	public List<Integer> getAreaStatus(int x, int y){

		ArrayList<Integer> l = new ArrayList<Integer>();
		for (Iterator<AreaItem> iterator = getItems().iterator(); iterator.hasNext();) 
		{
			AreaItem areaItem = iterator.next();
			if(areaItem.getAreax()==x && areaItem.getAreay()==y){
				int index = getItems().indexOf(areaItem);
				l.add( getItemsStatus().get( index ) );
			}
		}		
		return l;
	}



	public List<Integer> getItemsStatus() {
		return itemsStatus;
	}



}
