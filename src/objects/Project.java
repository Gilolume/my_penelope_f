package objects;

import java.io.File;
import java.util.ArrayList;

public class Project {
	private String name;
	private String detail;
    private ArrayList<Group> groups;
    private ArrayList<String> todos;
    private ArrayList<String> events;
    //TODO ArrayList d'object Files est non String
    private ArrayList<String> files;

    /*
     * Object Project à completer
     * Avec events, files, ...
     * */
    
    public Project(String name, String detail) {
        this.name = name;
        this.detail = detail;
        this.groups = new ArrayList<Group>();
        this.todos = new ArrayList<String>();
        this.events = new ArrayList<String>();
        this.files = new ArrayList<String>();
    }
    
    public Project(String name, String detail, ArrayList<Group> groups) {
        this.name = name;
        this.detail = detail;
        this.groups = groups;
        this.todos = new ArrayList<String>();
        this.events = new ArrayList<String>();
        this.files = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDetail() {
    	return detail;
    }
    
    public void setDetail(String detail) {
    	this.detail = detail;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> group) {
        this.groups = group;
    }
    
    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void deleteGroup(Group group) {
        this.groups.remove(group);
    }
    
    public ArrayList<String> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<String> todos) {
        this.todos = todos;
    }
    
    public void addTodo(String todo) {
        this.todos.add(todo);
    }

    public void deleteTodo(String todo) {
        this.todos.remove(todo);
    }
    
    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }
    
    public void addEvent(String event) {
        this.events.add(event);
    }

    public void deleteEvent(String event) {
        this.events.remove(event);
    }
    
    public ArrayList<String> getFiles() {
    	return files;
    }
    
    public void setFiles(ArrayList<String> files) {
    	this.files = files;
    }
    
    public void addFile(String filename) {
        this.files.add(filename);
    }
    
    public void deleteFile(String filename) {
        this.files.remove(filename);
    }

	@Override
	public String toString() {
		return "Project [name=" + name + ", detail=" + detail + ", groups=" + groups + ", todos=" + todos + ", events="
				+ events + ", files=" + files + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((todos == null) ? 0 : todos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (todos == null) {
			if (other.todos != null)
				return false;
		} else if (!todos.equals(other.todos))
			return false;
		return true;
	}

	public void createWorkingDirectory() {
		String path = "./data/" + name;
		File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory " + path + " is created !");
            } else {
                System.out.println("Failed to create directory " + path + " !");
            }
        }
	}
	
	public void deleteWorkingDirectory() {
		String path = "./data/" + name;
		File directoryToBeDeleted = new File(path);
		deleteDirectory(directoryToBeDeleted);
	}
	
	private void deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    directoryToBeDeleted.delete();
	}
}
