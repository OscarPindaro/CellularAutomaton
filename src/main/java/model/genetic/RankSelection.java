package model.genetic;

import java.util.List;
import java.util.Random;

public class RankSelection implements SelectionInterface{

    /**
     * The array is already ordered from lowest to highest
     * @param individuals
     * @return
     */
    @Override
    public Individual extractIndividual(List<Individual> individuals) {
        int gaussianSum = individuals.size()*(individuals.size()+1)/2;
        int selectionValue = new Random().nextInt(gaussianSum);
        int sum= 0;
        int pos= 0;
        while( sum <= selectionValue){
            pos++;
            sum += pos;
        }

        return individuals.get(pos-1);
    }
}
