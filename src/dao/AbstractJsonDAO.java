package dao;

import java.util.ArrayList;

public abstract class AbstractJsonDAO<T> {
	public AbstractJsonDAO(){}
	 
	/**
	* Méthode de création
	* @param obj
	* @return void 
	*/
	public abstract void add(T obj);
	
	/**
	* Méthode pour effacer
	* @param obj
	* @return void 
	*/
	public abstract void delete(T obj);
	
	/**
	* Méthode de mise à jour
	* @param obj
	* @return void
	*/
	public abstract void update(T obj);
	
	/**
	* Méthode exists
	* @param T
	* @return boolean
	*/
	public abstract boolean exists(T obj);
	
	/**
	* Méthode get by id
	* @param id
	* @return T
	*/
	public abstract T getById(String id);
	
	/**
	* Méthode get all
	* @param void
	* @return ArrayList<T>
	*/
	public abstract ArrayList<T> getAll();

}