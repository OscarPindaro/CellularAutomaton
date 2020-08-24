package view;

import controller.*;
import controller.MovementBehaviours.MovementBehaviour;
import controller.MovementBehaviours.RandomMovementBehaviour;
import model.Model;
import processing.core.PApplet;

import java.util.Random;

public class Automata extends PApplet {

    private static  Automata mySketch;
    private final
    static Object lock = new Object();

    //parameters
    private int width = 1000;
    private  int height = 1000;
    private final int NUM_OF_PREDATORS = 100;
    private final int NUM_OF_PREYS = 100;

    //controller
    private Controller controller;

    //model
    private Model model;

    //view
    private EntityDrawer entityDrawer;

    public Automata(){
        super();
    }

    public static Automata getInstance(){
        synchronized (lock){
            if(mySketch == null){
                mySketch = new Automata();
            }
            return mySketch;
        }
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
        controller.update();
        entityDrawer.drawEntities();
        textSize(26);
        fill(255, 0, 255);
        text(frameRate, 0,26);

    }

    public static void main(String[] args) {
        String[] appletArgs= new String[]{Automata.class.getName()};
        Automata mySketch= getInstance();
        createModel();
        assignController();
        mySketch.controller.setUp(mySketch.NUM_OF_PREYS, mySketch.NUM_OF_PREDATORS);
        createEntityDrawer();
        PApplet.runSketch(appletArgs, mySketch );
    }

    private static void createModel(){
        Model model = new Model(mySketch.width, mySketch.height);
        mySketch.model = model;
    }

    private static void assignController(){
        mySketch.controller = new Controller(mySketch.model);
    }

    private static void createEntityDrawer(){
        mySketch.entityDrawer = new EntityDrawer(mySketch.model);
    }

}
