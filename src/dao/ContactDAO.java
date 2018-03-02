package dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import objects.Contact;


public class ContactDAO extends AbstractJsonDAO<Contact> {
	private Gson gson;
	private String pathContactFile;
	private static ContactDAO ourInstance = new ContactDAO();
	
	public static ContactDAO getInstance() {
        return ourInstance;
    }

	private ContactDAO() {
		this.gson = new Gson();
		this.pathContactFile = "./data/Contacts.json";
		this.createFile();
	}
	
	private void createFile() {
		File f = new File(this.pathContactFile);
		if(!f.exists()) { 
			System.out.println("Creation du fichier " + this.pathContactFile);
			ArrayList<Contact> contacts = new ArrayList<Contact>();
			writeToJsonFile(contacts);
		}
	}
	
	public void add(Contact contact) {
		if (!exists(contact)) {
			ArrayList<Contact> contacts = this.getAll();
			contacts.add(contact);
			writeToJsonFile(contacts);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le contact existe déjà");
		}
	}
	
	/*
	 * La clé de l'objet est le username
	 * Fonction update utilisable seulement si le username du contact reste le même
	 * Pour update le username du contact supprimer utilisé delete puis add
	 * */
	public void update(Contact contact) {
		int indexOfProject = indexOf(contact);
		if (indexOfProject != -1) {
			ArrayList<Contact> contacts = this.getAll();
			contacts.set(indexOfProject, contact);
			writeToJsonFile(contacts);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le contact n'existe pas");
		}
	}
	
	public void delete(Contact contact) {
		int indexOfContact = indexOf(contact);
		if (indexOfContact != -1) {
			ArrayList<Contact> contacts = this.getAll();
			contacts.remove(indexOfContact);
			writeToJsonFile(contacts);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le contact n'existe pas");
		}
	}
	
	public boolean exists(Contact contact) {
		ArrayList<Contact> contacts = this.getAll();
		for (Contact c : contacts) {
			if (c.getUsername().equals(contact.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	public Contact getById(String id) {
		ArrayList<Contact> contacts = this.getAll();
		for (Contact c : contacts) {
			if (c.getUsername().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	public ArrayList<Contact> getAll(){
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		try (Reader reader = new FileReader(this.pathContactFile)) {
            contacts = this.gson.fromJson(reader, new TypeToken<List<Contact>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
		return contacts;
	}
	
	private void writeToJsonFile(ArrayList<Contact> contacts) {
		try (FileWriter writer = new FileWriter(this.pathContactFile)){
			this.gson.toJson(contacts, writer);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int indexOf(Contact contact) {
		ArrayList<Contact> contacts = this.getAll();
		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getUsername().equals(contact.getUsername())) {
				return i;
			}
		}
		return -1;
	}
}
