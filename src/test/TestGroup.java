package test;

import java.util.ArrayList;

import objects.Contact;
import objects.Group;

public class TestGroup {
	public static void main(String[] args) {

        Contact contact1 = new Contact("gierak_g", "Guillaume", "Gierak", "0101", "ggi@mail.com");
        Contact contact2 = new Contact("gerome_a", "Alex", "Gerome", "02012", "age@mail.com");

        ArrayList<Contact> contacts1 = new ArrayList<Contact>();
        contacts1.add(contact1);
        contacts1.add(contact2);
        Group group1 = new Group("groupe_pls", contacts1);
        
        System.out.println(contact1.toString());
        System.out.println(contact2.toString());
        System.out.println(group1.toString());

        group1.deleteContact(contact1);

        System.out.println(group1.toString());
    }
}
