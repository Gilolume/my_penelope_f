package my_penelope_f;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import dao.AbstractDAOFactory;
import objects.Contact;
import objects.Group;
import objects.Project;
import observer.ProjectObservable;
import observer.ProjectObserver;

public class Controller implements ProjectObserver, ProjectObservable {
	private ArrayList<ProjectObserver> myObservers;
    private View view;
    private AbstractDAOFactory adf;
    private Scanner scanner;

    public Controller(View view) {
    	this.myObservers = new ArrayList<ProjectObserver>();
        this.view = view;
        this.adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.JSON_DAO_FACTORY);
        this.scanner = new Scanner();
    }

    public void initController() {
        view.getExitButton().addActionListener(e -> view.getFrame().dispose());
        view.getProposButton().addActionListener(e -> view.proposWindow());
        
        view.getAddProjectButton().addActionListener(e -> this.actionAddProject());
        view.getDeleteProjectButton().addActionListener(e -> this.actionDeleteProject());
        view.getProjectsList().addListSelectionListener(e -> this.actionShowProject());
        view.getAddTodoButton().addActionListener(e -> this.actionAddTodo());
        view.getDeleteTodoButton().addActionListener(e -> this.actionDeleteTodo());
        
        view.getAddGroupButton().addActionListener(e -> this.actionAddGroup());
        view.getDeleteGroupButton().addActionListener(e -> this.actionDeleteGroup());
        
        view.getAddContactButton().addActionListener(e -> this.actionAddContact());
        view.getDeleteContactButton().addActionListener(e -> this.actionDeleteContact());
        
        this.addProjectObserver(this);
        scanner.addProjectObserver(this);
        scanner.StartScanProjects();
    }
    
    private void actionAddProject() {
    	int projectOptionPane = view.openAddProjectOptionPane();
    	if (projectOptionPane == JOptionPane.OK_OPTION) {
    		String projectNameValue = view.getProjectNameTextField().getText();
    		String projectDetailValue = view.getProjectDetailTextField().getText();
    		List<String> projectGroupsList = view.getProjectGroupsList().getSelectedValuesList();
    		
    		if (projectNameValue.equals("")) {
    			view.openErrorDialog("Le nom du projet est vide");
    		} else {
    			if (adf.getProjectDAO().getById(projectNameValue) != null) {
    				view.openErrorDialog("Ce nom de projet existe déjà");
    			} else {
    				ArrayList<Group> groups = new ArrayList<>();
        			for (String name : projectGroupsList) {
        				groups.add(adf.getGroupDAO().getById(name));
        			}
        			Project project = new Project(projectNameValue, projectDetailValue, groups);
        			
        			project.createWorkingDirectory();
        			adf.getProjectDAO().add(project);
                	view.updateProjectsList();
    			}
    		}
    	}
    }
    
    private void actionDeleteProject() {
    	//Suppression du project
    	int selectedIndex = view.getProjectsList().getSelectedIndex();
    	if (selectedIndex != -1) {
    		Project project = adf.getProjectDAO().getById(view.getProjectsListModel().getElementAt(selectedIndex));
    		project.deleteWorkingDirectory();
        	adf.getProjectDAO().delete(project);
        	
    		//Update view project
    		view.updateProjectsList();
    		view.getProjectSelectedGroupsListModel().clear();
    		view.getEventsListModel().clear();
    		view.getFilesListModel().clear();
    		view.getTodosListModel().clear();
    		view.getProjectDetailLabel().setText("");
    	}
    }
    
    private void actionShowProject() {
    	int selectedIndex = view.getProjectsList().getSelectedIndex();
    	if (selectedIndex != -1) {
    		//Faire autre action
    		Project project = adf.getProjectDAO().getById(view.getProjectsListModel().getElementAt(selectedIndex));
    		view.updateProjectGroupsList(project.getGroups());
        	view.updateFilesList(project.getFiles());
        	view.updateEventsList(project.getEvents());
        	view.updateTodosList(project.getTodos());
        	view.getProjectDetailLabel().setText(project.getDetail());
    	}
    }
    
    private void actionAddTodo() {
    	int todoOptionPane = view.openAddTodoOptionPane();
    	if (todoOptionPane == JOptionPane.OK_OPTION) {
    		String todoValue = view.getTodoTextField().getText();
    		
    		if (todoValue.equals("")) {
    			view.openErrorDialog("Le todo est vide");
    		} else {
    			int selectedIndex = view.getProjectsList().getSelectedIndex();
    			if (selectedIndex != -1) {
    				Project project = adf.getProjectDAO().getById(view.getProjectsListModel().getElementAt(selectedIndex));
    				if (project.getTodos().contains(todoValue)) {
        				view.openErrorDialog("Ce todo existe déjà");
        			} else {
        				project.addTodo(todoValue);
            			adf.getProjectDAO().update(project);
            			notifyProjectObservers(project, "Todo ajouté : " + todoValue);
                    	view.updateTodosList(project.getTodos());
        			}
    			}
    		}
    	}
    }
    
    private void actionDeleteTodo() {
    	int selectedIndex = view.getProjectsList().getSelectedIndex();
		if (selectedIndex != -1) {
			int selectedTodoIndex = view.getTodosList().getSelectedIndex();
			if (selectedTodoIndex != -1) {
				Project project = adf.getProjectDAO().getById(view.getProjectsListModel().getElementAt(selectedIndex));
				String todoToDelete = view.getTodosListModel().getElementAt(selectedTodoIndex);
				project.deleteTodo(todoToDelete);
				adf.getProjectDAO().update(project);
				notifyProjectObservers(project, "Todo supprimé : " + todoToDelete);
				//Update view project, group
				view.updateTodosList(project.getTodos());
			}
		}
    }
    
	private void actionAddGroup() {
    	int groupOptionPane = view.openAddGroupOptionPane();
        if (groupOptionPane == JOptionPane.OK_OPTION) {
        	String groupNameValue = view.getGroupNameTextField().getText();
        	List<String> groupContactsList = view.getGroupContactsList().getSelectedValuesList();
        	
        	if (groupNameValue.equals("")) {
        		view.openErrorDialog("Le nom du groupe est vide");
        	} else {
    			if (adf.getGroupDAO().getById(groupNameValue) != null) {
    				view.openErrorDialog("Ce nom de groupe existe déjà");
    			} else {
    				ArrayList<Contact> contacts = new ArrayList<>();
        			for (String username : groupContactsList) {
        				contacts.add(adf.getContactDAO().getById(username));
        			}
        			Group group = new Group(groupNameValue, contacts);
        			adf.getGroupDAO().add(group);
                	view.updateGroupsList();
    			}
        	}
        }
    }
	
	private void actionDeleteGroup() {
		int selectedIndex = view.getGroupsList().getSelectedIndex();
		if (selectedIndex != -1) {
			Group group = view.getGroupsListModel().getElementAt(selectedIndex);
			
			//Suppression du group dans les projects
	    	ArrayList<Project> projects = adf.getProjectDAO().getAll();
	    	for (Project project : projects) {
	    		project.deleteGroup(group);
	    		adf.getProjectDAO().update(project);
	    	}
			
	    	//Suppression du group
			adf.getGroupDAO().delete(group);
			
			//Update view project, group
			view.updateGroupsList();
			view.updateProjectsList();
		}
	}
    
    private void actionAddContact() {
		int contactOptionPane = view.openAddContactOptionPane();
        if (contactOptionPane == JOptionPane.OK_OPTION)
        {
        	String contactUsername = view.getContactUsernameTextField().getText();
        	String conatctFirstname = view.getContactFirstnameTextField().getText();
        	String contactLastname = view.getContactLastnameTextField().getText();
        	String contactPhone = view.getContactPhoneTextField().getText();
        	String contactMail = view.getContactMailTextField().getText();
        			
        	if (contactUsername.equals("")) {
        		view.openErrorDialog("Le username est vide");
        	} else {
        		if (adf.getContactDAO().getById(contactUsername) != null) {
                	view.openErrorDialog("Ce username existe déjà");
        		} else {
        			Contact contact = new Contact(contactUsername, conatctFirstname, contactLastname, contactPhone, contactMail);
        			adf.getContactDAO().add(contact);
                	view.updateContactsList();
        		} 
        	}
        }
    }

    private void actionDeleteContact() {
    	int selectedIndex = view.getContactsList().getSelectedIndex();
    	if (selectedIndex != -1) {
    		Contact contact = view.getContactsListModel().getElementAt(selectedIndex);
        	
        	//Suppression du contact dans les groups des projets
        	ArrayList<Project> projects = adf.getProjectDAO().getAll();
        	for (Project project : projects) {
        		ArrayList<Group> groups = project.getGroups();
        		ArrayList<Group> updatedGroups = new ArrayList<Group>();
        		for (Group group : groups) {
        			group.deleteContact(contact);
        			updatedGroups.add(group);
        		}
        		project.setGroups(updatedGroups);
    			adf.getProjectDAO().update(project);
        	}
        	
        	//Suppression du contact dans les groups
        	ArrayList<Group> groups = adf.getGroupDAO().getAll();
        	for (Group group : groups) {
        		group.deleteContact(contact);
        		adf.getGroupDAO().update(group);
        	}
        	
        	//Suppression du contact
        	adf.getContactDAO().delete(contact);
        	//Update view project, group, contact
        	view.updateContactsList();
        	view.updateGroupsList();
        	view.updateProjectsList();
    	}
    }
    
    @Override
	public void newProjectEvent(ProjectObservable o, Project project, Object arg) {
    	Date aujourdhui = new Date();
    	DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
    			DateFormat.SHORT,
    			DateFormat.SHORT, new Locale("FR","fr"));
		if(o instanceof Scanner) {
			project.addEvent(shortDateFormat.format(aujourdhui).toString() + " : " + o.getClass().getSimpleName() + " -> " + arg.toString());
			adf.getProjectDAO().update(project);
			int selectedIndex = view.getProjectsList().getSelectedIndex();
			if (selectedIndex != -1 && view.getProjectsListModel().getElementAt(selectedIndex).equals(project.getName())) {
				view.updateFilesList(project.getFiles());
				view.updateEventsList(project.getEvents());
			}
		}
		if(o instanceof Controller) {
			project.addEvent(shortDateFormat.format(aujourdhui).toString() + " : " + o.getClass().getSimpleName() + " -> " + arg.toString());
			adf.getProjectDAO().update(project);
			int selectedIndex = view.getProjectsList().getSelectedIndex();
			if (selectedIndex != -1 && view.getProjectsListModel().getElementAt(selectedIndex).equals(project.getName())) {
				view.updateEventsList(project.getEvents());
			}
		}
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
