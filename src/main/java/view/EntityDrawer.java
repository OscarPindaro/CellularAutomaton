package view;

import model.*;
import model.entity.Entity;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDrawer {
    private final Model model;

    public EntityDrawer(Model m){
        model = m;
    }

    public void drawEntities(){
        Automata mySketch = Automata.getInstance();
        List<Entity> entities = model.getEntities();
        //only live entities are drawn
        entities = entities.stream().filter(e -> !e.isDead()).collect(Collectors.toList());
        for (Entity e : entities){
            float xpos = e.getPosition().x;
            float ypos = e.getPosition().y;
            float size = e.getEntityRadius();
            Color color = e.getColor();
            mySketch.noStroke();
            mySketch.fill(color.getRed(), color.getGreen(), color.getBlue());
            mySketch.ellipse(xpos, ypos, size, size);
        }
    }
}
