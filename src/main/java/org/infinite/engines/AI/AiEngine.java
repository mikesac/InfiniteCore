package org.infinite.engines.AI;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.infinite.db.dao.DaoManager;
import org.infinite.db.dto.Item;
import org.infinite.db.dto.PlayerOwnItem;
import org.infinite.db.dto.Spell;
import org.infinite.engines.items.ItemsEngine;
import org.infinite.engines.magic.MagicEngine;
import org.infinite.objects.Monster;
import org.infinite.util.GenericUtil;
import org.infinite.util.InfiniteCst;


public class AiEngine {

	/* ***************** START SPRING BEANS ***************** */
	private static DaoManager daoManager;
	private static ItemsEngine itemsEngine;
	
	public void setDaoManager(DaoManager daoManager) {
		AiEngine.daoManager = daoManager;
	}

	public DaoManager getDaoManager() {
		return daoManager;
	}
	
	public void setItemsEngine(ItemsEngine itemsEngine) {
		AiEngine.itemsEngine = itemsEngine;
	}

	public ItemsEngine getItemsEngine() {
		return itemsEngine;
	}
	
	/* ***************** END SPRING BEANS ***************** */
	
	



	private static final Log LOG = LogFactory.getLog(AiEngine.class);

	private static final int LEVEL_RNDM_NOT = 8;
	private static final int LEVEL_RNDM_DWN = LEVEL_RNDM_NOT + 40;
	private static final int LEVEL_RNDM_EQ = LEVEL_RNDM_DWN + 50;
	private static final int LEVEL_RNDM_UP = LEVEL_RNDM_EQ + 2;


	

	public static int getLevelPx(int level){

		int px = 0;
		for (int i = 0; i <= level; i++) {
			px += i * InfiniteCst.CFG_LEVEL_PX;
		}

		return px;
	}
	
	public static int getLevelByPx(int px){

		int level = 0;
		int loop = px;
		while( loop>=0 ){
			level++;
			loop = px - getLevelPx(level);
		}

		return level;
	}


	public int getMatchingLevel(int level){

		if(level==0){
			return 0;
		}

		double rand =  100 * Math.random() ;
		int out = 0;
		if( rand <= LEVEL_RNDM_NOT ){
			out = 0;
		}
		else if( rand <= LEVEL_RNDM_DWN ){
			out = getMatchingLevel(level-1);
		}
		else if( rand <= LEVEL_RNDM_EQ ){
			out = level;
		}
		else if( rand <= LEVEL_RNDM_UP ){
			out = level+1;
		}

		return out;		
	}


	



	public Monster spawn(String szName) throws Exception{

		LOG.debug("Starting to spaw "+szName);

		Monster m = new Monster(szName,getDaoManager());

		LOG.debug("monster generated: "+m.getName() );

		int level = m.getLevel();

		//if monster can use weapons
		if( m.getDao().isUseWpn()){			
			spawinInventory(m, level,InfiniteCst.ITEM_TYPE_WEAPON,InfiniteCst.EQUIP_HAND_RIGHT);
		}

		//if monster can use Shields
		if( m.getDao().isUseShld()){
			spawinInventory(m, level,InfiniteCst.ITEM_TYPE_SHIELD,InfiniteCst.EQUIP_HAND_LEFT);
		}

		//if monster can use Shields
		if( m.getDao().isUseArm()){
			spawinInventory(m, level,InfiniteCst.ITEM_TYPE_ARMOR,InfiniteCst.EQUIP_BODY);
		}

		int nItems = m.getDao().getNitem();
		for (int i = 0; i < nItems; i++) {
			//TODO add items to inventory
		}

		
		//check if monster can cast spell
		int nSpellSlots = MagicEngine.getAvailableSpellSlots(m);
		for (int i = 0; i < nSpellSlots; i++) {
			
			int spellLevel = getMatchingLevel(nSpellSlots);
			if(spellLevel>0){
				LOG.debug("max spell level:"+spellLevel);
				//list all weapons within such level
				ArrayList<Spell> spells = getDaoManager().getSpellsByLevel(spellLevel);

				if(spells.size()>0){
					//choose randomly and equip 
					int index = GenericUtil.getRandomNumber(0, spells.size());
					Spell s = spells.get(index);
					m.learnSpells( new String[]{s.getName()});
					System.out.println(s.getName());
					
				}
			}
			else{
				LOG.debug("no item spawned");
			}
			
			
			
			
			
		}



		return m;
	}



	private void spawinInventory(Monster m, int level,int itemType,int bodyPart) {
		//get weapon max level randomly
		int wpnLevel = getMatchingLevel(level);
		if(wpnLevel>0){
			LOG.debug("max item level:"+wpnLevel);
			//list all weapons within such level
			ArrayList<Item> items =  getDaoManager().getItemsByTypeAndLevel(itemType,wpnLevel);

			if(items.size()>0){
				//choose randomly and equip 
				int index = GenericUtil.getRandomNumber(0, items.size());
				Item it = items.get(index);
				PlayerOwnItem poi = new PlayerOwnItem(null,it,0,bodyPart);
				getItemsEngine().equipItem(m, poi);
			}
		}
		else{
			LOG.debug("no item spawned");
		}
	}



}
