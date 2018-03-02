package test;

import java.util.ArrayList;

import dao.AbstractDAOFactory;
import objects.Contact;
import objects.Group;
import objects.Project;

public class TestDAO {
	public static void main(String[] args) {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.JSON_DAO_FACTORY);
		
        Contact contact1 = new Contact("gierak_g", "guillaume", "gierak", "0101", "ggi@mail.com");
        Contact contact2 = new Contact("gerome_a", "alexis", "gerome", "02012", "age@mail.com");
        Contact contact3 = new Contact("nousername", "noname", "noname", "02012", "age@mail.com");
        
        //Test ContactDAO
        System.out.println(adf.getContactDAO().getAll());
        
        adf.getContactDAO().add(contact1);
        System.out.println(adf.getContactDAO().getAll());
        
        adf.getContactDAO().add(contact2);
        System.out.println(adf.getContactDAO().getAll());
        
        adf.getContactDAO().delete(contact3);
        System.out.println(adf.getContactDAO().getAll());
        
        System.out.println(adf.getContactDAO().getById("gerome_a"));
        
        adf.getContactDAO().delete(adf.getContactDAO().getById(contact1.getUsername()));
        System.out.println(adf.getContactDAO().getAll());
        //Fin ContactDAO
        
        //Test GroupDAO
        ArrayList<Contact> contacts1 = new ArrayList<Contact>();
        contacts1.add(contact1);
        contacts1.add(contact2);
        Group group1 = new Group("group1", contacts1);
        
        ArrayList<Contact> contacts2 = new ArrayList<Contact>();
        contacts2.add(contact3);
        Group group2 = new Group("group2", contacts2);
        
        System.out.println(adf.getGroupDAO().getAll());
        
        adf.getGroupDAO().add(group1);
        System.out.println(adf.getGroupDAO().getAll());
        
        adf.getGroupDAO().delete(group2);
        System.out.println(adf.getGroupDAO().getAll());
        
        adf.getGroupDAO().add(group2);
        System.out.println(adf.getGroupDAO().getAll());
        
        adf.getGroupDAO().delete(adf.getGroupDAO().getById(group2.getName()));
        System.out.println(adf.getGroupDAO().getAll());
        //Fin GroupDAO
        
        //Test ProjectDAO
        ArrayList<Group> groups1 = new ArrayList<Group>();
        groups1.add(group1);
        Project project1 = new Project("project1", "detail project1", groups1);
        
        Project project2 = new Project("project2", "detail project2");
        
        System.out.println(adf.getProjectDAO().getAll());
        
        adf.getProjectDAO().add(project1);
        System.out.println(adf.getProjectDAO().getAll());
        
        adf.getProjectDAO().delete(project2);
        System.out.println(adf.getProjectDAO().getAll());
        
        adf.getProjectDAO().add(project2);
        System.out.println(adf.getProjectDAO().getAll());
        
        adf.getProjectDAO().delete(adf.getProjectDAO().getById(project2.getName()));
        System.out.println(adf.getProjectDAO().getAll());
        
        adf.getProjectDAO().update(project2);
        System.out.println(adf.getProjectDAO().getAll());
        
        project1.addGroup(group2);
        adf.getProjectDAO().update(project1);
        System.out.println(adf.getProjectDAO().getAll());
        //Fin ProjectDAO
        
    }
}
