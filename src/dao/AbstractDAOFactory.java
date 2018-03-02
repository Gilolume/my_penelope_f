package dao;

import objects.Contact;
import objects.Group;
import objects.Project;

public abstract class AbstractDAOFactory {
	public static final int JSON_DAO_FACTORY = 0;

	//Retourne un objet Classe interagissant avec la BDD
	public abstract AbstractJsonDAO<Project> getProjectDAO();

	//Retourne un objet Professeur interagissant avec la BDD
	public abstract AbstractJsonDAO<Group> getGroupDAO();

	//Retourne un objet Eleve interagissant avec la BDD
	public abstract AbstractJsonDAO<Contact> getContactDAO();
   
	//Méthode permettant de récupérer les Factory
	public static AbstractDAOFactory getFactory(int type){
		switch(type){
			case JSON_DAO_FACTORY:
				return new JsonDAOFactory();
			default:
				return new JsonDAOFactory();
		}
	}
}