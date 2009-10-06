package org.infinite.engines.items;

import org.infinite.db.dto.Item;
import org.infinite.db.dto.PlayerOwnItem;

public interface ItemsInterface {

	
	void moveToInventory(PlayerOwnItem poi,ItemsEngine itemsEngine);

	void addToInventory(Item item,ItemsEngine itemsEngine);
	
	void dropItem(PlayerOwnItem poi,ItemsEngine itemsEngine);

	void equipItem(PlayerOwnItem poi,ItemsEngine itemsEngine);
	
	void wearItem(PlayerOwnItem poi,ItemsEngine itemsEngine) throws Exception;
	
}
