package org.infinite.engines.quests;

import org.infinite.db.dao.DaoManager;
import org.infinite.db.dto.PlayerOwnQuest;
import org.infinite.db.dto.Quest;
import org.infinite.objects.Character;

public class QuestsEngine {

/* ***************** START SPRING BEANS ***************** */
	
	private DaoManager daoManager;
	
	public DaoManager getDaoManager() {return daoManager;}
	public void setDaoManager(DaoManager daoManager) {this.daoManager = daoManager;}
	
	/* ***************** END SPRING BEANS ***************** */
	
	
	
	public static final int QUEST_STATUS_UNASSIGNED	= 0;
	public static final int QUEST_STATUS_PENDING 	= 1;
	public static final int QUEST_STATUS_EXECUTED 	= 2;
	public static final int QUEST_STATUS_COMPLETED 	= 3;
	public static final int QUEST_STATUS_ABORTED 	= 4;
	
	public static final String QUEST_UNASSIGNED	= "U";
	public static final String QUEST_PENDING 	= "P";
	public static final String QUEST_EXECUTED 	= "E";
	public static final String QUEST_COMPLETED 	= "C";
	public static final String QUEST_ABORTED 	= "A";
	
	public void persistOwnQuest(PlayerOwnQuest poq,int ncompleted, int status){		
		poq.setNcompleted(ncompleted);
		poq.setStatus(status);
		getDaoManager().update(poq);
	}

	

	public  void assignQuest(Character player,Quest quest,boolean persist) {

		PlayerOwnQuest poq = new PlayerOwnQuest( player.getDao(),quest,QUEST_STATUS_PENDING,0);
		
		player.addQuests(poq);
		if( persist ){
			getDaoManager().create(poq);
		}
	}

	public void removeFromQuest(Character player,int poqId) {

		for (int i = 0; i < player.getQuests().size(); i++) {

			if( player.getQuests().get(i).getId() == poqId){
				player.getQuests().remove(i);
				break;
			}			
		}		
	}


	public void setQuestExecuted(Character c, int questId){
//		removeFromInventory(c,poi.getId());
//		getDaoManager().delete(poi);
	}

}
