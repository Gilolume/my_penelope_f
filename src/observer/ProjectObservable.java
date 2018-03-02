package observer;

import objects.Project;

public interface ProjectObservable {
	public void addProjectObserver(ProjectObserver o);
	public void deleteProjectObserver(ProjectObserver o);
	public void notifyProjectObservers(Project project, Object arg);
}
