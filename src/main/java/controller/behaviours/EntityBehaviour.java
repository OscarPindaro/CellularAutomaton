package controller.behaviours;

import controller.action.ActionExecutor;
import controller.action.ActionExecutorInterface;

public interface EntityBehaviour {

    void makeDecisions(ActionExecutorInterface executor);
}
