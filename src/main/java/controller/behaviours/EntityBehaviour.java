package controller.behaviours;

import controller.action.ActionExecutor;
import controller.action.ActionExecutorInterface;
import model.genetic.Function;

import java.util.List;

public interface EntityBehaviour {

    void makeDecisions(ActionExecutorInterface executor);

}
