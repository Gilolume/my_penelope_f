package my_penelope_f;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.AbstractDAOFactory;
import objects.Contact;
import objects.Group;
import objects.Project;

public class View extends JFrame {
	private AbstractDAOFactory adf;
	
	private JFrame frame;
	private JPanel contentPane;
	
	private JMenuBar menuBar;
    private JMenu fileMenu, helpMenu;
    private JMenuItem exitButton, proposButton;
    
    private JTabbedPane tabbedPanel;
    
    //Project panel
    private JPanel projectPanel;
    private JButton addProjectButton;
    private JButton deleteProjectButton;
    private DefaultListModel<String> projectsListModel;
    private JList<String> projectsList;
    private JScrollPane projectsScrollPane;
    //--Project Labels
    private JLabel groupsLabel;
    private JLabel filesLabel;
    private JLabel eventsLabel;
    private JLabel todosLabel;
    private JLabel detailLabel;
    private JTextPane projectDetailLabel;
    //--Groups
    private DefaultListModel<String> projectSelectedGroupsListModel;
    private JList<String> projectSelectedGroupsList;
    private JScrollPane projectSelectedGroupsScrollPane;
    //--Files
    private DefaultListModel<String> filesListModel;
    private JList<String> filesList;
    private JScrollPane filesScrollPane;
    //--Events
    private DefaultListModel<String> eventsListModel;
    private JList<String> eventsList;
    private JScrollPane eventsScrollPane;
    //--Todos
    private DefaultListModel<String> todosListModel;
    private JList<String> todosList;
    private JScrollPane todosScrollPane;
    private JButton addTodoButton;
    private JButton deleteTodoButton;
    private int todoOptionPane;
    private JTextField todoTextField;
    
    //Wizard Project
    private int projectOptionPane;
    private JTextField projectNameTextField;
    private JTextField projectDetailTextField;
	private DefaultListModel<String> projectGroupsListModel;
	private JList<String> projectGroupsList;
	private JScrollPane projectGroupsScrollPane;
    
    //Groups panel
    private JPanel groupPanel;
    private JButton addGroupButton;
    private JButton deleteGroupButton;
    private DefaultListModel<Group> groupsListModel;
    private JList<Group> groupsList;
    private JScrollPane groupsScrollPane;
    
    //Wizard Group
    private int groupOptionPane;
    private JTextField groupNameTextField;
    private DefaultListModel<String> groupContactsListModel;
    private JList<String> groupContactsList;
    private JScrollPane groupContactsScrollPane;
    
    
    //Contacts panel
    private JPanel contactPanel;
    private JButton addContactButton;
    private JButton deleteContactButton;
    private DefaultListModel<Contact> contactsListModel;
    private JList<Contact> contactsList;
    private JScrollPane contactsScrollPane;
    
    //Wizard Contact
    private int contactOptionPane;
    private JTextField contactUsernameTextField;
    private JTextField contactFirstnameTextField;
    private JTextField contactLastnameTextField;
    private JTextField contactPhoneTextField;
    private JTextField contactMailTextField;
    
	public View() {
		adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.JSON_DAO_FACTORY);
		frame = new JFrame("My_penelope_f");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		initComponents();
		addMenuBar();
		
