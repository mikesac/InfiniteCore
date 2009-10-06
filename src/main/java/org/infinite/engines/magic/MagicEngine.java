package org.infinite.engines.magic;

import java.util.ArrayList;
import java.util.Date;

import org.infinite.db.dao.DaoManager;
import org.infinite.db.dto.Player;
import org.infinite.db.dto.PlayerKnowSpell;
import org.infinite.db.dto.Spell;
import org.infinite.db.dto.SpellAffectPlayer;
import org.infinite.engines.fight.PlayerInterface;
import org.infinite.objects.Character;
import org.infinite.util.GenericUtil;
import org.infinite.util.InfiniteCst;


public class MagicEngine {

/* ***************** START SPRING BEANS ***************** */
	
	private DaoManager daoManager;
	
	public DaoManager getDaoManager() {return daoManager;}
	public void setDaoManager(DaoManager daoManager) {this.daoManager = daoManager;}
	
	/* ***************** END SPRING BEANS ***************** */
	
	
	public void persistKnowSpell(PlayerKnowSpell pks, int status){
		pks.setStatus(status);
		getDaoManager().update(pks);
	}

	public Spell castSpell(PlayerInterface player, Spell s) {
		
		//not enough magic point to cast
		if( player.getPointsMagic()<s.getCostMp() ){
			return null;
		}
		
		try {
			player.addMagicPoints( -1* s.getCostMp() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * Roll a saving throw against a spell
	 * @param s
	 * @param caster
	 * @param victim
	 * @return
	 */
	public boolean rollSavingThrow(Spell s, PlayerInterface caster, PlayerInterface victim) {
		int roll = GenericUtil.rollDice(1 , 20 , 0 ); 
		int success = s.getSavingthrow() - victim.getIntelligence() + caster.getIntelligence();
		return (roll>=success || roll==20);
	}



	public void  learnSpell(PlayerInterface player,Spell spell) {
		PlayerKnowSpell pks = new PlayerKnowSpell((Player)player.getDao(),spell,InfiniteCst.SPELL_KNOWN);
		learnSpell(player,pks, true);
	}


	public void learnSpell(PlayerInterface player,PlayerKnowSpell pks,boolean persist){

		switch ( pks.getSpell().getSpelltype() ) {
		case InfiniteCst.MAGIC_ATTACK:
			player.getSpellBookFight().add(pks);
			break;
		case InfiniteCst.MAGIC_HEAL:
			player.getSpellBookHeal().add(pks);
			break;
		case InfiniteCst.MAGIC_DEFEND:
			player.getSpellBookProtect().add(pks);
			break;
		}

		if( pks.getStatus() == InfiniteCst.SPELL_READY){
			player.addToPreparedSpells(pks);
		}
		if(persist){
			getDaoManager().create(pks);
		}
	}




	public void prepareSpell(PlayerInterface player,PlayerKnowSpell pks){
		prepareSpell(player,pks,true);
	}

	public void prepareSpell(PlayerInterface player,PlayerKnowSpell pks,boolean persist) {

		pks.setStatus(InfiniteCst.SPELL_READY);

		player.addToPreparedSpells(pks);
		if(persist){
			getDaoManager().update(pks);
		}
	}



	public void unprepareSpell(PlayerInterface player,int pksId) {
		PlayerKnowSpell pks = null;
		for (int i = 0; i < player.getPreparedSpells().size(); i++) {

			if( player.getPreparedSpells().get(i).getId() == pksId){
				pks = player.getPreparedSpells().remove(i);
				break;
			}			
		}

		if(pks!=null){
			pks.setStatus(InfiniteCst.SPELL_KNOWN);
			getDaoManager().update(pks);

		}
	}

	public static int getAvailableSpellSlots(PlayerInterface p) {
		int slot = p.getIntelligence()/InfiniteCst.CFG_INT_TO_SPELLS_SLOTS - p.getPreparedSpells().size();
		return slot<0?0:slot;
	}

	public void affectSpells(PlayerInterface p, Spell s,boolean persist) {
		
		SpellAffectPlayer sap= new SpellAffectPlayer();
		sap.setPlayer( (Player) p.getDao() );
		sap.setSpell( s );
		sap.setElapsing( (new Date()).getTime() + s.getDuration() );
		
		p.addToAffectingSpells(sap);
		
		if(persist){
			getDaoManager().create(sap);
		}
		
		
	}

	
	public boolean canUseSpell(Character c, Spell s){

		boolean canUse=( 
				(s.getReqStr()<=c.getDao().getBaseStr()) &&
				(s.getReqInt()<=c.getDao().getBaseInt()) &&
				(s.getReqDex()<=c.getDao().getBaseDex()) &&
				(s.getReqCha()<=c.getDao().getBaseCha()) &&
				(s.getLevel()<=c.getDao().getBaseInt()/InfiniteCst.CFG_INT_TO_SPELLS_SLOTS) 
		);

		return canUse;
	}

	public void buySpell(Character c, Spell spell, float priceAdj) throws Exception {	
		
		ArrayList<PlayerKnowSpell> pks = new ArrayList<PlayerKnowSpell>();
		pks.addAll(c.getSpellBookFight());
		pks.addAll(c.getSpellBookHeal());
		pks.addAll(c.getSpellBookProtect());
		
		for (PlayerKnowSpell s : pks) {
			if( spell.getId().equals(s.getSpell().getId()) ){
				throw new Exception("Character already knows this spell");
			}			
		}		
		
		boolean canBuy=( (c.getGold()>=( spell.getPrice()/priceAdj) ) && canUseSpell(c,spell)	);
		if(canBuy){		
			try {
				c.learnSpell(spell,this);
				c.payGold( spell.getPrice() * priceAdj );
			} catch (Throwable e) {
				throw new Exception(e);
			}
		}
		else{
			throw new Exception("Character does not met item requirements or price");
		}
	}
	
}
