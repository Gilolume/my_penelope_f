package dao;

import objects.Contact;
import objects.Group;
import objects.Project;

public class JsonDAOFactory extends AbstractDAOFactory {

	public AbstractJsonDAO<Project> getProjectDAO() {
		return ProjectDAO.getInstance();
	}

	public AbstractJsonDAO<Group> getGroupDAO() {
		return GroupDAO.getInstance();
	}

	public AbstractJsonDAO<Contact> getContactDAO() {
		return ContactDAO.getInstance();
	}
}
