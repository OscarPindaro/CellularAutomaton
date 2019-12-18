package View;

import Controller.FuzzyPredatorMovementBehaviour;
import Controller.MovementBehaviour;
import Controller.MovementController;
import Controller.RandomMovementBehaviour;
import Model.*;
import processing.core.PApplet;

import java.io.IOException;

public class Automata extends PApplet {

    private static  Automata mySketch;
    private int width = 500;
    private  int height = 500;
    private MovementController movementController;
    private Model model;
    private final int NUM_OF_PREDATORS = 1;
    private final int NUM_OF_PREYS = 100;
    public static Prey p;

    public Automata(){
        super();
    }
    public static Automata getInstance(){
        if(mySketch == null){
            mySketch = new Automata();
        }
        return mySketch;
    }

    public void settings(){
        size(width,height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void draw(){
        background(51);
        movementController.updateAllEntitiesSpeed();
        movementController.updateAllEntitiesPosition();
        for(Entity entity: model.getEntities()){
            entity.show();
        }
        textSize(26);
        fill(255, 0, 255);
        text(frameRate, 0,26);

    }

    public static void main1(String[] args) {
        String[] appletArgs= new String[]{Automata.class.getName()};
        Automata mySketch= getInstance();
        Model model = new Model(mySketch.width, mySketch.height);
        mySketch.model = model;
        setModelToFactory();
        MovementBehaviour randomMovementBehaviour = new RandomMovementBehaviour();
        String path = Automata.class.getResource("/fuzzyControllers/cellular.fis").getPath();
        MovementBehaviour fuzzyBehaviour;
        try{
            fuzzyBehaviour = new FuzzyPredatorMovementBehaviour(mySketch.model, path);
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            fuzzyBehaviour = new RandomMovementBehaviour();
        }
        EntityFactory.createRandomPredators(mySketch.NUM_OF_PREDATORS, fuzzyBehaviour);
        EntityFactory.createRandomPreys(mySketch.NUM_OF_PREYS, randomMovementBehaviour);
        setMovementController(mySketch, model);
        mySketch.movementController.addMovementBehaviour(randomMovementBehaviour);
        mySketch.movementController.addMovementBehaviour(fuzzyBehaviour);
        PApplet.runSketch(appletArgs, mySketch );
    }

    private static void setMovementController(Automata mySketch, Model model){
        mySketch.movementController = new MovementController(model);
    }

    private static void setModelToFactory(){
        EntityFactory.model = mySketch.model;
    }


    public static void main(String[] args) {
        String[] appletArgs= new String[]{Automata.class.getName()};
        Automata mySketch= getInstance();
        Model model = new Model(mySketch.width, mySketch.height);
        mySketch.model = model;
        setModelToFactory();
        MovementBehaviour randomMovementBehaviour = new RandomMovementBehaviour();
        String path = Automata.class.getResource("/fuzzyControllers/cellular.fis").getPath();
        EntityFactory.createRandomPreys(mySketch.NUM_OF_PREYS, randomMovementBehaviour);
        setMovementController(mySketch, model);
        assignFuzzyController(path);
        mySketch.movementController.addMovementBehaviour(randomMovementBehaviour);
        PApplet.runSketch(appletArgs, mySketch );
    }

    private static void assignFuzzyController(String path){
        MovementBehaviour fuzzyBehaviour;
        for(int i = 0; i < 3; i++){
            try{
                fuzzyBehaviour = new FuzzyPredatorMovementBehaviour(mySketch.model, path);
            }
            catch (IOException ioe){
                System.out.println(ioe.getMessage());
                fuzzyBehaviour = new RandomMovementBehaviour();
            }
            EntityFactory.createRandomPredators(mySketch.NUM_OF_PREDATORS, fuzzyBehaviour);
            mySketch.movementController.addMovementBehaviour(fuzzyBehaviour);
        }
    }
}
