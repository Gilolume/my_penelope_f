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

import objects.Project;

public class ProjectDAO extends AbstractJsonDAO<Project> {
	private Gson gson;
	private String pathProjectFile;
	private static ProjectDAO ourInstance = new ProjectDAO();
	
	public static ProjectDAO getInstance() {
        return ourInstance;
    }
	
	private ProjectDAO() {
		this.gson = new Gson();
		this.pathProjectFile = "./data/Projects.json";
		this.createFile();
	}
	
	private void createFile() {
		File f = new File(this.pathProjectFile);
		if(!f.exists()) { 
			System.out.println("Creation du fichier " + this.pathProjectFile);
			ArrayList<Project> projects = new ArrayList<Project>();
			writeToJsonFile(projects);
		}
	}
	
	public void add(Project project) {
		if (!exists(project)) {
			ArrayList<Project> projects = this.getAll();
			projects.add(project);
			writeToJsonFile(projects);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le project existe déjà");
		}
	}
	
	/*
	 * La clé de l'objet est le name
	 * Fonction update utilisable seulement si le name du projet reste le même
	 * Pour update le name du project supprimer utilisé delete puis add
	 * */
	public void update(Project project) {
		int indexOfProject = indexOf(project);
		if (indexOfProject != -1) {
			ArrayList<Project> projects = this.getAll();
			projects.set(indexOfProject, project);
			writeToJsonFile(projects);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le project n'existe pas");
		}
	}
	
	public void delete(Project project) {
		int indexOfProject = indexOf(project);
		if (indexOfProject != -1) {
			ArrayList<Project> projects = this.getAll();
			projects.remove(indexOfProject);
			writeToJsonFile(projects);
		} else {
			//TODO Faire vraie erreur
			System.out.println("Le project n'existe pas");
		}
	}
	
	public boolean exists(Project project) {
		ArrayList<Project> projects = this.getAll();
		for (Project c : projects) {
			if (c.getName().equals(project.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public Project getById(String id) {
		ArrayList<Project> projects = this.getAll();
		for (Project c : projects) {
			if (c.getName().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	public ArrayList<Project> getAll(){
		ArrayList<Project> projects = new ArrayList<Project>();
		try (Reader reader = new FileReader(this.pathProjectFile)) {
            projects = this.gson.fromJson(reader, new TypeToken<List<Project>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
		return projects;
	}
	
	private void writeToJsonFile(ArrayList<Project> projects) {
		try (FileWriter writer = new FileWriter(this.pathProjectFile)){
			this.gson.toJson(projects, writer);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int indexOf(Project project) {
		ArrayList<Project> projects = this.getAll();
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).getName().equals(project.getName())) {
				return i;
			}
		}
		return -1;
	}
}
