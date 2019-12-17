package Model;

import Controller.MovementBehaviour;
import jfml.FuzzyInferenceSystem;
import jfml.compatibility.ImportMatlab;
import processing.core.PVector;

import java.io.IOException;

public class FuzzyMovementBehaviour  {

    public FuzzyMovementBehaviour() throws IOException {
        ImportMatlab fis= new ImportMatlab();
        FuzzyInferenceSystem fs = fis.importFuzzySystem(getClass().getResource("fuzzyControllers/cellular.fis").getPath());

    }


    public PVector calculateSpeed(PVector oldSpeed) {
        return null;
    }


}
