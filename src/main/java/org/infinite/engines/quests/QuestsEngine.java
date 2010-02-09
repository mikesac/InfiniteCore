package org.infinite.engines.quests;

import java.util.ArrayList;

import org.infinite.db.dao.DaoManager;
import org.infinite.db.dto.Item;
import org.infinite.db.dto.PlayerOwnQuest;
import org.infinite.db.dto.Quest;
import org.infinite.engines.items.ItemsEngine;
import org.infinite.objects.Character;

public class QuestsEngine {

	/* ***************** START SPRING BEANS ***************** */

	private DaoManager daoManager;
	private ItemsEngine itemsEngine;

	public DaoManager getDaoManager() {return daoManager;}
	public void setDaoManager(DaoManager daoManager) {this.daoManager = daoManager;}
	public ItemsEngine getItemsEngine() {return itemsEngine;}
	public void setItemsEngine(ItemsEngine itemsEngine) {this.itemsEngine = itemsEngine;}

	/* ***************** END SPRING BEANS ***************** */



	public static final int QUEST_STATUS_UNASSIGNED	= 0;
	public static final int QUEST_STATUS_PENDING 	= 1;
	public static final int QUEST_STATUS_EXECUTED 	= 2;
	public static final int QUEST_STATUS_COMPLETED 	= 3;
	public static final int QUEST_STATUS_ABORTED 	= 4;

	
	public void persistOwnQuest(PlayerOwnQuest poq,int ncompleted, int status){		
		poq.setNcompleted(ncompleted);
		poq.setStatus(status);
		getDaoManager().update(poq);
	}


	private void removeFromQuest(Character player,int poqId) {

		for (int i = 0; i < player.getQuests().size(); i++) {
			if( player.getQuests().get(i).getId() == poqId){
				player.getQuests().remove(i);
				break;
			}			
		}		
	}

	public boolean isQuestAssigned(Character player,int questId){

		boolean assigned = false;
		for (int i = 0; i < player.getQuests().size(); i++) {

			if( player.getQuests().get(i).getQuest().getId() == questId){
				assigned = true;
				break;
			}			
		}
		return assigned;
	}

	public void setQuestAssigned(Character player,Quest quest,boolean persist) {

		PlayerOwnQuest poq = new PlayerOwnQuest( player.getDao(),quest,QUEST_STATUS_PENDING,0);
		player.addQuests(poq);
		if( persist ){
			getDaoManager().create(poq);
		}
	}

	public void setQuestExecuted(Character c, int questId)
	{		
		PlayerOwnQuest poq = getDaoManager().getPlayerOwnQuest(c,questId);
		poq.setStatus(QUEST_STATUS_EXECUTED);
		c.addToInventory( poq.getQuest().getGoalItem(), getItemsEngine() );

		getDaoManager().update(poq);
	}


	public void setQuestCompleted(Character c, int questId)
	{		
		PlayerOwnQuest poq = getDaoManager().getPlayerOwnQuest(c,questId);
		poq.setStatus(QUEST_STATUS_COMPLETED);
		poq.setNcompleted( poq.getNcompleted() + 1 );

		c.addExperience( poq.getQuest().getGrantXp());
		c.addGold( poq.getQuest().getGrantGold() );
		c.addToInventory( poq.getQuest().getGrantItem(), getItemsEngine() );		

		getDaoManager().update(poq);
	}

	public void setQuestAborted(Character c, int questId)
	{		
		PlayerOwnQuest poq = getDaoManager().getPlayerOwnQuest(c,questId);
		removeFromQuest(c, poq.getId());
		getDaoManager().delete(poq);
	}

	
	public PlayerOwnQuest getQuestSolvedByItem( Character c, Item it ){
		
		ArrayList<PlayerOwnQuest> apoq = c.getQuests();
		PlayerOwnQuest out = null;
		for (PlayerOwnQuest poq : apoq) 
		{
			if( 
				poq.getStatus()== QUEST_STATUS_PENDING && //quest must be pending
				poq.getQuest().getGoalItem().getId() == it.getId() && //must have my item as goal
				getItemsEngine().getNumOfItemInInventory(c, it) == poq.getQuest().getGoalItemN() // must have enough items to complete
				)
			{
				out = poq; 
				break; //this assume a single item cannot solve more than one quest!
			}
		}
		return out;
	}
}
