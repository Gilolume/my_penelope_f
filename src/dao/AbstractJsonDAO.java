package dao;

import java.util.ArrayList;

public abstract class AbstractJsonDAO<T> {
	public AbstractJsonDAO(){}
	 
	/**
	* M�thode de cr�ation
	* @param obj
	* @return void 
	*/
	public abstract void add(T obj);
	
	/**
	* M�thode pour effacer
	* @param obj
	* @return void 
	*/
	public abstract void delete(T obj);
	
	/**
	* M�thode de mise � jour
	* @param obj
	* @return void
	*/
	public abstract void update(T obj);
	
	/**
	* M�thode exists
	* @param T
	* @return boolean
	*/
	public abstract boolean exists(T obj);
	
	/**
	* M�thode get by id
	* @param id
	* @return T
	*/
	public abstract T getById(String id);
	
	/**
	* M�thode get all
	* @param void
	* @return ArrayList<T>
	*/
	public abstract ArrayList<T> getAll();

}