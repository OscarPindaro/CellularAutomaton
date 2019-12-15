package View;

import Controller.MovementController;
import Model.*;
import processing.core.PApplet;

public class Automata extends PApplet {

    private static  Automata mySketch;
    private int width = 500;
    private  int height = 500;
    private MovementController movementController;
    private Model model;
    private final int NUM_OF_PREDATORS = 20;
    private final int NUM_OF_PREYS = 20;
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
        movementController.updateAllEntitiesMovement();
        p.setXPosition(mouseX);
        p.setYposition(mouseY);
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
        model.addEntities(EntityFactory.createRandomPredators(mySketch.NUM_OF_PREDATORS));
        model.addEntities(EntityFactory.createPreys(mySketch.NUM_OF_PREYS));
        mySketch.movementController = new MovementController(model);
        PApplet.runSketch(appletArgs, mySketch );
    }

    public static void main(String[] args) {
        String[] appletArgs= new String[]{Automata.class.getName()};
        Automata mySketch= getInstance();
        Model model = new Model(mySketch.width, mySketch.height);
        mySketch.model = model;
        p = new Prey(480,480, 1,1);
        model.addEntity(p);
        try{
            model.addEntity(new FuzzyPredator(10, 10, 1, 1, p));
        }
        catch(Exception e){
            System.out.println("successa roba brutta");
            System.out.println(e.getMessage());
        }
        mySketch.movementController = new MovementController(model);
        PApplet.runSketch(appletArgs, mySketch );
    }
}
