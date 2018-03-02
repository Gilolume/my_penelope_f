package my_penelope_f;

import java.io.File;
import java.util.ArrayList;

import dao.AbstractDAOFactory;
import objects.Project;
import observer.ProjectObservable;
import observer.ProjectObserver;

public class Scanner implements ProjectObservable {
	private ArrayList<ProjectObserver> myObservers;
	private String pathDataDirectory;
	private AbstractDAOFactory adf;
    //private static Scanner ourInstance = new Scanner();

    /*public static Scanner getInstance() {
        return ourInstance;
    }*/

    public Scanner() {
    	this.myObservers = new ArrayList<ProjectObserver>();
    	this.pathDataDirectory = "./data";
    	this.adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.JSON_DAO_FACTORY);
    }
    
    public void StartScanProjects() {
    	while (true) {
    		ArrayList<Project> projects = adf.getProjectDAO().getAll();
    		for (Project project : projects) {
    			String path =  this.pathDataDirectory + "/" + project.getName();
    			File folder = new File(path);
    			System.out.println("Scann de " + path);
    			if (folder.exists()) {
    				ArrayList<String> filesFound = new ArrayList<String>();
    				for (File file : folder.listFiles()) {
    					String fileName = file.getName();
    					System.out.println(fileName);
    					filesFound.add(fileName);
    			    }
    				ApplyDifferences(project, filesFound);
    			} else {
    				System.out.println("Le repertoire " + path + " n'existe pas");
    			}
    		}
    		
    		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    private void ApplyDifferences(Project project, ArrayList<String> filesFound) {
    	ArrayList<String> prevProjectFiles = project.getFiles();
    	ArrayList<String> tmpFilesFound = new ArrayList<>();
    	tmpFilesFound.addAll(filesFound);
    	
    	if (tmpFilesFound.size() >= prevProjectFiles.size()) {
    		//Detect meme nombre ou supp de fichiers -> nouveau ou non
    		tmpFilesFound.removeAll(prevProjectFiles);
    		for (String newFile : tmpFilesFound) {
    			System.out.println("Nouveau fichier : " + newFile);
    			project.setFiles(filesFound);
    			adf.getProjectDAO().update(project);
    			notifyProjectObservers(project, "Nouveau fichier ajouté : " + newFile);
    		}
    	}else {
    		//Detect nombre inf donc fichier(s) supp
    		prevProjectFiles.removeAll(tmpFilesFound);
    		for (String deletedFile : prevProjectFiles) {
    			System.out.println("Fichier supprimé : " + deletedFile);
    			project.setFiles(filesFound);
        		adf.getProjectDAO().update(project);
    			notifyProjectObservers(project, "Fichier supprimé : " + deletedFile);
    		}
    	}
    	System.out.println("\n");
    }
    
	@Override
	public void addProjectObserver(ProjectObserver o) {
		if (!this.myObservers.contains(o)) {
			this.myObservers.add(o);
		}
	}

	@Override
	public void deleteProjectObserver(ProjectObserver o) {
		if (this.myObservers.contains(o)) {
			this.myObservers.remove(o);
		}
	}

	@Override
	public void notifyProjectObservers(Project project, Object arg) {
		for (ProjectObserver o : myObservers) {
			o.newProjectEvent(this, project, arg);
		}
	}
}

