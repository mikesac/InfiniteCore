package org.infinite.db.dao;

import java.util.ArrayList;

import org.infinite.db.dto.Area;
import org.infinite.db.dto.AreaItem;
import org.infinite.db.dto.Item;
import org.infinite.db.dto.Npc;
import org.infinite.db.dto.Player;
import org.infinite.db.dto.PlayerKnowSpell;
import org.infinite.db.dto.PlayerOwnItem;
import org.infinite.db.dto.Spell;
import org.infinite.db.dto.SpellAffectPlayer;
import org.infinite.db.dto.TomcatRoles;
import org.infinite.db.dto.TomcatUsers;
import org.infinite.objects.Character;

public interface DaoManager {

	/* ------------------ ITEMS ------------------ */
	
	ArrayList<Item> getItemsByTypeAndLevel(int itemType, int maxItemLevel);
	
	ArrayList<Item> getItemsToShop(Npc npc);
	
	ArrayList<Item> getItemsByName(String[] itemsNames);
	
	Item getItemById(int itemId);
	
	
	/* ------------------ SPELLS ------------------ */
	
	ArrayList<Spell> getSpellsByLevel(int maxSpellLevel);
	
	ArrayList<Spell> getSpellsByNameAndType(String[] spellsNames,int spellType);
	
	ArrayList<Spell> getSpellToShop(Npc npc);
	
	Spell getSpellById(int spellId);
	
	
	/* ------------------ NPC ------------------ */
	
	Npc getNpcByName(String name);
	
	Npc getNpcById(int id);
	
	String[] getMonsterListing();
	

	
	/* ------------------ Area & AreaItem ------------------ */

	Area getArea(Integer areaid);

	ArrayList<AreaItem> getAreaItems(Integer id);
	
	AreaItem getStartingAreaItem();
	
	AreaItem getAreaItem(Integer areaItemId);
	
	
/* ------------------ Player ------------------ */
	
	Player getPlayerByName(String accountName, String name);
	
	ArrayList<Player> getCharacterListing(String account);
	
	ArrayList<Npc> getMonsterList(int monsterLevel);

	ArrayList<Player> getOtherPlayerInArea(Character c);
	
	/* ------------------ POI PKS SAP------------------ */

	ArrayList<PlayerOwnItem> getPlayerItems(int playerID);
	
	PlayerOwnItem getPlayerItem(int poiId);
	
	ArrayList<PlayerKnowSpell> getPlayerSpells(int playerID);
	
	PlayerKnowSpell getPlayerSpell(int pksId);
	
	ArrayList<SpellAffectPlayer> getSpellsAffectingPlayer(int playerID);
	
	
	/* ------------------ Users ------------------ */
	
	TomcatUsers getTomcatUsers(String userName);

	ArrayList<TomcatUsers> findTomcatUsers(String userName, String email);

	TomcatRoles getUserRole(String userName);
	
	/* ------------------ Generics ------------------ */
	
	boolean delete(Object dto);
	
	boolean create(Object dto);
	
	boolean update(Object dto);

	ArrayList<String> getAreaItemsIcons();

	ArrayList<Area> listAllArea();

	ArrayList<Spell> getSpellList();

	ArrayList<Item> getItemList();

	ArrayList<Npc> getMonsterList();

	ArrayList<Npc> getNPCList();

	ArrayList<Item> getItemListByType(int type);

	ArrayList<Spell> getSpellListByType(int type);

	ArrayList<Npc> getNPCList(int monsterLevel);	
	
	
}
