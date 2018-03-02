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

import objects.Group;

public class GroupDAO extends AbstractJsonDAO<Group> {
	private Gson gson;
	private String pathGroupFile;
	private static GroupDAO ourInstance = new GroupDAO();
	
	public static GroupDAO getInstance() {
        return ourInstance;
    }
	
	private GroupDAO() {
		this.gson = new Gson();
		this.pathGroupFile = "./data/Groups.json";
		this.createFile();
	}
	
	private void createFile() {
		File f = new File(this.pathGroupFile);
		if(!f.exists()) { 
			System.out.println("Creation du fichier " + this.pathGroupFile);
			ArrayList<Group> groups = new ArrayList<Group>();
			writeToJsonFile(groups);
		}
	}
	
	public void add(Group group) {
		if (!exists(group)) {
			ArrayList<Group> groups = this.getAll();
			groups.add(group);
			writeToJsonFile(groups);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le group existe déjà");
		}
	}
	
	/*
	 * La clé de l'objet est le name
	 * Fonction update utilisable seulement si le name du group reste le même
	 * Pour update le name du group supprimer utilisé delete puis add
	 * */
	public void update(Group group) {
		int indexOfProject = indexOf(group);
		if (indexOfProject != -1) {
			ArrayList<Group> groups = this.getAll();
			groups.set(indexOfProject, group);
			writeToJsonFile(groups);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le group n'existe pas");
		}
	}
	
	public void delete(Group group) {
		int indexOfGroup = indexOf(group);
		if (indexOfGroup != -1) {
			ArrayList<Group> groups = this.getAll();
			groups.remove(indexOfGroup);
			writeToJsonFile(groups);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le group n'existe pas");
		}
	}
	
	public boolean exists(Group group) {
		ArrayList<Group> groups = this.getAll();
		for (Group c : groups) {
			if (c.getName().equals(group.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public Group getById(String id) {
		ArrayList<Group> groups = this.getAll();
		for (Group c : groups) {
			if (c.getName().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	public ArrayList<Group> getAll(){
		ArrayList<Group> groups = new ArrayList<Group>();
		try (Reader reader = new FileReader(this.pathGroupFile)) {
            groups = this.gson.fromJson(reader, new TypeToken<List<Group>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
		return groups;
	}
	
	private void writeToJsonFile(ArrayList<Group> groups) {
		try (FileWriter writer = new FileWriter(this.pathGroupFile)){
			this.gson.toJson(groups, writer);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int indexOf(Group group) {
		ArrayList<Group> groups = this.getAll();
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getName().equals(group.getName())) {
				return i;
			}
		}
		return -1;
	}
}
