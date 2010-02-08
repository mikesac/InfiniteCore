package org.infinite.engines.fight;

import java.util.ArrayList;

import org.infinite.db.dto.Item;
import org.infinite.db.dto.PlayerKnowSpell;
import org.infinite.db.dto.PlayerOwnItem;
import org.infinite.db.dto.Spell;
import org.infinite.db.dto.SpellAffectPlayer;
import org.infinite.engines.items.ItemsEngine;
import org.infinite.engines.magic.MagicEngine;

public interface PlayerInterface{

	// ----------------- Fight Interface -----------------
	int getBaseCA();

	int getTotalCA();

	int getArmorCA();

	int getShieldCA();

	int getAttackType(PlayerInterface defender);

	String[] getAttackName(int round);

	int getInitiative(int round);

	int getRollToAttack();

	int inflictDamage(int dmg);

	int healDamage(int heal);

	int restRound(int i);

	int getAttackDamage(int round);

	boolean isAlive();

	int getRewardPX();

	float getRewardGold();

	ArrayList<Item> getRewardItems();

	void prepareForFight(FightEngine fightEngine);
	
	Object getCurrentAttack(int round);
	
	float addGold(float rewardGold);

	int addExperience(int rewardPX);

	void lootItems(ArrayList<Item> rewardItems,ItemsEngine itemsEngine);
	
	ArrayList<SpellAffectPlayer> getSpellsAffecting();
	
	// ----------------- Spell Interface -----------------
	
	int getSpellDuration();
	
	ArrayList<PlayerKnowSpell> getSpellBookFight();

	ArrayList<PlayerKnowSpell> getSpellBookHeal();

	ArrayList<PlayerKnowSpell> getSpellBookProtect();

	void learnSpell(Spell spell,MagicEngine magicEngine);
	
	void prepareSpell(PlayerKnowSpell pks,MagicEngine magicEngine);
	
	void unprepareSpell(int pksId,MagicEngine magicEngine);
	
	Spell castSpell( Spell s,MagicEngine magicEngine);

	ArrayList<PlayerKnowSpell> getPreparedSpells();

	void addToPreparedSpells(PlayerKnowSpell pks);
	
	void addToAffectingSpells(Spell s,MagicEngine magicEngine);
	
	void addToAffectingSpells(SpellAffectPlayer sap);
	
	void removeSpellsAffecting( int sapId );
	
	// ----------------- Character Interface -----------------
	
	Object getDao();

	int getLevel();
	
	String getName();	

	Item getHandRight();

	Item getHandLeft();

	Item getBody();

	String getPic();

	int getStrenght();
	
	int getIntelligence();
	
	int getDexterity();

	int getCharisma();	
	
	int getPointsLife();
	
	int getPointsMagic();
	
	int getPointsAction();
	
	int getPointsCharm();
	
	int getPointsLifeMax();

	int getPointsMagicMax();
	
	int getPointsActionMax();

	int getPointsCharmMax();
	
	int addLifePoints(int points) throws Exception;

	int addMagicPoints(int points) throws Exception;

	int addActionPoints(int points) throws Exception;

	int addCharmPoints(int points) throws Exception;

	
	////////////////////////////
	
	//void equipItem(PlayerOwnItem poi);
	
	void addToInventory(PlayerOwnItem poi);

	PlayerOwnItem getBodyPoi();

	void setBody(PlayerOwnItem poi);

	ArrayList<PlayerOwnItem> getInventory();

	PlayerOwnItem getHandLeftPoi();

	void setHandLeft(PlayerOwnItem poi);

	PlayerOwnItem getHandRightPoi();

	void setHandRight(PlayerOwnItem poi);

	String[] getMeleeAttacks(int round);
	
	boolean isMonster();
}
