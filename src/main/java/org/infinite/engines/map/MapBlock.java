package org.infinite.engines.map;

import java.util.ArrayList;

public class MapBlock {

	private String background;
	private ArrayList<StatusAreaItem> items = new ArrayList<StatusAreaItem>();
	
	public void setItems(ArrayList<StatusAreaItem> list) {
		this.items = list;
	}
	public ArrayList<StatusAreaItem> getItems() {
		return items;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getBackground() {
		return background;
	}
	
}
