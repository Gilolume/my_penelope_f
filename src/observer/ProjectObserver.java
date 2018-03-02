package observer;

import objects.Project;

public interface ProjectObserver {
	public void newProjectEvent(ProjectObservable o, Project project, Object arg);
}
