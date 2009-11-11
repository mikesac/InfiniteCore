package org.infinite.engines.map;

import java.util.ArrayList;

import org.infinite.db.dao.DaoManager;
import org.infinite.db.dto.Area;
import org.infinite.db.dto.AreaItem;
import org.infinite.objects.Character;

public class MapEngine {

/* ***************** START SPRING BEANS ***************** */
	
	private static DaoManager daoManager;
	
	public DaoManager getDaoManager() {return daoManager;}
	public void setDaoManager(DaoManager daoManager) {MapEngine.daoManager = daoManager;}
	
	/* ***************** END SPRING BEANS ***************** */
	
	
	public static final int AREA_STATUS_HERE = 0;
	public static final int AREA_STATUS_BANNED = 1;
	public static final int AREA_STATUS_LOCKED = 2;
	public static final int AREA_STATUS_HIDDEN = 3;
	public static final int AREA_STATUS_FAR = 4;
	public static final int AREA_STATUS_ACCESSIBLE = 5;
	
	
	/**
	 * Check if player can move to the selected item, if so set player to the new area
	 * @param p Player
	 * @param ai Area to move into
	 * @return true if move is successful
	 * @throws InaccessibleAreaException thrown if area is not accessible, message = status
	 */
	public Area moveToAreaItem(Character p , AreaItem ai) throws InaccessibleAreaException {
		
		Area out= getAreaFromAreaItem(p.getAreaItem());		
	
		int status = getAreaStatus(p,ai);
		
		if( status == AREA_STATUS_ACCESSIBLE || status == AREA_STATUS_HERE ){
			p.getDao().setAreaItem(ai);
			
				try {
					p.addActionPoints( -1 * ai.getCost() );
					out = getAreaFromAreaItem(ai);
				} catch (Exception e) {
					e.printStackTrace();
				}			
		}
		else {
			throw new InaccessibleAreaException(status);
		}
		return out;
	}
	
	
	
	private boolean isSteppable(String lock, int id){
		
		if(lock.length()==0){
			return true;
		}
		
		return ( ("," + lock + ",").indexOf( ""+id ) != -1 );
	}
	
	public int getAreaStatus(Character p , AreaItem ai){
		
		//this one is done for admin section which does not have a player logged in
		if(p==null){
			return AREA_STATUS_ACCESSIBLE;
		}
		
		//if i'm on the area it cannot be unaccessible
		if( ai.getId().equals( p.getAreaItem().getId()) ){
			return AREA_STATUS_HERE;
		}
		
		int out = AREA_STATUS_FAR;
				
		//I'm near enough to access it
		if(  isSteppable( ai.getArealock() , p.getAreaItem().getId()) ){
			out = AREA_STATUS_ACCESSIBLE;
		}
		else{
			//if cannot reach no other status is shown
			return ai.isHidemode()?AREA_STATUS_HIDDEN:out;
		}
		
		//Areal level is to high to access
		if( p.getDao().getLevel() < ai.getLevel() ){
			out = AREA_STATUS_BANNED;
		}
		
		//TODO quest lock
		
		//if is not reachable an bust be hidden, change status
		if( out!=AREA_STATUS_ACCESSIBLE && out !=AREA_STATUS_HERE && ai.isHidemode() ){
			out = AREA_STATUS_HIDDEN;
		}
		
		return out;
		
	}
	
	public Area getAreaFromAreaItem(AreaItem ai){		
		return getDaoManager().getArea( ai.getAreaid() );		
	}
	
	public ArrayList<AreaItem> getAreaItemsFromArea(Area a){
		return getDaoManager().getAreaItems( a.getId() );	
	}
	
}
