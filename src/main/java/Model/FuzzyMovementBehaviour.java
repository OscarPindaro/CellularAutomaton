package Model;

import jfml.FuzzyInferenceSystem;
import jfml.compatibility.ImportMatlab;
import processing.core.PVector;

import java.io.IOException;

public class FuzzyMovementBehaviour extends MovementBehaviour {

    public FuzzyMovementBehaviour() throws IOException {
        ImportMatlab fis= new ImportMatlab();
        FuzzyInferenceSystem fs = fis.importFuzzySystem(getClass().getResource("fuzzyControllers/cellular.fis").getPath());

    }


    @Override
    public PVector calculateSpeed(PVector oldSpeed) {
        return null;
    }


}
