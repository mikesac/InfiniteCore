package org.infinite.db.dao;

import java.util.ArrayList;

import org.infinite.db.DbManager;
import org.infinite.db.dto.Area;
import org.infinite.db.dto.AreaItem;
import org.infinite.db.dto.Item;
import org.infinite.db.dto.Npc;
import org.infinite.db.dto.Player;
import org.infinite.db.dto.PlayerKnowSpell;
import org.infinite.db.dto.PlayerOwnItem;
import org.infinite.db.dto.PlayerOwnQuest;
import org.infinite.db.dto.Quest;
import org.infinite.db.dto.Spell;
import org.infinite.db.dto.SpellAffectPlayer;
import org.infinite.db.dto.TomcatRoles;
import org.infinite.db.dto.TomcatUsers;
import org.infinite.objects.Character;

public class BaseDaoManager implements DaoManager{

	private static DbManager manager;

	public void setManager(DbManager manager) {
		BaseDaoManager.manager = manager;
	}

	public DbManager getManager() {
		return manager;
	}


	/* ------------------ POI ------------------ */

	@SuppressWarnings("unchecked")
	public ArrayList<PlayerOwnItem> getPlayerItems(int playerID){	
		return (ArrayList<PlayerOwnItem>) getManager().listByQuery(PlayerOwnItem.class.getName() + " poi join fetch poi.item i "," poi.player='"+playerID+"'");
	}
	

