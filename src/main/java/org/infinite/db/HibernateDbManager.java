package org.infinite.db;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class HibernateDbManager implements DbManager{


	/*
	 * Bean's data + getter and setter
	 */

	private static HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate ht) {
		HibernateDbManager.hibernateTemplate = ht;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}


	/*
	 * READ Methods
	 */

	@SuppressWarnings("unchecked")
	public ArrayList<?> listByQuery(String query){		
		return (ArrayList<Object>) getHibernateTemplate().find(query);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<?> listByQuery(String className, String condition){		
		return (ArrayList<Object>) getHibernateTemplate().find("from "+className+" where "+condition);
	}

	public Object findById(String className, int id) {
		return getHibernateTemplate().get(className, id);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<?> listAllDto(String className){	
		return (ArrayList<Object>)listByQuery(className,"'1'='1'");
	}

	//TODO make transactional by AOP
	/*
	 * WRITE Methods 
	 */

	public boolean delete(Object o){

		boolean b = true;
		try {
			getHibernateTemplate().delete(o);
		} catch (DataAccessException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}


	public boolean create(Object o){
		boolean b = true;
		try {
			getHibernateTemplate().save(o);
		} catch (DataAccessException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	public boolean update(Object o){
		boolean b = true;
		try {
			getHibernateTemplate().update(o);
		} catch (DataAccessException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

}