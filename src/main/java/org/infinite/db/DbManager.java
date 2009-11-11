package org.infinite.db;

import java.util.ArrayList;

public interface DbManager {

	ArrayList<?> listByQuery(String className, String query);
       
	boolean delete(Object dto);    
	
	boolean create(Object dto);	
	
	boolean update(Object dto);	
	
	Object findById(String className, int id);
    
	ArrayList<?> listAllDto(String className);

	ArrayList<?> listByQuery(String query);

}