	public PlayerOwnItem getPlayerItem(int poiId){	
		return (PlayerOwnItem) getManager().findById(PlayerOwnItem.class.getName(), poiId);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PlayerKnowSpell> getPlayerSpells(int playerID){	
		return (ArrayList<PlayerKnowSpell>) getManager().listByQuery(PlayerKnowSpell.class.getName() + " pks join fetch pks.spell a "," pks.player='"+playerID+"'");
	}

	public PlayerKnowSpell getPlayerSpell(int pksId){	
		return (PlayerKnowSpell) getManager().findById(PlayerKnowSpell.class.getName(), pksId);
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<SpellAffectPlayer> getSpellsAffectingPlayer(int playerID){	
		return (ArrayList<SpellAffectPlayer>) getManager().listByQuery(SpellAffectPlayer.class.getName() + " sap join fetch sap.spell a "," sap.player='"+playerID+"'");
	}



	/* ------------------ ITEMS ------------------ */

	@SuppressWarnings("unchecked")
	public ArrayList<Item> getItemsByTypeAndLevel(int itemType, int maxItemLevel){
		return (ArrayList<Item>) getManager().listByQuery(Item.class.getName(),"type='"+itemType+"' and level<='"+maxItemLevel+"'");
	}
	
	public Item getItemById(int itemId){
		return (Item) getManager().findById(Item.class.getName(), itemId);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Item> getItemsByName(String[] itemsNames){

		if(itemsNames==null || itemsNames.length==0){
			return null;
		}

		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < itemsNames.length; i++) {
			sb.append(",'").append(itemsNames[i]).append("'");
		}
		String query = "name in ("+	sb.toString().substring(1)	+ ")";

		return (ArrayList<Item>) getManager().listByQuery(Item.class.getName(), query );

	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getItemList(){
		return (ArrayList<Item>) getManager().listAllDto(Item.class.getName());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getItemListByType(int type){

		ArrayList<Item> out = null;
		
		if(type>=0){
			String query = "type='"+type+"'";
			out = (ArrayList<Item>) getManager().listByQuery(Item.class.getName(), query );
		}
		else{
			out = (ArrayList<Item>) getManager().listAllDto(Item.class.getName());
		}

		return out; 

	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getItemsToShop(Npc npc){		
		return (ArrayList<Item>)getManager().listByQuery(Item.class.getName(),"level > '0' and level >= '"+ (npc.getLevel()-1) + "' and level <= '"+ (npc.getLevel()+1)+ "'");		
	}

	/* ------------------ SPELLS ------------------ */

	@SuppressWarnings("unchecked")
	public ArrayList<Spell> getSpellsByLevel(int maxSpellLevel){
		return (ArrayList<Spell>) getManager().listByQuery(Spell.class.getName(),"level<='"+maxSpellLevel+"'");
	}

	public Spell getSpellById(int spellId){
		return (Spell) getManager().findById(Spell.class.getName(), spellId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Spell> getSpellList(){
		return (ArrayList<Spell>) getManager().listAllDto(Spell.class.getName());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Spell> getSpellListByType(int type){

		ArrayList<Spell> out = null;
		
		if(type>=0){
			String query = "spelltype='"+type+"'";
			out = (ArrayList<Spell>) getManager().listByQuery(Spell.class.getName(), query );
		}
		else{
			out = (ArrayList<Spell>) getManager().listAllDto(Spell.class.getName());
		}

		return out; 

	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Spell> getSpellsByNameAndType(String[] spellsNames,int spellType){

		if(spellsNames==null || spellsNames.length==0){
			return null;
		}

		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < spellsNames.length; i++) {
			sb.append(",'").append(spellsNames[i]).append("'");
		}
		String query = "name in ("+
		sb.toString().substring(1)	+ ")";		

		query += " and spelltype="+spellType;

		return (ArrayList<Spell>) getManager().listByQuery(Spell.class.getName(), query );
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Spell> getSpellToShop(Npc npc){		
		return (ArrayList<Spell>)getManager().listByQuery(Spell.class.getName(),"level > '0' and level >= '"+ (npc.getLevel()-1) + "' and level <= '"+ (npc.getLevel()+1)+ "'");		
	}
	
	/* ------------------ NPCs ------------------ */
	
	public Npc getNpcByName(String name) {		
		return (Npc)getManager().listByQuery(Npc.class.getName() , " name='"+name+"'").get(0);
	}
	
	public Npc getNpcById(int id) {	
		return (Npc)getManager().findById(Npc.class.getName() ,id);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Npc> getNPCList(){
		return (ArrayList<Npc>)getManager().listByQuery(Npc.class.getName(), "ismonster='0'");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Npc> getNPCList(int monsterLevel){
		return (ArrayList<Npc>)getManager().listByQuery(Npc.class.getName(), " level="+monsterLevel+" and ismonster='0'");
	}


	@SuppressWarnings("unchecked")
	public String[] getMonsterListing(){

		ArrayList<Npc> l = (ArrayList<Npc>)getManager().listByQuery(Npc.class.getName(),"ismonster='1'");

		String[] s = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			s[i] = l.get(i).getName();
		}

		return s;
	}


	/* ------------------ Area & AreaItem ------------------ */

	public Area getArea(Integer areaid) {
		return (Area) getManager().findById(Area.class.getName(), areaid);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Area> listAllArea() {
		return (ArrayList<Area>) getManager().listAllDto(Area.class.getName());
	}

	@SuppressWarnings("unchecked")
	public ArrayList<AreaItem> getAreaItems(Integer areaId) {
		return (ArrayList<AreaItem>) getManager().listByQuery( AreaItem.class.getName(), "areaid='"+areaId+"'");
	}
	
	public AreaItem getAreaItem(Integer areaItemId) {
		return (AreaItem) getManager().findById(AreaItem.class.getName(), areaItemId);
	}
	
	
	public AreaItem getStartingAreaItem(){		
		return (AreaItem) getManager().findById(AreaItem.class.getName(), 1);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getAreaItemsIcons(){
		return (ArrayList<String>)getManager().listByQuery("select distinct icon from "+ AreaItem.class.getName() );
	}

	/* ------------------ Player ------------------ */


	public Player getPlayerByName(String accountName, String name) {		
		return  (Player)getManager().listByQuery(Player.class.getName()+" p join fetch p.areaItem a ", "p.tomcatUsers.user='"+accountName+"' and p.name='"+name+"'").get(0);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Player> getCharacterListing(String account){
		return (ArrayList<Player>) getManager().listByQuery(Player.class.getName()+" p join fetch p.areaItem a "," p.tomcatUsers.user='"+account+"'  ");

	}

	/* ------------------ NPCs ------------------ */
	
	@SuppressWarnings("unchecked")
	public ArrayList<Npc> getMonsterList(int monsterLevel){
		return (ArrayList<Npc>)getManager().listByQuery(Npc.class.getName(), " level="+monsterLevel+" and  ismonster='1'");
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Player> getOtherPlayerInArea(Character c){		
		return (ArrayList<Player>)getManager().listByQuery(Player.class.getName()," level <= '"+(c.getLevel()+3)+"' and level>='"+(c.getLevel()-3)+"' and id!='"+c.getDao().getId()+"' and areaItem.id='"+c.getDao().getAreaItem().getId()+"'");
	}
	
	@SuppressWarnings("unchecked")
	public
	ArrayList<Player> getAllPlayerInArea(int areaItemID){
		return (ArrayList<Player>)getManager().listByQuery(Player.class.getName()," areaItem.id='"+areaItemID+"'");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Npc> getMonsterList(){
		return (ArrayList<Npc>)getManager().listByQuery(Npc.class.getName(), " ismonster='1'");
	}

		
	/* ------------ USERS ------------- */
	public TomcatUsers getTomcatUsers(String userName) {
		return (TomcatUsers) getManager().listByQuery(TomcatUsers.class.getName(), "user='"+userName+"'").get(0);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TomcatUsers> findTomcatUsers(String userName,String email) {
		return (ArrayList<TomcatUsers>) getManager().listByQuery(TomcatUsers.class.getName(), "user='"+userName+"' or u.email='"+email+"'");
	}
	
	public TomcatRoles getUserRole(String userName){
		return (TomcatRoles) getManager().listByQuery(TomcatRoles.class.getName(), "user='"+userName+"'").get(0);
	}
	

	/* ------------------ Generics ------------------ */

	public boolean delete(Object dto){
		return getManager().delete(dto);
	}

	public Integer create(Object dto){
		return getManager().create(dto);
	}

	public boolean update(Object dto){
		return getManager().update(dto);
	}

	public Quest getQuestById( int questId){
		return  (Quest) getManager().findById(Quest.class.getName(), questId);
	}

	@SuppressWarnings("unchecked")
	public PlayerOwnQuest getPlayerOwnQuest(Character c, int questId){
		ArrayList<PlayerOwnQuest> poq =(ArrayList<PlayerOwnQuest>) getManager().listByQuery(PlayerOwnQuest.class.getName() + " poq join fetch poq.quest ", " poq.quest='"+questId+"' and poq.player='"+c.getDao().getId()+"' "); 
		return (poq==null)?null:poq.get(0);
	}


}
