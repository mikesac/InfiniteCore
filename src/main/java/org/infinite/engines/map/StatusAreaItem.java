package org.infinite.engines.map;

import org.infinite.db.dto.AreaItem;

public class StatusAreaItem extends AreaItem {

	private static final long serialVersionUID = -8094456017441340998L;
	
	public StatusAreaItem(AreaItem item) {
		super( item.getName(),
				item.getIcon(),
				item.getCost(),
				item.getAreaid(),
				item.getAreax(),
				item.getAreay(),
				item.getX(),
				item.getY(),
				item.getArealock(),
				item.getAreatype(),
				item.getQuestlock(),
				item.getUrl(),
				item.isDoublestep(),
				item.isHidemode(),
				item.getLevel(),
				item.getNpcs()		
		);
		setId(item.getId());
	}
	
	
	private int status;

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
}
