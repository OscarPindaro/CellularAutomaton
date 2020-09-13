package controller.behaviours;

import model.entity.Predator;
import model.genetic.Function;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PredatorBehaviour extends AbstractBehaviour{

    private final List<Predator> predatorList = new LinkedList<>();

    private final Map<Predator, List<Function>> predatorDecisionFunctions;

}