		frame.setVisible(true);
	}
	
	private void initComponents() {
		frame.getContentPane().setLayout(null);
		tabbedPanel = new JTabbedPane(JTabbedPane.TOP);
		tabbedPanel.setBounds(0, 0, 1262, 647);
        frame.getContentPane().add(tabbedPanel);
        
        //Project
        projectPanel = new JPanel();
        tabbedPanel.addTab("Projects", null, projectPanel, null);
        projectPanel.setLayout(null);
        
        addProjectButton = new JButton("+ Project");
        addProjectButton.setBounds(12, 579, 101, 25);
        projectPanel.add(addProjectButton);
        
        projectsListModel = new DefaultListModel<String>();
        projectsList = new JList<String>(projectsListModel);
        
        projectsScrollPane = new JScrollPane(projectsList);
        projectsScrollPane.setBounds(12, 13, 214, 553);
        projectPanel.add(projectsScrollPane);
        
        deleteProjectButton = new JButton("- Project");
        deleteProjectButton.setBounds(125, 579, 101, 25);
        projectPanel.add(deleteProjectButton);
        
        detailLabel = new JLabel("Details");
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailLabel.setBounds(238, 331, 254, 14);
        projectPanel.add(detailLabel);
        
        projectDetailLabel = new JTextPane();
        projectDetailLabel.setEditable(false);
        projectDetailLabel.setBounds(236, 355, 256, 207);
        projectPanel.add(projectDetailLabel);
        
        //--Groups
        projectSelectedGroupsListModel = new DefaultListModel<String>();
        projectSelectedGroupsList = new JList<String>(projectSelectedGroupsListModel);
        
        projectSelectedGroupsScrollPane = new JScrollPane(projectSelectedGroupsList);
        projectSelectedGroupsScrollPane.setBounds(236, 41, 212, 277);
        projectPanel.add(projectSelectedGroupsScrollPane);
        
        groupsLabel = new JLabel("Groupe(s)");
        groupsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupsLabel.setBounds(236, 15, 212, 14);
        projectPanel.add(groupsLabel);
        
        //--Files
        filesListModel = new DefaultListModel<String>();
        filesList = new JList<String>(filesListModel);
        
        filesScrollPane = new JScrollPane(filesList);
        filesScrollPane.setBounds(1033, 40, 214, 279);
        projectPanel.add(filesScrollPane);
        
        filesLabel = new JLabel("Fichier(s)");
        filesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filesLabel.setBounds(1033, 16, 212, 14);
        projectPanel.add(filesLabel);
        
        //--Events
        eventsListModel = new DefaultListModel<String>();
        eventsList = new JList<String>(eventsListModel);
        
        eventsScrollPane = new JScrollPane(eventsList);
        eventsScrollPane.setBounds(458, 40, 565, 279);
        projectPanel.add(eventsScrollPane);
        
        eventsLabel = new JLabel("Event(s)");
        eventsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        eventsLabel.setBounds(458, 15, 565, 14);
        projectPanel.add(eventsLabel);
        
        //--Todos
        todosListModel = new DefaultListModel<String>();
        todosList = new JList<String>(todosListModel);
        
        todosScrollPane = new JScrollPane(todosList);
        todosScrollPane.setBounds(502, 355, 743, 207);
        projectPanel.add(todosScrollPane);
        
        todosLabel = new JLabel("Todo(s)");
        todosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        todosLabel.setBounds(502, 330, 743, 14);
        projectPanel.add(todosLabel);
        
        addTodoButton = new JButton("+ Todo");
        addTodoButton.setBounds(773, 579, 101, 25);
        projectPanel.add(addTodoButton);
        
        deleteTodoButton = new JButton("- Todo");
        deleteTodoButton.setBounds(884, 579, 101, 25);
        projectPanel.add(deleteTodoButton);
        
        //Group
        groupPanel = new JPanel();
        tabbedPanel.addTab("Groups", null, groupPanel, null);
        groupPanel.setLayout(null);
        
        addGroupButton = new JButton("+ Groupe");
        addGroupButton.setBounds(12, 579, 101, 25);
        groupPanel.add(addGroupButton);
        
        groupsListModel = new DefaultListModel<Group>();
        groupsList = new JList<Group>(groupsListModel);
        
        groupsScrollPane = new JScrollPane(groupsList);
        groupsScrollPane.setBounds(12, 13, 1233, 553);
        groupPanel.add(groupsScrollPane);
        
        deleteGroupButton = new JButton("- Group");
        deleteGroupButton.setBounds(125, 579, 101, 25);
        groupPanel.add(deleteGroupButton);
        
        
        //Contact
        contactPanel = new JPanel();
        tabbedPanel.addTab("Users", null, contactPanel, null);
        contactPanel.setLayout(null);
        
        addContactButton = new JButton("+ Contact");
        addContactButton.setBounds(12, 579, 101, 25);
        contactPanel.add(addContactButton);
        
        contactsListModel = new DefaultListModel<Contact>();
        contactsList = new JList<Contact>(contactsListModel);
        
        contactsScrollPane = new JScrollPane(contactsList);
        contactsScrollPane.setBounds(12, 13, 1233, 553);
        contactPanel.add(contactsScrollPane);
        
        deleteContactButton = new JButton("- Contact");
        deleteContactButton.setBounds(125, 579, 101, 25);
        contactPanel.add(deleteContactButton);
        
        updateProjectsList();
        updateGroupsList();
        updateContactsList();
	}
	
	public void updateProjectsList() {
		projectsListModel.clear();
		ArrayList<Project> projects = adf.getProjectDAO().getAll();
		for (Project project : projects) {
			projectsListModel.addElement(project.getName());
		}
	}
	
	public void updateProjectGroupsList(ArrayList<Group> groups) {
		projectSelectedGroupsListModel.clear();
		for (Group group : groups) {
			projectSelectedGroupsListModel.addElement(group.getName());
		}
	}
	
	public void updateFilesList(ArrayList<String> files) {
		filesListModel.clear();
		for (String file : files) {
			filesListModel.addElement(file);
		}
	}
	
	public void updateEventsList(ArrayList<String> events) {
		eventsListModel.clear();
		for (String event : events) {
			eventsListModel.addElement(event);
		}
	}
	
	public void updateTodosList(ArrayList<String> todos) {
		todosListModel.clear();
		for (String todo : todos) {
			todosListModel.addElement(todo);
		}
	}
	
	public void updateGroupsList() {
		groupsListModel.clear();
		ArrayList<Group> groups = adf.getGroupDAO().getAll();
        for (Group group : groups) {
        	groupsListModel.addElement(group);
        }
	}
	
	public void updateContactsList() {
		contactsListModel.clear();
		ArrayList<Contact> contacts = adf.getContactDAO().getAll();
        for (Contact contact : contacts) {
        	contactsListModel.addElement(contact);
        }
	}	
	
	public int openAddProjectOptionPane() {
		projectNameTextField = new JTextField();
		projectDetailTextField = new JTextField();
		projectGroupsListModel = new DefaultListModel<String>();
		projectGroupsList = new JList<String>(projectGroupsListModel);
		projectGroupsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		projectGroupsList.setSelectionModel(new DefaultListSelectionModel() {
            public void setSelectionInterval(int index0, int index1) {
                if(isSelectedIndex(index0)) {
                    this.removeSelectionInterval(index0, index1);
                } else {
                    this.addSelectionInterval(index0, index1);
                }
            }
        });
		
		
		projectGroupsScrollPane = new JScrollPane(projectGroupsList);
		projectGroupsScrollPane.setBounds(12, 13, 100, 50);
		
		ArrayList<Group> groups = adf.getGroupDAO().getAll();
        for (Group group : groups) {
        	projectGroupsListModel.addElement(group.getName());
        }
		
		Object[] message = {
            "Name :", projectNameTextField,
            "Detail :", projectDetailTextField,
            "Groups", projectGroupsScrollPane,
        };
		
		projectOptionPane = JOptionPane.showConfirmDialog(null, message, "Add project", JOptionPane.OK_CANCEL_OPTION);
	
        return projectOptionPane;
	}
	
	public int openAddTodoOptionPane() {
		todoTextField = new JTextField();
		
		Object[] message = {
            "Todo :", todoTextField
        };
		
		todoOptionPane = JOptionPane.showConfirmDialog(null, message, "Add todo", JOptionPane.OK_CANCEL_OPTION);
	
        return todoOptionPane;
	}
	
	public int openAddGroupOptionPane() {
		groupNameTextField = new JTextField();
		groupContactsListModel = new DefaultListModel<String>();
		groupContactsList = new JList<String>(groupContactsListModel);
		groupContactsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		groupContactsList.setSelectionModel(new DefaultListSelectionModel() {
            public void setSelectionInterval(int index0, int index1) {
                if(isSelectedIndex(index0)) {
                    this.removeSelectionInterval(index0, index1);
                } else {
                    this.addSelectionInterval(index0, index1);
                }
            }
        });
		
		groupContactsScrollPane = new JScrollPane(groupContactsList);
		groupContactsScrollPane.setBounds(12, 13, 100, 50);
		
		ArrayList<Contact> contacts = adf.getContactDAO().getAll();
        for (Contact contact : contacts) {
        	groupContactsListModel.addElement(contact.getUsername());
        }
		
		Object[] message = {
            "Name :", groupNameTextField,
            "Contacts", groupContactsScrollPane,
        };
		
		groupOptionPane = JOptionPane.showConfirmDialog(null, message, "Add group", JOptionPane.OK_CANCEL_OPTION);
	
        return groupOptionPane;
	}
	
	public int openAddContactOptionPane() {
		contactUsernameTextField = new JTextField();
		contactFirstnameTextField = new JTextField();
		contactLastnameTextField = new JTextField();
		contactPhoneTextField = new JTextField();
		contactMailTextField = new JTextField();
		
		Object[] message = {
            "Username :", contactUsernameTextField,
            "Firstname :", contactFirstnameTextField,
            "Lastname :", contactLastnameTextField,
            "Phone :", contactPhoneTextField,
            "Mail :", contactMailTextField,
        };
		
		contactOptionPane = JOptionPane.showConfirmDialog(null, message, "Add user", JOptionPane.OK_CANCEL_OPTION);
	
        return contactOptionPane;
	}
	
	public void openErrorDialog(String error) {
		JOptionPane.showMessageDialog(null, error);
	}
	
	private void addMenuBar(){
        menuBar = new JMenuBar();

        fileMenu = new JMenu("Fichier");
        helpMenu = new JMenu("Aide");

        exitButton = new JMenuItem("Fermer");
        proposButton = new JMenuItem("A propos");

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        fileMenu.add(exitButton);
        helpMenu.add(proposButton);

        frame.setJMenuBar(menuBar);
    }

    public void proposWindow(){
        String textMsg = "" +
                "<html><div style='text-align: center;'>" +
                "Cette Application a été développée par :" +
                "<br>" +
                "<br> Guillaume GIERAK " +
                "<br> et " +
                "<br> Alexis GEROME " +
                "<br>" +
                "<br> dans le cadre d'un projet ETNA " +
                "<br> promotion 2019" +
                "</div></html>";
        JOptionPane.showMessageDialog(frame, textMsg, "A Propos", JOptionPane.PLAIN_MESSAGE);
    }
    
    //Other
    public JFrame getFrame(){
        return frame;
    }
    
    public JMenuItem getExitButton(){
        return exitButton;
    }

    public JMenuItem getProposButton(){
        return proposButton;
    }
    
    //Project Getter
    public JButton getAddProjectButton() {
    	return addProjectButton;
    }
    
    public JButton getDeleteProjectButton() {
    	return deleteProjectButton;
    }
    
    public DefaultListModel<String> getProjectsListModel() {
    	return projectsListModel;
    }
    
    public JList<String> getProjectsList() {
    	return projectsList;
    }
    public JTextPane getProjectDetailLabel() {
    	return projectDetailLabel;
    }
    
    //--Groups
    public DefaultListModel<String> getProjectSelectedGroupsListModel() {
		return projectSelectedGroupsListModel;
	}

	public JList<String> getProjectSelectedGroupsList() {
		return projectSelectedGroupsList;
	}

	public JScrollPane getProjectSelectedGroupsScrollPane() {
		return projectSelectedGroupsScrollPane;
	}
    
    //--Files
    public DefaultListModel<String> getFilesListModel() {
    	return filesListModel;
    }

	public JList<String> getFilesList() {
    	return filesList;
    }
    //--Events
    public DefaultListModel<String> getEventsListModel() {
    	return eventsListModel;
    }
    
    public JList<String> getEventsList() {
    	return eventsList;
    }
    //--Todos
    public DefaultListModel<String> getTodosListModel() {
    	return todosListModel;
    }
    
    public JList<String> getTodosList() {
    	return todosList;
    }
    
    public JButton getAddTodoButton() {
    	return addTodoButton;
    }
    
    public JButton getDeleteTodoButton() {
    	return deleteTodoButton;
    }
    
    public JTextField getTodoTextField() {
    	return todoTextField;
    }
    
    //Project Wizard
    public JTextField getProjectNameTextField() {
    	return projectNameTextField;
    }
    
    public JTextField getProjectDetailTextField() {
    	return projectDetailTextField;
    }
    
    public JList<String> getProjectGroupsList() {
		return projectGroupsList;
	}
    
    //Group Getter
    public JButton getAddGroupButton() {
    	return addGroupButton;
    }
    
    public JButton getDeleteGroupButton() {
    	return deleteGroupButton;
    }
    
    public DefaultListModel<Group> getGroupsListModel() {
    	return groupsListModel;
    }
    
    public JList<Group> getGroupsList() {
    	return groupsList;
    }
    
    //Group Wizard
    public JTextField getGroupNameTextField() {
    	return groupNameTextField;
    }
    
    public JList<String> getGroupContactsList() {
		return groupContactsList;
	}
    
    
    //Contact Getter
    public JButton getAddContactButton() {
    	return addContactButton;
    }
    
    public JButton getDeleteContactButton() {
    	return deleteContactButton;
    }
    
    public DefaultListModel<Contact> getContactsListModel() {
    	return contactsListModel;
    }
    
    public JList<Contact> getContactsList() {
    	return contactsList;
    }

    //Contact Wizard
	public JTextField getContactUsernameTextField() {
		return contactUsernameTextField;
	}
	
	public JTextField getContactFirstnameTextField() {
		return contactFirstnameTextField;
	}

	public JTextField getContactLastnameTextField() {
		return contactLastnameTextField;
	}

	public JTextField getContactPhoneTextField() {
		return contactPhoneTextField;
	}

	public JTextField getContactMailTextField() {
		return contactMailTextField;
	}
}
